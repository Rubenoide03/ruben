package dev.ruben.funkos.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FunkoCreateDTO{
    @NotBlank
    @Length(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    @Schema(description = "Nombre del Funko" , example = "Funko de Batman" ,required = true)
    private String name;
    @NotBlank
    @Length(min = 3, max = 200, message = "La descripción debe tener entre 3 y 200 caracteres")
    @Schema(description = "Descripción del Funko" , example = "Funko de Batman de la película de 1989" ,required = true)
    private String description;
    @Positive
    @NotNull(message = "El precio no puede ser nulo")
    @Schema(description = "Precio del Funko" , example = "12.99" ,required = true)
    private Double price;
    @PositiveOrZero
    @NotNull(message = "El stock no puede ser nulo")
    @Schema(description = "Stock del Funko" , example = "10" ,required = true)
    private Integer stock;
    @NotEmpty
   @Length(min = 3, max = 50, message = "El modelo debe tener entre 3 y 50 caracteres")
    @Schema(description = "Modelo del Funko" , example = "Batman" ,required = true)
    private String model;

}
