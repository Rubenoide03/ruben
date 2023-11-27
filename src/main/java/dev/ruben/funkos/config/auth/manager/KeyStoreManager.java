package dev.ruben.funkos.config.auth.manager;

import com.auth0.jwt.interfaces.RSAKeyProvider;

import java.io.FileInputStream;
import java.security.KeyStore;

import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class KeyStoreManager implements RSAKeyProvider {
    private String keystorePath;
    private String keystorePassword;
    private String keyAlias;
    private String keyPassword;

    public KeyStoreManager(String keystorePath, String keystorePassword, String keyAlias, String keyPassword) {
        this.keystorePath = keystorePath;
        this.keystorePassword = keystorePassword;
        this.keyAlias = keyAlias;
        this.keyPassword = keyPassword;
    }

    @Override
    public RSAPublicKey getPublicKeyById(String keyId) {
        try {
            KeyStore keystore = KeyStore.getInstance("JKS");
            keystore.load(new FileInputStream(keystorePath), keystorePassword.toCharArray());

            Certificate cert = keystore.getCertificate(keyAlias);
            return (RSAPublicKey) cert.getPublicKey();
        } catch (Exception e) {
            // Manejar la excepción apropiadamente en tu aplicación
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public RSAPrivateKey getPrivateKey() {
        try {
            KeyStore keystore = KeyStore.getInstance("JKS");
            keystore.load(new FileInputStream(keystorePath), keystorePassword.toCharArray());

            return (RSAPrivateKey) keystore.getKey(keyAlias, keyPassword.toCharArray());
        } catch (Exception e) {
            // Manejar la excepción apropiadamente en tu aplicación
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getPrivateKeyId() {
        return keyAlias;
    }

}

