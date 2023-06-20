package fr.doranco.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DaoFactory {
  private static final Logger LOGGER = LogManager.getLogger(DaoFactory.class);

  private static SessionFactory sf = null;

  private DaoFactory() {}

  static {
    try {
      sf = new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      LOGGER.atFatal().log("ERREUR PENDAT INITIALIZATION CONFIG Hibernate {}", ex.getMessage());
      throw new ExceptionInInitializerError(ex);
    }
  }

  public static SessionFactory getSessionFactory() {
    return sf;
  }
}
