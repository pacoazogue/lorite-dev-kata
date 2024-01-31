package dev.franciscolorite.pruebatecnicabcnc.exception;

import dev.franciscolorite.pruebatecnicabcnc.model.responses.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({PhotoNotFoundException.class, PhotoWithSameTitleException.class,
            AlbumNotFoundException.class, AlbumWithSameTitleException.class, Exception.class})
    public final ResponseEntity<ErrorResponse> handleException(Exception exception) {

        LOGGER.error("Manejo de la excepción " + exception.getClass().getSimpleName() + ". Motivo: "
                + exception.getMessage());

        if (exception instanceof PhotoNotFoundException) {
            return buildResponseEntity(HttpStatus.NOT_FOUND, exception);
        } else if (exception instanceof AlbumNotFoundException) {
            return buildResponseEntity(HttpStatus.NOT_FOUND, exception);
        } else if (exception instanceof AlbumWithSameTitleException) {
            return buildResponseEntity(HttpStatus.BAD_REQUEST, exception);
        } else if (exception instanceof PhotoWithSameTitleException) {
            return buildResponseEntity(HttpStatus.BAD_REQUEST, exception);
        } else {
            return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatus httpStatus, Exception exception) {

        ErrorResponse responseDto = new ErrorResponse(httpStatus.value(), exception.getMessage());
        return new ResponseEntity<>(responseDto, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException,
                                                                  HttpHeaders httpHeaders, HttpStatusCode httpStatusCode,
                                                                  WebRequest webRequest) {

        Map<String, String> errorList = new HashMap<>();

        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error) ->{

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            errorList.put(fieldName, message);
        });
        return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
    }
}
