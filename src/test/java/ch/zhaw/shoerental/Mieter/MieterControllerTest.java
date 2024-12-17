package ch.zhaw.shoerental.Mieter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ch.zhaw.shoerental.controller.MieterController;
import ch.zhaw.shoerental.model.Mieter;
import ch.zhaw.shoerental.repository.MieterRepository;


class MieterControllerTest {

    private MieterRepository mieterRepository;
    private MieterController mieterController;
    

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        mieterRepository = mock(MieterRepository.class);
        mieterController = new MieterController();
                Field field = MieterController.class.getDeclaredField("mieterRepository");
        field.setAccessible(true);
        field.set(mieterController, mieterRepository);
    }

    @Test
    void testGetMieterById() {

        String mieterId = "1";
        Mieter expectedMieter = new Mieter();
        when(mieterRepository.findById(mieterId)).thenReturn(Optional.of(expectedMieter));
        ResponseEntity<Mieter> response = mieterController.getMieterById(mieterId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedMieter, response.getBody());
    }

    @Test
    void testGetMieterByIdNotFound() {
        String mieterId = "2";
        when(mieterRepository.findById(mieterId)).thenReturn(Optional.empty());
        ResponseEntity<Mieter> response = mieterController.getMieterById(mieterId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
    @Test
    void testGetAllMieter() {
        List<Mieter> expectedMieterList = new ArrayList<>();
        expectedMieterList.add(new Mieter());
        expectedMieterList.add(new Mieter());

        Page<Mieter> expectedPage = new PageImpl<>(expectedMieterList);


        when(mieterRepository.findAll(any(PageRequest.class))).thenReturn(expectedPage);
        ResponseEntity<Page<Mieter>> response = mieterController.getAllMieter(1,2);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedPage, response.getBody());
    }
}
