package dev.franciscolorite.pruebatecnicabcnc.controller;

import dev.franciscolorite.pruebatecnicabcnc.exception.AlbumNotFoundException;
import dev.franciscolorite.pruebatecnicabcnc.exception.AlbumWithSameTitleException;
import dev.franciscolorite.pruebatecnicabcnc.model.AlbumDto;
import dev.franciscolorite.pruebatecnicabcnc.model.AlbumResponse;
import dev.franciscolorite.pruebatecnicabcnc.service.AlbumService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bcncapp/api/albums")
public class AlbumController {

    public static final String ALBUM_CREATED_MESSAGE = "El album ha sido creado correctamente";
    public static final String ALBUM_UPDATED_MESSAGE = "El album ha sido actualizado correctamente";
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
    public AlbumResponse createAlbum(@RequestBody @NotNull AlbumDto albumDto) {

        AlbumDto albumDtoCreated = albumService.createAlbum(albumDto);

        return buildAlbumResponse(albumDtoCreated.getId(), ALBUM_CREATED_MESSAGE);
    }

    @PutMapping("/{albumId}")
    public AlbumResponse updateAlbum(@RequestBody @NotNull AlbumDto albumDto, @PathVariable Long albumId) {

        AlbumDto albumDtoFromUpdate = albumService.updateAlbum(albumId, albumDto);

        return buildAlbumResponse(albumDtoFromUpdate.getId(), ALBUM_UPDATED_MESSAGE);
    }


    @DeleteMapping("/{albumId}")
    public AlbumResponse deleteAlbum(@PathVariable Long albumId) {

        albumService.delete(albumId);

        return buildAlbumResponse(albumId, ALBUM_DELETED_MESSAGE);
    }

    @PatchMapping("/{albumId}")
    public AlbumResponse updateAlbumTitle(@RequestParam @NotEmpty String title, @PathVariable Long albumId)
            throws AlbumWithSameTitleException, AlbumNotFoundException {

        albumService.updateAlbumTitle(albumId, title);

        return buildAlbumResponse(albumId, ALBUM_TITLE_UPDATED_MESSAGE);
    }

    private AlbumResponse buildAlbumResponse(Long albumId, String responseMessage) {

        AlbumResponse albumResponse = new AlbumResponse();

        albumResponse.setResponseMessage(responseMessage);
        albumResponse.setId(albumId);

        return albumResponse;
    }
}
