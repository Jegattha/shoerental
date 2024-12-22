package ch.zhaw.shoerental.Mail;

import org.junit.jupiter.api.Test;

import ch.zhaw.shoerental.model.Mail;

import static org.junit.jupiter.api.Assertions.*;

class MailTest {

    @Test
    void testMailGettersAndSetters() {
        // Arrange: Erstelle ein Mail-Objekt
        Mail mail = new Mail();

        // Setze die Testdaten
        String testTo = "test@example.com";
        String testSubject = "Test Subject";
        String testMessage = "This is a test message.";

        // Act: Setter aufrufen
        mail.setTo(testTo);
        mail.setSubject(testSubject);
        mail.setMessage(testMessage);

        // Assert: Überprüfe die Getter
        assertEquals(testTo, mail.getTo(), "Empfängeradresse (to) sollte korrekt zurückgegeben werden.");
        assertEquals(testSubject, mail.getSubject(), "Betreff (subject) sollte korrekt zurückgegeben werden.");
        assertEquals(testMessage, mail.getMessage(), "Nachricht (message) sollte korrekt zurückgegeben werden.");
    }

    @Test
    void testMailNoArgsConstructor() {
        // Arrange & Act: Erstelle ein Mail-Objekt mit dem NoArgsConstructor
        Mail mail = new Mail();

        // Assert: Standardwerte prüfen (sollten null sein)
        assertNull(mail.getTo(), "Empfängeradresse (to) sollte null sein.");
        assertNull(mail.getSubject(), "Betreff (subject) sollte null sein.");
        assertNull(mail.getMessage(), "Nachricht (message) sollte null sein.");
    }
}
