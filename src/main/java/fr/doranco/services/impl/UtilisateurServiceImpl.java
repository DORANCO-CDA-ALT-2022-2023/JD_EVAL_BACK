package fr.doranco.services.impl;

import java.util.Set;
import javax.validation.ConstraintViolation;
import fr.doranco.cryptage.impl.PasswordHasher;
import fr.doranco.dao.ISuperDao;
import fr.doranco.dao.impl.UtilisateurDaoImpl;
import fr.doranco.dto.utilisateur.ResponseAuthDto;
import fr.doranco.dto.utilisateur.SignUpDto;
import fr.doranco.entities.Utilisateur;
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
      dao.create(Utilisateur.builder()
                            .email(dto.getEmail())
                            .password(PasswordHasher.getHash(dto.getPassword()))
                            .build());
    } catch (ErrorException e) {
      // return new ResponseAuthDto(e.getMessage());
      throw new ErrorException(e.getCode(), e.getMessage());
    }

    return new ResponseAuthDto("Utilisateur avec email " + dto.getEmail() + " est enregistré!");
  }


  /**
   * @param dto
   * @return
   */
  public ResponseAuthDto authentification(SignUpDto dto) {

    return null;
  }

}


