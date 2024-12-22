package ch.zhaw.shoerental.Schuhe;

import org.junit.jupiter.api.Test;

import ch.zhaw.shoerental.model.Schuhe;
import ch.zhaw.shoerental.model.SchuheState;
import ch.zhaw.shoerental.model.SchuheType;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SchuheTest {

    @Test
    void testNoArgsConstructor() {
        // Act: Erstelle ein Schuhe-Objekt mit dem No-Args-Konstruktor
        Schuhe schuhe = new Schuhe();

        // Assert: Überprüfe die Standardwerte
        assertNull(schuhe.getSchuheId(), "Die schuheId sollte null sein.");
        assertNull(schuhe.getMarke(), "Die Marke sollte null sein.");
        assertNull(schuhe.getPreis(), "Der Preis sollte null sein.");
        assertEquals(SchuheState.VERFUEGBAR, schuhe.getSchuheState(), "Der Standardwert für schuheState sollte VERFUEGBAR sein.");
        assertNull(schuhe.getSchuheType(), "Der SchuheType sollte null sein.");
        assertNull(schuhe.getGroesse(), "Die Groesse sollte null sein.");
        assertNull(schuhe.getSchuheFarbe(), "Die SchuheFarbe sollte null sein.");
        assertNull(schuhe.getSchuheBeschreibung(), "Die SchuheBeschreibung sollte null sein.");
        assertNull(schuhe.getVermieterId(), "Die VermieterId sollte null sein.");
        assertNull(schuhe.getMieterId(), "Die MieterId sollte null sein.");
        assertNull(schuhe.getMietdauerVon(), "MietdauerVon sollte null sein.");
        assertNull(schuhe.getMietdauerBis(), "MietdauerBis sollte null sein.");
        assertNull(schuhe.getTotalPreis(), "TotalPreis sollte null sein.");
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange: Testdaten erstellen
        String marke = "Nike";
        Double preis = 120.50;
        SchuheType schuheType = SchuheType.DAMENSCHUH;
        String groesse = "42";
        String schuheFarbe = "Rot";
        String schuheBeschreibung = "Hochwertige Laufschuhe";
        String vermieterId = "vermieter123";

        // Act: Erstelle ein Schuhe-Objekt mit dem benutzerdefinierten Konstruktor
        Schuhe schuhe = new Schuhe(marke, preis, schuheType, groesse, schuheFarbe, schuheBeschreibung, vermieterId);

        // Assert: Überprüfe die Felder
        assertNull(schuhe.getSchuheId(), "Die schuheId sollte null sein.");
        assertEquals(marke, schuhe.getMarke(), "Die Marke sollte korrekt gesetzt sein.");
        assertEquals(preis, schuhe.getPreis(), "Der Preis sollte korrekt gesetzt sein.");
        assertEquals(schuheType, schuhe.getSchuheType(), "Der SchuheType sollte korrekt gesetzt sein.");
        assertEquals(groesse, schuhe.getGroesse(), "Die Groesse sollte korrekt gesetzt sein.");
        assertEquals(schuheFarbe, schuhe.getSchuheFarbe(), "Die SchuheFarbe sollte korrekt gesetzt sein.");
        assertEquals(schuheBeschreibung, schuhe.getSchuheBeschreibung(), "Die SchuheBeschreibung sollte korrekt gesetzt sein.");
        assertEquals(vermieterId, schuhe.getVermieterId(), "Die VermieterId sollte korrekt gesetzt sein.");
        assertEquals(SchuheState.VERFUEGBAR, schuhe.getSchuheState(), "Der SchuheState sollte VERFUEGBAR sein.");
    }

    @Test
    void testSetters() {
        // Arrange: Erstelle ein Schuhe-Objekt
        Schuhe schuhe = new Schuhe();

        // Testdaten setzen
        String schuheId = "schuhe123";
        String marke = "Adidas";
        Double preis = 99.99;
        SchuheType schuheType = SchuheType.HERRENSCHUH;
        String groesse = "44";
        String schuheFarbe = "Blau";
        String schuheBeschreibung = "Bequeme Sportschuhe";
        String vermieterId = "vermieter123";
        String mieterId = "mieter456";
        Date mietdauerVon = new Date();
        Date mietdauerBis = new Date();
        Double totalPreis = 199.98;

        // Act: Werte setzen
        schuhe.setSchuheId(schuheId);
        schuhe.setMarke(marke);
        schuhe.setPreis(preis);
        schuhe.setSchuheType(schuheType);
        schuhe.setGroesse(groesse);
        schuhe.setSchuheFarbe(schuheFarbe);
        schuhe.setSchuheBeschreibung(schuheBeschreibung);
        schuhe.setVermieterId(vermieterId);
        schuhe.setMieterId(mieterId);
        schuhe.setMietdauerVon(mietdauerVon);
        schuhe.setMietdauerBis(mietdauerBis);
        schuhe.setTotalPreis(totalPreis);

        // Assert: Überprüfe die Werte
        assertEquals(schuheId, schuhe.getSchuheId());
        assertEquals(marke, schuhe.getMarke());
        assertEquals(preis, schuhe.getPreis());
        assertEquals(schuheType, schuhe.getSchuheType());
        assertEquals(groesse, schuhe.getGroesse());
        assertEquals(schuheFarbe, schuhe.getSchuheFarbe());
        assertEquals(schuheBeschreibung, schuhe.getSchuheBeschreibung());
        assertEquals(vermieterId, schuhe.getVermieterId());
        assertEquals(mieterId, schuhe.getMieterId());
        assertEquals(mietdauerVon, schuhe.getMietdauerVon());
        assertEquals(mietdauerBis, schuhe.getMietdauerBis());
        assertEquals(totalPreis, schuhe.getTotalPreis());
    }
}
