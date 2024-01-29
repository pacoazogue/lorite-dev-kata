package dev.franciscolorite.pruebatecnicabcnc.model.dto;

import dev.franciscolorite.pruebatecnicabcnc.model.dto.AlbumDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@NoArgsConstructor
@Getter @Setter
public class AlbumResponse {

    private String responseMessage;
    private AlbumDto albumDto;

}
