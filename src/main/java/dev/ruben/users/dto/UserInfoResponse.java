package dev.ruben.users.dto;

import dev.ruben.users.models.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
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
    private Set<Role> roles = Set.of(Role.USER);
    @Builder.Default
    @Schema(description = "Si el usuario está borrado", example = "false", required = true)
    private Boolean isDeleted = false;
    @Builder.Default
    @Schema(description = "Pedidos del usuario", example = "1", required = true)
    private List<String> pedidos = new ArrayList<>();
}

