package dev.ruben.funkos.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Document(collection = "funkos")
@EntityListeners(AuditingEntityListener.class)
public class Funko {
    private static final String IMAGE_DEFAULT = "https://via.placeholder.com/150";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Identificador único del Funko" , example = "123e4567-e89b-12d3-a456-426614174000" ,required = true)
    private UUID id;
    @Column(name = "nombre", nullable = false, length = 50)
    @NotBlank
    @Length(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    @Schema(description = "Nombre del Funko" , example = "Funko de Batman" ,required = true)
    private String name;
    @Column(name = "Modelo", nullable = false, length = 50)
    @Schema(description = "Modelo del Funko" , example = "Batman" ,required = true)
    @NotNull
    private String model;
    @Length(min = 3, max = 200, message = "La descripción debe tener entre 3 y 200 caracteres")
    @Column(name = "descripcion", nullable = false)
    @Schema(description = "Descripción del Funko" , example = "Funko de Batman de la película de 1989" ,required = true)
    private String description;
    @Positive(message = "El precio debe ser mayor que 0")
    @Column(name = "precio", nullable = false)
    @Schema(description = "Precio del Funko" , example = "12.99" ,required = true)
    private Double price;
    @Column(name = "stock", nullable = false)
    @PositiveOrZero(message = "El stock debe ser mayor o igual que 0")
    @Schema(description = "Stock del Funko" , example = "10" ,required = true)
    private Integer stock;
    @Column(name = "imagen", nullable = true)
    @Schema(description = "Imagen del Funko" , example = "https://via.placeholder.com/150" ,required = true)
    private String image = IMAGE_DEFAULT;
    @Column(name = "createdAt")
    @Schema(description = "Fecha de creación del Funko" , example = "2021-05-12T12:00:00" ,required = true)
    LocalDateTime createdAt;
    @Column(name = "updatedAt")
    @Schema(description = "Fecha de actualización del Funko" , example = "2021-05-12T12:00:00" ,required = true)
    LocalDateTime updatedAt;

}
