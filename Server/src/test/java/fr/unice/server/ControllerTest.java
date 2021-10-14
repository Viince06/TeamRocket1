package fr.unice.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StatService statService;

    @Test
    void addStatistics() throws Exception {
        this.mockMvc.perform(post("/stats/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Player 1\", \"points\":10}"))
                .andExpect(status().isOk());
    }
}
