package dev.franciscolorite.pruebatecnicabcnc.controller;

import dev.franciscolorite.pruebatecnicabcnc.model.dto.AlbumWithPhotosDto;
import dev.franciscolorite.pruebatecnicabcnc.model.responses.DataCollectorH2Response;
import dev.franciscolorite.pruebatecnicabcnc.service.DataCollectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Carga de datos", description = "API para gestionar la carga de datos en el sistema")
@RestController
@RequestMapping("/bcncapp/api/")
public class DataCollectorController {
    private final DataCollectorService dataCollectorService;

    public DataCollectorController(DataCollectorService dataCollectorService) {
        this.dataCollectorService = dataCollectorService;
    }

    @Operation(
            description = "Obtiene datos pertenecientes a albums y photos a través de 2 endpoints de un " +
                    "servicio externo (https://jsonplaceholder.typicode.com/) y los guarda en memoria H2",
            responses = {
                    @ApiResponse(
                            description = "Sucess",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/loadDataIntoH2Memory")
    public DataCollectorH2Response loadDataFromJsonPlaceHolderServerIntoH2Memory() {

        return dataCollectorService.loadDataFromJsonPlaceHolderServerAndSaveIntoH2Memory();

    }

    @Operation(
            description = "Obtiene datos pertenecientes a albums y photos a través de 2 endpoints de un " +
                    "servicio externo (https://jsonplaceholder.typicode.com/) y los guarda en memoria",
            responses = {
                    @ApiResponse(
                            description = "Sucess",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/loadDataIntoMemory")
    public List<AlbumWithPhotosDto> loadDataFromJsonPlaceHolderServerIntoMemory() {
        return dataCollectorService.loadDataFromJsonPlaceHolderServerAndSaveIntoMemory();
    }


}
