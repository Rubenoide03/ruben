package dev.ruben.funkos.dto;

import dev.ruben.funkos.models.Model;
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
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String image;
    private String model;


}
