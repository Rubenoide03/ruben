package dev.ruben.funkos.controllers;


import dev.ruben.funkos.dto.FunkoCreateDTO;
import dev.ruben.funkos.dto.FunkoDTOResponse;
import dev.ruben.funkos.dto.FunkoUpdateDTO;
import dev.ruben.funkos.services.FunkoService;
import dev.ruben.utils.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/v1/funkos")
@CacheConfig(cacheNames = "funkos")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class FunkoController {
    private final FunkoService funkoService;


    @Autowired
    public FunkoController(FunkoService funkoService) {
        this.funkoService = funkoService;

    }

    @Operation(summary="Obtener todas las piezas con filtros opcionales")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Éxito, devuelve la lista de piezas"),
            @ApiResponse(responseCode = "400", description = "Error de validación en los parámetros de entrada"),
    })
    @Parameters({
            @io.swagger.v3.oas.annotations.Parameter(name = "name", description = "Nombre de la pieza", example = "Funko de Batman"),
            @io.swagger.v3.oas.annotations.Parameter(name = "model", description = "Modelo de la pieza", example = "Batman"),
            @io.swagger.v3.oas.annotations.Parameter(name = "description", description = "Descripción de la pieza", example = "Funko de Batman de la película de 1989"),
            @io.swagger.v3.oas.annotations.Parameter(name = "price", description = "Precio de la pieza", example = "12.99"),
            @io.swagger.v3.oas.annotations.Parameter(name = "stock", description = "Stock de la pieza", example = "10"),
            @io.swagger.v3.oas.annotations.Parameter(name = "page", description = "Número de página", example = "0"),
            @io.swagger.v3.oas.annotations.Parameter(name = "size", description = "Tamaño de la página", example = "1"),
            @io.swagger.v3.oas.annotations.Parameter(name = "sortBy", description = "Campo por el cual ordenar", example = "id"),
            @io.swagger.v3.oas.annotations.Parameter(name = "order", description = "Orden de clasificación (ascendente o descendente)", example = "asc")
    })
    @GetMapping()
    public ResponseEntity<PageResponse<FunkoDTOResponse>> getAllPiezas(
            @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<String> model,
            @RequestParam(required = false) Optional<String> description,
            @RequestParam(required = false) Optional<Double> price,
            @RequestParam(required = false) Optional<Integer> stock,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "1") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        log.info("Buscando piezas con los siguientes filtros:" + name + " " + description + " " + price + " " + stock);
        Sort sort = order.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(PageResponse.of(funkoService.findAll(name, model, description, price, stock, pageable), sortBy, order));
    }

    @Cacheable(key = "#id")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary="Obtener una pieza por id")
    @Parameters({
            @Parameter(name = "id", description = "Identificador de la pieza", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    })
    public ResponseEntity<FunkoDTOResponse> getPiezaById(@PathVariable @Valid UUID id) {
        log.info("Buscando pieza por id: " + id);
        return ResponseEntity.ok(funkoService.findById(id));
    }

    @CachePut(key = "#result.id")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    @Operation(summary="Crear una pieza")
    @Parameters({
            @Parameter(name = "funkoCreateDTO", description = "Objeto de tipo FunkoCreateDTO", required = true)
    })

    public ResponseEntity<FunkoDTOResponse> createPieza(@Valid @RequestBody FunkoCreateDTO funkoCreateDTO) {


        log.info("Creando pieza: " + funkoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(funkoService.save(funkoCreateDTO));
    }

    @CachePut(key = "#result.id")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary="Actualizar una pieza por id")
    @Parameters({
            @Parameter(name = "id", description = "Identificador de la pieza", example = "123e4567-e89b-12d3-a456-426614174000", required = true),
            @Parameter(name = "funkoUpdateDTO", description = "Objeto de tipo FunkoUpdateDTO", required = true)
    })

    public ResponseEntity<FunkoDTOResponse> updatePieza(@PathVariable UUID id, @Valid @RequestBody FunkoUpdateDTO funkoUpdateDTO) {
        log.info("Actualizando pieza por id: " + id + " con pieza: " + funkoUpdateDTO);
        return ResponseEntity.ok(funkoService.update(id, funkoUpdateDTO));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary="Actualizar parcialmente una pieza por id")
    @Parameters({
            @Parameter(name = "id", description = "Identificador de la pieza", example = "123e4567-e89b-12d3-a456-426614174000", required = true),
            @Parameter(name = "funkoUpdateDTO", description = "Objeto de tipo FunkoUpdateDTO", required = true)
    })

    public ResponseEntity<FunkoDTOResponse> updatePartialPieza(@PathVariable UUID id, @Valid @RequestBody FunkoUpdateDTO funkoUpdateDTO) {
        log.info("Actualizando parcialmente pieza por id: " + id + " con pieza: " + funkoUpdateDTO);
        return ResponseEntity.ok(funkoService.update(id, funkoUpdateDTO));
    }

    @CacheEvict(key = "#id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary="Borrar una pieza por id")
    @Parameters({
            @Parameter(name = "id", description = "Identificador de la pieza", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    })
    public ResponseEntity<Void> deletePieza(@PathVariable @Valid UUID id) {
        log.info("Borrando pieza por id: " + id);
        funkoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}