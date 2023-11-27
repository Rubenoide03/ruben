package dev.ruben.funkos.config.auth.dto;


import dev.ruben.users.models.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignUpRequest {

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
    @Schema(description = "Password del usuario", example = "123456", required = true)
    private String password;

    @NotBlank(message = "Password no puede estar vacío")
    @Length(min = 5, message = "Password de comprobación debe tener al menos 5 caracteres")
    @Schema(description = "Password de comprobación del usuario", example = "123456", required = true)
    private String passwordComprobacion;
    private Set<Role> roles;

}
