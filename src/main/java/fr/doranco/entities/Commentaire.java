package fr.doranco.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import fr.doranco.entities.enumeration.NoteCommentaireEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "commentaire")
@Getter
@Setter
public class Commentaire {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String texte;

  @Enumerated(EnumType.ORDINAL)
  private NoteCommentaireEnum note;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "article_id")
  private Article article;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "utilisateur_id")
  private Utilisateur utilisateur;

  
}
