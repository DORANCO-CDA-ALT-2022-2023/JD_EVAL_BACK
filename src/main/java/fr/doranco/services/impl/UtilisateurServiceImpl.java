package fr.doranco.services.impl;

import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Cookie;
import fr.doranco.cryptage.impl.PasswordHasher;
import fr.doranco.dao.impl.UtilisateurDaoImpl;
import fr.doranco.dto.utilisateur.ResponseAuthDto;
import fr.doranco.dto.utilisateur.SignUpDto;
import fr.doranco.dto.utilisateur.logInDto;
import fr.doranco.entities.Utilisateur;
import fr.doranco.entities.enumeration.ProfilUtilisateurEnum;
import fr.doranco.exception.ErrorException;
import fr.doranco.services.ServiceAbsctract;

public class UtilisateurServiceImpl extends ServiceAbsctract {

  UtilisateurDaoImpl dao = new UtilisateurDaoImpl();
  MailService ms = new MailService();


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
      ms.sendEmail(dto.getEmail(),
                   "Bienvenu cher nous !",
                   "Il faut penser à valider votre compte ..." +
                   "¯\\_(ツ)_/¯");
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


  /**
   * @param role
   * @return
   * @throws ErrorException
   */
  public List<Utilisateur> getAllUser(String role) throws ErrorException {

    List<Utilisateur> liste = null;

    try {
      if (role == null) {
        LOGGER.atWarn().log("role : {}", role);
        liste = dao.getAll();
        return liste;
      }

      if (!(role.equals(ProfilUtilisateurEnum.ADMIN.getProfil()) ||
            role.equals(ProfilUtilisateurEnum.STOREKEEPER.getProfil()) ||
            role.equals(ProfilUtilisateurEnum.CUSTOMER.getProfil()))) {
        LOGGER.atWarn().log("role : {}", role);
        throw new ErrorException(400, "Rôle n'est pas correct !");
      }

      liste = dao.getAllByRole(role);

      if (liste == null || liste.size() == 0) {
        LOGGER.atWarn().log("liste : {}", liste.toArray());
        throw new ErrorException(204, "Pas de utilisateurs");
      }

    } catch (ErrorException e) {
      // return new ResponseAuthDto(e.getMessage());
      LOGGER.atError().log("ErrorException : {}", e.getMessage());
      throw new ErrorException(e.getCode(), e.getMessage());
    } catch (Exception e) {
      LOGGER.atError().log("Exception : {}", e.getMessage());
      throw new ErrorException(500, e.getMessage());
    }

    return liste;

  }


  /**
   * @param u
   * @return
   */
  private Cookie getCookieWithJWT(Utilisateur u) {
    Cookie cookie = new Cookie("accessToken",
                               JwtService.generateToken(Long.toString(u.getId()),
                                                        u.getProfil()));
    LOGGER.atInfo().log("JWT from Cookie :: {}", cookie.getValue());

    return cookie;
  }



}


