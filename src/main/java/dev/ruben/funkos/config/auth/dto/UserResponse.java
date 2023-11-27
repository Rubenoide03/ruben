package dev.ruben.funkos.config.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Role;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    @Schema(description = "Identificador único del usuario", example = "1", required = true)
    private Long id;
    @Schema(description = "Nombre", example = "Rubén", required = true)
    private String nombre;
    @Schema(description = "Apellidos del usuario", example = "García", required = true)
    private String apellidos;
    @Schema(description = "Nombre de usuario", example = "ruben", required = true)
    private String username;
    @Schema(description = "Email del usuario", example = "ruben@gmail.com", required = true)
    private String email;
    @Builder.Default
    @Schema(description = "Roles del usuario", example = "ROLE_USER", required = true)
    private Set<String> roles = Set.of();
}
