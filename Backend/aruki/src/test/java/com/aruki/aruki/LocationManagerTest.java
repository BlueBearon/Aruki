package com.aruki.aruki;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.AdditionalMatchers.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LocationManagerTest {

    @Mock
    private APIManager apiManager;

    @InjectMocks
    private LocationManager locationManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPlaces() {
        String location = "Sample Location";
        List<Location> samplePlaces = APIManager.sampleData_retrievePlacesOfCategory(location, "restaurant");

        // Mock the APIManager to return sample places, if the method is has the "restaurant" category
        when(apiManager.retrievePlacesOfCategory(eq(location), eq("restaurant"), eq(true))).thenReturn(samplePlaces);
        // If the apimanager is not called with the "restaurant" parameter, return nothing
        when(apiManager.retrievePlacesOfCategory(anyString(), not(eq("restaurant")), eq(true))).thenReturn(List.of());

        when(apiManager.getWalkingDistances(anyString(), anyList(), eq(true))).thenReturn(Arrays.asList("0.4", "0.6", "1.2"));

        Map<String, String> places = locationManager.getPlaces(location);

        assertEquals("[{\"name\":\"The Green Leaf Diner\",\"address\":\"456 Oak Avenue, Example City, 12345\",\"types\":[restaurant],\"distance\":\"0.4\"}, {\"name\":\"The Red Tomato\",\"address\":\"789 Maple Street, Example City, 12345\",\"types\":[restaurant],\"distance\":\"0.6\"}, {\"name\":\"The Blue Ocean\",\"address\":\"123 Pine Road, Example City, 12345\",\"types\":[restaurant],\"distance\":\"1.2\"}]", places.get("places"));
    }

    @Test
    public void testGetScore() {
        String location = "Sample Location";
        String[] types = {"restaurant"};

        List<Location> samplePlaces = Arrays.asList(
            new Location("Place 1", "Address 1", types,  "0.4"),
            new Location("Place 2", "Address 2", types, "0.6")
        );

        when(apiManager.retrievePlacesOfCategory(anyString(), eq("restaurant"), eq(true))).thenReturn(samplePlaces);
        when(apiManager.retrievePlacesOfCategory(anyString(), not(eq("restaurant")), eq(true))).thenReturn(List.of());
        when(apiManager.getWalkingDistances(anyString(), anyList(), eq(true))).thenReturn(Arrays.asList("0.4", "0.6"));

        Map<String, String> score = locationManager.getScore(location);

        assertEquals("3.5999999999999996", score.get("walkability"));

    }
}
