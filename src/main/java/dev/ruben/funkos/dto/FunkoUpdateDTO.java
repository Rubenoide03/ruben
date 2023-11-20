package dev.ruben.funkos.dto;

import dev.ruben.funkos.models.Model;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FunkoUpdateDTO{
    @Length(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String name;
    @Length(min = 3, max = 200, message = "La descripción debe tener entre 3 y 200 caracteres")
    private String description;
    @Positive(message = "El precio debe ser mayor que 0")
    private Double price;
    @NotEmpty
    private String image;
    @PositiveOrZero
    private Integer stock;
    private LocalDateTime updatedAt;
    @NotNull
    @Length(min = 3, max = 50, message = "El modelo debe tener entre 3 y 50 caracteres")
    private String model;


}
