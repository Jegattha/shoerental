package ch.zhaw.shoerental.Vermieter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.zhaw.shoerental.model.Vermieter;
import ch.zhaw.shoerental.repository.VermieterRepository;
import ch.zhaw.shoerental.service.VermieterService;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class VermieterServiceTest {

    @InjectMocks
    private VermieterService vermieterService;

    @Mock
    private VermieterRepository vermieterRepository;

    @Test
    void testGetVermieterById() {
        String vermieterId = "rggsagsa15676776sfd";
        Vermieter expectedVermieter = new Vermieter();
        when(vermieterRepository.findById(vermieterId)).thenReturn(Optional.of(expectedVermieter));
        Vermieter retrievedVermieter = vermieterService.getVermieterById(vermieterId);
        assertNotNull(retrievedVermieter);
        assertEquals(expectedVermieter, retrievedVermieter);
        verify(vermieterRepository, times(1)).findById(vermieterId);
    }
}