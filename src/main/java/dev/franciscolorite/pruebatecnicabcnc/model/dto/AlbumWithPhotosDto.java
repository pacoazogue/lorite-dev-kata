package dev.franciscolorite.pruebatecnicabcnc.model.dto;

import dev.franciscolorite.pruebatecnicabcnc.model.Photo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class AlbumWithPhotosDto extends AlbumDto {

    private List<Photo> photosLinkedToAlbumList;

    public AlbumWithPhotosDto() {
        super();
        photosLinkedToAlbumList = new ArrayList<>();
    }

    public AlbumWithPhotosDto(Long id, String title, String usedId, List<Photo> photosLinkedToAlbumList) {
        super(id, title, usedId);
        this.photosLinkedToAlbumList = photosLinkedToAlbumList;
    }

    public AlbumWithPhotosDto(AlbumDto albumDto) {
        super(albumDto.getId(), albumDto.getTitle(), albumDto.getUserId());
        photosLinkedToAlbumList = new ArrayList<>();
    }


}
