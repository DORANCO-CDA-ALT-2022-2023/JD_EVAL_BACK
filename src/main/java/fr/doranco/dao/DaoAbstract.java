package fr.doranco.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class DaoAbstract {
  
  protected Session session;

  protected Transaction transaction;

  protected Session getSessions() {
    return DaoFactory.getSessionFactory().openSession();
  }

  protected Transaction getTransaction() {
    return this.getSessions().beginTransaction();
  }
}
