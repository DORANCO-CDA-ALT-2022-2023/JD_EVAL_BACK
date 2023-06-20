package fr.doranco.entities;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "utilisateur")
@Getter
@Setter
public class Utilisateur {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nom;

  private String prenom;

  @Column(name = "date_naissance")
  private Date dateNaissance;

  private boolean isActif;

  private String profil;

  private String email;

  private String password;

  private String telephone;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "utilisateur_id")
  private List<Adresse> adresses;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "utilisateur_id")
  private List<Commande> commandes;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "utilisateur_id")
  private List<CartePaiement> cartesPaiement;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "utilisateur_id")
  private List<Commentaire> commentaires;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "utilisateur_id")
  private List<ArticlePanier> panier;

}
