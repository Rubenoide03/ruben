package dev.ruben.funkos.config.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserDiferentePasswords extends RuntimeException{
    public UserDiferentePasswords(String message) {
        super(message);
    }
}
