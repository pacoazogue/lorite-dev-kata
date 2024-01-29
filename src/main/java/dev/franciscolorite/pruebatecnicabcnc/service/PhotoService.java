package dev.franciscolorite.pruebatecnicabcnc.service;

import dev.franciscolorite.pruebatecnicabcnc.exception.PhotoNotFoundException;
import dev.franciscolorite.pruebatecnicabcnc.exception.PhotoWithSameTitleException;
import dev.franciscolorite.pruebatecnicabcnc.model.PhotoDto;

import java.util.List;

public interface PhotoService {

    List<PhotoDto> findAll(Integer page, Integer size);

    PhotoDto findById(Long photoId) throws PhotoNotFoundException;

    PhotoDto createPhoto(PhotoDto photoDto);

    void delete(Long photoId);

    PhotoDto updatePhoto(Long photoId, PhotoDto photoDto);

    void updatePhotoTitle(Long photoId, String title) throws PhotoWithSameTitleException, PhotoNotFoundException;
}
