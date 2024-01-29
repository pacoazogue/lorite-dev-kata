package dev.franciscolorite.pruebatecnicabcnc.service;

import dev.franciscolorite.pruebatecnicabcnc.exception.AlbumNotFoundException;
import dev.franciscolorite.pruebatecnicabcnc.exception.AlbumWithSameTitleException;
import dev.franciscolorite.pruebatecnicabcnc.model.AlbumDto;

import java.util.List;

public interface AlbumService {

    List<AlbumDto> findAll(Integer page, Integer size, Boolean includePhotos);
    AlbumDto findById(Long albumId) throws AlbumNotFoundException;
    AlbumDto createAlbum(AlbumDto albumDto);
    void delete(Long albumId);
    AlbumDto updateAlbum(Long albumId, AlbumDto albumDto);
    void updateAlbumTitle(Long albumId, String title) throws AlbumWithSameTitleException, AlbumNotFoundException;
}
