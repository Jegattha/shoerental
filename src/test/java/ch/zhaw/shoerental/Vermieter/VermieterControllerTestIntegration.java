package ch.zhaw.shoerental.Vermieter;

import ch.zhaw.shoerental.model.Vermieter;
import ch.zhaw.shoerental.repository.VermieterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VermieterControllerTestIntegration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VermieterRepository vermieterRepository;

 

    @Autowired
    private ObjectMapper objectMapper;

    private String username = "admin";
    private String password = "password";

    @BeforeEach
    void setUp() {
        // Lösche alle Einträge und erstelle Test-Benutzer
        vermieterRepository.deleteAll();
        
        // Optional: Erstellen eines Testbenutzers, falls benötigt
        // userRepository.save(new User(username, passwordEncoder.encode(password), "ROLE_ADMIN"));
    }

    @Test
    void testGetAllVermieter() throws Exception {
        Vermieter v1 = new Vermieter("Max Mustermann", "max@example.com", "123456", "Hauptstr. 1", "8000", "Zürich");
        vermieterRepository.save(v1);

        mockMvc.perform(get("/api/vermieter")
                .with(httpBasic(username, password)) // Basic Auth
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Max Mustermann"));
    }

    @Test
    void testGetVermieterById() throws Exception {
        Vermieter v1 = new Vermieter("Max Mustermann", "max@example.com", "123456", "Hauptstr. 1", "8000", "Zürich");
        vermieterRepository.save(v1);

        mockMvc.perform(get("/api/" + v1.getVermieterId())
                .with(httpBasic(username, password)) // Basic Auth
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Max Mustermann"));
    }

    @Test
    void testCreateVermieter_Valid() throws Exception {
        Vermieter newVermieter = new Vermieter("Lisa Müller", "lisa@example.com", "987654", "Musterstr. 2", "9000", "Bern");

        mockMvc.perform(post("/api/vermieter")
                .with(httpBasic(username, password)) // Basic Auth
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newVermieter)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Lisa Müller"));
    }

    @Test
    void testDeleteVermieter() throws Exception {
        Vermieter v1 = new Vermieter("Max Mustermann", "max@example.com", "123456", "Hauptstr. 1", "8000", "Zürich");
        vermieterRepository.save(v1);

        mockMvc.perform(delete("/api/vermieter/delete/" + v1.getVermieterId())
                .with(httpBasic(username, password))) // Basic Auth
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteVermieter_NotFound() throws Exception {
        mockMvc.perform(delete("/api/vermieter/delete/123")
                .with(httpBasic(username, password))) // Basic Auth
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateVermieter_InvalidEmail() throws Exception {
        Vermieter invalidVermieter = new Vermieter("Invalid User", "invalid-email", "987654", "Musterstr. 2", "9000", "Bern");

        mockMvc.perform(post("/api/vermieter")
                .with(httpBasic(username, password)) // Basic Auth
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidVermieter)))
                .andExpect(status().isBadRequest());
    }
}
