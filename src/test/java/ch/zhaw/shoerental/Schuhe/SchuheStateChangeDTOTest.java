package ch.zhaw.shoerental.Schuhe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.Test;

import ch.zhaw.shoerental.model.SchuheStateChangeDTO;

public class SchuheStateChangeDTOTest {
   
    @Test
    void testNoArgsConstructor() {
        SchuheStateChangeDTO schuheStateChangeDTO = new SchuheStateChangeDTO();
        assertNotNull(schuheStateChangeDTO);
    }

    @Test
    void testGetSetSchuheId() {
        String schuheId = "123";
        SchuheStateChangeDTO schuheStateChangeDTO = new SchuheStateChangeDTO();
        schuheStateChangeDTO.setSchuheId(schuheId);
        String retrievedSchuheId = schuheStateChangeDTO.getSchuheId();
        assertEquals(schuheId, retrievedSchuheId);
    }

    @Test
    void testGetSetMieterId() {
        String mieterId = "456";
        SchuheStateChangeDTO schuheStateChangeDTO = new SchuheStateChangeDTO();
        schuheStateChangeDTO.setMieterId(mieterId);
        String retrievedMieterId = schuheStateChangeDTO.getMieterId();
        assertEquals(mieterId, retrievedMieterId);
    }

    @Test
    void testGetSetMietdauerVon() {
        Date mietdauerVon = new Date();
        SchuheStateChangeDTO schuheStateChangeDTO = new SchuheStateChangeDTO();
       schuheStateChangeDTO.setMietdauerVon(mietdauerVon);
        Date retrievedMietdauerVon = schuheStateChangeDTO.getMietdauerVon();
        assertEquals(mietdauerVon, retrievedMietdauerVon);
    }

    @Test
    void testGetSetMietdauerBis() {
        Date mietdauerBis = new Date();
        SchuheStateChangeDTO schuheStateChangeDTO = new SchuheStateChangeDTO();
        schuheStateChangeDTO.setMietdauerBis(mietdauerBis);
        Date retrievedMietdauerBis = schuheStateChangeDTO.getMietdauerBis();
        assertEquals(mietdauerBis, retrievedMietdauerBis);
    }
}
