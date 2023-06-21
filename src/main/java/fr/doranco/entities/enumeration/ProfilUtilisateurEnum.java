package fr.doranco.entities.enumeration;

public enum ProfilUtilisateurEnum {
                                   ADMIN(1, "Admin"),
                                   STOREKEEPER(2, "Magasinier"),
                                   CUSTOMER(3, "Client");


  private final int id;
  private final String value;

  ProfilUtilisateurEnum(int id, String value) {
    this.id = id;
    this.value = value;
  }

  public int getId() {
    return id;
  }

  public String getProfil() {
    return value;
  }

}
