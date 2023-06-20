package fr.doranco.dao.impl;

import java.util.List;
import javax.persistence.PersistenceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
      this.transaction = this.getTransaction();

      this.session.save(t);

      transaction.commit();
    } catch (Exception e) {
      this.transaction.rollback();
      LOGGER.atError().log("DAO Utilisateur {}", e.getMessage());
      throw new PersistenceException("Erreur pendant de création d'un utilisateur");
    } finally {
      if (this.session != null && this.session.isOpen()) {
        this.session.close();
      }
    }

  }

  @Override
  public Utilisateur getById(int id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Utilisateur getByField(String field) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void update(Utilisateur t) {
    // TODO Auto-generated method stub

  }

  @Override
  public void delete(Utilisateur t) {
    // TODO Auto-generated method stub

  }

  @Override
  public List<Utilisateur> getAll() {
    // TODO Auto-generated method stub
    return null;
  }

}
