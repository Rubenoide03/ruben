package dev.ruben.funkos.services;

import dev.ruben.funkos.dto.FunkoCreateDTO;
import dev.ruben.funkos.dto.FunkoDTOResponse;
import dev.ruben.funkos.dto.FunkoUpdateDTO;
import dev.ruben.funkos.mappers.FunkoMapper;
import dev.ruben.funkos.models.Funko;
import dev.ruben.funkos.repositories.FunkoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FunkoServiceImplTest {

    @Mock
    private FunkoRepository funkoRepository;

    @Mock
    private FunkoMapper funkoMapper;

    @InjectMocks
    private FunkoServiceImpl funkoService;

    @Test
    void findAll() {
        // Puedes ajustar los parámetros según tus necesidades de prueba
        Optional<String> name = Optional.of("FunkoName");
        Optional<String> model = Optional.of("FunkoModel");
        Optional<String> description = Optional.of("FunkoDescription");
        Optional<Double> price = Optional.of(20.0);
        Optional<Integer> stock = Optional.of(10);
        Pageable pageable = mock(Pageable.class);

        // Mock de los resultados del repositorio y del mapper
        Page<Funko> funkoPage = new PageImpl<>(Collections.emptyList());
        when(funkoRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(funkoPage);

        Page<FunkoDTOResponse> result = funkoService.findAll(name, model, description, price, stock, pageable);

        // Verificación de llamadas a métodos y resultados
        verify(funkoRepository).findAll(any(Specification.class), eq(pageable));
        verify(funkoMapper, times(funkoPage.getContent().size())).toFunkoResponse(any(Funko.class));
    }

    @Test
    void findById() {
        UUID id = UUID.randomUUID();
        when(funkoRepository.findById(id)).thenReturn(Optional.of(mock(Funko.class)));
        when(funkoMapper.toFunkoResponse(any(Funko.class))).thenReturn(mock(FunkoDTOResponse.class));

        FunkoDTOResponse result = funkoService.findById(id);

        verify(funkoRepository).findById(id);
        verify(funkoMapper).toFunkoResponse(any(Funko.class));
    }

    @Test
    void save() {
        FunkoCreateDTO createDTO = mock(FunkoCreateDTO.class);
        Funko funkoToSave = mock(Funko.class);
        when(funkoMapper.toFunko(createDTO)).thenReturn(funkoToSave);
        when(funkoRepository.save(funkoToSave)).thenReturn(funkoToSave);
        when(funkoMapper.toFunkoResponse(funkoToSave)).thenReturn(mock(FunkoDTOResponse.class));

        FunkoDTOResponse result = funkoService.save(createDTO);

        verify(funkoRepository).save(funkoToSave);
        verify(funkoMapper).toFunkoResponse(funkoToSave);
    }

    @Test
    void update() {
        UUID id = UUID.randomUUID();
        FunkoUpdateDTO updateDTO = mock(FunkoUpdateDTO.class);
        Funko existingFunko = mock(Funko.class);
        when(funkoRepository.findById(id)).thenReturn(Optional.of(existingFunko));
        when(funkoMapper.toFunko(updateDTO, existingFunko)).thenReturn(existingFunko);
        when(funkoRepository.save(existingFunko)).thenReturn(existingFunko);
        when(funkoMapper.toFunkoResponse(existingFunko)).thenReturn(mock(FunkoDTOResponse.class));

        FunkoDTOResponse result = funkoService.update(id, updateDTO);

        verify(funkoRepository).findById(id);
        verify(funkoRepository).save(existingFunko);
        verify(funkoMapper).toFunkoResponse(existingFunko);
    }

    @Test
    void deleteById() {
        UUID id = UUID.randomUUID();
        when(funkoRepository.findById(id)).thenReturn(Optional.of(mock(Funko.class)));

        funkoService.deleteById(id);

        verify(funkoRepository).findById(id);
        verify(funkoRepository).deleteById(id);
    }
}
