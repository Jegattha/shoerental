package ch.zhaw.shoerental.Mieter;

import org.junit.jupiter.api.Test;

import ch.zhaw.shoerental.model.MieterSchuheAggregationDTO;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MieterSchuheAggregationDTOTest {

    @Test
    void testNoArgsConstructorAndDefaultValues() {
        // Arrange & Act: Erstelle ein Objekt mit dem No-Args-Konstruktor
        MieterSchuheAggregationDTO dto = new MieterSchuheAggregationDTO();

        // Assert: Überprüfe die Standardwerte
        assertNull(dto.getSchuheId(), "Die Liste der schuheId sollte null sein.");
        assertEquals(0.0, dto.getPreis(), "Der Preis sollte standardmäßig 0.0 sein.");
        assertNull(dto.getId(), "Die ID sollte null sein.");
    }

    @Test
    void testSetValuesAndGetValues() {
        // Arrange: Erstelle ein DTO und Testwerte
        MieterSchuheAggregationDTO dto = new MieterSchuheAggregationDTO();

        List<String> schuheIds = Arrays.asList("schuhe1", "schuhe2", "schuhe3");
        double preis = 150.50;
        String id = "mieter123";

        // Act: Setze die Werte über Reflection (da keine Setter vorhanden sind)
        setField(dto, "schuheId", schuheIds);
        setField(dto, "preis", preis);
        setField(dto, "id", id);

        // Assert: Überprüfe die Getter-Werte
        assertEquals(schuheIds, dto.getSchuheId(), "Die schuheId-Liste sollte korrekt zurückgegeben werden.");
        assertEquals(preis, dto.getPreis(), "Der Preis sollte korrekt zurückgegeben werden.");
        assertEquals(id, dto.getId(), "Die ID sollte korrekt zurückgegeben werden.");
    }

    // Hilfsmethode zur Verwendung von Reflection zum Setzen privater Felder
    private void setField(Object target, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set field value using reflection", e);
        }
    }
}
