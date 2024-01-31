package dev.franciscolorite.pruebatecnicabcnc.model.responses;

import dev.franciscolorite.pruebatecnicabcnc.model.dto.PhotoDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@NoArgsConstructor
@Getter @Setter
public class PhotoResponse {

    private String responseMessage;
    private PhotoDto photoDto;

}
