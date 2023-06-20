package fr.doranco.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "carte_paiement")
@Getter
@Setter
public class CartePaiement {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nom_proprietaire")
  private String nomProprietaire;

  @Column(name = "prenom_proprietaire")
  private String prenomProprietaire;

  @Lob
  private byte[] numero;

  @Column(name = "date_fin_validite")
  private Date dateFinValidite;

  @Lob
  private byte[] cryptogramme;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "utilisateur_id")
  private Utilisateur utilisateur;

}
