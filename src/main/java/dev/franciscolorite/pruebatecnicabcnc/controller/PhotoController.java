package dev.franciscolorite.pruebatecnicabcnc.controller;

import dev.franciscolorite.pruebatecnicabcnc.exception.PhotoNotFoundException;
import dev.franciscolorite.pruebatecnicabcnc.exception.PhotoWithSameTitleException;
import dev.franciscolorite.pruebatecnicabcnc.model.dto.PhotoDto;
import dev.franciscolorite.pruebatecnicabcnc.model.dto.PhotoResponse;
import dev.franciscolorite.pruebatecnicabcnc.service.PhotoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("")
    public List<PhotoDto> findAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "0") int size) {

        return photoService.findAll(page, size);
    }

    @GetMapping("/{photoId}")
    public PhotoDto findByPhotoId(@PathVariable Long photoId) throws PhotoNotFoundException {
       return photoService.findById(photoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PhotoResponse createPhoto(@RequestBody @NotNull @Valid PhotoDto photoDto) {

        PhotoDto photoDtoCreated = photoService.createPhoto(photoDto);

        return buildPhotoResponse(PHOTO_CREATED_MESSAGE, photoDtoCreated);
    }

    @PutMapping("/{photoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PhotoResponse updatePhoto(@RequestBody @NotNull @Valid PhotoDto photoDto, @PathVariable Long photoId) {

        PhotoDto photoDtoFromUpdate = photoService.updatePhoto(photoId, photoDto);

        return buildPhotoResponse(PHOTO_UPDATED_MESSAGE, photoDtoFromUpdate);
    }


    @DeleteMapping("/{photoId}")
    public PhotoResponse deletePhoto(@PathVariable Long photoId) {

        photoService.delete(photoId);

        PhotoResponse photoResponse = new PhotoResponse();
        photoResponse.setResponseMessage(PHOTO_DELETED_MESSAGE);

        return photoResponse;
    }

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
