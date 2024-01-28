package dev.franciscolorite.pruebatecnicabcnc.exception;

public class AlbumWithSameTitleException extends Exception {

    private final Long albumId;
    private final String albumTitle;

    public AlbumWithSameTitleException(Long albumId, String albumTitle) {
        this.albumId = albumId;
        this.albumTitle = albumTitle;
    }

    @Override
    public String getMessage() {
        return String.format("El album con identificador %d ya tiene el mismo título que el nuevo introducido: %s"
                , albumId, albumTitle);
    }
}
