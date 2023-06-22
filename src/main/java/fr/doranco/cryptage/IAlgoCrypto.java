package fr.doranco.cryptage;

import javax.crypto.SecretKey;

public interface IAlgoCrypto {

  SecretKey generateKey();

  byte[] encrypte(String messageEncrypt, SecretKey key);

  byte[] decrypte(byte[] cipherBytes, SecretKey key);

  // String getMessagFromBytes(byte[] bytes);

}
