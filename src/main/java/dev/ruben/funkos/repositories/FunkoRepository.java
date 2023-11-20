package dev.ruben.funkos.repositories;

import dev.ruben.funkos.models.Funko;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface FunkoRepository extends MongoRepository<Funko, UUID> {
    Page<Funko> findAll(Optional<String> nombre, Optional<String> model, Optional<String> descripcion, Optional<Double> precio, Optional<Integer> cantidad, Pageable pageable);


}
