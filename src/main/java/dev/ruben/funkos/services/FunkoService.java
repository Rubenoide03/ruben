package dev.ruben.funkos.services;


import dev.ruben.funkos.dto.FunkoDTOResponse;
import dev.ruben.funkos.dto.FunkoCreateDTO;
import dev.ruben.funkos.dto.FunkoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface FunkoService {
    Page<FunkoDTOResponse> findAll(Optional<String> name, Optional<String> model, Optional<String> description, Optional<Double> price, Optional<Integer> stock, Pageable pageable);

    FunkoDTOResponse findById(UUID id);
    FunkoDTOResponse save(FunkoCreateDTO pieza);
    FunkoDTOResponse update(UUID id, FunkoUpdateDTO pieza);
    void deleteById(UUID id);





    }
