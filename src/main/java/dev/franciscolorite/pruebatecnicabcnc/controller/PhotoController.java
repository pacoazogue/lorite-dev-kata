package dev.franciscolorite.pruebatecnicabcnc.controller;

import dev.franciscolorite.pruebatecnicabcnc.exception.PhotoNotFoundException;
import dev.franciscolorite.pruebatecnicabcnc.exception.PhotoWithSameTitleException;
import dev.franciscolorite.pruebatecnicabcnc.model.dto.PhotoDto;
import dev.franciscolorite.pruebatecnicabcnc.model.dto.PhotoResponse;
import dev.franciscolorite.pruebatecnicabcnc.service.PhotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Photos", description = "API de gestión de fotografías")
@RestController
@RequestMapping("/bcncapp/api/photos")
public class PhotoController {

    public static final String PHOTO_CREATED_MESSAGE = "La fotografía se ha creado correctamente";
    public static final String PHOTO_UPDATED_MESSAGE = "La fotografía se ha actualizado correctamente";
    public static final String PHOTO_DELETED_MESSAGE = "La fotografía ha sido borrada correctamente";
    public static final String PHOTO_TITLE_UPDATED_MESSAGE =
            "El título de la fotografía ha sido actualizado correctamente";

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @Operation(
            description = "Obtiene todas las fotografías almacenadas en el sistema",
            responses = {
                    @ApiResponse(
                            description = "Sucess",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("")
    public List<PhotoDto> findAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "0") int size) {

        return photoService.findAll(page, size);
    }

    @Operation(
            description = "Obtiene una fotografía a partir de su id",
            responses = {
                    @ApiResponse(
                            description = "Sucess",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Photo not found",
                            responseCode = "404"
                    )
            }
    )
    @GetMapping("/{photoId}")
    public PhotoDto findByPhotoId(@PathVariable Long photoId) throws PhotoNotFoundException {
       return photoService.findById(photoId);
    }

    @Operation(
            description = "Crea una fotografía a partir de la información aportada",
            responses = {
                    @ApiResponse(
                            description = "Foto creada",
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
    public PhotoResponse createPhoto(@RequestBody @NotNull @Valid PhotoDto photoDto) {

        PhotoDto photoDtoCreated = photoService.createPhoto(photoDto);

        return buildPhotoResponse(PHOTO_CREATED_MESSAGE, photoDtoCreated);
    }

    @Operation(
            description = "Modifica una fotografía (Dado su id) a partir de la información aportada. Sino existe, la crea",
            responses = {
                    @ApiResponse(
                            description = "Foto modificada o creada",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Error en la petición",
                            responseCode = "400"
                    )
            }
    )
    @PutMapping("/{photoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PhotoResponse updatePhoto(@RequestBody @NotNull @Valid PhotoDto photoDto, @PathVariable Long photoId) {

        PhotoDto photoDtoFromUpdate = photoService.updatePhoto(photoId, photoDto);

        return buildPhotoResponse(PHOTO_UPDATED_MESSAGE, photoDtoFromUpdate);
    }

    @Operation(
            description = "Elimina una fotografía a partir de su id",
            responses = {
                    @ApiResponse(
                            description = "Foto borrada",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Error en la petición",
                            responseCode = "400"
                    )
            }
    )
    @DeleteMapping("/{photoId}")
    public PhotoResponse deletePhoto(@PathVariable Long photoId) {

        photoService.delete(photoId);

        PhotoResponse photoResponse = new PhotoResponse();
        photoResponse.setResponseMessage(PHOTO_DELETED_MESSAGE);

        return photoResponse;
    }

    @Operation(
            description = "Modifica el titulo una fotografía a partir de su id y habiendo aportado un nuevo titulo",
            responses = {
                    @ApiResponse(
                            description = "Título de la fotografía modificado correctamente",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Foto no encontrada",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "El titulo de la fotografía aportado es igual al existente",
                            responseCode = "400"
                    )
            }
    )
    @PatchMapping("/{photoId}")
    public PhotoResponse updatePhotoTitle(@RequestParam @NotEmpty String title, @PathVariable Long photoId)
            throws PhotoWithSameTitleException, PhotoNotFoundException {

        photoService.updatePhotoTitle(photoId, title);

        PhotoResponse photoResponse = new PhotoResponse();
        photoResponse.setResponseMessage(PHOTO_TITLE_UPDATED_MESSAGE);

        return photoResponse;
    }

    private PhotoResponse buildPhotoResponse(String responseMessage, PhotoDto photoDto) {

        PhotoResponse photoResponse = new PhotoResponse();

        photoResponse.setResponseMessage(responseMessage);
        photoResponse.setPhotoDto(photoDto);

        return photoResponse;
    }

}
