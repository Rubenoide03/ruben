package dev.ruben.funkos.controllers;

import dev.ruben.funkos.controllers.FunkoController;
import dev.ruben.funkos.dto.FunkoCreateDTO;
import dev.ruben.funkos.dto.FunkoDTOResponse;
import dev.ruben.funkos.dto.FunkoUpdateDTO;
import dev.ruben.funkos.services.FunkoService;
import dev.ruben.utils.PageResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FunkoControllerTest {

    @Mock
    private FunkoService funkoService;

    @InjectMocks
    private FunkoController funkoController;

    @Test
    void getAllFunkos() {
        Pageable pageable = mock(Pageable.class);
        when(funkoService.findAll(any(), any(), any(), any(), any(), any())).thenReturn(new PageImpl<>(Collections.emptyList()));

        ResponseEntity<PageResponse<FunkoDTOResponse>> response = funkoController.getAllFunkos(
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), 0, 1, "id", "asc");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(funkoService).findAll(any(), any(), any(), any(), any(), any());
    }

    @Test
    void getFUNKOById() {
        UUID id = UUID.randomUUID();
        when(funkoService.findById(id)).thenReturn(mock(FunkoDTOResponse.class));

        ResponseEntity<FunkoDTOResponse> response = funkoController.getFUNKOById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(funkoService).findById(id);
    }

    @Test
    void createFUNKO() {
        FunkoCreateDTO createDTO = mock(FunkoCreateDTO.class);
        when(funkoService.save(createDTO)).thenReturn(mock(FunkoDTOResponse.class));

        ResponseEntity<FunkoDTOResponse> response = funkoController.createFUNKO(createDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(funkoService).save(createDTO);
    }

    @Test
    void updateFUNKO() {
        UUID id = UUID.randomUUID();
        FunkoUpdateDTO updateDTO = mock(FunkoUpdateDTO.class);
        when(funkoService.update(id, updateDTO)).thenReturn(mock(FunkoDTOResponse.class));

        ResponseEntity<FunkoDTOResponse> response = funkoController.updateFUNKO(id, updateDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(funkoService).update(id, updateDTO);
    }

    @Test
    void updatePartialFunko() {
        UUID id = UUID.randomUUID();
        FunkoUpdateDTO updateDTO = mock(FunkoUpdateDTO.class);
        when(funkoService.update(id, updateDTO)).thenReturn(mock(FunkoDTOResponse.class));

        ResponseEntity<FunkoDTOResponse> response = funkoController.updatePartialFunko(id, updateDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(funkoService).update(id, updateDTO);
    }

    @Test
    void deleteFunko() {
        UUID id = UUID.randomUUID();

        ResponseEntity<Void> response = funkoController.deleteFunko(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(funkoService).deleteById(id);
    }
}
