package fr.doranco.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import javax.persistence.PersistenceException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import fr.doranco.dao.ISuperDao;
import fr.doranco.entities.Utilisateur;
import fr.doranco.entities.enumeration.ProfilUtilisateurEnum;

class UtilisateurDaoImplTest {

  private Utilisateur createMockUtilisateur() {
    Utilisateur user = new Utilisateur();
    user.setNom("John");
    user.setEmail("john@example.com");
    return user;
  }

  static ISuperDao<Utilisateur> daoUnderTest;
  static Utilisateur userMock1;
  static Utilisateur userMock2;
  private Utilisateur userMock = createMockUtilisateur();

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    daoUnderTest = new UtilisateurDaoImpl();
    userMock1 = Utilisateur.builder()
                           .nom("TestNom")
                           .prenom("TestPrenom")
                           .email("test1@test.fr")
                           .profil(ProfilUtilisateurEnum.ADMIN.getProfil())
                           .dateNaissance(null)
                           .build();

    userMock2 = Utilisateur.builder()
                           .nom("TestNom")
                           .prenom("TestPrenom")
                           .email("test2@test.fr")
                           .profil(ProfilUtilisateurEnum.ADMIN.getProfil())
                           .dateNaissance(null)
                           .build();

  }

  @Test
  void testCreateUtilisateur() {
    try {
      daoUnderTest.create(userMock1);
      daoUnderTest.create(userMock2);

      assertNotNull(userMock1.getId());
      assertNotNull(userMock2.getId());
      assertNotEquals(userMock1.getId(), userMock2.getId());
    } catch (PersistenceException e) {
      System.err.println(e.getMessage());
      this.testGetUtilisateurByEmail(userMock1.getEmail());
      this.testGetUtilisateurByEmail(userMock2.getEmail());
    }
  }

  @ParameterizedTest(name = "Get utilisateur avec id : {0}")
  @ValueSource(longs = {1, 2})
  void testGetUtilisateurById(long arg) {
    Utilisateur utilisateur = daoUnderTest.getById(arg);

    assertNotNull(utilisateur);
    assertEquals(utilisateur.getId(), arg);
  }

  @ParameterizedTest(name = "Get utilisateur avec email : {0}")
  @ValueSource(strings = {"test1@test.fr"})
  void testGetUtilisateurByEmail(String email) {
    Utilisateur utilisateur = daoUnderTest.getByField(email);

    assertNotNull(utilisateur);
    assertEquals(utilisateur.getEmail(), email);
  }

  @ParameterizedTest(name = "Update utilisateur | old email {0} new email {1}s")
  @CsvSource({"test1@test.fr,newemail@test.fr"})
  void testUpdateUtilisateur(String emailCriteria, String newEmail) {
    Utilisateur userFromDB = daoUnderTest.getByField(emailCriteria);

    assertNotNull(userFromDB);
    assertFalse(userFromDB.isActif());

    userFromDB.setEmail(newEmail);
    userFromDB.setNom("test");

    daoUnderTest.update(userFromDB);

    Utilisateur userFromDBAfterUpdate = daoUnderTest.getById(userFromDB.getId());

    assertNotNull(userFromDBAfterUpdate);
    assertEquals(newEmail, userFromDBAfterUpdate.getEmail());
    assertEquals("test", userFromDBAfterUpdate.getNom());
  }

  @Test
  void getAllUtilisateur() {
    List<Utilisateur> users = daoUnderTest.getAll();

    assertNotEquals(users.size(), 0);
  }

  @Test
  void deleteUser() {
    Utilisateur userMock = createMockUtilisateur();
    daoUnderTest.create(userMock);

    Long userId = userMock.getId();

    daoUnderTest.delete(userMock);

    assertNull(daoUnderTest.getById(userId));
  }

  @AfterAll
  static void tearDownAfterClass() throws Exception {
    assertTrue(userMock1.isActif());
  }


}
