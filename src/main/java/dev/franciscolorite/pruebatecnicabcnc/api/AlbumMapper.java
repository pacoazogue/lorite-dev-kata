package dev.franciscolorite.pruebatecnicabcnc.api;

import dev.franciscolorite.pruebatecnicabcnc.model.Album;
import dev.franciscolorite.pruebatecnicabcnc.model.dto.AlbumDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AlbumMapper {

    private final ModelMapper modelMapper;

    public AlbumMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AlbumDto entityToDto(Album album) {
        return modelMapper.map(album, AlbumDto.class);
    }

    public Album dtoToEntity(AlbumDto albumDto) {
        return modelMapper.map(albumDto, Album.class);
    }
}
