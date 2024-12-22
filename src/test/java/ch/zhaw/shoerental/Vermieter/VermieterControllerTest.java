package ch.zhaw.shoerental.Vermieter;

import ch.zhaw.shoerental.controller.VermieterController;
import ch.zhaw.shoerental.model.MailInformation;
import ch.zhaw.shoerental.model.Vermieter;
import ch.zhaw.shoerental.model.VermieterCreateDTO;
import ch.zhaw.shoerental.repository.VermieterRepository;
import ch.zhaw.shoerental.service.MailValidatorService;
import ch.zhaw.shoerental.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VermieterControllerTest {

    @Mock
    private VermieterRepository vermieterRepository;

    @Mock
    private MailValidatorService mailValidatorService;

    @Mock
    private RoleService roleService;

    private VermieterController vermieterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Verwende den Konstruktor, um die Abhängigkeiten bereitzustellen
        vermieterController = new VermieterController(vermieterRepository, mailValidatorService, roleService);
    }

    @Test
    void testGetAllVermieter() {
        Page<Vermieter> vermieterPage = new PageImpl<>(Collections.singletonList(new Vermieter()));
        when(roleService.userHasRole("admin")).thenReturn(true);
        when(vermieterRepository.findAll(PageRequest.of(0, 2))).thenReturn(vermieterPage);

        ResponseEntity<Page<Vermieter>> response = vermieterController.getAllVermieter(1, 2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(vermieterPage, response.getBody());
    }

    @Test
    void testGetAllVermieterForbidden() {
        when(roleService.userHasRole("admin")).thenReturn(false);

        ResponseEntity<Page<Vermieter>> response = vermieterController.getAllVermieter(1, 2);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testGetVermieterById() {
        String vermieterId = "1";
        Vermieter vermieter = new Vermieter();
        when(roleService.userHasRole("admin")).thenReturn(true);
        when(vermieterRepository.findById(vermieterId)).thenReturn(Optional.of(vermieter));

        ResponseEntity<Vermieter> response = vermieterController.getVermieterById(vermieterId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(vermieter, response.getBody());
    }

    @Test
    void testGetVermieterByIdNotFound() {
        String vermieterId = "1";
        when(roleService.userHasRole("admin")).thenReturn(true);
        when(vermieterRepository.findById(vermieterId)).thenReturn(Optional.empty());

        ResponseEntity<Vermieter> response = vermieterController.getVermieterById(vermieterId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetVermieterByIdForbidden() {
        when(roleService.userHasRole("admin")).thenReturn(false);

        ResponseEntity<Vermieter> response = vermieterController.getVermieterById("1");

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testCreateVermieter() {
        // Arrange
        VermieterCreateDTO vDTO = new VermieterCreateDTO(
            "John Doe", 
            "john.doe@example.com", 
            "123456789", 
            "Teststrasse 1", 
            "8000", 
            "Zürich"
        );
        Vermieter vermieter = new Vermieter(
            vDTO.getName(), 
            vDTO.getEmail(), 
            vDTO.getTelefonnummer(), 
            vDTO.getAdresse(), 
            vDTO.getPlz(), 
            vDTO.getOrt()
        );
    
        // Mock MailInformation
        MailInformation mailInfo = mock(MailInformation.class);
        when(mailInfo.isDns()).thenReturn(true);
        when(mailInfo.isFormat()).thenReturn(true);
        when(mailInfo.isDisposable()).thenReturn(false);
    
        // Mock Services
        when(roleService.userHasRole("admin")).thenReturn(true);
        when(mailValidatorService.validateEmail(vDTO.getEmail())).thenReturn(mailInfo);
        when(vermieterRepository.save(any(Vermieter.class))).thenReturn(vermieter);
    
        // Act
        ResponseEntity<Vermieter> response = vermieterController.createVermieter(vDTO);
    
        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(vermieter, response.getBody());
    }
    

    @Test
void testCreateVermieterInvalidEmail() {
    VermieterCreateDTO vDTO = new VermieterCreateDTO(
        "Invalid Vermieter",
        "invalid-email",
        "123456789",
        "Teststrasse 1",
        "8000",
        "Zürich"
    );

    // Erstelle ein Vermieter-Objekt, das vom Repository zurückgegeben werden soll
    Vermieter vDAO = new Vermieter(
        vDTO.getName(),
        vDTO.getEmail(),
        vDTO.getTelefonnummer(),
        vDTO.getAdresse(),
        vDTO.getPlz(),
        vDTO.getOrt()
    );

    // Mock MailInformation
    MailInformation mailInfo = mock(MailInformation.class);
    when(mailInfo.isDns()).thenReturn(false); // DNS-Check schlägt fehl
    when(mailInfo.isFormat()).thenReturn(false); // Format ist ungültig
    when(mailInfo.isDisposable()).thenReturn(true); // Wegwerf-Adresse

    when(roleService.userHasRole("admin")).thenReturn(true);
    when(mailValidatorService.validateEmail(vDTO.getEmail())).thenReturn(mailInfo);
    when(vermieterRepository.save(any(Vermieter.class))).thenReturn(vDAO);

    ResponseEntity<Vermieter> response = vermieterController.createVermieter(vDTO);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
}

    

    @Test
    void testCreateVermieterForbidden() {
        VermieterCreateDTO vDTO = new VermieterCreateDTO("John Doe", "john.doe@example.com", "123456789", "Teststrasse 1", "8000", "Zürich");

        when(roleService.userHasRole("admin")).thenReturn(false);

        ResponseEntity<Vermieter> response = vermieterController.createVermieter(vDTO);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testDeleteVermieter() {
        String vermieterId = "1";
        when(roleService.userHasRole("admin")).thenReturn(true);
        when(vermieterRepository.existsById(vermieterId)).thenReturn(true);

        ResponseEntity<Void> response = vermieterController.deleteVermieter(vermieterId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(vermieterRepository, times(1)).deleteById(vermieterId);
    }

    @Test
    void testDeleteVermieterNotFound() {
        String vermieterId = "1";
        when(roleService.userHasRole("admin")).thenReturn(true);
        when(vermieterRepository.existsById(vermieterId)).thenReturn(false);

        ResponseEntity<Void> response = vermieterController.deleteVermieter(vermieterId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteVermieterForbidden() {
        String vermieterId = "1";
        when(roleService.userHasRole("admin")).thenReturn(false);

        ResponseEntity<Void> response = vermieterController.deleteVermieter(vermieterId);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
