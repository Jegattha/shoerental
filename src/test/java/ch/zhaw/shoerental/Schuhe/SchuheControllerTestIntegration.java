package ch.zhaw.shoerental.Schuhe;

import ch.zhaw.shoerental.controller.ServiceController;
import ch.zhaw.shoerental.model.*;
import ch.zhaw.shoerental.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SchuheControllerTestIntegration {

    private MockMvc mockMvc;

    @Mock
    private SchuheService schuheService;

    @Mock
    private MieterService mieterService;

    @Mock
    private VermieterService vermieterService;

    @Mock
    private MailService mailService;

    private ServiceController serviceController;

    @BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
    serviceController = new ServiceController(schuheService, mieterService, vermieterService, mailService);
    mockMvc = MockMvcBuilders.standaloneSetup(serviceController).build();
}


    @Test
    void testAssignSchuhe() throws Exception {
        SchuheStateChangeDTO dto = new SchuheStateChangeDTO();
        dto.setMieterId("mieter1");
        dto.setSchuheId("schuhe1");

        Schuhe schuhe = new Schuhe();
        schuhe.setSchuheId("schuhe1");
        schuhe.setVermieterId("vermieter1");

        when(schuheService.assignSchuhe("schuhe1", "mieter1")).thenReturn(Optional.of(schuhe));
        when(mieterService.getEmail()).thenReturn("mieter@example.com");
        when(vermieterService.getEmailById("vermieter1")).thenReturn(Optional.of("vermieter@example.com"));

        mockMvc.perform(put("/api/service/assignSchuhe")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "mieterId": "mieter1",
                            "schuheId": "schuhe1"
                        }
                        """))
                .andExpect(status().isOk());
    }

    @Test
    void testAvailableSchuhe() throws Exception {
        AvailabeSchuheDTO dto = new AvailabeSchuheDTO();
        dto.setSchuheId("schuhe1");

        Schuhe schuhe = new Schuhe();
        schuhe.setSchuheId("schuhe1");
        schuhe.setVermieterId("vermieter1");

        when(schuheService.availableSchuhe("schuhe1")).thenReturn(Optional.of(schuhe));
        when(vermieterService.getEmailById("vermieter1")).thenReturn(Optional.of("vermieter@example.com"));
        when(mailService.sendMail(any(Mail.class))).thenReturn(true);

        mockMvc.perform(post("/api/service/availableSchuhe")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "schuheId": "schuhe1"
                        }
                        """))
                .andExpect(status().isOk());
    }

    @Test
    void testMietSchuhe() throws Exception {
        SchuheStateChangeDTO dto = new SchuheStateChangeDTO();
        dto.setMieterId("mieter1");
        dto.setSchuheId("schuhe1");
        dto.setMietdauerVon(new Date());
        dto.setMietdauerBis(new Date());

        Schuhe schuhe = new Schuhe();
        schuhe.setSchuheId("schuhe1");
        schuhe.setVermieterId("vermieter1");

        when(schuheService.mietSchuhe(eq("schuhe1"), eq("mieter1"), any(Date.class), any(Date.class)))
                .thenReturn(Optional.of(schuhe));
        when(mieterService.getEmail()).thenReturn("mieter@example.com");
        when(vermieterService.getEmailById("vermieter1")).thenReturn(Optional.of("vermieter@example.com"));

        mockMvc.perform(post("/api/service/mietSchuhe")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "mieterId": "mieter1",
                            "schuheId": "schuhe1",
                            "mietdauerVon": "2024-12-21T10:00:00.000Z",
                            "mietdauerBis": "2024-12-28T10:00:00.000Z"
                        }
                        """))
                .andExpect(status().isOk());
    }

    @Test
    void testSendAboutMail() throws Exception {
        when(mailService.sendMail(any(Mail.class))).thenReturn(true);

        mockMvc.perform(post("/api/service/about/sendMail")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "to": "info@example.com",
                            "subject": "Test Subject",
                            "message": "Test Message"
                        }
                        """))
                .andExpect(status().isOk());
    }
}
