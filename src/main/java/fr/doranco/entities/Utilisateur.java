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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "utilisateur")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@NamedQueries({
               @NamedQuery(
                   name = "Utilisateur:findByEmail",
                   query = "FROM Utilisateur u WHERE u.email = :email"),
               @NamedQuery(
                   name = "Utilisateur:findByRole",
                   query = "FROM Utilisateur u WHERE u.profil = :profil"),
               @NamedQuery(
                   name = "Utilisateur:findAll",
                   query = "FROM Utilisateur u"),
})
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

  @Column(unique = true)
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

  public Utilisateur(String nom, String prenom, Date dateNaissance, String profil, String email,
                     String password, String telephone) {
    super();
    this.nom = nom;
    this.prenom = prenom;
    this.dateNaissance = dateNaissance;
    this.profil = profil;
    this.email = email;
    this.password = password;
    this.telephone = telephone;
  }



}
