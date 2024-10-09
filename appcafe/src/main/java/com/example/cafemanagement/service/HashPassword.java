package com.example.cafemanagement.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {
//  private String username;
  private static String hashedPassword;
//
  public HashPassword(String password) {
    this.hashedPassword = hashPassword(password);
  }

  public HashPassword() {
  }

  public static String hashPassword(String password) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] hash = md.digest(password.getBytes());
      StringBuilder hexString = new StringBuilder();

      for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean authenticate(String passwordAttempt) {
    return hashedPassword.equals(hashPassword(passwordAttempt));
  }

  public boolean hasPassword() {
    return hashedPassword != null && !hashedPassword.isEmpty();
  }
}

