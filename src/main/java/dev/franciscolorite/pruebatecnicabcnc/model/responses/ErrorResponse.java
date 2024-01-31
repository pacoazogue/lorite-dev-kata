package dev.franciscolorite.pruebatecnicabcnc.model.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private final Integer errorCode;
    private final String errorMessage;
}
