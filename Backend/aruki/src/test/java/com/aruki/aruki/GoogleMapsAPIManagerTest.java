package com.aruki.aruki;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import java.util.List;

public class GoogleMapsAPIManagerTest {

    private GoogleMapsAPIManager googleMapsAPIManager;

    @BeforeEach
    public void setUp() {
        googleMapsAPIManager = new GoogleMapsAPIManager();
    }

    @Test
    public void testGetPlaces() {
        String location = "some_location";
        Map<String, String> places = googleMapsAPIManager.getPlaces(location);
        assertNotNull(places);
        assertTrue(places.containsKey("places"));
    }

    @Test
    public void testGetScore() {
        String location = "some_location";
        Map<String, String> score = googleMapsAPIManager.getScore(location);
        assertNotNull(score);
        assertTrue(score.containsKey("walkability"));
        assertTrue(score.containsKey("scores"));
    }

    @Test
    public void testCalculateOverallScore() {
        List<Location> places = List.of(
            new Location("Restaurant", "id1", "address1", "0.3", "0.3", "restaurant"),
            new Location("Park", "id2", "address2", "0.7", "0.7", "park")
        );
        double score = googleMapsAPIManager.calculateOverallScore(places);
        assertTrue(score > 0);
    }

    @Test
    public void testCalculateCategoryScores() {
        List<Location> places = List.of(
            new Location("Restaurant", "id1", "address1", "0.3", "0.3", "restaurant"),
            new Location("Park", "id2", "address2", "0.7", "0.7", "park")
        );
        Map<String, String> scores = googleMapsAPIManager.calculateCategoryScores(places);
        assertNotNull(scores);
        assertTrue(scores.containsKey("restaurant"));
        assertTrue(scores.containsKey("park"));
    }
}
