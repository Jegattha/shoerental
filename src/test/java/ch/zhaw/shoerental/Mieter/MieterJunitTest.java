package ch.zhaw.shoerental.Mieter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zhaw.shoerental.model.Mieter;

public class MieterJunitTest {
    
    private Mieter mieter;

    @BeforeEach  
    void setUp() {
        mieter = new Mieter("1", "John Doe", "john.doe@gmail.com","123456789", "Musterstrasse","8000", "Basel");
    }

    @Test
    void testGettersAndSetters() {
        assertEquals("1", mieter.getMieterId());
        assertEquals("John Doe", mieter.getName());
        assertEquals("john.doe@gmail.com", mieter.getEmail());

        mieter.setTelefonnummer("123456789");
        assertEquals("123456789", mieter.getTelefonnummer());

        mieter.setAdresse("Musterstrasse");
        assertEquals("Musterstrasse", mieter.getAdresse());

        mieter.setPlz("8000");
        assertEquals("8000", mieter.getPlz());

        mieter.setOrt("Basel");
        assertEquals("Basel", mieter.getOrt());
    }  

    @Test
    void testConstructorWithNonNullParameters() {
        assertNotNull(mieter);
        assertEquals("1", mieter.getMieterId());
        assertEquals("John Doe", mieter.getName());
        assertEquals("john.doe@gmail.com", mieter.getEmail());
    }

    @Test
    void testNoArgsConstructor() {
        Mieter emptyMieter = new Mieter();
        assertNotNull(emptyMieter);
    }

    @Test
    void testAllArgsConstructor() {
        Mieter allArgsMieter = new Mieter("1", "John Doe", "john.doe@gmail.com", "123456789", "Musterstrasse", "8000", "Basel");
        assertNotNull(allArgsMieter);
        assertEquals("1", allArgsMieter.getMieterId());
        assertEquals("John Doe", allArgsMieter.getName());
        assertEquals("john.doe@gmail.com", allArgsMieter.getEmail());
        assertEquals("123456789", allArgsMieter.getTelefonnummer());
        assertEquals("Musterstrasse", allArgsMieter.getAdresse());
        assertEquals("8000", allArgsMieter.getPlz());
        assertEquals("Basel", allArgsMieter.getOrt());
    }
    @Test
    void testMieterConstructorAndSetters() {
        

        String name = "John Doe";
        String email = "john.doe@example.com";

        Mieter mieter = new Mieter(name, email);
        assertEquals(name, mieter.getName());
        assertEquals(email, mieter.getEmail());

    }

    @Test
    void testSetName() {
        Mieter mieter = new Mieter();
        String name = "John Doe";
        mieter.setName(name);
        assertEquals(name, mieter.getName());
    }

    @Test
    void testSetEmail() {
        Mieter mieter = new Mieter();
        String email = "john.doe@example.com";
        mieter.setEmail(email);
        assertEquals(email, mieter.getEmail());
    }
}
