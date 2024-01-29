package dev.franciscolorite.pruebatecnicabcnc.exception;

public class AlbumNotFoundException extends Exception {

    private final Long albumId;

    public AlbumNotFoundException(Long albumId) {
        this.albumId = albumId;
    }

    @Override
    public String getMessage() {
        return String.format("El album con identificador %d no está registrado en el sistema", albumId);
    }
}
