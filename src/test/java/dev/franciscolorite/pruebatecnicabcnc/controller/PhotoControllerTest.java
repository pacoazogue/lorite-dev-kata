package dev.franciscolorite.pruebatecnicabcnc.controller;

import dev.franciscolorite.pruebatecnicabcnc.exception.PhotoNotFoundException;
import dev.franciscolorite.pruebatecnicabcnc.model.dto.AlbumDto;
import dev.franciscolorite.pruebatecnicabcnc.model.dto.PhotoDto;
import dev.franciscolorite.pruebatecnicabcnc.service.PhotoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PhotoController.class)
@AutoConfigureMockMvc
public class PhotoControllerTest {

    List<PhotoDto> photoDtoList = new ArrayList<>();

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PhotoService photoService;

    @BeforeEach
    void setUp(){
        photoDtoList =
                List.of(new PhotoDto(1L,"Foto testing", "urlFake", "thumbnailUrlFake", "1"),
                        new PhotoDto( 2L, "Foto testing2", "urlFake2", "thumbnailUrlFake2","1"));
    }

    @Test
    void findAllOperationTest() throws Exception {

        String jsonResponse = """
                [
                    {
                        "id": 1,
                        "title": "Foto testing",
                        "url": "urlFake",
                        "thumbnailUrl": "thumbnailUrlFake",
                        "albumId": "1"
                    },
                    {
                        "id": 2,
                        "title": "Foto testing2",
                        "url": "urlFake2",
                        "thumbnailUrl": "thumbnailUrlFake2",
                        "albumId": "1"
                    }
                ]
                """;

        when(photoService.findAll(0,0)).thenReturn(photoDtoList);
        mockMvc.perform(get("/bcncapp/api/photos"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    void findByIdOperationTest() throws Exception {

        PhotoDto photoDto = photoDtoList.get(0);

        when(photoService.findById(1L)).thenReturn(photoDto);

        String jsonResponse = """
                    {
                        "id": 1,
                        "title": "Foto testing",
                        "url": "urlFake",
                        "thumbnailUrl": "thumbnailUrlFake",
                        "albumId": "1"
                    }
                """;

        mockMvc.perform(get("/bcncapp/api/photos/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    void findByIdOperationPhotoNotFoundExceptionTest() throws Exception {

        when(photoService.findById(3L)).thenThrow(PhotoNotFoundException.class);

        mockMvc.perform(get("/bcncapp/api/photos/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    void postOperationTest() throws Exception {

        PhotoDto photoDtoRequest = photoDtoList.get(0);

        when(photoService.createPhoto(any())).thenReturn(photoDtoRequest);

        String jsonRequest = """
                {
                   "albumId": 1,
                   "title": "Foto testing",
                   "url": "urlFake",
                   "thumbnailUrl": "thumbnailUrlFake"
                }
                """;
        String jsonResponse = """
                    {
                        "responseMessage": "La fotografía se ha creado correctamente",
                        "photoDto": {
                            "id": 1,
                            "title": "Foto testing",
                            "url": "urlFake",
                            "thumbnailUrl": "thumbnailUrlFake",
                            "albumId": "1"
                        }
                    }
                """;

        mockMvc.perform(post("/bcncapp/api/photos")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonResponse));
    }


    @Test
    void givenBodyWithEmptyTitle_whenPostOperationIsDone_thenBadRequestIsObtained() throws Exception {

        PhotoDto photoDtoRequest = photoDtoList.get(0);

        when(photoService.createPhoto(any())).thenReturn(photoDtoRequest);

        String jsonRequest = """
                {
                   "albumId": 1,
                   "title": "",
                   "url": "urlFake",
                   "thumbnailUrl": "thumbnailUrlFake"
                }
                """;

        mockMvc.perform(post("/bcncapp/api/photos")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }


    @Test
    void putOperationTest() throws Exception {

        PhotoDto photoDtoToUpdate = photoDtoList.get(0);
        photoDtoToUpdate.setTitle("New title");
        photoDtoToUpdate.setUrl("New url");

        when(photoService.updatePhoto(anyLong(), any())).thenReturn(photoDtoToUpdate);

        String jsonRequest = """
                {
                   "albumId": 1,
                   "title": "Foto testing",
                   "url": "urlFake",
                   "thumbnailUrl": "thumbnailUrlFake"
                }
                """;
        String jsonResponse = """
                    {
                        "responseMessage": "La fotografía se ha actualizado correctamente",
                        "photoDto": {
                            "id": 1,
                            "title": "New title",
                            "url": "New url",
                            "thumbnailUrl": "thumbnailUrlFake",
                            "albumId": "1"
                        }
                    }
                """;

        mockMvc.perform(put("/bcncapp/api/photos/1")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    void patchOperationTest() throws Exception {

        String jsonResponse = """
                   {
                       "responseMessage": "El título de la fotografía ha sido actualizado correctamente",
                       "photoDto": null
                   }
                """;

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "Nuevo titulo");

        mockMvc.perform(patch("/bcncapp/api/photos/1")
                        .contentType("application/json")
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    void deleteOperationTest() throws Exception {

        doNothing().when(photoService).delete(1L);

        mockMvc.perform(delete("/bcncapp/api/photos/1"))
                .andExpect(status().isOk());

        verify(photoService, times(1)).delete(1L);
    }

}
