package dev.franciscolorite.pruebatecnicabcnc.exception;

public class PhotoNotFoundException extends Exception {

    private final Long photoId;

    public PhotoNotFoundException(Long photoId) {
        this.photoId = photoId;
    }

    @Override
    public String getMessage() {
        return String.format("La photo con identificador %d no está registrada en el sistema",photoId);
    }
}
