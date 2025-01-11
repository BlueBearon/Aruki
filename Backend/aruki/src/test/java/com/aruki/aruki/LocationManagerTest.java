package com.aruki.aruki;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.AdditionalMatchers.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import java.util.Arrays;
import java.util.List;

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

        try{
            when(apiManager.retrievePlacesOfCategory(eq(location), eq(PlaceType.RESTAURANT), eq(true))).thenReturn(samplePlaces);
            when(apiManager.retrievePlacesOfCategory(anyString(), not(eq(PlaceType.RESTAURANT)), eq(true))).thenReturn(List.of());
            when(apiManager.getWalkingDistances(anyString(), anyList(), eq(true))).thenReturn(Arrays.asList("0.4", "0.6", "1.2"));
            List<Location> places = locationManager.getPlaces(location, true);
            assertEquals("[{\"name\":\"The Green Leaf Diner\",\"address\":\"456 Oak Avenue, Example City, 12345\",\"types\":[restaurant],\"distance\":\"0.4\"}, {\"name\":\"The Red Tomato\",\"address\":\"789 Maple Street, Example City, 12345\",\"types\":[restaurant],\"distance\":\"0.6\"}, {\"name\":\"The Blue Ocean\",\"address\":\"123 Pine Road, Example City, 12345\",\"types\":[restaurant],\"distance\":\"1.2\"}]", places.toString());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            //fail the test with information about the error
            assertEquals("Error: " + e.getMessage(), "");
        }


        
    }

    @Test
    public void testGetScore() {
        String location = "Sample Location";
        String[] types = {PlaceType.RESTAURANT.toString()};

        List<Location> samplePlaces = Arrays.asList(
            new Location("Place 1", "Address 1", types,  "0.4"),
            new Location("Place 2", "Address 2", types, "0.6")
        );

        try{
            when(apiManager.retrievePlacesOfCategory(eq(location), eq(PlaceType.RESTAURANT), eq(true))).thenReturn(samplePlaces);
            when(apiManager.retrievePlacesOfCategory(anyString(), not(eq(PlaceType.RESTAURANT)), eq(true))).thenReturn(List.of());
            when(apiManager.getWalkingDistances(anyString(), anyList(), eq(true))).thenReturn(Arrays.asList("0.4", "0.6"));
            ScoreResponse score = locationManager.getScore(location, true);
            assertEquals(1.87, score.getWalkabilityScore());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            //fail the test with information about the error
            assertEquals("Error: " + e.getMessage(), "");
        }

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

        try{
            when(apiManager.retrievePlacesOfCategory(anyString(), eq(PlaceType.RESTAURANT), eq(true))).thenReturn(samplePlaces);
            when(apiManager.retrievePlacesOfCategory(anyString(), not(eq(PlaceType.RESTAURANT)), eq(true))).thenReturn(List.of());
            when(apiManager.getWalkingDistances(anyString(), anyList(), eq(true))).thenReturn(distances);

            List<Location> places = locationManager.getPlaces("Sample Location", true);

            assertEquals("[{\"name\":\"Place 1\",\"address\":\"Address 1\",\"types\":[restaurant],\"distance\":\"0.4\"}, {\"name\":\"Place 2\",\"address\":\"Address 2\",\"types\":[restaurant],\"distance\":\"0.6\"}]", places.toString());
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            //fail the test with information about the error
            assertEquals("Error: " + e.getMessage(), "");
        }
    }

}
