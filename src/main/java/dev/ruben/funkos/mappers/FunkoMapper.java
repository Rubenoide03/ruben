package dev.ruben.funkos.mappers;



import dev.ruben.funkos.dto.FunkoCreateDTO;
import dev.ruben.funkos.dto.FunkoDTOResponse;
import dev.ruben.funkos.dto.FunkoUpdateDTO;
import dev.ruben.funkos.models.Funko;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FunkoMapper {
    public Funko toFunko(FunkoCreateDTO funkoCreateDTO) {
        return Funko.builder()
                .name(funkoCreateDTO.getName())
                .description(funkoCreateDTO.getDescription())
                .price(funkoCreateDTO.getPrice())
                .stock(funkoCreateDTO.getStock())
                .model(funkoCreateDTO.getModel())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public Funko toFunko(FunkoUpdateDTO funkoUpdateDTO, Funko funko) {
        return Funko.builder()
                .name(funkoUpdateDTO.getName() != null ? funkoUpdateDTO.getName() : funko.getName())
                .description(funkoUpdateDTO.getDescription() != null ? funkoUpdateDTO.getDescription() : funko.getDescription())
                .price(funkoUpdateDTO.getPrice() != null ? funkoUpdateDTO.getPrice() : funko.getPrice())
                .stock(funkoUpdateDTO.getStock() != null ? funkoUpdateDTO.getStock() : funko.getStock())
                .model(funkoUpdateDTO.getModel() != null ? funkoUpdateDTO.getModel() : funko.getModel())
                .image(funkoUpdateDTO.getImage() != null ? funkoUpdateDTO.getImage() : funko.getImage())
                .updatedAt(LocalDateTime.now())
                .build();

    }
    public FunkoDTOResponse toFunkoResponse(Funko funko) {
        return FunkoDTOResponse.builder()
                .id(funko.getId())
                .name(funko.getName())
                .description(funko.getDescription())
                .price(funko.getPrice())
                .stock(funko.getStock())
                .image(funko.getImage())
                .model(funko.getModel())
                .build();
    }


}

