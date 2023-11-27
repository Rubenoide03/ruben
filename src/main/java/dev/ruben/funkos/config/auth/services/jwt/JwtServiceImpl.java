package dev.ruben.funkos.config.auth.services.jwt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import dev.ruben.funkos.config.auth.manager.KeyStoreManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Servicio de JWT
 */
@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String jwtSigningKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Value("${jwt.keystore.path}")
    private String keystorePath;

    @Value("${jwt.keystore.password}")
    private String keystorePassword;

    @Value("${jwt.key.alias}")
    private String keyAlias;

    @Value("${jwt.key.password}")
    private String keyPassword;
    private Algorithm getAlgorithm() {
        RSAKeyProvider keyProvider = new KeyStoreManager(keystorePath, keystorePassword, keyAlias, keyPassword);

        return Algorithm.RSA256(keyProvider);

    }


    public RSAPublicKey getPublicKeyById(String keyId) {
        try {
            KeyStore keystore = KeyStore.getInstance("JKS");
            try (FileInputStream fis = new FileInputStream(keystorePath)) {
                keystore.load(fis, keystorePassword.toCharArray());
            }

            Certificate cert = keystore.getCertificate(keyAlias);
            PublicKey publicKey = cert.getPublicKey();

            if (publicKey instanceof RSAPublicKey) {
                return (RSAPublicKey) publicKey;
            } else {
                throw new RuntimeException("No se pudo obtener una clave RSA pública del keystore.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la clave pública del keystore.", e);
        }
    }

    public RSAPrivateKey getPrivateKey() {
        try {
            KeyStore keystore = KeyStore.getInstance("JKS");
            try (FileInputStream fis = new FileInputStream(keystorePath)) {
                keystore.load(fis, keystorePassword.toCharArray());
            }

            KeyStore.PasswordProtection keyPasswordProtection = new KeyStore.PasswordProtection(keyPassword.toCharArray());
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keystore.getEntry(keyAlias, keyPasswordProtection);

            if (privateKeyEntry == null) {
                throw new RuntimeException("No se pudo obtener una clave privada del keystore.");
            }

            PrivateKey privateKey = privateKeyEntry.getPrivateKey();

            if (privateKey instanceof RSAPrivateKey) {
                return (RSAPrivateKey) privateKey;
            } else {
                throw new RuntimeException("No se pudo obtener una clave privada RSA del keystore.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la clave privada del keystore.", e);
        }
    }
    @Override
    public String extractUserName(String token) {
        log.info("Extracting username from token " + token);
        return extractClaim(token, DecodedJWT::getSubject);
    }

    /**
     * Genera un token
     *
     * @param userDetails Detalles del usuario
     * @return token
     */
    @Override
    public String generateToken(UserDetails userDetails) {
        log.info("Generating token for user " + userDetails.getUsername());
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Genera un token con datos extra
     *
     * @param token       token
     * @param userDetails Detalles del usuario
     * @return token
     */
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        log.info("Validating token " + token + " for user " + userDetails.getUsername());
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Genera un token con datos extra
     *
     * @param token           token
     * @param claimsResolvers Detalles del usuario
     * @return token
     */
    private <T> T extractClaim(String token, Function<DecodedJWT, T> claimsResolvers) {
        log.info("Extracting claim from token " + token);
        final DecodedJWT decodedJWT = JWT.decode(token);
        return claimsResolvers.apply(decodedJWT);
    }

    /**
     * Genera un token con datos extra
     *
     * @param extraClaims Datos extra
     * @param userDetails Detalles del usuario
     * @return token
     */
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        // Preparamos el token
        Algorithm algorithm = getAlgorithm();
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + (1000 * jwtExpiration));

        return JWT.create()
                .withHeader(createHeader())
                .withSubject(userDetails.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(expirationDate)
                .withClaim("extraClaims", extraClaims)
                .sign(algorithm);


    }


    /**
     * Comprueba si el token ha expirado
     *
     * @param token token
     * @return true si ha expirado
     */
    private boolean isTokenExpired(String token) {
        Date expirationDate = extractExpiration(token);
        return expirationDate.before(new Date());
    }

    /**
     * Extrae la fecha de expiración del token
     *
     * @param token token
     * @return fecha de expiración
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, DecodedJWT::getExpiresAt);
    }

    /**
     * Crea el encabezado del token
     *
     * @return encabezado del token
     */
    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        return header;
    }

    /**
     * Obtiene la clave de firma
     *
     * @return clave de firma
     */
    private byte[] getSigningKey() {
        return Base64.getEncoder().encode(jwtSigningKey.getBytes());

    }
}