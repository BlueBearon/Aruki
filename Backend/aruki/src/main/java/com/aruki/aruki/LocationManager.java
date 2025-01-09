package com.aruki.aruki;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;


import com.google.maps.model.PlaceType;

public class LocationManager {


    // Constants/weights for each category of place
    public static final Map<PlaceType, Double> CATEGORY_CONSTANTS = Map.of(
        PlaceType.GROCERY_OR_SUPERMARKET, 1.5, // Essential for daily living
        PlaceType.RESTAURANT, 1.2,            // Regular need but less critical than groceries
        PlaceType.PARK, 1.1,                  // Encourages recreation and health
        PlaceType.SCHOOL, 1.3,                // Critical for families and community
        PlaceType.PHARMACY, 1.4,              // High priority for health and daily needs
        PlaceType.GYM, 1.0,                   // Important but not essential
        PlaceType.LIBRARY, 0.9,               // Valuable but situational
        PlaceType.SHOPPING_MALL, 0.8,         // Adds convenience but non-essential
        PlaceType.MOVIE_THEATER, 0.7,         // Entertainment, lower priority
        PlaceType.MUSEUM, 0.6                 // Cultural, but less frequent need
    );

    
    private static final double CLOSE_DISTANCE = 0.5;

    private static final double MEDIUM_DISTANCE = 1.0;

    private static final double FAR_DISTANCE = 1.5;

    private static final double SEARCH_RADIUS = 2.0;

    @Autowired
    private APIManager apiManager; //Isolates the Google Maps API calls, so that the LocationManager class is not directly dependent on the Google Maps API. 

    public LocationManager() {}

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

        for (PlaceType category : CATEGORY_CONSTANTS.keySet()) {
            Callable<List<Location>> task = () -> apiManager.retrievePlacesOfCategory(location, category, true);
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
        places = verifyWalkingDistances(location, places);

        return places;
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
    private List<Location> verifyWalkingDistances(String originAddress, List<Location> places) {

        List<Location> verifiedPlaces = new ArrayList<Location>();

        try
        {

            //API Request: Send Origin Address, and each place's address to Google Maps API to get walking distance
            List<String> placeAddresses = new ArrayList<String>();
            for (Location place : places)
            {
                placeAddresses.add(place.getAddress());
            }

            List<String> walkingDistances = apiManager.getWalkingDistances(originAddress, placeAddresses, true);

            for (int i = 0; i < places.size(); i++)
            {
                Location place = places.get(i);
                String walkingDistance = walkingDistances.get(i);

                if (Double.parseDouble(walkingDistance) <= SEARCH_RADIUS)
                {
                    place.setDistance(walkingDistance);
                    verifiedPlaces.add(place);
                }
            }

            return verifiedPlaces; 
        }
        catch (Exception e)
        {
            return places;
        }

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

        Map<String, String> result = new HashMap<>();
        result.put("walkability", String.valueOf(walkability));
        result.put("scores", scores.toString());

        return result;

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
        PlaceType category = getCategory(location.getTypes()[0]);

        return CATEGORY_CONSTANTS.getOrDefault(category, 0.0) * (2 - Double.parseDouble(location.getDistance()));
    }


    /**
     * This method calculates the walkability score of a location respective to one specific category of place.
     * 
     * The score is calculated by summing the walkability significance scores of each place in the location that is of the specified category.
     * 
     * @param places - The list of places in the location
     * @return double - The walkability score of the location respective to the category of place
     */
    private Map<String, String> calculateCategoryScore(List<Location> places, PlaceType category)
    {
        double score = 0.0;

        int closePlaces = 0;

        int mediumPlaces = 0;

        int farPlaces = 0;

        for (Location place : places)
        {
            if (place.getTypes()[0].equals(category.toString()))
            {
                double distance = Double.parseDouble(place.getDistance());

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
        //return Map.of("category", category, "score", String.valueOf(score), "closePlaces", String.valueOf(closePlaces), "mediumPlaces", String.valueOf(mediumPlaces), "farPlaces", String.valueOf(farPlaces));
        Map<String, String> categoryScore = new HashMap<String, String>();
        categoryScore.put("category", category.toString());
        categoryScore.put("score", String.valueOf(score));
        categoryScore.put("closePlaces", String.valueOf(closePlaces));
        categoryScore.put("mediumPlaces", String.valueOf(mediumPlaces));
        categoryScore.put("farPlaces", String.valueOf(farPlaces));

        System.out.println("Category Score: " + categoryScore);

        return categoryScore;
        
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
       
        Map<String, String> scores = new HashMap<String, String>();

        for (PlaceType category : CATEGORY_CONSTANTS.keySet()) // Use CATEGORY_CONSTANTS key set
        {
            Map<String, String> categoryScore = calculateCategoryScore(places, category);
            scores.put(category.toString(), categoryScore.toString());
        }

        return scores;
    }


    /**
     * This method retrieves the category of a place.
     * 
     * @param type - The type of the place
     * @return PlaceType - The category of the place
     */
    private PlaceType getCategory(String type) {
        for (PlaceType category : CATEGORY_CONSTANTS.keySet()) {
            if (category.toString().equals(type)) {
                return category;
            }
        }
        return null;
    }
  
    
}
