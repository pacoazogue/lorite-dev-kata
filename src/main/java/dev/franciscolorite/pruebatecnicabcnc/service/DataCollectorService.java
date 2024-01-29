package dev.franciscolorite.pruebatecnicabcnc.service;

import dev.franciscolorite.pruebatecnicabcnc.model.dto.AlbumDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DataCollectorService {

    String LOAD_DATA_FROM_JSONPLACEHOLDER_SERVER_SUCCESS_MESSAGE = "Registros de albums y fotos importados desde servidor" +
            " externo y almacenados en base de datos H2 correctamente";

    ResponseEntity<?> loadDataFromJsonPlaceHolderServerAndSaveIntoH2Memory();
    List<AlbumDto> loadDataFromJsonPlaceHolderServerAndSaveIntoMemory();
}
