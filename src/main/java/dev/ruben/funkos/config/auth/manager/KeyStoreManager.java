package dev.ruben.funkos.config.auth.manager;

import com.auth0.jwt.interfaces.RSAKeyProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.FileInputStream;
import java.security.KeyStore;

import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class KeyStoreManager implements RSAKeyProvider {
    @Schema(description = "Ruta del keystore", example = "src/main/resources/cert/serverstore.jks", required = true)
    private String keystorePath;
    @Schema(description = "Contraseña del keystore", example = "123456", required = true)
    private String keystorePassword;
    @Schema(description = "Alias de la clave", example = "server", required = true)
    private String keyAlias;
    @Schema(description = "Contraseña de la clave", example = "123456", required = true)
    private String keyPassword;

    public KeyStoreManager(String keystorePath, String keystorePassword, String keyAlias, String keyPassword) {
        this.keystorePath = keystorePath;
        this.keystorePassword = keystorePassword;
        this.keyAlias = keyAlias;
        this.keyPassword = keyPassword;
    }

    @Override
    @Operation(summary = "Obtener la clave pública por id")
    public RSAPublicKey getPublicKeyById(String keyId) {
        try {
            KeyStore keystore = KeyStore.getInstance("JKS");
            keystore.load(new FileInputStream(keystorePath), keystorePassword.toCharArray());

            Certificate cert = keystore.getCertificate(keyAlias);
            return (RSAPublicKey) cert.getPublicKey();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Operation(summary = "Obtener la clave privada")
    public RSAPrivateKey getPrivateKey() {
        try {
            KeyStore keystore = KeyStore.getInstance("JKS");
            keystore.load(new FileInputStream(keystorePath), keystorePassword.toCharArray());

            return (RSAPrivateKey) keystore.getKey(keyAlias, keyPassword.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Operation(summary = "Obtener el id de la clave privada")
    public String getPrivateKeyId() {
        return keyAlias;
    }

}

