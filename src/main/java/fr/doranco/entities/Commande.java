package fr.doranco.entities;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "commande")
@Getter
@Setter
public class Commande {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String numero;

  @Column(name = "date_creation")
  private Date dateCreation;

  @Column(name = "date_livraison")
  private Date dateLivraison;

  @Column(name = "total_remise")
  private double totalRemise;

  @Column(name = "frais_expedition")
  private double fraisExpedition;

  @Column(name = "total_general")
  private double totalGeneral;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "adresse_facturation_id")
  private Adresse adresseFacturation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "adresse_livraison_id")
  private Adresse adresseLivraison;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "carte_paiement_defaut_id")
  private CartePaiement cartePaiementDefaut;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "utilisateur_id")
  private Utilisateur utilisateur;

  @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<LigneCommande> lignesCommande;

}
