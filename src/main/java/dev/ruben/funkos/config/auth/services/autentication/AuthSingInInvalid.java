package dev.ruben.funkos.config.auth.services.autentication;

public class AuthSingInInvalid extends RuntimeException {
    public AuthSingInInvalid(String usuarioOContraseñaIncorrectos) {
        super(usuarioOContraseñaIncorrectos);
    }
}
