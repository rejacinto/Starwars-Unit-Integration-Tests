package com.mercadolibre.starwars.controller;

import com.mercadolibre.starwars.dto.CharacterDTO;
import com.mercadolibre.starwars.util.TestUtilsGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest
public class FindControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findTestOk() throws Exception {
        MvcResult result = mockMvc.perform(get("/{query}", "Luke"))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();

        List<CharacterDTO> expectedList = List.of(
                TestUtilsGenerator.getLukeSkywalker()
        );
        String jsonParsedList = TestUtilsGenerator.getJsonPayload(expectedList);
        result.getResponse().setCharacterEncoding("UTF-8");
        assertEquals(jsonParsedList, result.getResponse().getContentAsString());
    }

    @Test
    public void findTestEmptyResponse() throws Exception {
        String query = "asdasdasd";
        MvcResult result = mockMvc.perform(get("/{query}", query))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();

        assertEquals("[]", result.getResponse().getContentAsString());
    }

}
