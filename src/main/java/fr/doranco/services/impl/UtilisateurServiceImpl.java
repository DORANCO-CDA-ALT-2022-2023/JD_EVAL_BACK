package fr.doranco.services.impl;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Cookie;
import fr.doranco.cryptage.impl.PasswordHasher;
import fr.doranco.dao.ISuperDao;
import fr.doranco.dao.impl.UtilisateurDaoImpl;
import fr.doranco.dto.utilisateur.ResponseAuthDto;
import fr.doranco.dto.utilisateur.SignUpDto;
import fr.doranco.dto.utilisateur.logInDto;
import fr.doranco.entities.Utilisateur;
import fr.doranco.entities.enumeration.ProfilUtilisateurEnum;
import fr.doranco.exception.ErrorException;
import fr.doranco.services.ServiceAbsctract;

public class UtilisateurServiceImpl extends ServiceAbsctract {

  ISuperDao<Utilisateur> dao = new UtilisateurDaoImpl();


  /**
   * @param dto
   * @return
   * @throws ErrorException
   */
  public ResponseAuthDto createCompte(SignUpDto dto) throws ErrorException {
    Set<ConstraintViolation<Object>> violations = this.validator.validate(dto);

    try {
      if (!violations.isEmpty()) {
        LOGGER.atWarn().log("VIOLATION : {}", this.getMessagesViolation(dto, violations));
        throw new ErrorException(400, this.getMessagesViolation(dto, violations));
      }
      dao.create(new Utilisateur(dto.getNom(),
                                 dto.getPrenom(),
                                 dto.getDateNaissance(),
                                 ProfilUtilisateurEnum.CUSTOMER.getProfil(),
                                 dto.getEmail(),
                                 PasswordHasher.getHash(dto.getPassword()),
                                 dto.getTelephone()));
    } catch (ErrorException e) {
      // return new ResponseAuthDto(e.getMessage());
      throw new ErrorException(e.getCode(), e.getMessage());
    }

    return new ResponseAuthDto("Utilisateur avec email " + dto.getEmail() + " est enregistré!", null);
  }


  /**
   * @param dto
   * @return
   * @throws ErrorException
   */
  public ResponseAuthDto authentification(logInDto dto) throws ErrorException, Exception {
    Set<ConstraintViolation<Object>> violations = this.validator.validate(dto);

    Utilisateur u = null;

    try {
      if (!violations.isEmpty()) {
        LOGGER.atWarn().log("VIOLATION : {}", this.getMessagesViolation(dto, violations));
        throw new ErrorException(400, this.getMessagesViolation(dto, violations));
      }

      u = dao.getByField(dto.getEmail());

      if (u == null) {
        throw new ErrorException(204, "Utilisateur n'existe pas, voulez vous créer le compte ?");
      }

      if (!PasswordHasher.verifyPassword(dto.getPassword(), u.getPassword())) {
        throw new ErrorException(401, "Mot de passe ou e-mail sont incorrectes");
      }



    } catch (ErrorException e) {
      // return new ResponseAuthDto(e.getMessage());
      throw new ErrorException(e.getCode(), e.getMessage());
    } catch (Exception e) {
      throw new ErrorException(500, e.getMessage());
    }

    return ResponseAuthDto.builder()
                          .message("Bienvenue " + u.getNom() + " !")
                          .cookieWithJwt(getCookieWithJWT(u))
                          .build();
  }


  public Cookie getCookieWithJWT(Utilisateur u) {
    Cookie cookie = new Cookie("accessToken",
                               JwtService.generateToken(Long.toString(u.getId()),
                                                        u.getProfil()));
    LOGGER.atInfo().log("JWT from Cookie :: {}", cookie.getValue());

    return cookie;
  }

}


