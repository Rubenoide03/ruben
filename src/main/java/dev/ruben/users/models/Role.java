package dev.ruben.users.models;


import io.swagger.v3.oas.annotations.media.Schema;

public enum Role {
    @Schema(description = "Rol de usuario", example = "ROLE_USER", required = true)
    USER,
    ROLE_USER, ADMIN
}
