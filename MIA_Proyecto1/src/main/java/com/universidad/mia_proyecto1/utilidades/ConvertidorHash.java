package com.universidad.mia_proyecto1.utilidades;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ConvertidorHash {
    public static String stringSHA256(String mensaje){
        try {
            MessageDigest digest;
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(mensaje.getBytes(StandardCharsets.UTF_8));
            String hashString = Base64.getEncoder().encodeToString(hash);
            return hashString.substring(0,hashString.length()-1);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    } 
}
