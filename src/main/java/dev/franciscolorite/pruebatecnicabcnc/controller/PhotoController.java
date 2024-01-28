package dev.franciscolorite.pruebatecnicabcnc.controller;

import dev.franciscolorite.pruebatecnicabcnc.exception.PhotoNotFoundException;
import dev.franciscolorite.pruebatecnicabcnc.exception.PhotoWithSameTitleException;
import dev.franciscolorite.pruebatecnicabcnc.model.PhotoDto;
import dev.franciscolorite.pruebatecnicabcnc.model.PhotoResponse;
import dev.franciscolorite.pruebatecnicabcnc.service.PhotoService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bcncapp/api/photos")
public class PhotoController {

    public static final String PHOTO_CREATED_MESSAGE = "La fotografía ha sido creada correctamente";
    public static final String PHOTO_UPDATED_MESSAGE = "La fotografía ha sido actualizada correctamente";
    public static final String PHOTO_DELETED_MESSAGE = "La fotografía ha sido borrada correctamente";
    public static final String PHOTO_TITLE_UPDATED_MESSAGE =
            "El título de la fotografía ha sido actualizado correctamente";

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("")
    public List<PhotoDto> findAll() {
        return photoService.findAll();
    }

    @GetMapping("/{photoId}")
    public PhotoDto findByPhotoId(@PathVariable Long photoId) throws PhotoNotFoundException {
       return photoService.findById(photoId);
    }

    @PostMapping
    public PhotoResponse createPhoto(@RequestBody @NotNull PhotoDto photoDto) {

        PhotoDto photoDtoCreated = photoService.createPhoto(photoDto);

        return buildPhotoResponse(photoDtoCreated.getId(), PHOTO_CREATED_MESSAGE);
    }

    @PutMapping("/{photoId}")
    public PhotoResponse updatePhoto(@RequestBody @NotNull PhotoDto photoDto, @PathVariable Long photoId) {

        PhotoDto photoDtoFromUpdate = photoService.updatePhoto(photoId, photoDto);

        return buildPhotoResponse(photoDtoFromUpdate.getId(), PHOTO_UPDATED_MESSAGE);
    }


    @DeleteMapping("/{photoId}")
    public PhotoResponse deletePhoto(@PathVariable Long photoId) {

        photoService.delete(photoId);

        return buildPhotoResponse(photoId, PHOTO_DELETED_MESSAGE);
    }

    @PatchMapping("/{photoId}")
    public PhotoResponse updatePhotoTitle(@RequestParam @NotEmpty String title, @PathVariable Long photoId)
            throws PhotoWithSameTitleException, PhotoNotFoundException {

        photoService.updatePhotoTitle(photoId, title);

        return buildPhotoResponse(photoId, PHOTO_TITLE_UPDATED_MESSAGE);
    }

    private PhotoResponse buildPhotoResponse(Long photoId, String responseMessage) {

        PhotoResponse photoResponse = new PhotoResponse();

        photoResponse.setResponseMessage(responseMessage);
        photoResponse.setId(photoId);

        return photoResponse;
    }





}
