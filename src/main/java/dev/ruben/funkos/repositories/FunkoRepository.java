package dev.ruben.funkos.repositories;

import dev.ruben.funkos.models.Funko;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface FunkoRepository extends JpaRepository<Funko, UUID>, JpaSpecificationExecutor<Funko> {




}
