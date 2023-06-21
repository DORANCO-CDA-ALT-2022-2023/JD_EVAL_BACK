package fr.doranco.dao.impl;

import java.util.List;
import javax.persistence.PersistenceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import fr.doranco.dao.DaoAbstract;
import fr.doranco.dao.ISuperDao;
import fr.doranco.entities.Utilisateur;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UtilisateurDaoImpl extends DaoAbstract
                                implements ISuperDao<Utilisateur> {

  private static final Logger LOGGER = LogManager.getLogger(UtilisateurDaoImpl.class);

  @Override
  public void create(Utilisateur t) {
    try {
      this.session = this.getSessions();
      this.transaction = this.beginTransaction();

      this.session.save(t);

      this.transaction.commit();
    } catch (Exception e) {
      this.transaction.rollback();
      LOGGER.atError().log("DAO Utilisateur --ROLLBACK {}", e.getMessage());
      throw new PersistenceException("Erreur pendant de création d'un utilisateur");
    } finally {
      if (this.session != null && this.session.isOpen()) {
        this.session.close();
      }
    }

  }

  @Override
  public Utilisateur getById(Long id) {
    this.session = this.getSessions();

    Utilisateur utilisateurFromDB = this.session.get(Utilisateur.class, id);

    this.session.close();

    return utilisateurFromDB;

  }

  @Override
  public Utilisateur getByField(String field) {

    try {
      this.session = this.getSessions();

      Query<Utilisateur> query = session.createNamedQuery("Utilisateur:findByEmail", Utilisateur.class);

      query.setParameter("email", field);

      return query.uniqueResult();

    } catch (Exception e) {
      LOGGER.atError().log("DAO Utilisateur getByField {}", e.getMessage());
      throw new PersistenceException("Erreur pendant de recuperation par Email");
    } finally {
      if (this.session != null && this.session.isOpen()) {
        this.session.close();
      }
    }

  }

  @Override
  public void update(Utilisateur t) {
    try {
      this.session = this.getSessions();
      this.transaction = this.beginTransaction();

      this.session.update(t);

      this.transaction.commit();
    } catch (Exception e) {
      if (this.transaction != null) {
        this.transaction.rollback();
        LOGGER.atError().log("DAO Utilisateur update --ROLLBACK {}", e.getMessage());
      }
      throw new PersistenceException("Erreur pendant de update d'un utilisateur avec id : " + t.getId());
    } finally {
      if (this.session != null && this.session.isOpen()) {
        this.session.close();
      }
    }

  }

  @Override
  public void delete(Utilisateur t) {
    try {
      this.session = this.getSessions();
      this.transaction = this.beginTransaction();

      this.session.delete(t);

      this.transaction.commit();
    } catch (Exception e) {
      if (this.transaction != null) {
        this.transaction.rollback();
        LOGGER.atError().log("DAO Utilisateur delete --ROLLBACK {}", e.getMessage());
      }
      throw new PersistenceException("Erreur pendant de delete d'un utilisateur avec id : " + t.getId());
    } finally {
      if (this.session != null && this.session.isOpen()) {
        this.session.close();
      }
    }

  }


  @Override
  public List<Utilisateur> getAll() {
    try {
      this.session = this.getSessions();

      Query<Utilisateur> query = session.createNamedQuery("Utilisateur:findAll", null);

      return query.getResultList();

    } catch (Exception e) {
      LOGGER.atError().log("DAO Utilisateur findAll {}", e.getMessage());
      throw new PersistenceException("Erreur pendant de recuperation des utilisateurs");
    } finally {
      if (this.session != null && this.session.isOpen()) {
        this.session.close();
      }
    }

  }

}
