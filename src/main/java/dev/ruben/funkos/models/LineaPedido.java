package dev.ruben.funkos.models;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LineaPedido {
    @Min(value = 1, message = "La cantidad del producto no puede ser negativa")
    @Builder.Default
    @Schema(description = "Cantidad del producto", example = "1", required = true)
    private Integer cantidad = 1;
    @Schema(description = "Identificador del producto", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    private UUID idProducto;

    @Min(value = 0, message = "El precio del producto no puede ser negativo")
    @Builder.Default
    @Schema(description = "Precio del producto", example = "12.99", required = true)
    private Double precioProducto = 0.0;

    @Builder.Default
    @Schema(description = "Total de la línea", example = "12.99", required = true)
    private Double total = 0.0;

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        this.total = this.cantidad * this.precioProducto;
    }

    public void setPrecioProducto(Double precioProducto) {
        this.precioProducto = precioProducto;
        this.total = this.cantidad * this.precioProducto;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}