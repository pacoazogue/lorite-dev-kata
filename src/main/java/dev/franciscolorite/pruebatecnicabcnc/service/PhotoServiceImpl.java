package dev.franciscolorite.pruebatecnicabcnc.service;

import dev.franciscolorite.pruebatecnicabcnc.config.api.PhotoMapper;
import dev.franciscolorite.pruebatecnicabcnc.exception.PhotoNotFoundException;
import dev.franciscolorite.pruebatecnicabcnc.exception.PhotoWithSameTitleException;
import dev.franciscolorite.pruebatecnicabcnc.model.Photo;
import dev.franciscolorite.pruebatecnicabcnc.model.dto.PhotoDto;
import dev.franciscolorite.pruebatecnicabcnc.repository.PhotoRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Getter @Setter
    private PhotoRepository photoRepository;
    private final PhotoMapper photoMapper;

    public PhotoServiceImpl(PhotoRepository photoRepository, PhotoMapper photoMapper) {

        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;
    }

    /**
     *
     * @param page Teniendo en cuenta el tamaño de página (Página X del total donde mostrar datos)
     * @param size Tamaño de la página (Nº de registros a mostrar)
     * @return
     */
    @Override
    public List<PhotoDto> findAll(Integer page, Integer size) {

        List<Photo> photoList;

        if (size != 0) {
            Pageable pageable = PageRequest.of(page, size);

            Page<Photo> pagePhotoList = photoRepository.findAll(pageable);
            photoList = pagePhotoList.getContent();
        } else {
            photoList = photoRepository.findAll();
        }

        return photoList.stream()
                .map(photoMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PhotoDto findById(Long photoId) throws PhotoNotFoundException {

        Optional<Photo> photoOpt = photoRepository.findById(photoId);

        if (photoOpt.isEmpty()) {
            throw new PhotoNotFoundException(photoId);
        }

        return photoMapper.entityToDto(photoOpt.get());
    }

    @Override
    public PhotoDto createPhoto(PhotoDto photoDto) {

        Photo photo =  photoMapper.dtoToEntity(photoDto);

        Photo createdPhoto = photoRepository.save(photo);

        return photoMapper.entityToDto(createdPhoto);
    }

    @Override
    public void delete(Long photoId) {
        photoRepository.deleteById(photoId);
    }

    @Override
    public PhotoDto updatePhoto(Long photoId, PhotoDto photoDto) {

        PhotoDto result;

        Optional<Photo> photoOpt = photoRepository.findById(photoId);

        Photo photo;

        if (photoOpt.isEmpty()) {
            result = createPhoto(photoDto);
        } else {

            photo = photoOpt.get();

            photo.setTitle(photoDto.getTitle());
            photo.setUrl(photoDto.getUrl());
            photo.setThumbnailUrl(photoDto.getThumbnailUrl());
            photo.setAlbumId(Long.valueOf(photoDto.getAlbumId()));

            photoRepository.save(photo);
            result = photoMapper.entityToDto(photo);
        }

        return result;
    }

    @Override
    public void updatePhotoTitle(Long photoId, String title) throws PhotoWithSameTitleException, PhotoNotFoundException {

        Optional<Photo> photoOpt = photoRepository.findById(photoId);

        if (photoOpt.isPresent()) {

            Photo photo = photoOpt.get();

            if (photo.getTitle().equals(title)) {
                throw new PhotoWithSameTitleException(photoId, title);
            } else {

                photo.setTitle(title);
                photoRepository.save(photo);
            }
        } else {
            throw new PhotoNotFoundException(photoId);
        }
    }
}
