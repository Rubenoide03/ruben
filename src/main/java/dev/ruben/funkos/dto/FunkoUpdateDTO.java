package dev.ruben.funkos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Nombre del Funko" , example = "Funko de Batman" ,required = true)
    private String name;
    @Length(min = 3, max = 200, message = "La descripción debe tener entre 3 y 200 caracteres")
    @Schema(description = "Descripción del Funko" , example = "Funko de Batman de la película de 1989" ,required = true)
    private String description;
    @Positive(message = "El precio debe ser mayor que 0")
    @Schema(description = "Precio del Funko" , example = "12.99" ,required = true)
    private Double price;
    @NotEmpty
    private String image;
    @Schema(description = "Stock del Funko" , example = "10" ,required = true)
    @PositiveOrZero
    private Integer stock;
    @Schema(description = "Fecha de creación del Funko" , example = "2021-05-12T12:00:00" ,required = true)
    private LocalDateTime updatedAt;
    @NotNull
    @Length(min = 3, max = 50, message = "El modelo debe tener entre 3 y 50 caracteres")
    @Schema(description = "Modelo del Funko" , example = "Batman" ,required = true)
    private String model;


}
