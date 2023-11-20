package dev.ruben.funkos.exceptions;



import java.util.UUID;

public class FunkoNotFound extends RuntimeException{
    public FunkoNotFound(UUID id) {
        super("Funko con id" + id + "no encontrada");
    }
}
