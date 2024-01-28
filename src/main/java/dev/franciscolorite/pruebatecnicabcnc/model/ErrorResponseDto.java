package dev.franciscolorite.pruebatecnicabcnc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto  {

    private final Integer errorCode;
    private final String errorMessage;
}
