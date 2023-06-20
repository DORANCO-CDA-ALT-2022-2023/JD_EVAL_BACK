package fr.doranco.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "utilisateur", propOrder = {
        "id",
        "nom",
        "prenom",
        "dateNaissance",
        "isActif",
        "profil",
        "email",
        "password",
        "telephone",
        "adresses",
        "commandes",
        "cartesDePaiement",
        "commentaires",
        "panier"
})

public class Utilisateur {
    @XmlElement(required = false)
    private Integer id;

    @XmlElement(required = true)
    private String nom;

    @XmlElement(required = true)
    private String prenom;

    private Date dateNaissance;

    private boolean isActif;

    private String profil;

    private String email;

    private String password;
    
    private String telephone;

    private List<Adresse> addresses;

    private List<Commande> commandes;

    private List<CartePaiement> cartesDePaiement;

    private List<Commentaire> commentaires;

    private List<ArticlePanier> panier;
}