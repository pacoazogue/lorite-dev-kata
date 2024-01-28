package dev.franciscolorite.pruebatecnicabcnc.exception;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PhotoWithSameTitleException extends Exception {

    private final Long photoId;
    private final String photoTitle;

    public PhotoWithSameTitleException(Long photoId, String photoTitle) {
        this.photoId = photoId;
        this.photoTitle = photoTitle;
    }

    @Override
    public String getMessage() {
        return String.format("La photo con identificador %d ya tiene el mismo título que el nuevo introducido: %s"
                ,photoId, photoTitle);
    }
}
