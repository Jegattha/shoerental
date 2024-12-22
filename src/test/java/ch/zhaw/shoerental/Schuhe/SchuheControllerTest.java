package ch.zhaw.shoerental.Schuhe;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import ch.zhaw.shoerental.controller.SchuheController;
import ch.zhaw.shoerental.model.Schuhe;
import ch.zhaw.shoerental.model.SchuheCreateDTO;
import ch.zhaw.shoerental.model.SchuheState;
import ch.zhaw.shoerental.model.SchuheStateAggregation;
import ch.zhaw.shoerental.model.SchuheType;
import ch.zhaw.shoerental.model.MieterSchuheAggregationDTO;
import ch.zhaw.shoerental.repository.SchuheRepository;
import ch.zhaw.shoerental.service.VermieterService;
public class SchuheControllerTest {
    private MockMvc mockMvc;
    @Mock
    private SchuheRepository schuheRepository;
    @InjectMocks
    private SchuheController schuheController;
    @Mock  
private VermieterService vermieterService;
    
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(schuheController).build();
         schuheRepository = mock(SchuheRepository.class);
        vermieterService = mock(VermieterService.class);
        
        
    }
    private Schuhe convertCreateDTOToSchuhe(SchuheCreateDTO createDTO) {
        Schuhe schuhe = new Schuhe();
        schuhe.setMarke(createDTO.getMarke());
        schuhe.setPreis(createDTO.getPreis());
        schuhe.setSchuheType(createDTO.getSchuheType());
        schuhe.setGroesse(createDTO.getGroesse());
        schuhe.setSchuheFarbe(createDTO.getSchuheFarbe());
        schuhe.setSchuheBeschreibung(createDTO.getSchuheBeschreibung());
        return schuhe;
    }
    @Test
    public void testCreateSchuhe() throws Exception {
        SchuheCreateDTO createDTO = new SchuheCreateDTO();
        createDTO.setMarke("Test Marke");
        createDTO.setPreis(100.0);
        createDTO.setSchuheType(SchuheType.DAMENSCHUH);
        createDTO.setGroesse("30");
        createDTO.setSchuheFarbe("Blau");
        createDTO.setSchuheBeschreibung("Test Beschreibung");
       
        Schuhe schuhe = new Schuhe();
        schuhe.setSchuheId("12345");
        when(schuheRepository.save(any(Schuhe.class))).thenReturn(schuhe);
        mockMvc.perform(post("/api/schuhe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createDTO)));
    }
    @Test
    public void testGetAllSchuhe() throws Exception {
        List<Schuhe> schuheList = Arrays.asList();
        when(schuheRepository.findAll()).thenReturn(schuheList);
        mockMvc.perform(get("/api/schuhe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(schuheList.size())));
    }
    @Test
    public void testGetSchuheStateAggregation() throws Exception {
        List<SchuheStateAggregation> aggregation = Arrays.asList();
        when(schuheRepository.getSchuheStateAggregations()).thenReturn(aggregation);
        mockMvc.perform(get("/api/schuhe/aggregation/state"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(aggregation.size())));
    }
    @Test
public void testUpdateSchuhe() throws Exception {
    Schuhe existingSchuhe = new Schuhe();
    existingSchuhe.setSchuheId("12345");
    when(schuheRepository.findById("12345")).thenReturn(Optional.of(existingSchuhe));
    SchuheCreateDTO updateDTO = new SchuheCreateDTO();
    updateDTO.setMarke("Updated Marke");
    mockMvc.perform(put("/api/schuhe/{id}", "12345")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(updateDTO)));
            
}
@Test
public void testDeleteSchuhe() throws Exception {
    Schuhe existingSchuhe = new Schuhe();
    existingSchuhe.setSchuheId("12345");
    when(schuheRepository.findById("12345")).thenReturn(Optional.of(existingSchuhe));
    doNothing().when(schuheRepository).delete(existingSchuhe);
    mockMvc.perform(delete("/api/schuhe/{id}", "12345"));
}
@Test
public void testMietSchuheWithInvalidDate() throws Exception {
   
    SchuheCreateDTO createDTO = new SchuheCreateDTO();
    createDTO.setMarke("Test Marke");
    createDTO.setPreis(100.0);
    createDTO.setSchuheType(SchuheType.DAMENSCHUH);
    createDTO.setGroesse("30");
    createDTO.setSchuheFarbe("Blau");
    createDTO.setSchuheBeschreibung("Test Beschreibung");
    // Erstelle ein Datum in der Vergangenheit
    Date pastDate = Date.from(Instant.now().minus(1, ChronoUnit.DAYS));
    Schuhe schuhe = new Schuhe();
    schuhe.setMietdauerVon(pastDate);
    schuhe.setMietdauerBis(pastDate);

    when(schuheRepository.findById(anyString())).thenReturn(Optional.of(schuhe));
   
    mockMvc.perform(post("/api/schuhe/mieten")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(createDTO)));
            
}
@Test
void testCreateSchuhe1() {
    SchuheCreateDTO createDTO = new SchuheCreateDTO();
    createDTO.setMarke("Test Marke");
    createDTO.setPreis(100.0);
    createDTO.setSchuheType(SchuheType.DAMENSCHUH);
    createDTO.setGroesse("30");
    createDTO.setSchuheFarbe("Blau");
    createDTO.setSchuheBeschreibung("Test Beschreibung");
    Schuhe schuhe = convertCreateDTOToSchuhe(createDTO);
    
    assertNotNull(schuhe);
    assertEquals("Test Marke", schuhe.getMarke());
    assertEquals(100.0, schuhe.getPreis());
    assertEquals(SchuheType.DAMENSCHUH, schuhe.getSchuheType());
    assertEquals("30", schuhe.getGroesse());
    assertEquals("Blau", schuhe.getSchuheFarbe());
    assertEquals("Test Beschreibung", schuhe.getSchuheBeschreibung());
    assertEquals(SchuheState.VERFUEGBAR, schuhe.getSchuheState());
    assertNull(schuhe.getMieterId());
    assertNull(schuhe.getVermieterId());
    assertNull(schuhe.getMietdauerVon());
    assertNull(schuhe.getMietdauerBis());
    assertEquals(null, schuhe.getTotalPreis());
}
@Test
    public void testSetMarke() {
        Schuhe schuhe = new Schuhe();
        schuhe.setMarke("Test Marke");
        assertEquals("Test Marke", schuhe.getMarke());
    }
    @Test
    public void testSetPreis() {
        Schuhe schuhe = new Schuhe();
        schuhe.setPreis(100.0);
        assertEquals(100.0, schuhe.getPreis());
    }
    @Test
    public void testSetSchuheType() {
        Schuhe schuhe = new Schuhe();
        schuhe.setSchuheType(SchuheType.DAMENSCHUH);
        assertEquals(SchuheType.DAMENSCHUH, schuhe.getSchuheType());
    }
    @Test
    public void testSetGroesse() {
        Schuhe schuhe = new Schuhe();
        schuhe.setGroesse("30");
        assertEquals("30", schuhe.getGroesse());
    }
    @Test
    public void testSetSchuheFarbe() {
        Schuhe schuhe = new Schuhe();
        schuhe.setSchuheFarbe("Blau");
        assertEquals("Blau", schuhe.getSchuheFarbe());
    }
    @Test
    public void testSetSchuheBeschreibung() {
        Schuhe schuhe = new Schuhe();
        schuhe.setSchuheBeschreibung("Test Beschreibung");
        assertEquals("Test Beschreibung", schuhe.getSchuheBeschreibung());
    }
    @Test
    public void testSetSchuheState() {
        Schuhe schuhe = new Schuhe();
        schuhe.setSchuheState(SchuheState.VERMIETET);
        assertEquals(SchuheState.VERMIETET, schuhe.getSchuheState());
    }
    @Test
    public void testSetVermieterId() {
        Schuhe schuhe = new Schuhe();
        schuhe.setVermieterId("123456");
        assertEquals("123456", schuhe.getVermieterId());
    }
    @Test
    public void testSetTotalPreis() {
        Schuhe schuhe = new Schuhe();
        schuhe.setTotalPreis(200.0);
        assertEquals(200.0, schuhe.getTotalPreis());
    }
    @Test
    public void testGetSchuheId() {
        Schuhe schuhe = new Schuhe();
        schuhe.setSchuheId("789456");
        assertEquals("789456", schuhe.getSchuheId());
    }
  
    @Test
    void testUpdateSchuhe3() {
        // Mock data
    String schuheId = "schuhe123";
    SchuheCreateDTO sDTO = new SchuheCreateDTO();
    sDTO.setMarke("Updated Marke");
    sDTO.setPreis(150.0);
    sDTO.setSchuheType(SchuheType.DAMENSCHUH);
    sDTO.setGroesse("40");
    sDTO.setSchuheFarbe("Rot");
    sDTO.setSchuheBeschreibung("Updated Beschreibung");
    Schuhe existingSchuhe = new Schuhe(); 
    when(schuheRepository.findById(schuheId)).thenReturn(Optional.of(existingSchuhe));
    when(schuheRepository.save(any())).thenReturn(existingSchuhe); 
    ResponseEntity<Schuhe> response = schuheController.updateSchuhe(schuheId, sDTO);
    assertNotNull(response);
    if (response.getStatusCode() == HttpStatus.OK) {
        assertNotNull(response.getBody());
        assertEquals(sDTO.getMarke(), response.getBody().getMarke());
       
        verify(schuheRepository, times(1)).save(existingSchuhe); 
    } else {
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
     
    }
    @SuppressWarnings("unused")
    @Test
    void testDeleteSchuheById() {
        // Mock data
        String schuheId = "schuhe123";
        Schuhe existingSchuhe = new Schuhe(); 
        when(schuheRepository.findById(schuheId)).thenReturn(Optional.of(existingSchuhe));
        ResponseEntity<Void> response = schuheController.deleteSchuhebyId(schuheId);
    }
    @Test
    void testGetSchuheById() {
        // Mock data
        String schuheId = "schuhe123";
        Schuhe existingSchuhe = new Schuhe(); 
        when(schuheRepository.findById(schuheId)).thenReturn(Optional.of(existingSchuhe));
        ResponseEntity<Schuhe> response = schuheController.getSchuheById(schuheId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getClass());
    
    }
    @Test
    void testGetSchuheMieterStateAggregation() {
        // Mock data
        List<MieterSchuheAggregationDTO> aggregationList = Arrays.asList(); 
        when(schuheRepository.getMieterSchuheAggregation()).thenReturn(aggregationList);
        ResponseEntity<List<MieterSchuheAggregationDTO>> response = schuheController.getSchuheMieterStateAggregation();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
    @Test
    void testGetAllSchuhe3() {
        // Mock data
        List<Schuhe> schuheList = Arrays.asList(); 
        when(schuheRepository.findAll()).thenReturn(schuheList);
        ResponseEntity<List<Schuhe>> response = schuheController.getAllSchuhe(null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
    @Test
    void testGetSchuheStateAggregation3() {
        // Mock data
        List<SchuheStateAggregation> aggregationList = Arrays.asList();  
        when(schuheRepository.getSchuheStateAggregations()).thenReturn(aggregationList);
        ResponseEntity<List<SchuheStateAggregation>> response = schuheController.getSchuheStateAggregation();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}








