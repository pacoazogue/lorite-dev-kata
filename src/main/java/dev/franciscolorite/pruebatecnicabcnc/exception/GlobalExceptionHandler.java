package dev.franciscolorite.pruebatecnicabcnc.exception;

import dev.franciscolorite.pruebatecnicabcnc.model.ErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({PhotoNotFoundException.class, PhotoWithSameTitleException.class,
            AlbumNotFoundException.class, AlbumWithSameTitleException.class, Exception.class})
    public final ResponseEntity<ErrorResponseDto> handleException(Exception exception) {

        LOGGER.error("Manejo de la excepción " + exception.getClass().getSimpleName() + ". Motivo: "
                + exception.getMessage());

        if (exception instanceof PhotoNotFoundException) {
            return buildResponseEntity(HttpStatus.NOT_FOUND, exception);
        } else if (exception instanceof AlbumNotFoundException) {
            return buildResponseEntity(HttpStatus.NOT_FOUND, exception);
        } else if (exception instanceof AlbumWithSameTitleException) {
            return buildResponseEntity(HttpStatus.OK, exception);
        } else if (exception instanceof PhotoWithSameTitleException) {
            return buildResponseEntity(HttpStatus.OK, exception);
        } else {
            return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    private ResponseEntity<ErrorResponseDto> buildResponseEntity(HttpStatus httpStatus, Exception exception) {

        ErrorResponseDto responseDto = new ErrorResponseDto(httpStatus.value(), exception.getMessage());
        return new ResponseEntity<>(responseDto, httpStatus);
    }


}
