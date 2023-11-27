package dev.ruben.funkos.controllers;



import dev.ruben.funkos.models.Pedido;
import dev.ruben.funkos.services.PedidosService;
import dev.ruben.utils.PageResponse;
import dev.ruben.utils.PaginationLinksUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("${api.version}/pedidos")
@Slf4j
public class PedidoController {
    private final PedidosService pedidosService;
    private final PaginationLinksUtils paginationLinksUtils;

    @Autowired
    public PedidoController(PedidosService pedidosService, PaginationLinksUtils paginationLinksUtils) {
        this.pedidosService = pedidosService;
        this.paginationLinksUtils = paginationLinksUtils;
    }


    @GetMapping()
    @Operation(summary = "Obtener todos los pedidos con filtros opcionales")
    @Parameters({
            @io.swagger.v3.oas.annotations.Parameter(name = "page", description = "Número de página", example = "0"),
            @io.swagger.v3.oas.annotations.Parameter(name = "size", description = "Tamaño de la página", example = "10"),
            @io.swagger.v3.oas.annotations.Parameter(name = "sortBy", description = "Campo por el que ordenar", example = "id"),
            @io.swagger.v3.oas.annotations.Parameter(name = "direction", description = "Dirección de la ordenación", example = "asc"),
    })
    public ResponseEntity<PageResponse<Pedido>> getAllPedidos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            HttpServletRequest request
    ) {
        log.info("Obteniendo todos los pedidos");
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
        Page<Pedido> pageResult = pedidosService.findAll(PageRequest.of(page, size, sort));
        return ResponseEntity.ok()
                .header("link", paginationLinksUtils.createLinkHeader(pageResult, uriBuilder))
                .body(PageResponse.of(pageResult, sortBy, direction));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtener un pedido por su id")
    @Parameters({
            @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "Identificador único del pedido", example = "60a5f9b9e0b9a72f7c9f0b1a", required = true)
    })
    public ResponseEntity<Pedido> getPedido(@PathVariable("id") ObjectId idPedido) {
        log.info("Obteniendo pedido con id: " + idPedido);
        return ResponseEntity.ok(pedidosService.findById(idPedido));
    }


    @GetMapping("/usuario/{id}")
    @Operation(summary = "Obtener todos los pedidos de un usuario")
    @Parameters({
            @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "Identificador único del usuario", example = "1", required = true),
            @io.swagger.v3.oas.annotations.Parameter(name = "page", description = "Número de página", example = "0"),
            @io.swagger.v3.oas.annotations.Parameter(name = "size", description = "Tamaño de la página", example = "10"),
            @io.swagger.v3.oas.annotations.Parameter(name = "sortBy", description = "Campo por el que ordenar", example = "id"),
            @io.swagger.v3.oas.annotations.Parameter(name = "direction", description = "Dirección de la ordenación", example = "asc"),
    })
    public ResponseEntity<PageResponse<Pedido>> getPedidosByUsuario(
            @PathVariable("id") Long idUsuario,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        log.info("Obteniendo pedidos del usuario con id: " + idUsuario);
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(PageResponse.of(pedidosService.findByIdUsuario(idUsuario, pageable), sortBy, direction));
    }


    @PostMapping()
    @Operation(summary = "Crear un pedido")
    @Parameters({
            @io.swagger.v3.oas.annotations.Parameter(name = "pedido", description = "Objeto de tipo Pedido", required = true)
    })
    public ResponseEntity<Pedido> createPedido(@Valid @RequestBody Pedido pedido) {
        log.info("Creando pedido: " + pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidosService.save(pedido));
    }


    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un pedido por su id")
    @Parameters({
            @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "Identificador único del pedido", example = "60a5f9b9e0b9a72f7c9f0b1a", required = true),
            @io.swagger.v3.oas.annotations.Parameter(name = "pedido", description = "Objeto de tipo Pedido", required = true)
    })
    public ResponseEntity<Pedido> updatePedido(@PathVariable("id") ObjectId idPedido, @Valid @RequestBody Pedido pedido) {
        log.info("Actualizando pedido con id: " + idPedido);
        return ResponseEntity.ok(pedidosService.update(idPedido, pedido));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar un pedido por su id")
    @Parameters({
            @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "Identificador único del pedido", example = "60a5f9b9e0b9a72f7c9f0b1a", required = true)
    })
    public ResponseEntity<Pedido> deletePedido(@PathVariable("id") ObjectId idPedido) {
        log.info("Borrando pedido con id: " + idPedido);
        pedidosService.delete(idPedido);
        return ResponseEntity.noContent().build();
    }
}
