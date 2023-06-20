package fr.doranco.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DaoFactoryTest {
  private static SessionFactory sessionFactory = null;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    sessionFactory = DaoFactory.getSessionFactory();
  }

  @Test
  void test() {
    assertNotNull(sessionFactory);
    assertTrue(sessionFactory instanceof SessionFactory,
               "SessionFactory should be an instance of SessionFactory");
  }

}
