package fr.doranco.services.impl;

import java.util.Set;
import javax.validation.ConstraintViolation;
import fr.doranco.cryptage.impl.PasswordHasher;
import fr.doranco.dao.ISuperDao;
import fr.doranco.dao.impl.UtilisateurDaoImpl;
import fr.doranco.dto.impl.ResponseAuthDto;
import fr.doranco.dto.impl.SignUpDto;
import fr.doranco.entities.Utilisateur;
import fr.doranco.services.ServiceAbsctract;

public class UtilisateurServiceImpl extends ServiceAbsctract {


  ISuperDao<Utilisateur> dao = new UtilisateurDaoImpl();

  public ResponseAuthDto signUp(SignUpDto dto) {
    Set<ConstraintViolation<Object>> violations = this.validator.validate(dto);

    ResponseAuthDto response =
                             this.handleViolations(dto,
                                                   violations,
                                                   () -> {
                                                     try {
                                                       dao.create(Utilisateur.builder()
                                                                             .email(dto.getEmail())
                                                                             .password(PasswordHasher.getHash(dto.getPassword()))
                                                                             .build());

                                                       return new ResponseAuthDto("Utilisateur avec: " +
                                                                                  dto.getEmail() +
                                                                                  " est créé !");
                                                     } catch (Exception e) {
                                                       return new ResponseAuthDto(e.getMessage());
                                                     }
                                                   },
                                                   errorResponse -> new ResponseAuthDto(errorResponse));

    return response;
  }


  public ResponseAuthDto login(SignUpDto dto) {

    Set<ConstraintViolation<Object>> violations = this.validator.validate(dto);

    ResponseAuthDto response =
                             this.handleViolations(dto,
                                                   violations,
                                                   () -> {
                                                     try {
                                                       dao.create(Utilisateur.builder()
                                                                             .email(dto.getEmail())
                                                                             .password(PasswordHasher.getHash(dto.getPassword()))
                                                                             .build());

                                                       return new ResponseAuthDto("Utilisateur avec: " +
                                                                                  dto.getEmail() +
                                                                                  " est créé !");
                                                     } catch (Exception e) {
                                                       return new ResponseAuthDto(e.getMessage());
                                                     }
                                                   },
                                                   errorResponse -> new ResponseAuthDto(errorResponse));

    return response;
  }

}


