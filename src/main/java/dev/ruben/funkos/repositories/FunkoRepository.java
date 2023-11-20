package dev.ruben.funkos.repositories;

import dev.ruben.funkos.models.Funko;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface FunkoRepository extends MongoRepository<Funko, UUID> {
    Page<Funko> findAllByNombreContainingAndModelContainingAndDescripcionContainingAndPrecioLessThanEqualAndCantidadGreaterThanEqual(
            String nombre,
            String model,
            String descripcion,
            Double precio,
            Integer cantidad,
            Pageable pageable
    );


}
