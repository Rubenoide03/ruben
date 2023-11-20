package dev.ruben.funkos.services;


import dev.ruben.funkos.dto.FunkoCreateDTO;
import dev.ruben.funkos.dto.FunkoDTOResponse;
import dev.ruben.funkos.dto.FunkoUpdateDTO;
import dev.ruben.funkos.exceptions.FunkoNotFound;
import dev.ruben.funkos.mappers.FunkoMapper;
import dev.ruben.funkos.models.Funko;
import dev.ruben.funkos.models.Model;
import dev.ruben.funkos.repositories.FunkoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
@CacheConfig(cacheNames = "piezas")
@Slf4j
public class FunkoServiceImpl implements FunkoService {
    private final FunkoRepository funkoRepository;
    private final FunkoMapper funkoMapper;

    @Autowired
    public FunkoServiceImpl(FunkoRepository funkoRepository, FunkoMapper funkoMapper) {
        this.funkoRepository = funkoRepository;
        this.funkoMapper = funkoMapper;
    }


    @Override
    public Page<FunkoDTOResponse> findAll(Optional<String> name, Optional<String> model, Optional<String> description, Optional<Double> price, Optional<Integer> stock, Pageable pageable) {
        Specification<Funko> specNombrePieza = (root, query, criteriaBuilder) ->
                name.map(m -> criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + m.toLowerCase() + "%"))
                        .orElseGet(() -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)));

        Specification<Funko> specDescripcionPieza = (root, query, criteriaBuilder) ->
                description.map(m -> criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + m.toLowerCase() + "%"))
                        .orElseGet(() -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)));

        Specification<Funko> specMaxPrecioPieza = (root, query, criteriaBuilder) ->
                price.map(m -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), m))
                        .orElseGet(() -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)));

        Specification<Funko> specStockPieza = (root, query, criteriaBuilder) ->
                price.map(m -> criteriaBuilder.greaterThanOrEqualTo(root.get("stock"), m))
                        .orElseGet(() -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)));

        Specification<Funko> criterio = Specification.where(specStockPieza)
                .and(specNombrePieza)
                .and(specMaxPrecioPieza)
                .and(specDescripcionPieza);


        return funkoRepository.findAll(criterio, pageable).map(funkoMapper::toFunkoResponse);




    }

    @Override
    @Cacheable(key = "#id")
    public FunkoDTOResponse findById(UUID id) {
        log.info("Buscando pieza por id: " + id);
        return funkoMapper.toFunkoResponse(funkoRepository.findById(id).orElseThrow(() -> new FunkoNotFound(id)));
    }

    @CachePut(key = "#result.id")
    @Override
    public FunkoDTOResponse save(FunkoCreateDTO funkoCreateDTO) {
        try {
            log.info("Guardando funko: " + funkoCreateDTO);
            var funkoToSave = funkoMapper.toFunko(funkoCreateDTO);
            var savedFunko = funkoRepository.save(funkoToSave);
            log.info("Funko guardado exitosamente: " + savedFunko);
            return funkoMapper.toFunkoResponse(savedFunko);
        } catch (Exception e) {
            log.error("Error al guardar el funko: " + e.getMessage(), e);
            throw new RuntimeException("Error al guardar el funko", e);
        }
    }
@CachePut(key = "#result.id")
    @Override
    public FunkoDTOResponse update(UUID id, FunkoUpdateDTO funkoUpdateDTO) {
        log.info("Actualizando pieza con ID: " + id);
        var funkoToUpdate = funkoRepository.findById(id)
                .orElseThrow(() -> new FunkoNotFound(id));
        funkoToUpdate = funkoMapper.toFunko(funkoUpdateDTO, funkoToUpdate);
        var updatedFunko = funkoRepository.save(funkoToUpdate);
        log.info("Funko actualizado: " + updatedFunko);
        return funkoMapper.toFunkoResponse(updatedFunko);
    }




    @Override
    public void deleteById(UUID id) {
            log.info("Borrando pieza con id: " + id);
            funkoRepository.findById(id).orElseThrow(() -> new FunkoNotFound(id));
        funkoRepository.deleteById(id);

    }


}
