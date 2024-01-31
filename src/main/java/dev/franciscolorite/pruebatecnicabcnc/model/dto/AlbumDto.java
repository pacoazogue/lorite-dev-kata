package dev.franciscolorite.pruebatecnicabcnc.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AlbumDto {

    private Long id;

    @NotBlank(message = "El titulo es obligatorio y no puede estar vacío")
    @Size(max = 255, message = "El titulo no puede tener más de 255 caracteres")
    private String title;

    @NotBlank(message = "El id de usuario es obligatorio y no puede estar vacío")
    private String userId;

    public AlbumDto(){}
    public AlbumDto(Long id, String title, String userId){
        this.id = id;
        this.title = title;
        this.userId = userId;
    }
}
