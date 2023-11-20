package dev.ruben.funkos.dto;
import dev.ruben.funkos.models.Model;
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
    private String name;
    @NotBlank
    @Length(min = 3, max = 200, message = "La descripción debe tener entre 3 y 200 caracteres")
    private String description;
    @Positive
    @NotNull(message = "El precio no puede ser nulo")
    private Double price;
    @PositiveOrZero
    @NotNull(message = "El stock no puede ser nulo")
    private Integer stock;
    @NotEmpty
   @Length(min = 3, max = 50, message = "El modelo debe tener entre 3 y 50 caracteres")
    private String model;

}
