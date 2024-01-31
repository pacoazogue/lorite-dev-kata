package dev.franciscolorite.pruebatecnicabcnc.model.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@NoArgsConstructor
@Getter @Setter
public class DataCollectorH2Response {

    private String responseMessage;
    private String albumsMessage;
    private String photosMessage;

}
