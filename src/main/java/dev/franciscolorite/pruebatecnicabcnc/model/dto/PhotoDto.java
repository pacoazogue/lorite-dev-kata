package dev.franciscolorite.pruebatecnicabcnc.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PhotoDto {

    private Long id;

    @NotBlank(message = "Es obligatorio establecer un título no vacío de fotografía")
    @Size(max = 255, message = "El titulo no puede tener más de 255 caracteres")
    private String title;

    @Size(max = 255, message = "La url no puede tener más de 255 caracteres")
    private String url;

    @Size(max = 255, message = "Thumbnail de url no puede tener más de 255 caracteres")
    private String thumbnailUrl;

    private String albumId;

    public PhotoDto(){}
    public PhotoDto(Long id, String title, String url, String thumbnailUrl, String albumId){
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
        this.albumId = albumId;
    }
}
