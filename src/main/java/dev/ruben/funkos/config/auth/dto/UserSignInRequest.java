package dev.ruben.funkos.config.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignInRequest {
    @NotBlank(message = "Username no puede estar vacío")
    @Schema(description = "Nombre de usuario", example = "ruben", required = true)
    private String username;

    @NotBlank(message = "Password no puede estar vacío")
    @Schema(description = "Password del usuario", example = "123456", required = true)
    @Length(min = 5, message = "Password debe tener al menos 5 caracteres")
    private String password;
}
