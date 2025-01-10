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

import com.google.maps.model.PlaceType;

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
        List<Location> samplePlaces = APIManager.sampleData_retrievePlacesOfCategory(location, PlaceType.RESTAURANT);

        // Mock the APIManager to return sample places, if the method is has the PlaceType.RESTAURANT category
        when(apiManager.retrievePlacesOfCategory(eq(location), eq(PlaceType.RESTAURANT), eq(true))).thenReturn(samplePlaces);
        // If the apimanager is not called with the PlaceType.RESTAURANT parameter, return nothing
        when(apiManager.retrievePlacesOfCategory(anyString(), not(eq(PlaceType.RESTAURANT)), eq(true))).thenReturn(List.of());

        when(apiManager.getWalkingDistances(anyString(), anyList(), eq(true))).thenReturn(Arrays.asList("0.4", "0.6", "1.2"));

        Map<String, String> places = locationManager.getPlaces(location, true);

        assertEquals("[{\"name\":\"The Green Leaf Diner\",\"address\":\"456 Oak Avenue, Example City, 12345\",\"types\":[restaurant],\"distance\":\"0.4\"}, {\"name\":\"The Red Tomato\",\"address\":\"789 Maple Street, Example City, 12345\",\"types\":[restaurant],\"distance\":\"0.6\"}, {\"name\":\"The Blue Ocean\",\"address\":\"123 Pine Road, Example City, 12345\",\"types\":[restaurant],\"distance\":\"1.2\"}]", places.get("places"));
    }

    @Test
    public void testGetScore() {
        String location = "Sample Location";
        String[] types = {PlaceType.RESTAURANT.toString()};

        List<Location> samplePlaces = Arrays.asList(
            new Location("Place 1", "Address 1", types,  "0.4"),
            new Location("Place 2", "Address 2", types, "0.6")
        );

        when(apiManager.retrievePlacesOfCategory(anyString(), eq(PlaceType.RESTAURANT), eq(true))).thenReturn(samplePlaces);
        when(apiManager.retrievePlacesOfCategory(anyString(), not(eq(PlaceType.RESTAURANT)), eq(true))).thenReturn(List.of());
        when(apiManager.getWalkingDistances(anyString(), anyList(), eq(true))).thenReturn(Arrays.asList("0.4", "0.6"));

        Map<String, String> score = locationManager.getScore(location, true);

        assertEquals("1.87", score.get("walkability"));

    }

    /**
     * Test to make sure that when the locations are returned by the API, the VerifyWalkingDistances private method correctly filters out the locations that are too far away.
     */
    @Test
    public void testLocationsFiltered()
    {

        // Create a list of locations, some of which are beyond 3.2 km

        List<Location> samplePlaces = Arrays.asList(
            new Location("Place 1", "Address 1", new String[] {PlaceType.RESTAURANT.toString()}, "0.4"),
            new Location("Place 2", "Address 2", new String[] {PlaceType.RESTAURANT.toString()}, "0.6"),
            new Location("Place 3", "Address 3", new String[] {PlaceType.RESTAURANT.toString()}, "3.4"),
            new Location("Place 4", "Address 4", new String[] {PlaceType.RESTAURANT.toString()}, "3.6")
        );

        List<String> distances = Arrays.asList("0.4", "0.6", "3.4", "3.6");

        when(apiManager.retrievePlacesOfCategory(anyString(), eq(PlaceType.RESTAURANT), eq(true))).thenReturn(samplePlaces);
        when(apiManager.retrievePlacesOfCategory(anyString(), not(eq(PlaceType.RESTAURANT)), eq(true))).thenReturn(List.of());
        when(apiManager.getWalkingDistances(anyString(), anyList(), eq(true))).thenReturn(distances);

        Map<String, String> places = locationManager.getPlaces("Sample Location", true);

        assertEquals("[{\"name\":\"Place 1\",\"address\":\"Address 1\",\"types\":[restaurant],\"distance\":\"0.4\"}, {\"name\":\"Place 2\",\"address\":\"Address 2\",\"types\":[restaurant],\"distance\":\"0.6\"}]", places.get("places"));
        
    }

    /**
     * Test actual functionality of APIManager, having it actually call the Google Maps API
     * 
     * No expected values because we don't know what the API will return
     */
    @Test
    public void testGetPlacesActual() {

        String testAddress = "1029 Sandoval Drive, Virginia Beach, VA 23454";

        Map<String, String> places = locationManager.getPlaces(testAddress, true);

        System.out.println(places.get("places"));
    }
}
