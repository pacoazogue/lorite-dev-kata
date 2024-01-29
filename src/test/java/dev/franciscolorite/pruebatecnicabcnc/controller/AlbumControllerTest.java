package dev.franciscolorite.pruebatecnicabcnc.controller;

import dev.franciscolorite.pruebatecnicabcnc.exception.AlbumNotFoundException;
import dev.franciscolorite.pruebatecnicabcnc.model.dto.AlbumDto;
import dev.franciscolorite.pruebatecnicabcnc.service.AlbumService;
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

@WebMvcTest(AlbumController.class)
@AutoConfigureMockMvc
public class AlbumControllerTest {

    List<AlbumDto> albumDtoList = new ArrayList<>();

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AlbumService albumService;

    @BeforeEach
    void setUp(){
        albumDtoList =
                List.of(new AlbumDto(1L,"Album testing", "1"),
                        new AlbumDto( 2L, "Album testing", "2"));
    }

    @Test
    void findAllOperationTest() throws Exception {

        String jsonResponse = """
                [
                    {
                        "id": 1,
                        "title": "Album testing",
                        "userId": "1"
                    },
                    {
                        "id": 2,
                        "title": "Album testing",
                        "userId": "2"
                    }
                ]
                """;

        when(albumService.findAll(0,0, false)).thenReturn(albumDtoList);
        mockMvc.perform(get("/bcncapp/api/albums"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    void findByIdOperationTest() throws Exception {

        AlbumDto albumDto = albumDtoList.get(0);

        when(albumService.findById(1L)).thenReturn(albumDto);

        String jsonResponse = """
                    {
                        "id": 1,
                        "title": "Album testing",
                        "userId": "1"
                    }
                """;

        mockMvc.perform(get("/bcncapp/api/albums/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    void findByIdOperationPhotoNotFoundExceptionTest() throws Exception {

        when(albumService.findById(3L)).thenThrow(AlbumNotFoundException.class);

        mockMvc.perform(get("/bcncapp/api/albums/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    void postOperationTest() throws Exception {

        AlbumDto albumDtoRequest = albumDtoList.get(0);

        when(albumService.createAlbum(any())).thenReturn(albumDtoRequest);

        String jsonRequest = """
                {
                   "userId": 1,
                   "title": "Album testing"
                }
                """;
        String jsonResponse = """
                   {
                       "responseMessage": "El album se ha creado correctamente",
                       "albumDto": {
                           "id": 1,
                           "title": "Album testing",
                           "userId": "1",
                           "photoList": null
                       }
                   }
                """;

        mockMvc.perform(post("/bcncapp/api/albums")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    void givenBodyWithEmptyTitle_whenPostOperationIsDone_thenBadRequestIsObtained() throws Exception {

        AlbumDto albumDtoRequest = albumDtoList.get(0);

        when(albumService.createAlbum(any())).thenReturn(albumDtoRequest);

        String jsonRequest = """
                {
                   "userId": 1,
                   "title": ""
                }
                """;

        mockMvc.perform(post("/bcncapp/api/albums")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenBodyWithLargeTitle_whenPostOperationIsDone_thenBadRequestIsObtained() throws Exception {

        AlbumDto albumDtoRequest = albumDtoList.get(0);

        when(albumService.createAlbum(any())).thenReturn(albumDtoRequest);

        String jsonRequest = """
                {
                   "userId": 1,
                   "title": "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
                   bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb
                   ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"
                }
                """;

        mockMvc.perform(post("/bcncapp/api/albums")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenBodyWithEmptyUserId_whenPostOperationIsDone_thenBadRequestIsObtained() throws Exception {

        AlbumDto albumDtoRequest = albumDtoList.get(0);

        when(albumService.createAlbum(any())).thenReturn(albumDtoRequest);

        String jsonRequest = """
                {
                   "userId": "",
                   "title": "test"
                }
                """;

        mockMvc.perform(post("/bcncapp/api/albums")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void putOperationTest() throws Exception {

        AlbumDto albumDtoToUpdate = albumDtoList.get(0);
        albumDtoToUpdate.setTitle("Album testing Changed");

        when(albumService.updateAlbum(anyLong(), any())).thenReturn(albumDtoToUpdate);

        String jsonRequest = """
                {
                   "userId": 1,
                   "title": "Album testing Changed"
                }
                """;
        String jsonResponse = """
                   {
                       "responseMessage": "El album se ha actualizado correctamente",
                       "albumDto": {
                           "id": 1,
                           "title": "Album testing Changed",
                           "userId": "1",
                           "photoList": null
                       }
                   }
                """;

        mockMvc.perform(put("/bcncapp/api/albums/1")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    void patchOperationTest() throws Exception {

        String jsonResponse = """
                   {
                       "responseMessage": "El título del album ha sido actualizado correctamente",
                       "albumDto": null
                   }
                """;

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "Nuevo titulo");

        mockMvc.perform(patch("/bcncapp/api/albums/1")
                        .contentType("application/json")
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));
    }

    @Test
    void deleteOperationTest() throws Exception {

        doNothing().when(albumService).delete(1L);

        mockMvc.perform(delete("/bcncapp/api/albums/1"))
                .andExpect(status().isOk());

        verify(albumService, times(1)).delete(1L);
    }

}
