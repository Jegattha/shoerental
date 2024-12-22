package ch.zhaw.shoerental.Vermieter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import ch.zhaw.shoerental.controller.VermieterController;
import ch.zhaw.shoerental.model.MailInformation;
import ch.zhaw.shoerental.model.Vermieter;
import ch.zhaw.shoerental.model.VermieterCreateDTO;
import ch.zhaw.shoerental.repository.VermieterRepository;
import ch.zhaw.shoerental.service.MailValidatorService;
import ch.zhaw.shoerental.service.SchuheService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VermieterControllerTest {

    @InjectMocks
    private VermieterController vermieterController;

    @Mock
    private VermieterRepository vermieterRepository;

    @Mock
    private MailValidatorService mailValidatorService;

    @Mock
    private SchuheService schuheService;

    // Ersatzklasse für MailValidationResponse
    static class MockMailValidationResponse {
        private boolean dns;
        private boolean format;
        private boolean disposable;

        public MockMailValidationResponse(boolean dns, boolean format, boolean disposable) {
            this.dns = dns;
            this.format = format;
            this.disposable = disposable;
        }

        public boolean isDns() { return dns; }
        public boolean isFormat() { return format; }
        public boolean isDisposable() { return disposable; }
    }

    @Test
    void testGetAllVermieter() {
        List<Vermieter> expectedVermieterList = new ArrayList<>();
        expectedVermieterList.add(new Vermieter());
        expectedVermieterList.add(new Vermieter());

        Page<Vermieter> expectedPage = new PageImpl<>(expectedVermieterList);

        when(vermieterRepository.findAll(any(PageRequest.class))).thenReturn(expectedPage);
        ResponseEntity<Page<Vermieter>> response = vermieterController.getAllVermieter(1, 2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedPage, response.getBody());
    }

    @Test
    void testGetVermieterById() {
        String vermieterId = "1";
        Vermieter expectedVermieter = new Vermieter();
        when(vermieterRepository.findById(vermieterId)).thenReturn(Optional.of(expectedVermieter));
        ResponseEntity<Vermieter> response = vermieterController.getVermieterById(vermieterId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedVermieter, response.getBody());
    }

    @Test
    void testGetVermieterByIdNotFound() {
        String vermieterId = "2";
        when(vermieterRepository.findById(vermieterId)).thenReturn(Optional.empty());

        ResponseEntity<Vermieter> response = vermieterController.getVermieterById(vermieterId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteVermieterSuccess() {
        String vermieterId = "123";
        when(vermieterRepository.existsById(vermieterId)).thenReturn(true);
        doNothing().when(vermieterRepository).deleteById(vermieterId);

        ResponseEntity<Void> response = vermieterController.deleteVermieter(vermieterId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(vermieterRepository, times(1)).deleteById(vermieterId);
    }

    @Test
    void testDeleteVermieterNotFound() {
        String vermieterId = "123";
        when(vermieterRepository.existsById(vermieterId)).thenReturn(false);

        ResponseEntity<Void> response = vermieterController.deleteVermieter(vermieterId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(vermieterRepository, never()).deleteById(vermieterId);
    }

   @Test
void testCreateVermieterSuccess() {
    // Arrange: Erstelle das DTO für den Test
    VermieterCreateDTO createDTO = new VermieterCreateDTO(
        "Test Name", "test@example.com", "123456789", "Test Address", "8000", "Zürich"
    );

    // Erstelle ein validiertes MailInformation-Objekt
    MailInformation mailInformation = new MailInformation();
    ReflectionTestUtils.setField(mailInformation, "dns", true);
    ReflectionTestUtils.setField(mailInformation, "format", true);
    ReflectionTestUtils.setField(mailInformation, "disposable", false);

    Vermieter savedVermieter = new Vermieter(
        "Test Name", "test@example.com", "123456789", "Test Address", "8000", "Zürich"
    );

    // Mock-Verhalten
    when(mailValidatorService.validateEmail(anyString())).thenReturn(mailInformation);
    when(vermieterRepository.save(any(Vermieter.class))).thenReturn(savedVermieter);

    // Act: Rufe die Methode des Controllers auf
    ResponseEntity<Vermieter> response = vermieterController.createVermieter(createDTO);

    // Assert: Überprüfe die Ergebnisse
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(savedVermieter, response.getBody());
}


@Test
void testCreateVermieterInvalidEmail() {
    // Arrange: Erstelle das DTO für den Test
    VermieterCreateDTO createDTO = new VermieterCreateDTO(
        "Test Name", "invalid@example.com", "123456789", "Test Address", "8000", "Zürich"
    );

    MailInformation mailInformation = new MailInformation();
    ReflectionTestUtils.setField(mailInformation, "dns", false);
    ReflectionTestUtils.setField(mailInformation, "format", true);
    ReflectionTestUtils.setField(mailInformation, "disposable", false);

    when(mailValidatorService.validateEmail(anyString())).thenReturn(mailInformation);

    // Act: Rufe die Methode des Controllers auf
    ResponseEntity<Vermieter> response = vermieterController.createVermieter(createDTO);

    // Assert: Überprüfe die Ergebnisse
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNull(response.getBody());
    verify(vermieterRepository, never()).save(any(Vermieter.class));
}
}