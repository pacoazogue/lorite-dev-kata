package dev.franciscolorite.pruebatecnicabcnc.controller;

import dev.franciscolorite.pruebatecnicabcnc.exception.AlbumNotFoundException;
import dev.franciscolorite.pruebatecnicabcnc.exception.AlbumWithSameTitleException;
import dev.franciscolorite.pruebatecnicabcnc.model.dto.AlbumDto;
import dev.franciscolorite.pruebatecnicabcnc.model.dto.AlbumResponse;
import dev.franciscolorite.pruebatecnicabcnc.service.AlbumService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("")
    public List<AlbumDto> findAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "0") int size,
                                  @RequestParam(required = false, defaultValue = "false") boolean includePhotos) {
        return albumService.findAll(page, size, includePhotos);
    }

    @GetMapping("/{albumId}")
    public AlbumDto findByAlbumId(@PathVariable Long albumId) throws AlbumNotFoundException {
        return albumService.findById(albumId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumResponse createAlbum(@RequestBody @NotNull @Valid AlbumDto albumDto) {

        AlbumDto albumDtoCreated = albumService.createAlbum(albumDto);

        return buildAlbumResponse(albumDtoCreated, ALBUM_CREATED_MESSAGE);
    }

    @PutMapping("/{albumId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumResponse updateAlbum(@RequestBody @NotNull AlbumDto albumDto, @PathVariable Long albumId) {

        AlbumDto albumDtoFromUpdate = albumService.updateAlbum(albumId, albumDto);

        return buildAlbumResponse(albumDtoFromUpdate, ALBUM_UPDATED_MESSAGE);
    }


    @DeleteMapping("/{albumId}")
    public AlbumResponse deleteAlbum(@PathVariable Long albumId) {

        albumService.delete(albumId);

        AlbumResponse albumResponse = new AlbumResponse();
        albumResponse.setResponseMessage(ALBUM_DELETED_MESSAGE);

        return albumResponse;
    }

    @PatchMapping("/{albumId}")
    public AlbumResponse updateAlbumTitle(@RequestParam @NotEmpty String title, @PathVariable Long albumId)
            throws AlbumWithSameTitleException, AlbumNotFoundException {

        albumService.updateAlbumTitle(albumId, title);

        AlbumResponse albumResponse = new AlbumResponse();
        albumResponse.setResponseMessage(ALBUM_TITLE_UPDATED_MESSAGE);

        return albumResponse;
    }

    private AlbumResponse buildAlbumResponse(AlbumDto albumDto, String responseMessage) {

        AlbumResponse albumResponse = new AlbumResponse();

        albumResponse.setResponseMessage(responseMessage);
        albumResponse.setAlbumDto(albumDto);

        return albumResponse;
    }
}
