package dev.evgeni.personsapi.error;

import java.util.UUID;

public class PersonsApiBaseException extends RuntimeException {
    private final UUID errorId;

    public PersonsApiBaseException(String message) {
        super(message);
        this.errorId = UUID.randomUUID();
    }

    public UUID getErrorId() {
        return errorId;
    }
    
}
