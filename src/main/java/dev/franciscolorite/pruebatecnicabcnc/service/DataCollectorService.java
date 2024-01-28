package dev.franciscolorite.pruebatecnicabcnc.service;

import dev.franciscolorite.pruebatecnicabcnc.model.AlbumDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DataCollectorService {

    String LOAD_DATA_FROM_JSONPLACEHOLDER_SERVER_SUCCESS_MESSAGE = "Albums y fotos importados correctamente";
    ResponseEntity<?> loadDataFromJsonPlaceHolderServerAndSaveIntoH2Memory();
    List<AlbumDto> loadDataFromJsonPlaceHolderServerAndSaveIntoMemory();
}
