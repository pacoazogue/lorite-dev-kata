package dev.franciscolorite.pruebatecnicabcnc.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.franciscolorite.pruebatecnicabcnc.BcncApplication;
import dev.franciscolorite.pruebatecnicabcnc.service.DataCollectorService;
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

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { BcncApplication.class })
@WebAppConfiguration
class DataCollectorControllerIT {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        this.mockMvc
                .perform(get("/bcncapp/api/loadDataIntoMemory"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenWebApplicationContext_whenServletContext_thenItProvidesDataCollectorController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("dataCollectorController"));
    }

    @Test
    public void loadDataFromJsonPlaceHolderServerIntoMemoryIntegrationTest() throws Exception {

        JsonNode json;

        try (InputStream inputStream =
                     TypeReference.class.getResourceAsStream("/data/loadJsonPlaceHolderDataViaMemoryResults.json")) {
            json = objectMapper.readValue(inputStream, JsonNode.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON data", e);
        }

        this.mockMvc
                .perform(get("/bcncapp/api/loadDataIntoMemory"))
                .andExpect(status().isOk())
                .andExpect(content().json(json.toPrettyString()));
    }

    @Test
    public void loadDataFromJsonPlaceHolderServerIntoH2MemoryIntegrationTest() throws Exception {

        this.mockMvc
                .perform(get("/bcncapp/api/loadDataIntoH2Memory"))
                .andExpect(status().isOk())
                .andExpect(content().string(DataCollectorService.LOAD_DATA_FROM_JSONPLACEHOLDER_SERVER_SUCCESS_MESSAGE));

    }
}