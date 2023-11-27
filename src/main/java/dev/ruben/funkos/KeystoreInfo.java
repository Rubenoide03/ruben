package dev.ruben.funkos;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

public class KeystoreInfo {

    public static void main(String[] args) {
        try {
            // Ruta al archivo JKS
            String jksFile = "src/main/resources/cert/serverstore.jks";
            // Contraseña del keystore
            String keystorePassword = "123456";
            // Alias de la clave privada en el keystore
            String keyAlias = "serverkey";

            // Cargar el keystore
            KeyStore keystore = KeyStore.getInstance("JKS");
            FileInputStream keystoreFile = new FileInputStream(jksFile);
            keystore.load(keystoreFile, keystorePassword.toCharArray());

            // Obtener la clave privada y el certificado asociado
            KeyStore.PrivateKeyEntry privateKeyEntry =
                    (KeyStore.PrivateKeyEntry) keystore.getEntry(keyAlias, new KeyStore.PasswordProtection(keystorePassword.toCharArray()));
            PrivateKey privateKey = privateKeyEntry.getPrivateKey();
            Certificate cert = keystore.getCertificate(keyAlias);

            // Imprimir el algoritmo de la clave privada
            System.out.println("Algoritmo de la clave privada: " + privateKey.getAlgorithm());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

