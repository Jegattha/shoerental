package ch.zhaw.shoerental.Schuhe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.zhaw.shoerental.model.Schuhe;
import ch.zhaw.shoerental.model.SchuheState;
import ch.zhaw.shoerental.model.Mieter;
import ch.zhaw.shoerental.repository.SchuheRepository;
import ch.zhaw.shoerental.repository.MieterRepository;
import ch.zhaw.shoerental.service.SchuheService;

@ExtendWith(MockitoExtension.class)
public class SchuheServiceTest {
    @Mock
    private SchuheRepository schuheRepository;

    @Mock
    private MieterRepository mieterRepository;

    @InjectMocks
    private SchuheService schuheService;


     @Test
    void testMietSchuhe() {
        // Mock-Daten
        String schuheId = "123";
        String mieterId = "456";
        Date mietdauerVon = new Date();
        Date mietdauerBis = new Date();
        Schuhe schuhe = new Schuhe();
        schuhe.setSchuheState(SchuheState.VERFUEGBAR);
        schuhe.setPreis(10.0); 
        Optional<Mieter> mockMieter = Optional.of(new Mieter());
        when(mieterRepository.findById(mieterId)).thenReturn(mockMieter);
        Optional<Schuhe> mockSchuhe = Optional.of(schuhe);
        when(schuheRepository.findById(schuheId)).thenReturn(mockSchuhe);
        Optional<Schuhe> result = schuheService.mietSchuhe(schuheId, mieterId, mietdauerVon, mietdauerBis);
        assertTrue(result.isPresent());
        assertEquals(SchuheState.VERMIETET, schuhe.getSchuheState());
        assertEquals(mietdauerVon, schuhe.getMietdauerVon());
        assertEquals(mietdauerBis, schuhe.getMietdauerBis());
        long rentalDays = mietdauerBis.toInstant().toEpochMilli() - mietdauerVon.toInstant().toEpochMilli();
        double expectedTotalPreis = rentalDays * schuhe.getPreis();
        assertEquals(expectedTotalPreis, schuhe.getTotalPreis(), 0.001);
        verify(schuheRepository, times(1)).save(schuhe);
    }

    @Test
    void testAvailableSchuhe1() {
        String schuheId = "123";
        when(schuheRepository.findById(schuheId)).thenReturn(Optional.empty());
        Optional<Schuhe> result = schuheService.availableSchuhe(schuheId);
        assertTrue(result.isEmpty());
        verify(schuheRepository, never()).save(any());
    }

    @Test
    void testAvailableSchuheWhenRented() {
        String schuheId = "123";

        Schuhe schuhe = new Schuhe();
        schuhe.setSchuheState(SchuheState.VERMIETET);
        schuhe.setMieterId("456");
        when(schuheRepository.findById(schuheId)).thenReturn(Optional.of(schuhe));
        Optional<Schuhe> result = schuheService.availableSchuhe(schuheId);
        assertTrue(result.isPresent());
        assertEquals(SchuheState.VERFUEGBAR, schuhe.getSchuheState());
        assertNull(schuhe.getMieterId());
        verify(schuheRepository, times(1)).save(schuhe);
    }
}