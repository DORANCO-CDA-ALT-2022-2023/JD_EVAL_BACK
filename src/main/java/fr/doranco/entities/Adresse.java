package fr.doranco.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "adresse")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Adresse {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String numero;

  private String rue;

  private String ville;

  @Column(name = "code_postal")
  private String codePostal;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "utilisateur_id")
  private Utilisateur utilisateur;

}
