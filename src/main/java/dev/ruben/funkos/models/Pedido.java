package dev.ruben.funkos.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityListeners;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// Nombre de la colección en MongoDB
@Document("pedidos")
// Para que sepa con qué clase recuperarlo al traerlo con MongoDB y aplicar polimorfismo
@TypeAlias("Pedido")
@EntityListeners(AuditingEntityListener.class) // Para que sea auditada y se autorellene
public class Pedido {
    // Id de mongo
    @Id
    @Builder.Default
    @Schema(description = "Identificador único del pedido", example = "60a5f9b9e0b9a72f7c9f0b1a", required = true)
    private ObjectId id = new ObjectId();

    @NotNull(message = "El id del usuario no puede ser nulo")
    @Schema(description = "Identificador único del usuario", example = "1", required = true)
    private Long idUsuario;

    @NotNull(message = "El id del cliente no puede ser nulo")
    @Schema(description ="Cliente vinculado al pedido", required = true)
    private Cliente cliente;

    @NotNull(message = "El pedido debe tener al menos una línea de pedido")
    @Schema(description = "Líneas de pedido del pedido", required = true)
    private List<LineaPedido> lineasPedido;

    @Builder.Default()
    @Schema(description = "Número de líneas de pedido del pedido", example = "1", required = true)
    private Integer totalItems = 0;

    @Builder.Default()
    @Schema(description = "Total del pedido", example = "12.99", required = true)
    private Double total = 0.0;

    @CreationTimestamp
    @Builder.Default()
    @Schema(description = "Fecha de creación del pedido", example = "2021-05-12T12:00:00", required = true)
    private LocalDateTime createdAt = LocalDateTime.now();

    @UpdateTimestamp
    @Builder.Default()
    @Schema(description = "Fecha de actualización del pedido", example = "2021-05-12T12:00:00", required = true)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder.Default()
    @Schema(description = "Indica si el pedido ha sido borrado", example = "false", required = true)
    private Boolean isDeleted = false;

    @JsonProperty("id")
    public String get_id() {
        return id.toHexString();
    }

    // Podemos añadir los set para que calculen los campos calculados, por ejemplo con las líneas de pedido
    public void setLineasPedido(List<LineaPedido> lineasPedido) {
        this.lineasPedido = lineasPedido;
        this.totalItems = lineasPedido != null ? lineasPedido.size() : 0;
        this.total = lineasPedido != null ? lineasPedido.stream().mapToDouble(LineaPedido::getTotal).sum() : 0.0;
    }
}