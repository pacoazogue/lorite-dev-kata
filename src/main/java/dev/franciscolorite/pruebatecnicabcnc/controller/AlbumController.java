package dev.franciscolorite.pruebatecnicabcnc.controller;

import dev.franciscolorite.pruebatecnicabcnc.exception.AlbumNotFoundException;
import dev.franciscolorite.pruebatecnicabcnc.exception.AlbumWithSameTitleException;
import dev.franciscolorite.pruebatecnicabcnc.model.dto.AlbumDto;
import dev.franciscolorite.pruebatecnicabcnc.model.dto.AlbumWithPhotosDto;
import dev.franciscolorite.pruebatecnicabcnc.model.responses.AlbumResponse;
import dev.franciscolorite.pruebatecnicabcnc.service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Albums", description = "API de gestión de albumes")
@RestController
@RequestMapping("/bcncapp/api/albums")
public class AlbumController {

    public static final String ALBUM_CREATED_MESSAGE = "El album se ha creado correctamente";
    public static final String ALBUM_UPDATED_MESSAGE = "El album se ha actualizado correctamente";
    public static final String ALBUM_DELETED_MESSAGE = "El album ha sido borrado correctamente";
    public static final String ALBUM_TITLE_UPDATED_MESSAGE = "El título del album ha sido actualizado correctamente";

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @Operation(
            description = "Obtiene todas los albums almacenados en el sistema",
            responses = {
                    @ApiResponse(
                            description = "Sucess",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("")
    public List<? extends AlbumDto> findAll(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "0") int size,
                                            @RequestParam(required = false, defaultValue = "false") boolean includePhotos) {
        return albumService.findAll(page, size, includePhotos);
    }

    @Operation(
            description = "Obtiene un album a partir de su id",
            responses = {
                    @ApiResponse(
                            description = "Sucess",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Album not found",
                            responseCode = "404"
                    )
            }
    )
    @GetMapping("/{albumId}")
    public AlbumWithPhotosDto findByAlbumId(@PathVariable Long albumId) throws AlbumNotFoundException {
        return albumService.findById(albumId);
    }

    @Operation(
            description = "Crea un album a partir de la información aportada",
            responses = {
                    @ApiResponse(
                            description = "Album creado",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Error en la petición",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumResponse createAlbum(@RequestBody @NotNull @Valid AlbumDto albumDto) {

        AlbumDto albumDtoCreated = albumService.createAlbum(albumDto);

        return buildAlbumResponse(albumDtoCreated, ALBUM_CREATED_MESSAGE);
    }

    @Operation(
            description = "Modifica un album (Dado su id) a partir de la información aportada. Sino existe, la crea",
            responses = {
                    @ApiResponse(
                            description = "Album modificado o creado",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Error en la petición",
                            responseCode = "400"
                    )
            }
    )
    @PutMapping("/{albumId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumResponse updateAlbum(@RequestBody @NotNull @Valid AlbumDto albumDto, @PathVariable Long albumId) {

        AlbumDto albumDtoFromUpdate = albumService.updateAlbum(albumId, albumDto);

        return buildAlbumResponse(albumDtoFromUpdate, ALBUM_UPDATED_MESSAGE);
    }

    @Operation(
            description = "Elimina un album a partir de su id",
            responses = {
                    @ApiResponse(
                            description = "Album borrada",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Error en la petición",
                            responseCode = "400"
                    )
            }
    )
    @DeleteMapping("/{albumId}")
    public AlbumResponse deleteAlbum(@PathVariable Long albumId) {

        albumService.delete(albumId);

        AlbumResponse albumResponse = new AlbumResponse();
        albumResponse.setResponseMessage(ALBUM_DELETED_MESSAGE);

        return albumResponse;
    }

    @Operation(
            description = "Modifica el titulo un album a partir de su id y habiendo aportado un nuevo titulo",
            responses = {
                    @ApiResponse(
                            description = "Título de un album modificado correctamente",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "album no encontrada",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "El titulo del album aportado es igual al existente",
                            responseCode = "400"
                    )
            }
    )
    @PatchMapping("/{albumId}")
    public AlbumResponse updateAlbumTitle(@RequestParam @NotEmpty String title, @PathVariable Long albumId)
            throws AlbumWithSameTitleException, AlbumNotFoundException {

        albumService.updateAlbumTitle(albumId, title);

        AlbumResponse albumResponse = new AlbumResponse();
        albumResponse.setResponseMessage(ALBUM_TITLE_UPDATED_MESSAGE);

        return albumResponse;
    }

    @Operation(
            description = "Desvincula todas las fotos de un album dado su id",
            responses = {
                    @ApiResponse(
                            description = "Fotografías del album desvinculadas",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Album no encontrado",
                            responseCode = "404"
                    )
            }
    )
    @GetMapping("/unlinkphotosfromalbum/{albumId}")
    public AlbumDto unlinkPhotosFromAlbum(@PathVariable Long albumId) throws AlbumNotFoundException {
        return albumService.unlinkPhotosFromAlbum(albumId);
    }

    private AlbumResponse buildAlbumResponse(AlbumDto albumDto, String responseMessage) {

        AlbumResponse albumResponse = new AlbumResponse();

        albumResponse.setResponseMessage(responseMessage);
        albumResponse.setAlbumDto(albumDto);

        return albumResponse;
    }

}
