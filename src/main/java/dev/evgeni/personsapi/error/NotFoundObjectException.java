package dev.evgeni.personsapi.error;

import java.util.UUID;

public class NotFoundObjectException extends PersonsApiBaseException {

    private final String objectClazz;
    private final String id;

    public NotFoundObjectException(String message, String objectClazz, String id) {
        super(message);
        this.objectClazz = objectClazz;
        this.id = id;
    }

    public NotFoundObjectException(String message, String objectClazz, UUID id) {
        super(message);
        this.objectClazz = objectClazz;
        this.id = id.toString();
    }

    public String getObjectClazz() {
        return objectClazz;
    }

    public String getId() {
        return id;
    }



}
