package dev.ruben.funkos.mappers;

import dev.ruben.funkos.dto.FunkoCreateDTO;
import dev.ruben.funkos.dto.FunkoDTOResponse;
import dev.ruben.funkos.dto.FunkoUpdateDTO;
import dev.ruben.funkos.models.Funko;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class FunkoMapperTest {

    @InjectMocks
    private FunkoMapper funkoMapper;

    @Test
    void toFunkoCreateDTO() {
        FunkoCreateDTO createDTO = mock(FunkoCreateDTO.class);
        Funko funko = funkoMapper.toFunko(createDTO);
        assertAll(
                () -> assertEquals(createDTO.getName(), funko.getName()),
                () -> assertEquals(createDTO.getDescription(), funko.getDescription()),
                () -> assertEquals(createDTO.getPrice(), funko.getPrice()),
                () -> assertEquals(createDTO.getStock(), funko.getStock()),
                () -> assertEquals(createDTO.getModel(), funko.getModel()),
                () -> assertEquals(LocalDateTime.now().getDayOfMonth(), funko.getCreatedAt().getDayOfMonth())

        );

    }

    @Test
    void toFunkoUpdateDTO() {
        FunkoUpdateDTO updateDTO = mock(FunkoUpdateDTO.class);
        Funko existingFunko = mock(Funko.class);
        Funko updatedFunko = funkoMapper.toFunko(updateDTO, existingFunko);
        assertAll(
                () -> assertEquals(updateDTO.getName(), updatedFunko.getName()),
                () -> assertEquals(updateDTO.getDescription(), updatedFunko.getDescription()),
                () -> assertEquals(updateDTO.getPrice(), updatedFunko.getPrice()),
                () -> assertEquals(updateDTO.getStock(), updatedFunko.getStock()),
                () -> assertEquals(updateDTO.getModel(), updatedFunko.getModel()),
                () -> assertEquals(LocalDateTime.now().getDayOfMonth(), updatedFunko.getUpdatedAt().getDayOfMonth())
        );
    }

    @Test
    void toFunkoResponse() {
        Funko funko = mock(Funko.class);
        FunkoDTOResponse response = funkoMapper.toFunkoResponse(funko);
        assertAll(
                () -> assertEquals(funko.getId(), response.getId()),
                () -> assertEquals(funko.getName(), response.getName()),
                () -> assertEquals(funko.getDescription(), response.getDescription()),
                () -> assertEquals(funko.getPrice(), response.getPrice()),
                () -> assertEquals(funko.getStock(), response.getStock()),
                () -> assertEquals(funko.getImage(), response.getImage()),
                () -> assertEquals(funko.getModel(), response.getModel())
        );
    }
}
