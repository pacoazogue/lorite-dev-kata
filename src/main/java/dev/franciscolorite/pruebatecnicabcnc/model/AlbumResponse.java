package dev.franciscolorite.pruebatecnicabcnc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@NoArgsConstructor
@Getter @Setter
public class AlbumResponse {

    private Long id;
    private String responseMessage;

}
