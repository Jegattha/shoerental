package ch.zhaw.shoerental.Mieter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import ch.zhaw.shoerental.service.MieterService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MieterServiceTest {

    private MieterService mieterService;

    @BeforeEach
    void setUp() {
        mieterService = new MieterService();
    }

    @Test
    void testGetEmail() {
        // Arrange: JWT-Token und SecurityContext simulieren
        String expectedEmail = "test@example.com";

        // JWT-Mock erstellen
        Jwt jwtMock = mock(Jwt.class);
        when(jwtMock.getClaimAsString("email")).thenReturn(expectedEmail);

        // Authentication-Mock erstellen
        Authentication authenticationMock = mock(Authentication.class);
        when(authenticationMock.getPrincipal()).thenReturn(jwtMock);

        // SecurityContext-Mock erstellen
        SecurityContext securityContextMock = mock(SecurityContext.class);
        when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);

        // SecurityContextHolder konfigurieren
        SecurityContextHolder.setContext(securityContextMock);

        // Act: Methode aufrufen
        String email = mieterService.getEmail();

        // Assert: Ergebnis prüfen
        assertEquals(expectedEmail, email);

        // Verify: Prüfen, dass JWT-Methoden aufgerufen wurden
        verify(jwtMock, times(1)).getClaimAsString("email");
    }
}
