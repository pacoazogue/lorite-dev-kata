package dev.franciscolorite.pruebatecnicabcnc.service;

import dev.franciscolorite.pruebatecnicabcnc.api.AlbumMapper;
import dev.franciscolorite.pruebatecnicabcnc.exception.AlbumNotFoundException;
import dev.franciscolorite.pruebatecnicabcnc.exception.AlbumWithSameTitleException;
import dev.franciscolorite.pruebatecnicabcnc.model.Album;
import dev.franciscolorite.pruebatecnicabcnc.model.AlbumDto;
import dev.franciscolorite.pruebatecnicabcnc.repository.AlbumRepository;
import dev.franciscolorite.pruebatecnicabcnc.repository.PhotoRepository;
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
    public List<AlbumDto> findAll() {

        List<Album> albumsList = albumRepository.findAll();

        return albumsList.stream()
                .map(albumMapper::entityToDto)
                .collect(Collectors.toList());
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
}
