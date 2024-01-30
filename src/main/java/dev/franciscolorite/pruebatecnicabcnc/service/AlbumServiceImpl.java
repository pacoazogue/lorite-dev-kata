package dev.franciscolorite.pruebatecnicabcnc.service;

import dev.franciscolorite.pruebatecnicabcnc.api.AlbumMapper;
import dev.franciscolorite.pruebatecnicabcnc.exception.AlbumNotFoundException;
import dev.franciscolorite.pruebatecnicabcnc.exception.AlbumWithSameTitleException;
import dev.franciscolorite.pruebatecnicabcnc.model.Album;
import dev.franciscolorite.pruebatecnicabcnc.model.Photo;
import dev.franciscolorite.pruebatecnicabcnc.model.dto.AlbumDto;
import dev.franciscolorite.pruebatecnicabcnc.repository.AlbumRepository;
import dev.franciscolorite.pruebatecnicabcnc.repository.PhotoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;
    private final AlbumMapper albumMapper;

    public AlbumServiceImpl(AlbumRepository albumRepository, PhotoRepository photoRepository, AlbumMapper albumMapper) {
        this.albumRepository = albumRepository;
        this.photoRepository = photoRepository;
        this.albumMapper = albumMapper;
    }

    @Override
    public List<AlbumDto> findAll(Integer page, Integer size, Boolean includePhotos) {

        List<AlbumDto> result;
        List<Album> albumsList;

        if (size != 0) {
            Pageable pageable = PageRequest.of(page, size);

            Page<Album> pageAlbumList = albumRepository.findAll(pageable);
            albumsList = pageAlbumList.getContent();
        } else {
            albumsList = albumRepository.findAll();
        }

        result = albumsList.stream()
                .map(albumMapper::entityToDto)
                .collect(Collectors.toList());

        if (includePhotos) {

            return result.stream().map(albumDto -> {
                albumDto.setPhotoList(photoRepository.findByAlbumId(albumDto.getId()));
                return albumDto;
            }).collect(Collectors.toList());
        }

        return result;
    }

    @Override
    public AlbumDto findById(Long albumId) throws AlbumNotFoundException {

        Optional<Album> albumOpt = albumRepository.findById(albumId);

        if (albumOpt.isEmpty()) {
            throw new AlbumNotFoundException(albumId);
        }

        AlbumDto albumDto = albumMapper.entityToDto(albumOpt.get());

        albumDto.setPhotoList(photoRepository.findByAlbumId(albumId));

        return albumDto;
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
    public AlbumDto unlinkPhotosFromAlbum(Long albumId) throws AlbumNotFoundException {

        Optional<Album> albumOpt = albumRepository.findById(albumId);

        if (albumOpt.isEmpty()) {
            throw new AlbumNotFoundException(albumId);
        } else {

            // Desvincular el album de las photos
            List<Photo> photoList = photoRepository.findByAlbumId(albumId).stream().map(photo -> {photo.setAlbumId(null);
                return photo;
            }).toList();

            photoRepository.saveAll(photoList);
        }

        AlbumDto albumDto = albumMapper.entityToDto(albumOpt.get());
        albumDto.setPhotoList(null);

        return albumDto;
    }
}
