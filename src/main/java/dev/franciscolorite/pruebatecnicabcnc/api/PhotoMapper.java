package dev.franciscolorite.pruebatecnicabcnc.api;

import dev.franciscolorite.pruebatecnicabcnc.model.Photo;
import dev.franciscolorite.pruebatecnicabcnc.model.dto.PhotoDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PhotoMapper {

    private final ModelMapper modelMapper;

    public PhotoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PhotoDto entityToDto(Photo photo) {
        return modelMapper.map(photo, PhotoDto.class);
    }

    public Photo dtoToEntity(PhotoDto photoDto) {
        return modelMapper.map(photoDto, Photo.class);
    }
}
