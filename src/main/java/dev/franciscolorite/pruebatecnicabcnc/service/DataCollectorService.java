package dev.franciscolorite.pruebatecnicabcnc.service;

import org.springframework.http.ResponseEntity;

public interface DataCollectorService {

    String LOAD_DATA_FROM_JSONPLACEHOLDER_SERVER_SUCCESS_MESSAGE = "Albums y fotos importados correctamente";
    ResponseEntity<?> loadDataFromJsonPlaceHolderServer();
}
