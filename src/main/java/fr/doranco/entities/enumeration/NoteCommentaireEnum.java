package fr.doranco.entities.enumeration;

public enum NoteCommentaireEnum {
                                 UNE(1),
                                 DEUX(2),
                                 TROIS(3),
                                 QUATRE(4),
                                 CINQ(5);

  private final int value;

  NoteCommentaireEnum(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

}
