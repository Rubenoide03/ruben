package dev.ruben.funkos.config.auth.controllers;
import dev.ruben.funkos.config.auth.dto.JwtAuthResponse;
import dev.ruben.funkos.config.auth.dto.UserSignInRequest;
import dev.ruben.funkos.config.auth.dto.UserSignUpRequest;
import dev.ruben.funkos.config.auth.services.autentication.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("${api.version}/auth") // Es la ruta del controlador
public class AuthenticationRestController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationRestController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Registra un usuario
     *
     * @param request datos del usuario
     * @return Token de autenticación
     */
    @PostMapping("/signup")
    @Operation(summary = "Registrar un usuario")
    public ResponseEntity<JwtAuthResponse> signUp(@Valid @RequestBody UserSignUpRequest request) {
        log.info("Registrando usuario: {}", request);
        log.info("Usuario: " + request.getUsername());
        log.info("Email del usuario: " + request.getEmail());
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    /**
     * Inicia sesión de un usuario
     *
     * @param request datos del usuario
     * @return Token de autenticación
     */
    @PostMapping("/signin")
    @Operation (summary = "Iniciar sesión de un usuario")
    @Parameters
    public ResponseEntity<JwtAuthResponse> signIn(@Valid @RequestBody UserSignInRequest request) {
        log.info("Iniciando sesión de usuario: {}", request);
        return ResponseEntity.ok(authenticationService.signIn(request));
    }

    /**
     * Manejador de excepciones de Validación: 400 Bad Request
     *
     * @param ex excepción
     * @return Mapa de errores de validación con el campo y el mensaje
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}