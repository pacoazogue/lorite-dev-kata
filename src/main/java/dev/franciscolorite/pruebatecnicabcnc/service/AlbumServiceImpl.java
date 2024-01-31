package dev.franciscolorite.pruebatecnicabcnc.service;

import dev.franciscolorite.pruebatecnicabcnc.config.api.AlbumMapper;
import dev.franciscolorite.pruebatecnicabcnc.exception.AlbumNotFoundException;
import dev.franciscolorite.pruebatecnicabcnc.exception.AlbumWithSameTitleException;
import dev.franciscolorite.pruebatecnicabcnc.model.Album;
import dev.franciscolorite.pruebatecnicabcnc.model.Photo;
import dev.franciscolorite.pruebatecnicabcnc.model.dto.AlbumDto;
import dev.franciscolorite.pruebatecnicabcnc.model.dto.AlbumWithPhotosDto;
import dev.franciscolorite.pruebatecnicabcnc.repository.AlbumRepository;
import dev.franciscolorite.pruebatecnicabcnc.repository.PhotoRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Getter @Setter
    private AlbumRepository albumRepository;
    @Getter @Setter
    private PhotoRepository photoRepository;

    private final AlbumMapper albumMapper;

    public AlbumServiceImpl(AlbumRepository albumRepository, PhotoRepository photoRepository, AlbumMapper albumMapper) {
        this.albumRepository = albumRepository;
        this.photoRepository = photoRepository;
        this.albumMapper = albumMapper;
    }

    @Override
    public List<? extends AlbumDto> findAll(Integer page, Integer size, Boolean includePhotos) {

        List<AlbumDto> albumDtoList;
        List<Album> albumsList;

        if (size != 0) {
            Pageable pageable = PageRequest.of(page, size);

            Page<Album> pageAlbumList = albumRepository.findAll(pageable);
            albumsList = pageAlbumList.getContent();
        } else {
            albumsList = albumRepository.findAll();
        }

        albumDtoList = albumsList.stream()
                .map(albumMapper::entityToDto)
                .collect(Collectors.toList());

        if (includePhotos) {

            List<AlbumWithPhotosDto> albumWithPhotosDtoList = new ArrayList<>();

            albumDtoList.forEach(albumDto -> {
                AlbumWithPhotosDto albumWithPhotosDto = new AlbumWithPhotosDto(albumDto);
                albumWithPhotosDto.setPhotosLinkedToAlbumList(photoRepository.findByAlbumId(albumDto.getId()));
                albumWithPhotosDtoList.add(albumWithPhotosDto);
            });
            
            return albumWithPhotosDtoList;
        } else {
            return albumDtoList;
        }
    }

    @Override
    public AlbumWithPhotosDto findById(Long albumId) throws AlbumNotFoundException {

        Optional<Album> albumOpt = albumRepository.findById(albumId);

        if (albumOpt.isEmpty()) {
            throw new AlbumNotFoundException(albumId);
        }

        AlbumDto albumDto = albumMapper.entityToDto(albumOpt.get());

        AlbumWithPhotosDto albumWithPhotosDto = new AlbumWithPhotosDto(albumDto);
        albumWithPhotosDto.setPhotosLinkedToAlbumList(photoRepository.findByAlbumId(albumId));

        return albumWithPhotosDto;
    }

    @Override
    public AlbumDto createAlbum(AlbumDto albumDto) {
        Album album =  albumMapper.dtoToEntity(albumDto);

        Album createdAlbum = albumRepository.save(album);

        return albumMapper.entityToDto(createdAlbum);
    }

    @Override
    public void delete(Long albumId) {
        albumRepository.deleteById(albumId);
    }

    @Override
    public AlbumDto updateAlbum(Long albumId, AlbumDto albumDto) {
        AlbumDto result;

        Optional<Album> albumOpt = albumRepository.findById(albumId);

        Album album;

        if (albumOpt.isEmpty()) {
            result = createAlbum(albumDto);
        } else {

            album = albumOpt.get();

            album.setTitle(albumDto.getTitle());
            album.setUserId(Integer.valueOf(albumDto.getUserId()));

            albumRepository.save(album);
            result = albumMapper.entityToDto(album);
        }

        return result;
    }

    @Override
    public void updateAlbumTitle(Long albumId, String title) throws AlbumWithSameTitleException, AlbumNotFoundException {
        Optional<Album> albumOpt = albumRepository.findById(albumId);

        if (albumOpt.isPresent()) {

            Album album = albumOpt.get();

            if (album.getTitle().equals(title)) {
                throw new AlbumWithSameTitleException(albumId, title);
            } else {

                album.setTitle(title);
                albumRepository.save(album);
            }
        } else {
            throw new AlbumNotFoundException(albumId);
        }
    }

    @Override
    public AlbumWithPhotosDto unlinkPhotosFromAlbum(Long albumId) throws AlbumNotFoundException {

        Optional<Album> albumOpt = albumRepository.findById(albumId);

        if (albumOpt.isEmpty()) {
            throw new AlbumNotFoundException(albumId);
        } else {

            // Desvincular la referencia de "album_id" en todas las fotos que contuvieran su "album_id"
            List<Photo> photoList = photoRepository.findByAlbumId(albumId).stream().map(photo -> {
                photo.setAlbumId(null);
                return photo;
            }).toList();

            photoRepository.saveAll(photoList);
        }

        AlbumDto albumDto = albumMapper.entityToDto(albumOpt.get());

        return new AlbumWithPhotosDto(albumDto);
    }

}
