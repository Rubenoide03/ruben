package dev.ruben.funkos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class FunkoDTOResponse{
    @Schema(description = "Identificador del Funko" , example = "123e4567-e89b-12d3-a456-426614174000" ,required = true)
    private UUID id;
    @Schema(description = "Nombre del Funko" , example = "Funko de Batman" ,required = true)
    private String name;
    @Schema(description = "Descripción del Funko" , example = "Funko de Batman de la película de 1989" ,required = true)
    private String description;
    @Schema(description = "Precio del Funko" , example = "12.99" ,required = true)
    private Double price;
    @Schema(description = "Stock del Funko" , example = "10" ,required = true)
    private Integer stock;
    @Schema(description = "Imagen del Funko" , example = "https://via.placeholder.com/150" ,required = true)
    private String image;
    @Schema(description = "Modelo del Funko" , example = "Batman" ,required = true)
    private String model;


}
