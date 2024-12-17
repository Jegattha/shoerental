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





import ch.zhaw.shoerental.controller.VermieterController;

import ch.zhaw.shoerental.model.Vermieter;

import ch.zhaw.shoerental.repository.VermieterRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VermieterControllerTest {

    @InjectMocks
    private VermieterController vermieterController;

    @Mock
    private VermieterRepository vermieterRepository;

  

    @Test
    void testGetAllVermieter() {
        List<Vermieter> expectedVermieterList = new ArrayList<>();
        expectedVermieterList.add(new Vermieter());
        expectedVermieterList.add(new Vermieter());

        Page<Vermieter> expectedPage = new PageImpl<>(expectedVermieterList);


        when(vermieterRepository.findAll(any(PageRequest.class))).thenReturn(expectedPage);
        ResponseEntity<Page<Vermieter>> response = vermieterController.getAllVermieter(1,2);

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
        // Arrange
        String vermieterId = "2";
        when(vermieterRepository.findById(vermieterId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Vermieter> response = vermieterController.getVermieterById(vermieterId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

}
