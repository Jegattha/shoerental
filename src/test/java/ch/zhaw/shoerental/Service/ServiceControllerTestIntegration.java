package ch.zhaw.shoerental.Service;

import ch.zhaw.shoerental.model.Schuhe;
import ch.zhaw.shoerental.model.SchuheType;
import ch.zhaw.shoerental.model.SchuheStateChangeDTO;
import ch.zhaw.shoerental.model.AvailabeSchuheDTO;
import ch.zhaw.shoerental.repository.SchuheRepository;

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
public class ServiceControllerTestIntegration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SchuheRepository schuheRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String username = "admin";
    private String password = "password";

    @BeforeEach
    void setUp() {
        // Lösche alle Einträge aus der Schuhe-Collection vor jedem Test
        schuheRepository.deleteAll();
    }

    @Test
    void testAssignSchuhe() throws Exception {
        // Schuhe erstellen und speichern
        Schuhe schuhe = new Schuhe("NIKE", 100.0, SchuheType.FRAUENSCHUH, "42", "Schwarz", "Hochwertige Laufschuhe", "Vermieter123");
        schuheRepository.save(schuhe);

        // DTO für die Anfrage
        SchuheStateChangeDTO changeDTO = new SchuheStateChangeDTO();
        changeDTO.setSchuheId(schuhe.getSchuheId());
        changeDTO.setMieterId("Mieter123");

        mockMvc.perform(put("/api/service/assignSchuhe")
                .with(httpBasic(username, password)) // Authentifizierung
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marke").value("NIKE"))
                .andExpect(jsonPath("$.mieterId").value("Mieter123"));
    }

    @Test
    void testAvailableSchuhe() throws Exception {
        // Schuhe erstellen und speichern
        Schuhe schuhe = new Schuhe("Adidas", 80.0, SchuheType.MAENNERSCHUH, "41", "Weiß", "Bequeme Freizeitschuhe", "Vermieter456");
        schuheRepository.save(schuhe);

        AvailabeSchuheDTO availableDTO = new AvailabeSchuheDTO();
        availableDTO.setSchuheId(schuhe.getSchuheId());

        mockMvc.perform(post("/api/service/availableSchuhe")
                .with(httpBasic(username, password)) // Authentifizierung
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(availableDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.schuheId").value(schuhe.getSchuheId()))
                .andExpect(jsonPath("$.marke").value("Adidas"));
    }

    @Test
    void testMietSchuhe() throws Exception {
        // Schuhe erstellen und speichern
        Schuhe schuhe = new Schuhe("Puma", 70.0, SchuheType.FRAUENSCHUH, "43", "Blau", "Robuste Trainingsschuhe", "Vermieter789");
        schuheRepository.save(schuhe);

        SchuheStateChangeDTO mietDTO = new SchuheStateChangeDTO();
        mietDTO.setSchuheId(schuhe.getSchuheId());
        mietDTO.setMieterId("Mieter456");

        mockMvc.perform(post("/api/service/mietSchuhe")
                .with(httpBasic(username, password)) // Authentifizierung
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mietDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marke").value("Puma"))
                .andExpect(jsonPath("$.mieterId").value("Mieter456"));
    }

    @Test
    void testSendVerificationMail() throws Exception {
        String mailPayload = """
                {
                    "to": "test@example.com",
                    "subject": "Verification",
                    "message": "This is a test verification email"
                }
                """;

        mockMvc.perform(post("/api/service/verifizierung/sendMail")
                .with(httpBasic(username, password)) // Authentifizierung
                .contentType(MediaType.APPLICATION_JSON)
                .content(mailPayload))
                .andExpect(status().isOk())
                .andExpect(content().string("E-Mail erfolgreich gesendet!"));
    }

    @Test
    void testSendAboutMail() throws Exception {
        String mailPayload = """
                {
                    "to": "about@example.com",
                    "subject": "About Request",
                    "message": "This is an about page email"
                }
                """;

        mockMvc.perform(post("/api/service/about/sendMail")
                .with(httpBasic(username, password)) // Authentifizierung
                .contentType(MediaType.APPLICATION_JSON)
                .content(mailPayload))
                .andExpect(status().isOk())
                .andExpect(content().string("E-Mail erfolgreich gesendet!"));
    }
}
    