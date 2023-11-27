package dev.ruben.funkos.models;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;


public record Cliente(
        @Length(min = 3, message = "El nombre debe tener al menos 3 caracteres")
                @Schema(description = "Nombre del cliente", example = "Rubén", required = true)
        String nombreCompleto,

        @Email(message = "El email debe ser válido")
                @NotBlank(message = "El email no puede estar vacío")
                @Schema(description = "Email del cliente", example = "ruben@gmail.com", required = true)
        String email,

        @NotBlank(message = "El teléfono no puede estar vacío")
                @Schema(description = "Teléfono del cliente", example = "666666666", required = true)
        String telefono,

        @NotNull(message = "La dirección no puede ser nula")
                @Schema(description = "Dirección del cliente", required = true)
        Direccion direccion
) {
}
