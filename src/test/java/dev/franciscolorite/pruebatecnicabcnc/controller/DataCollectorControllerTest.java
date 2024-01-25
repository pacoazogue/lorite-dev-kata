package dev.franciscolorite.pruebatecnicabcnc.controller;

import dev.franciscolorite.pruebatecnicabcnc.service.DataCollectorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(DataCollectorController.class)
class DataCollectorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DataCollectorService dataCollectorService;

    @Test
    void loadDataFromJsonPlaceHolderServerIntoH2MemoryTest() throws Exception {

       mockMvc.perform(MockMvcRequestBuilders.get("/bcncapp/api/loadDataIntoH2Memory"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}