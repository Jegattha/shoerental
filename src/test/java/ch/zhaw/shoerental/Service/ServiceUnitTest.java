package ch.zhaw.shoerental.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


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
public class ServiceUnitTest {

    @Mock
    private SchuheRepository schuheRepository;

    @Mock
    private MieterRepository mieterRepository;

    @InjectMocks
    private SchuheService schuheService;

    @Test
    public void testAssignSchuhe() {
        // Arrange
        String schuheId = "testSchuheId";
        String mieterId = "testMieterId";
        Schuhe schuhe = new Schuhe();
        schuhe.setSchuheState(SchuheState.VERFUEGBAR);

        Mieter mieter = new Mieter();
        mieter.setMieterId(mieterId);

        when(schuheRepository.findById(schuheId)).thenReturn(Optional.of(schuhe));
        when(mieterRepository.findById(mieterId)).thenReturn(Optional.of(mieter));

        
        Optional<Schuhe> result = schuheService.assignSchuhe(schuheId, mieterId);

       
        assertEquals(SchuheState.VERMIETET, schuhe.getSchuheState());
        assertEquals(mieterId, schuhe.getMieterId());
        assertEquals(Optional.of(schuhe), result);
        verify(schuheRepository, times(1)).save(schuhe);
    }

    @Test
    public void testAvailableSchuhe() {
        
        String schuheId = "testSchuheId";
        Schuhe schuhe = new Schuhe();
        schuhe.setSchuheState(SchuheState.VERMIETET);
        schuhe.setMieterId("testMieterId");

        when(schuheRepository.findById(schuheId)).thenReturn(Optional.of(schuhe));

        
        Optional<Schuhe> result = schuheService.availableSchuhe(schuheId);

       
        assertEquals(SchuheState.VERFUEGBAR, schuhe.getSchuheState());
        assertEquals(null, schuhe.getMieterId());
        assertEquals(Optional.of(schuhe), result);
        verify(schuheRepository, times(1)).save(schuhe);
    }

}
