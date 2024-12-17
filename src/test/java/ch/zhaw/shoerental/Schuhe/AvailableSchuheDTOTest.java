package ch.zhaw.shoerental.Schuhe;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ch.zhaw.shoerental.model.AvailabeSchuheDTO;

public class AvailableSchuheDTOTest {

    @Test  
    void testSetGetSchuheId() {
        String schuheId = "123";
        AvailabeSchuheDTO availabeSchuheDTO = new AvailabeSchuheDTO();
        availabeSchuheDTO.setSchuheId(schuheId);
        String retrievedSchuheId = availabeSchuheDTO.getSchuheId();
        assertEquals(schuheId, retrievedSchuheId);
    }

    @Test
    void testSetSchuheIdWithMock() {
        String schuheId = "456";
        AvailabeSchuheDTO availabeSchuheDTO = Mockito.mock(AvailabeSchuheDTO.class);
        availabeSchuheDTO.setSchuheId(schuheId);
        Mockito.verify(availabeSchuheDTO).setSchuheId(schuheId);
    }

    @Test
    void testGetSchuheIdWithMock() {
        String schuheId = "789";
        AvailabeSchuheDTO availabeSchuheDTO = Mockito.mock(AvailabeSchuheDTO.class);
        Mockito.when(availabeSchuheDTO.getSchuheId()).thenReturn(schuheId);
        String retrievedSchuheId = availabeSchuheDTO.getSchuheId();
        assertEquals(schuheId, retrievedSchuheId);
        Mockito.verify(availabeSchuheDTO).getSchuheId();
    }

    @Test
    void testAllArgsConstructor() {
        String schuheId = "123";
        AvailabeSchuheDTO availabeSchuheDTO = new AvailabeSchuheDTO(schuheId);
        assertNotNull(availabeSchuheDTO);
        assertEquals(schuheId, availabeSchuheDTO.getSchuheId());
    }
}
