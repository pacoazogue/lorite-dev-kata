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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { BcncApplication.class })
@WebAppConfiguration
class AlbumControllerIT {

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
        assertNotNull(webApplicationContext.getBean("albumController"));
    }

    /**
     * Test de integración
     * 1. Se crea un album via POST
     * 2. Se recupera dicho registro via GET
     *
     * @throws Exception
     */
    @Test
    public void givenValidAlbum_whenAlbumIsCreatedViaPost_thenAlbumCanBeFoundById() throws Exception {

        String jsonRequest = """
                {
                   "userId": 1,
                   "title": "Album testing"
                }
                """;
        mockMvc.perform(post("/bcncapp/api/albums")
                        .contentType("application/json")
                        .content(jsonRequest)
                ).andExpect(status().isCreated())
                .andDo(
                        result -> mockMvc.perform(get("/bcncapp/api/albums/1")).andExpect(status().isOk()));
    }

    /**
     * Test de integración
     * 1. Se borra el album cuyo id es 1
     * 2. Se intenta recuperar dicho album, pero no existe
     * @throws Exception
     */
    @Test
    public void givenNotInsertedAlbumId_whenAlbumIsSearched_thenError404IsObtained() throws Exception {

        mockMvc.perform(delete("/bcncapp/api/albums/1"))
                .andExpect(status().isOk())
                .andDo(result ->
                        mockMvc.perform(get("/bcncapp/api/albums/1").contentType("application/json"))
                        .andExpect(status().isNotFound()));
    }
}