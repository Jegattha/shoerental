package ch.zhaw.shoerental.Vermieter;

import org.junit.jupiter.api.Test;

import ch.zhaw.shoerental.model.VermieterCreateDTO;

import static org.junit.jupiter.api.Assertions.*;

class VermieterCreateDTOTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        // Arrange: Erstelle Testwerte
        String name = "Max Mustermann";
        String email = "max@example.com";
        String telefonnummer = "0123456789";
        String adresse = "Musterstrasse 123";
        String plz = "8000";
        String ort = "Zürich";

        // Act: Erstelle ein Objekt mit dem AllArgsConstructor
        VermieterCreateDTO dto = new VermieterCreateDTO(name, email, telefonnummer, adresse, plz, ort);

        // Assert: Überprüfe die Getter-Werte
        assertEquals(name, dto.getName(), "Der Name sollte korrekt zurückgegeben werden.");
        assertEquals(email, dto.getEmail(), "Die E-Mail sollte korrekt zurückgegeben werden.");
        assertEquals(telefonnummer, dto.getTelefonnummer(), "Die Telefonnummer sollte korrekt zurückgegeben werden.");
        assertEquals(adresse, dto.getAdresse(), "Die Adresse sollte korrekt zurückgegeben werden.");
        assertEquals(plz, dto.getPlz(), "Die Postleitzahl sollte korrekt zurückgegeben werden.");
        assertEquals(ort, dto.getOrt(), "Der Ort sollte korrekt zurückgegeben werden.");
    }

    @Test
    void testSetters() {
        // Arrange: Erstelle ein DTO-Objekt
        VermieterCreateDTO dto = new VermieterCreateDTO("", "", "", "", "", "");

        // Act: Setze die Werte mit den Setter-Methoden
        dto.setName("Anna Müller");
        dto.setEmail("anna@example.com");
        dto.setTelefonnummer("9876543210");
        dto.setAdresse("Beispielstrasse 456");
        dto.setPlz("9000");
        dto.setOrt("Bern");

        // Assert: Überprüfe die Getter-Werte
        assertEquals("Anna Müller", dto.getName(), "Der Name sollte korrekt gesetzt werden.");
        assertEquals("anna@example.com", dto.getEmail(), "Die E-Mail sollte korrekt gesetzt werden.");
        assertEquals("9876543210", dto.getTelefonnummer(), "Die Telefonnummer sollte korrekt gesetzt werden.");
        assertEquals("Beispielstrasse 456", dto.getAdresse(), "Die Adresse sollte korrekt gesetzt werden.");
        assertEquals("9000", dto.getPlz(), "Die Postleitzahl sollte korrekt gesetzt werden.");
        assertEquals("Bern", dto.getOrt(), "Der Ort sollte korrekt gesetzt werden.");
    }
}
