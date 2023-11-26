package dev.ruben.funkos.repositories;

import dev.ruben.funkos.models.Pedido;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidosRepository extends MongoRepository<Pedido, ObjectId> {
    Page<Pedido> findByIdUsuario(Long idUsuario, Pageable pageable);
    List<Pedido> findPedidosIdsByIdUsuario(Long idUsuario);
    boolean existsByIdUsuario(Long idUsuario);
}