package fr.doranco.dao.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import fr.doranco.entities.Utilisateur;

class UtilisateurDaoImplTest {

  static UtilisateurDaoImpl daoUnderTest;
  static Utilisateur userMock;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    daoUnderTest = new UtilisateurDaoImpl();
    userMock = Utilisateur.builder()
                          .nom("TestNom")
                          .prenom("TestPrenom")
                          .email(null)
                          .adresses(null)
                          .cartesPaiement(null)
                          .commandes(null)
                          .commentaires(null)
                          .dateNaissance(null)
                          .panier(null)
                          .build();

  }

  @Test
  void test() {
    daoUnderTest.create(userMock);
    System.out.println(userMock);

    assertNotNull(userMock.getId());
  }

}
