package dev.franciscolorite.pruebatecnicabcnc.controller;

import dev.franciscolorite.pruebatecnicabcnc.service.DataCollectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bcncapp/api/")
public class DataCollectorController {
    private final DataCollectorService dataCollectorService;

    public DataCollectorController(DataCollectorService dataCollectorService) {
        this.dataCollectorService = dataCollectorService;
    }

    @Operation(
            description = "Obtiene albums y photos de 2 endpoints de un servicio externo y los guarda en memoria H2",
            responses = {
                    @ApiResponse(
                            description = "Sucess",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/loadDataIntoH2Memory")
    public ResponseEntity<?> loadDataFromJsonPlaceHolderServerIntoH2Memory() {

        return dataCollectorService.loadDataFromJsonPlaceHolderServer();

    }


}
