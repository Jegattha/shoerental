package ch.zhaw.shoerental.Mieter;

import ch.zhaw.shoerental.controller.MieterController;
import ch.zhaw.shoerental.model.Mieter;
import ch.zhaw.shoerental.model.MieterCreateDTO;
import ch.zhaw.shoerental.repository.MieterRepository;
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
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MieterControllerTest {

    @Mock
    private MieterRepository mieterRepository;

    @Mock
    private MailValidatorService mailValidatorService;

    @Mock
    private RoleService roleService;

    private MieterController mieterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mieterController = new MieterController();
        mieterController.mieterRepository = mieterRepository;
        mieterController.mailValidatorService = mailValidatorService;
        mieterController.roleService = roleService;
    }

    @Test
    void testGetAllMieter() {
        Page<Mieter> mieterPage = new PageImpl<>(Collections.singletonList(new Mieter()));
        when(mieterRepository.findAll(PageRequest.of(0, 2))).thenReturn(mieterPage);

        ResponseEntity<Page<Mieter>> response = mieterController.getAllMieter(1, 2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mieterPage, response.getBody());
    }

    @Test
    void testGetMieterById() {
        String mieterId = "1";
        Mieter mieter = new Mieter();
        when(mieterRepository.findById(mieterId)).thenReturn(Optional.of(mieter));

        ResponseEntity<Mieter> response = mieterController.getMieterById(mieterId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mieter, response.getBody());
    }

    @Test
    void testGetMieterByIdNotFound() {
        when(mieterRepository.findById("1")).thenReturn(Optional.empty());

        ResponseEntity<Mieter> response = mieterController.getMieterById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCreateMieter() {
        MieterCreateDTO mDTO = new MieterCreateDTO("John Doe", "john.doe@example.com", "123456789", "Teststrasse 1", "8000", "Zürich");
        Mieter mieter = new Mieter(mDTO.getName(), mDTO.getEmail(), mDTO.getTelefonnummer(), mDTO.getAdresse(), mDTO.getPlz(), mDTO.getOrt());

        when(roleService.userHasRole("admin")).thenReturn(true);
        when(mailValidatorService.validateEmail(mDTO.getEmail()).isDns()).thenReturn(true);
        when(mailValidatorService.validateEmail(mDTO.getEmail()).isFormat()).thenReturn(true);
        when(mailValidatorService.validateEmail(mDTO.getEmail()).isDisposable()).thenReturn(false);
        when(mieterRepository.save(any(Mieter.class))).thenReturn(mieter);

        ResponseEntity<Mieter> response = mieterController.createMieter(mDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mieter, response.getBody());
    }

    @Test
    void testCreateMieterInvalidEmail() {
        MieterCreateDTO mDTO = new MieterCreateDTO("John Doe", "invalid-email", "123456789", "Teststrasse 1", "8000", "Zürich");

        when(roleService.userHasRole("admin")).thenReturn(true);
        when(mailValidatorService.validateEmail(mDTO.getEmail()).isDns()).thenReturn(false);

        ResponseEntity<Mieter> response = mieterController.createMieter(mDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testDeleteMieter() {
        String mieterId = "1";

        when(roleService.userHasRole("admin")).thenReturn(true);
        when(mieterRepository.existsById(mieterId)).thenReturn(true);

        ResponseEntity<Void> response = mieterController.deleteMieter(mieterId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(mieterRepository, times(1)).deleteById(mieterId);
    }

    @Test
    void testDeleteMieterNotFound() {
        String mieterId = "1";

        when(roleService.userHasRole("admin")).thenReturn(true);
        when(mieterRepository.existsById(mieterId)).thenReturn(false);

        ResponseEntity<Void> response = mieterController.deleteMieter(mieterId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateMieter() {
        String mieterId = "1";
        MieterCreateDTO mDTO = new MieterCreateDTO("Updated Name", "updated.email@example.com", null, null, null, null);
        Mieter mieter = new Mieter("Old Name", "old.email@example.com", "123456789", "Old Address", "8000", "Zürich");

        when(mieterRepository.findById(mieterId)).thenReturn(Optional.of(mieter));
        when(mieterRepository.save(any(Mieter.class))).thenReturn(mieter);

        ResponseEntity<Mieter> response = mieterController.updateMieter(mieterId, mDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Name", response.getBody().getName());
        assertEquals("updated.email@example.com", response.getBody().getEmail());
    }

    @Test
    void testUpdateMieterNotFound() {
        String mieterId = "1";
        MieterCreateDTO mDTO = new MieterCreateDTO("Updated Name", "updated.email@example.com", null, null, null, null);

        when(mieterRepository.findById(mieterId)).thenReturn(Optional.empty());

        ResponseEntity<Mieter> response = mieterController.updateMieter(mieterId, mDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAssignToMe() {
        String userEmail = "user@example.com";
        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaimAsString("email")).thenReturn(userEmail);

        Mieter mieter = new Mieter();
        mieter.setEmail(userEmail);
        when(mieterRepository.findFirstByEmail(userEmail)).thenReturn(mieter);

        ResponseEntity<Mieter> response = mieterController.assignToMe(jwt);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userEmail, response.getBody().getEmail());
    }

    @Test
    void testAssignToMeNotFound() {
        String userEmail = "user@example.com";
        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaimAsString("email")).thenReturn(userEmail);

        when(mieterRepository.findFirstByEmail(userEmail)).thenReturn(null);

        ResponseEntity<Mieter> response = mieterController.assignToMe(jwt);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
