package fr.doranco.entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "article")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String description;

    private double prix;

    @Column(columnDefinition = "DECIMAL(5, 2) DEFAULT 0.00")
    private double remise;

    private int stock;

    @Column(name = "is_vendable")
    private boolean isVendable;
    
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @ElementCollection
    @CollectionTable(name = "article_photos")
    private List<String> photos;

    @ElementCollection
    @CollectionTable(name = "article_videos")
    private List<String> videos;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commentaire> commentaires;

}
