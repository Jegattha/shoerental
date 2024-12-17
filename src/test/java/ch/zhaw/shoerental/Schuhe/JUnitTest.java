package ch.zhaw.shoerental.Schuhe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import ch.zhaw.shoerental.model.Schuhe;
import ch.zhaw.shoerental.model.SchuheState;

import ch.zhaw.shoerental.repository.SchuheRepository;
import ch.zhaw.shoerental.repository.MieterRepository;

@SpringBootTest
public class JUnitTest {

    @Autowired
    MieterRepository mieterRepository;

    @Autowired
    SchuheRepository schuheRepository;

    @MockitoBean
    private MieterRepository mockMieterRepository;

    @MockitoBean
    private SchuheRepository mockSchuheRepository;

    @Test
    public void availableSchuheState() {
        String schuheId = "675d71c31ea1e026311e6cc3"; 
        Optional<Schuhe> schuheToAssign = schuheRepository.findById(schuheId);
        
        if (schuheToAssign.isPresent()) {
            Schuhe schuhe = schuheToAssign.get();
            
            if (schuhe.getSchuheState() == SchuheState.VERMIETET) {
                schuhe.setSchuheState(SchuheState.VERFUEGBAR);
                schuheRepository.save(schuhe);
            }
            
            assertEquals(SchuheState.VERFUEGBAR, schuhe.getSchuheState());
        }
    }

@Test
public void schuheStateChange() {
    Optional<Schuhe> schuheToAssign = schuheRepository.findById("675d71c31ea1e026311e6cc3");

    if (schuheToAssign.isPresent()) {
        Schuhe schuhe = schuheToAssign.get();

        if (schuhe.getSchuheState() == SchuheState.VERFUEGBAR) {
            schuhe.setSchuheState(SchuheState.VERMIETET);
            schuheRepository.save(schuhe);
        }

        assertEquals(SchuheState.VERMIETET, schuhe.getSchuheState());
    } else {
       
        System.out.println("Schuhe with ID '675d71c31ea1e026311e6cc3' not found");
    }
}




@Test
public void schuheMieter() {
    Optional<Schuhe> schuheToAssign = schuheRepository.findById("675d71c31ea1e026311e6cc3");

    if (schuheToAssign.isPresent()) {
        Schuhe schuhe = schuheToAssign.get();

        if (schuhe.getSchuheState() == SchuheState.VERMIETET) {
            schuhe.setMieterId("675db76bb8c9d3496ce577c1");
            schuheRepository.save(schuhe);
        }

        assertEquals("675db76bb8c9d3496ce577c1", schuhe.getMieterId());
    } else {
       
        System.out.println("Schuhe with ID '675d71c31ea1e026311e6cc3' not found");
    }
}



///////////////////////
//Mockito Test-Cases
///////////////////////
    public void setMieterRepository(MieterRepository mieterRepository) {
        this.mieterRepository = mieterRepository;
    }

    public void setSchuheRepository(SchuheRepository schuheRepository) {
        this.schuheRepository = schuheRepository;
    }


  @Test
    public void mockAvailableSchuheState() {
        when(mockSchuheRepository.findById(anyString()))
                .thenReturn(Optional.of(new Schuhe()));

        JUnitTest testClass = new JUnitTest();
        testClass.setSchuheRepository(mockSchuheRepository);
        testClass.availableSchuheState();

        verify(mockSchuheRepository).findById("675d71c31ea1e026311e6cc3");
    }

    @Test
public void mockSchuheMieter() {
 
    Schuhe mockSchuhe = new Schuhe();
    mockSchuhe.setMieterId("675db76bb8c9d3496ce577c1"); 
    when(mockSchuheRepository.findById(anyString()))
            .thenReturn(Optional.of(mockSchuhe));

    JUnitTest testClass = new JUnitTest();
    testClass.setSchuheRepository(mockSchuheRepository);
    testClass.schuheMieter();
    verify(mockSchuheRepository).findById("675d71c31ea1e026311e6cc3");

    assertEquals("675db76bb8c9d3496ce577c1", mockSchuhe.getMieterId());
}


}
