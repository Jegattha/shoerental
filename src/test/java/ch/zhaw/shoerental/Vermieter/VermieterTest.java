package ch.zhaw.shoerental.Vermieter;

import org.junit.jupiter.api.Test;

import ch.zhaw.shoerental.model.Vermieter;

import static org.junit.jupiter.api.Assertions.*;

class VermieterTest {

    @Test
    void testNoArgsConstructor() {
        // Act: Erstelle ein Vermieter-Objekt mit dem NoArgsConstructor
        Vermieter vermieter = new Vermieter();

        // Assert: Überprüfe, ob Felder null sind
        assertNull(vermieter.getVermieterId(), "Die vermieterId sollte null sein.");
        assertNull(vermieter.getName(), "Der Name sollte null sein.");
        assertNull(vermieter.getEmail(), "Die E-Mail sollte null sein.");
        assertNull(vermieter.getTelefonnummer(), "Die Telefonnummer sollte null sein.");
        assertNull(vermieter.getAdresse(), "Die Adresse sollte null sein.");
        assertNull(vermieter.getPlz(), "Die PLZ sollte null sein.");
        assertNull(vermieter.getOrt(), "Der Ort sollte null sein.");
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange: Testdaten erstellen
        String vermieterId = "123";
        String name = "Max Mustermann";
        String email = "max@example.com";
        String telefonnummer = "0123456789";
        String adresse = "Musterstrasse 1";
        String plz = "8000";
        String ort = "Zürich";

        // Act: Erstelle ein Vermieter-Objekt mit dem AllArgsConstructor
        Vermieter vermieter = new Vermieter(vermieterId, name, email, telefonnummer, adresse, plz, ort);

        // Assert: Überprüfe die Getter-Werte
        assertEquals(vermieterId, vermieter.getVermieterId(), "Die vermieterId sollte korrekt zurückgegeben werden.");
        assertEquals(name, vermieter.getName(), "Der Name sollte korrekt zurückgegeben werden.");
        assertEquals(email, vermieter.getEmail(), "Die E-Mail sollte korrekt zurückgegeben werden.");
        assertEquals(telefonnummer, vermieter.getTelefonnummer(), "Die Telefonnummer sollte korrekt zurückgegeben werden.");
        assertEquals(adresse, vermieter.getAdresse(), "Die Adresse sollte korrekt zurückgegeben werden.");
        assertEquals(plz, vermieter.getPlz(), "Die PLZ sollte korrekt zurückgegeben werden.");
        assertEquals(ort, vermieter.getOrt(), "Der Ort sollte korrekt zurückgegeben werden.");
    }

    @Test
    void testConstructorWithMandatoryFields() {
        // Arrange: Testdaten erstellen
        String name = "Anna Müller";
        String email = "anna@example.com";

        // Act: Erstelle ein Vermieter-Objekt mit dem speziellen Konstruktor
        Vermieter vermieter = new Vermieter(name, email, null, null, null, null);

        // Assert: Überprüfe die Getter-Werte
        assertEquals(name, vermieter.getName(), "Der Name sollte korrekt zurückgegeben werden.");
        assertEquals(email, vermieter.getEmail(), "Die E-Mail sollte korrekt zurückgegeben werden.");
        assertNull(vermieter.getTelefonnummer(), "Die Telefonnummer sollte null sein.");
        assertNull(vermieter.getAdresse(), "Die Adresse sollte null sein.");
        assertNull(vermieter.getPlz(), "Die PLZ sollte null sein.");
        assertNull(vermieter.getOrt(), "Der Ort sollte null sein.");
    }

    @Test
    void testSetters() {
        // Arrange: Erstelle ein Vermieter-Objekt
        Vermieter vermieter = new Vermieter();

        // Act: Setze Werte mit den Setter-Methoden
        vermieter.setVermieterId("456");
        vermieter.setName("Lisa Schmidt");
        vermieter.setEmail("lisa@example.com");
        vermieter.setTelefonnummer("0987654321");
        vermieter.setAdresse("Beispielstrasse 2");
        vermieter.setPlz("9000");
        vermieter.setOrt("Bern");

        // Assert: Überprüfe die gesetzten Werte
        assertEquals("456", vermieter.getVermieterId(), "Die vermieterId sollte korrekt gesetzt werden.");
        assertEquals("Lisa Schmidt", vermieter.getName(), "Der Name sollte korrekt gesetzt werden.");
        assertEquals("lisa@example.com", vermieter.getEmail(), "Die E-Mail sollte korrekt gesetzt werden.");
        assertEquals("0987654321", vermieter.getTelefonnummer(), "Die Telefonnummer sollte korrekt gesetzt werden.");
        assertEquals("Beispielstrasse 2", vermieter.getAdresse(), "Die Adresse sollte korrekt gesetzt werden.");
        assertEquals("9000", vermieter.getPlz(), "Die PLZ sollte korrekt gesetzt werden.");
        assertEquals("Bern", vermieter.getOrt(), "Der Ort sollte korrekt gesetzt werden.");
    }
}
