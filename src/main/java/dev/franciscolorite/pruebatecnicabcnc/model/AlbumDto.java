package dev.franciscolorite.pruebatecnicabcnc.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AlbumDto {

    private Long id;

    private String title;

    private String userId;

    private List<Photo> photoList;

    public AlbumDto(){};

    public AlbumDto(Long id, String title, String userId){
        this.id = id;
        this.title = title;
        this.userId = userId;
        photoList = null;
    }
}
