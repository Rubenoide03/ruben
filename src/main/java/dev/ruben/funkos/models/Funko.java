package dev.ruben.funkos.models;

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
    private UUID id;
    @Column(name = "nombre", nullable = false, length = 50)
    @NotBlank
    @Length(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String name;
    @Column(name = "Modelo", nullable = false, length = 50)
    @NotNull
    private String model;
    @Length(min = 3, max = 200, message = "La descripción debe tener entre 3 y 200 caracteres")
    @Column(name = "descripcion", nullable = false)
    private String description;
    @Positive(message = "El precio debe ser mayor que 0")
    @Column(name = "precio", nullable = false)
    private Double price;
    @Column(name = "stock", nullable = false)
    @PositiveOrZero(message = "El stock debe ser mayor o igual que 0")
    private Integer stock;
    @Column(name = "imagen", nullable = true)
    private String image = IMAGE_DEFAULT;
    @Column(name = "createdAt")
    LocalDateTime createdAt;
    @Column(name = "updatedAt")
    LocalDateTime updatedAt;

}
