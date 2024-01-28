package dev.franciscolorite.pruebatecnicabcnc.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PhotoDto {

    private Long id;

    private String title;

    private String url;

    private String thumbnailUrl;

    private String albumId;

    public PhotoDto(){};

    public PhotoDto(Long id, String title, String url, String thumbnailUrl, String albumId){
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
        this.albumId = albumId;
    }
}
