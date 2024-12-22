package ch.zhaw.shoerental.Service;

import ch.zhaw.shoerental.controller.ServiceController;
import ch.zhaw.shoerental.model.*;
import ch.zhaw.shoerental.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class ServiceControllerTestIntegration {

    private MockMvc mockMvc;

    @InjectMocks
    private ServiceController serviceController;

    @Mock
    private SchuheService schuheService;

    @Mock
    private MieterService mieterService;

    @Mock
    private VermieterService vermieterService;

    @Mock
    private MailService mailService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(serviceController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testAssignSchuheSuccess() throws Exception {
        // Arrange: DTO erstellen und Felder mit Settern setzen
        SchuheStateChangeDTO changeDTO = new SchuheStateChangeDTO();
        changeDTO.setMieterId("mieter123");
        changeDTO.setSchuheId("schuhe123");
        changeDTO.setMietdauerVon(new Date());
        changeDTO.setMietdauerBis(new Date());
    
        // Mock für Schuhe erstellen
        Schuhe schuhe = new Schuhe();
        schuhe.setSchuheId("schuhe123");
        schuhe.setVermieterId("vermieter123");
    
        when(schuheService.assignSchuhe("schuhe123", "mieter123")).thenReturn(Optional.of(schuhe));
        when(mieterService.getEmail()).thenReturn("mieter@example.com");
        when(vermieterService.getEmailById("vermieter123")).thenReturn(Optional.of("vermieter@example.com"));
        when(mailService.sendMail(any(Mail.class))).thenReturn(true);
    
        // Act & Assert: Endpunkt aufrufen und Ergebnis prüfen
        mockMvc.perform(put("/api/service/assignSchuhe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.schuheId").value("schuhe123"));
    
        // Überprüfen, ob E-Mails gesendet wurden
        verify(mailService, times(2)).sendMail(any(Mail.class));
    }
    

    
    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testAvailableSchuheSuccess() throws Exception {
        // Arrange: DTO erstellen
        AvailabeSchuheDTO availableDTO = new AvailabeSchuheDTO();
        availableDTO.setSchuheId("schuhe123");
    
        // Schuhe-Objekt erstellen
        Schuhe schuhe = new Schuhe();
        schuhe.setSchuheId("schuhe123");
        schuhe.setVermieterId("vermieter123");
    
        // Mock-Verhalten konfigurieren
        when(schuheService.availableSchuhe("schuhe123")).thenReturn(Optional.of(schuhe));
        when(vermieterService.getEmailById("vermieter123")).thenReturn(Optional.of("vermieter@example.com"));
        when(mailService.sendMail(any(Mail.class))).thenReturn(true);
    
        // Act: Endpunkt aufrufen
        mockMvc.perform(post("/api/service/availableSchuhe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(availableDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.schuheId").value("schuhe123"));
    
        // Verify: sendMail wurde genau einmal aufgerufen
        verify(mailService, times(1)).sendMail(any(Mail.class));
    }
    
    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testMietSchuheSuccess() throws Exception {
        // Arrange: DTO erstellen und Felder mit Settern setzen
        SchuheStateChangeDTO changeDTO = new SchuheStateChangeDTO();
        changeDTO.setMieterId("mieter123");
        changeDTO.setSchuheId("schuhe123");
        changeDTO.setMietdauerVon(new Date());
        changeDTO.setMietdauerBis(new Date());

        Schuhe schuhe = new Schuhe();
        schuhe.setSchuheId("schuhe123");
        schuhe.setVermieterId("vermieter123");

        when(schuheService.mietSchuhe("schuhe123", "mieter123", changeDTO.getMietdauerVon(), changeDTO.getMietdauerBis()))
                .thenReturn(Optional.of(schuhe));
        when(mieterService.getEmail()).thenReturn("mieter@example.com");
        when(vermieterService.getEmailById("vermieter123")).thenReturn(Optional.of("vermieter@example.com"));
        when(mailService.sendMail(any(Mail.class))).thenReturn(true);

        // Act & Assert: Endpunkt aufrufen und Ergebnis prüfen
        mockMvc.perform(post("/api/service/mietSchuhe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.schuheId").value("schuhe123"));

        // Verifizieren, dass zwei E-Mails gesendet wurden
        verify(mailService, times(2)).sendMail(any(Mail.class));
    }

  
    @Test
@WithMockUser(username = "testUser", roles = {"USER"})
void testSendAboutMailSuccess() throws Exception {
    Mail mail = new Mail();
    mail.setTo("about@example.com");
    mail.setSubject("About Us Inquiry");
    mail.setMessage("This is a test message for about.");

    when(mailService.sendMail(any(Mail.class))).thenReturn(true);

    mockMvc.perform(post("/api/service/about/sendMail")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(mail)))
            .andExpect(status().isOk())
            .andExpect(content().string("E-Mail erfolgreich gesendet!"));

    verify(mailService, times(1)).sendMail(any(Mail.class));
}

@Test
@WithMockUser(username = "testUser", roles = {"USER"})
void testSendAboutMailFailure() throws Exception {
    Mail mail = new Mail();
    mail.setTo("about@example.com");
    mail.setSubject("About Us Inquiry");
    mail.setMessage("This is a test message for about.");

    when(mailService.sendMail(any(Mail.class))).thenReturn(false);

    mockMvc.perform(post("/api/service/about/sendMail")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(mail)))
            .andExpect(status().isInternalServerError())
            .andExpect(content().string("Fehler beim Senden der E-Mail."));

    verify(mailService, times(1)).sendMail(any(Mail.class));
}

}
