package ch.zhaw.shoerental.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import ch.zhaw.shoerental.controller.ServiceController;
import ch.zhaw.shoerental.model.AvailabeSchuheDTO;
import ch.zhaw.shoerental.model.Schuhe;
import ch.zhaw.shoerental.model.SchuheStateChangeDTO;
import ch.zhaw.shoerental.service.MieterService;
import ch.zhaw.shoerental.service.SchuheService;
import ch.zhaw.shoerental.service.VermieterService;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceControllerTest {

  @Mock
private MieterService mieterService;

@Mock
private VermieterService vermieterService;

@InjectMocks
private ServiceController serviceController;

 

    @Mock
    private SchuheService schuheService;

    

        @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
    }

    @Test
    void testAssignSchuhe() {
        SchuheStateChangeDTO changeDTO = new SchuheStateChangeDTO();
        changeDTO.setMieterId("mieter123");
        changeDTO.setSchuheId("schuhe123");

        Schuhe assignedSchuhe = new Schuhe();
        when(schuheService.assignSchuhe("schuhe123", "mieter123")).thenReturn(Optional.of(assignedSchuhe));

        ResponseEntity<Schuhe> response = serviceController.assignSchuhe(changeDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(assignedSchuhe, response.getBody());
    }

    @Test
    void testAssignSchuheBadRequest() {
        SchuheStateChangeDTO changeDTO = new SchuheStateChangeDTO();
        changeDTO.setMieterId("mieter123");
        changeDTO.setSchuheId("kleidung123");

        when(schuheService.assignSchuhe("schuhe123", "mieter123")).thenReturn(Optional.empty());

        ResponseEntity<Schuhe> response = serviceController.assignSchuhe(changeDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testAvailableSchuhe() {
        AvailabeSchuheDTO changeDTO = new AvailabeSchuheDTO();
        changeDTO.setSchuheId("schuhe123");

        Schuhe availableSchuhe = new Schuhe();
        when(schuheService.availableSchuhe("schuhe123")).thenReturn(Optional.of(availableSchuhe));
        ResponseEntity<Schuhe> response = serviceController.availableSchuhe(changeDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(availableSchuhe, response.getBody());
    }

    @Test
    void testAvailableSchuheBadRequest() {
        AvailabeSchuheDTO changeDTO = new AvailabeSchuheDTO();
        changeDTO.setSchuheId("schuhe123");
        when(schuheService.availableSchuhe("schuhe123")).thenReturn(Optional.empty());
        ResponseEntity<Schuhe> response = serviceController.availableSchuhe(changeDTO);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testMietSchuhe() throws ParseException {
        SchuheStateChangeDTO changeDTO = new SchuheStateChangeDTO();
        changeDTO.setMieterId("mieter123");
        changeDTO.setSchuheId("schuhe123");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        changeDTO.setMietdauerVon(dateFormat.parse("2023-01-01"));
        changeDTO.setMietdauerBis(dateFormat.parse("2023-02-01"));

        Schuhe rentedSchuhe = new Schuhe();
        when(schuheService.mietSchuhe("schuhe123", "mieter123",
                dateFormat.parse("2023-01-01"), dateFormat.parse("2023-02-01")))
                .thenReturn(Optional.of(rentedSchuhe));

        ResponseEntity<Schuhe> response = serviceController.mietSchuhe(changeDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(rentedSchuhe, response.getBody());
    }

    @Test
    void testMietSchuheBadRequest() throws ParseException {
        SchuheStateChangeDTO changeDTO = new SchuheStateChangeDTO();
        changeDTO.setMieterId("mieter123");
        changeDTO.setSchuheId("schuhe123");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        changeDTO.setMietdauerVon(dateFormat.parse("2023-01-01"));
        changeDTO.setMietdauerBis(dateFormat.parse("2023-02-01"));

        when(schuheService.mietSchuhe("schuhe123", "mieter123",
                dateFormat.parse("2023-01-01"), dateFormat.parse("2023-02-01")))
                .thenReturn(Optional.empty());

        ResponseEntity<Schuhe> response = serviceController.mietSchuhe(changeDTO);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}
