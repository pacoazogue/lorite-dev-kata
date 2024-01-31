package dev.franciscolorite.pruebatecnicabcnc.controller;

import dev.franciscolorite.pruebatecnicabcnc.BcncApplication;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { BcncApplication.class })
@WebAppConfiguration
class PhotoControllerIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void givenWebApplicationContext_whenServletContext_thenItProvidesDataCollectorController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertInstanceOf(MockServletContext.class, servletContext);
        assertNotNull(webApplicationContext.getBean("photoController"));
    }

    /**
     * Test de integración
     * 1. Se crea una foto via POST
     * 2. Se recupera dicho registro via GET
     *
     * @throws Exception
     */
    @Test
    public void givenValidPhoto_whenPhotoIsCreatedViaPost_thenPhotoCanBeFoundById() throws Exception {

        String jsonRequest = """
               {
                   "albumId": 1,
                   "title": "Foto testing",
                   "url": "urlFake",
                   "thumbnailUrl": "thumbnailUrlFake"
                }
                """;
        mockMvc.perform(post("/bcncapp/api/photos")
                        .contentType("application/json")
                        .content(jsonRequest)
                ).andExpect(status().isCreated())
                .andDo(
                        result -> mockMvc.perform(get("/bcncapp/api/photos/1")).andExpect(status().isOk()));
    }


    /**
     * Test de integración
     * 1. Se borra la foto cuyo id es 1
     * 2. Se intenta recuperar dicha foto, pero no existe
     * @throws Exception
     */
    @Test
    public void givenNotInsertedPhotoId_whenPhotoIsSearched_thenError404IsObtained() throws Exception {

        mockMvc.perform(delete("/bcncapp/api/photos/1"))
                .andExpect(status().isOk())
                .andDo(result ->
                        mockMvc.perform(get("/bcncapp/api/photos/1").contentType("application/json"))
                        .andExpect(status().isNotFound()));
    }

    /**
     * Test de integración
     * 1. Se crea una foto via POST
     * 2. Se modifica la fotografia via PUT
     *
     * @throws Exception
     */
    @Test
    public void givenPhotoCreated_whenPhotoIsModifiedViaPut_thenPhotoInformationIsUpdated() throws Exception {

        String jsonPhotoCreationRequest = """
               {
                   "albumId": 1,
                   "title": "Foto testing",
                   "url": "urlFake",
                   "thumbnailUrl": "thumbnailUrlFake"
                }
                """;
        String jsonPhotoModificationRequest = """
               {
                   "albumId": 2,
                   "title": "Foto testing cambiada",
                   "url": "urlFakecambiada",
                   "thumbnailUrl": "thumbnailUrlFakecambiada"
                }
                """;

        String jsonResponse = """
                {
                    "responseMessage": "La fotografía se ha actualizado correctamente",
                    "photoDto": {
                        "id": 1,
                        "albumId": "2",
                        "title": "Foto testing cambiada",
                        "url": "urlFakecambiada",
                         "thumbnailUrl": "thumbnailUrlFakecambiada"
                    }
                }
                """;

        mockMvc.perform(post("/bcncapp/api/photos")
                        .contentType("application/json")
                        .content(jsonPhotoCreationRequest)
                ).andExpect(status().isCreated())
                .andDo(
                        result -> mockMvc.perform(put("/bcncapp/api/photos/1")
                                .contentType("application/json")
                                .content(jsonPhotoModificationRequest))
                                .andExpect(status().isCreated())
                                .andExpect(content().json(jsonResponse)));
    }
}