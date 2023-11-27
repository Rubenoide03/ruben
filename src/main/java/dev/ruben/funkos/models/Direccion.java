package dev.ruben.funkos.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class Direccion {
    @Length(min = 3, message = "La calle debe tener al menos 3 caracteres")
    @Schema(description = "Calle de la dirección" , example = "Calle de la piruleta" ,required = true)
    String calle;

    @NotBlank(message = "El número no puede estar vacío")
    @Schema(description = "Número de la dirección" , example = "12" ,required = true)
    String numero;

    @Length(min = 3, message = "La ciudad debe tener al menos 3 caracteres")
    @Schema(description = "Ciudad de la dirección" , example = "Madrid" ,required = true)
    String ciudad;

    @Length(min = 3, message = "La provincia debe tener al menos 3 caracteres")
    @Schema(description = "Provincia de la dirección" , example = "Madrid" ,required = true)
    String provincia;

    @Length(min = 3, message = "El país debe tener al menos 3 caracteres")
    @Schema(description = "País de la dirección" , example = "España" ,required = true)
    String pais;

    @NotBlank(message = "El código postal no puede estar vacío")
    @Schema(description = "Código postal de la dirección" , example = "28001" ,required = true)
    String codigoPostal;

}

