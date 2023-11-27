package dev.ruben.funkos.config.auth;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class JwtConfig {



    @Value("${jwt.keystore.password}")
    private String keystorePassword;

    @Value("${jwt.key.alias}")
    private String keyAlias;

    @Value("${jwt.key.password}")
    private String keyPassword;

    @Bean
    public Algorithm getAlgorithm() {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");

            InputStream keystoreStream = getClass().getResourceAsStream("/cert/serverstore.jks");


            keyStore.load(keystoreStream, keystorePassword.toCharArray());

            KeyPair keyPair = getKeyPair(keyStore, keyAlias, keyPassword);

            return Algorithm.RSA256((RSAPublicKey) keyPair.getPublic(), (RSAPrivateKey) keyPair.getPrivate());
        } catch (Exception e) {
            throw new RuntimeException("Error loading keystore", e);
        }
    }


    private KeyPair getKeyPair(KeyStore keyStore, String alias, String password) throws Exception {
        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias,
                new KeyStore.PasswordProtection(password.toCharArray()));
        if (privateKeyEntry == null) {
            throw new RuntimeException("Error loading private key: PrivateKeyEntry is null");
        }

        return new KeyPair(privateKeyEntry.getCertificate().getPublicKey(), privateKeyEntry.getPrivateKey());
    }
}
