package com.aruki.aruki;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GoogleMapsAPIManager {


    //Constants/weights for each category of place
    private static final Map<String, Double> CATEGORY_CONSTANTS = Map.of("restaurant", 1.0, "school", 1.0, "park", 1.0, "grocery_or_supermarket", 1.0, "gym", 1.0, "library", 1.0, "movie_theater", 1.0, "museum", 1.0, "shopping_mall", 1.0);

    private static final double CLOSE_DISTANCE = 0.5;

    private static final double MEDIUM_DISTANCE = 1.0;

    private static final double FAR_DISTANCE = 1.5;

    private static final double SEARCH_RADIUS = 2.0;

    public GoogleMapsAPIManager() {}

    public Map<String, String> getPlaces(String location) {
        List<Location> places = retrievePlaces(location);
        return Map.of("places", places.toString());
    }
    
    /**
     * This method retrieves places of each category near the location.
     * 
     * The method uses the Google Maps API to retrieve places of each category near the location.
     * 
     * The method then verifies the walking distances of the places.
     * 
     * @param location - The location to retrieve places near
     * @return List<Location> - The list of places near the location
     */
    private List<Location> retrievePlaces(String location) {
        
        List<Location> places = new ArrayList<Location>();
        ExecutorService executor = Executors.newFixedThreadPool(CATEGORY_CONSTANTS.size());
        List<Future<List<Location>>> futures = new ArrayList<>();

        for (String category : CATEGORY_CONSTANTS.keySet()) {
            Callable<List<Location>> task = () -> retrievePlacesOfCategory(location, category);
            futures.add(executor.submit(task));
        }

        for (Future<List<Location>> future : futures) {
            try {
                places.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        // Verify walking distances of places
        places = verifyWalkingDistances(places);

        return places;
    }

    private List<Location> retrievePlacesOfCategory(String location, String category) {

        // Use Google Maps API to retrieve places of the specified category near the location
        
        return null;
    }

    /**
     * This method verifies the walking distances of the places in the location.
     * 
     * This function is necessary as distance as the crow flies often does equal the actual walking distance.
     * 
     * If the walking distance is greater than the maximum search radius, the place is removed from the list of places.
     * 
     * @param places
     * @return
     */
    private List<Location> verifyWalkingDistances(List<Location> places) {

        List<Location> verifiedPlaces = new ArrayList<Location>();

        try
        {

            for (Location place : places)
            {
                Location verifiedPlace = verifyWalkingDistance(place);

                if (verifiedPlace != null)
                {
                    verifiedPlaces.add(verifiedPlace);
                }
            }

            return verifiedPlaces; 
        }
        catch (Exception e)
        {
            return null;
        }

    }

    /**
     * This method verifies the walking distance of a place.
     * 
     * This function is necessary as distance as the crow flies often does equal the actual walking distance.
     * 
     * If the walking distance is greater than the maximum search radius, the place is not returned, instead null is returned.
     * 
     * @param place - The place to verify the walking distance of
     * @return Location - The place with the verified walking distance
     */
    private Location verifyWalkingDistance(Location place) {

        // Use Google Maps Distance Matrix API to verify walking distance of place

        return null;
    }

    /**
     * 
     * @param location
     * @return
     */
    public Map<String, String> getScore(String location) {

        // Example Response: { "walkability": "7.5", "scores": [ { "category": "restaurant", "score": "8.2", "closePlaces" : "5", "mediumPlaces" : "3", "farPlaces" : "2" }, { "category": "park", "score": "9.1", "closePlaces" : "3", "mediumPlaces" : "4", "farPlaces" : "1" } ] }

        List<Location> places = retrievePlaces(location);

        // Find overall walking score
        double walkability = calculateOverallScore(places);

        // Find walking score for each category
        Map<String, String> scores = calculateCategoryScores(places);

        return Map.of("walkability", String.valueOf(walkability), "scores", scores.toString());

    }

    /**
     * This method calculates the walkability significance score of a location to the original location.
     * 
     * WS (of location i) = categoryConstant * (2 - distanceToLocation)
     * 
     * @param location - The location to calculate the score of
     * @return double - The walkability significance score of the location
     */
    private double calculateLocationScore(Location location)
    {
        return CATEGORY_CONSTANTS.get(location.getCategory()) * (2 - Double.parseDouble(location.getWalkingDistance()));
    }


    /**
     * This method calculates the walkability score of a location respective to one specific category of place.
     * 
     * The score is calculated by summing the walkability significance scores of each place in the location that is of the specified category.
     * 
     * @param places - The list of places in the location
     * @return double - The walkability score of the location respective to the category of place
     */
    private Map<String, String> calculateCategoryScore(List<Location> places, String category)
    {

        double score = 0.0;

        int closePlaces = 0;

        int mediumPlaces = 0;

        int farPlaces = 0;

        for (Location place : places)
        {
            if (place.getCategory().equals(category))
            {
                double distance = Double.parseDouble(place.getWalkingDistance());

                score += calculateLocationScore(place);

                if (distance <= CLOSE_DISTANCE)
                {
                    closePlaces++;
                }
                else if (distance <= MEDIUM_DISTANCE)
                {
                    mediumPlaces++;
                }
                else if (distance <= FAR_DISTANCE)
                {
                    farPlaces++;
                }
               
            }
        }

        return Map.of("category", category, "score", String.valueOf(score), "closePlaces", String.valueOf(closePlaces), "mediumPlaces", String.valueOf(mediumPlaces), "farPlaces", String.valueOf(farPlaces));
        
    }

    /**
     * This method calculates the overall walkability score of a location.
     * 
     * The score is calculated by summing the walkability significance scores of each place in the location.
     * 
     * @param places - The list of places in the location
     * @return double - The overall walkability score of the location
     */
    public double calculateOverallScore(List<Location> places) {
            
        double score = 0.0;

        for (Location place : places)
        {
            score += calculateLocationScore(place);
        }

        return score;
    }

    /**
     * This method calculates the walkability score of a location respective to each category of place.
     * 
     * The score is calculated by summing the walkability significance scores of each place in the location that is of the specified category.
     * 
     * @param places - The list of places in the location
     * @return Map<String, String> - The walkability score of the location respective to each category of place
     */
    public Map<String, String> calculateCategoryScores(List<Location> places) {
       
        Map<String, String> scores = Map.of();

        for (String category : CATEGORY_CONSTANTS.keySet()) // Use CATEGORY_CONSTANTS key set
        {
            Map<String, String> categoryScore = calculateCategoryScore(places, category);
            scores.put(category, categoryScore.toString());
        }

        return scores;
    }
  
    
}
