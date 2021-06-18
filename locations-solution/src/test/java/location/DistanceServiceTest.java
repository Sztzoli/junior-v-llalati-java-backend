package location;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DistanceServiceTest {

    @Mock
    LocationRepository locationRepository;

    @InjectMocks
    DistanceService service;

    @Test
    void calculateDistance() {
        Optional<Location> locationOptional = Optional.of(new Location("Budapest", 47.497912, 19.040235));

        when(locationRepository.findByName(anyString())).thenReturn(locationOptional);

        Optional<Double> result = service.calculateDistance("name1", "nema2");
        assertEquals(0d, result.get(), 0.01);
        verify(locationRepository, times(2)).findByName(anyString());
    }

    @Test
    void calculateDistanceEmpty() {
        Optional<Location> locationOptional = Optional.empty();

        when(locationRepository.findByName(anyString())).thenReturn(locationOptional);

        Optional<Double> result = service.calculateDistance("name1", "nema2");
        assertTrue(result.isEmpty());
        verify(locationRepository, times(2)).findByName(anyString());
    }
}