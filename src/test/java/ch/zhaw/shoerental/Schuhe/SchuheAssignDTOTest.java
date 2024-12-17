package ch.zhaw.shoerental.Schuhe;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ch.zhaw.shoerental.model.SchuheAssignDTO;

public class SchuheAssignDTOTest {
    

        @Test
    public void testConstructorWithParameters() {
     
        String schuheId = "12345";
        String mieterId = "67890";

        SchuheAssignDTO assignDTO = new SchuheAssignDTO(schuheId, mieterId);
        assertNotNull(assignDTO);
        assertEquals(schuheId, assignDTO.getSchuheId());
        assertEquals(mieterId, assignDTO.getMieterId());
    }

    @Test
    public void testDefaultConstructor() {
        SchuheAssignDTO assignDTO = new SchuheAssignDTO();
        assertNotNull(assignDTO);
        assertNull(assignDTO.getSchuheId());
        assertNull(assignDTO.getMieterId());
    }
}
