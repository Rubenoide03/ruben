package dev.ruben.users.dto;

import dev.ruben.users.models.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank(message = "Nombre no puede estar vacío")
    @Schema(description = "Nombre", example = "Rubén", required = true)
    private String nombre;

    @NotBlank(message = "Apellidos no puede estar vacío")
    @Schema(description = "Apellidos del usuario", example = "García", required = true)
    private String apellidos;

    @NotBlank(message = "Username no puede estar vacío")
    @Schema(description = "Nombre de usuario", example = "ruben", required = true)
    private String username;

    @Email(regexp = ".*@.*\\..*", message = "Email debe ser válido")
    @Schema(description = "Email del usuario", example = "ruben@gmail.com", required = true)
    @NotBlank(message = "Email no puede estar vacío")
    private String email;

    @NotBlank(message = "Password no puede estar vacío")
    @Length(min = 5, message = "Password debe tener al menos 5 caracteres")
    @Size(min = 5, message = "Password debe tener al menos 5 caracteres")
    @Schema(description = "Password del usuario", example = "123456", required = true)
    private String password;

    @Builder.Default
    @Schema(description = "Roles del usuario", example = "ROLE_USER", required = true)
    private Set<Role> roles = Set.of(Role.USER);

    @Builder.Default
    @Schema(description = "Si el usuario está borrado", example = "false", required = true)
    private Boolean isDeleted = false;
}
