package dev.ruben.funkos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ruben.funkos.controllers.FunkoController;
import dev.ruben.funkos.dto.FunkoCreateDTO;
import dev.ruben.funkos.dto.FunkoDTOResponse;
import dev.ruben.funkos.dto.FunkoUpdateDTO;
import dev.ruben.funkos.services.FunkoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FunkoController.class)
@AutoConfigureMockMvc
class FunkoControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FunkoService funkoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllFunkos() throws Exception {
        when(funkoService.findAll(any(), any(), any(), any(), any(), any())).thenReturn(new PageImpl<>(Collections.emptyList()));

        mockMvc.perform(get("/v1/funkos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());

        verify(funkoService).findAll(any(), any(), any(), any(), any(), any());
    }

    @Test
    void getFUNKOById() throws Exception {
        UUID id = UUID.randomUUID();
        when(funkoService.findById(id)).thenReturn(mock(FunkoDTOResponse.class));

        mockMvc.perform(get("/v1/funkos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

        verify(funkoService).findById(id);
    }

    @Test
    void createFUNKO() throws Exception {
        FunkoCreateDTO createDTO = new FunkoCreateDTO("Funko Name", "Description", 10.99, 50, "Model");
        when(funkoService.save(createDTO)).thenReturn(mock(FunkoDTOResponse.class));

        mockMvc.perform(post("/v1/funkos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        verify(funkoService).save(createDTO);
    }

    @Test
    void updateFUNKO() throws Exception {
        UUID id = UUID.randomUUID();
        FunkoUpdateDTO updateDTO = new FunkoUpdateDTO("Update Name", "Update Description", 10.99, "imagen", 50, LocalDateTime.now(), "Update Model");
        when(funkoService.update(id, updateDTO)).thenReturn(mock(FunkoDTOResponse.class));

        mockMvc.perform(put("/v1/funkos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

        verify(funkoService).update(id, updateDTO);
    }

    @Test
    void deleteFunko() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/v1/funkos/{id}", id))
                .andExpect(status().isNoContent());

        verify(funkoService).deleteById(id);
    }
}
