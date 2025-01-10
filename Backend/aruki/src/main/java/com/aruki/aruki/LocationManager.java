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

import io.github.cdimascio.dotenv.Dotenv;





public class LocationManager {


    private class CategoryScore
    {
        public String category;
        public double score;
        public int closePlaces;
        public int mediumPlaces;
        public int farPlaces;

        public CategoryScore(String category, double score, int closePlaces, int mediumPlaces, int farPlaces)
        {
            this.category = category;
            this.score = score;
            this.closePlaces = closePlaces;
            this.mediumPlaces = mediumPlaces;
            this.farPlaces = farPlaces;
        }

        public double getScore()
        {
            return this.score;
        }

        public int getClosePlaces()
        {
            return this.closePlaces;
        }

        public int getMediumPlaces()
        {
            return this.mediumPlaces;
        }

        public int getFarPlaces()
        {
            return this.farPlaces;
        }


        public void setScore(double score)
        {
            this.score = score;
        }

        public void setClosePlaces(int closePlaces)
        {
            this.closePlaces = closePlaces;
        }

        public void setMediumPlaces(int mediumPlaces)
        {
            this.mediumPlaces = mediumPlaces;
        }

        public void setFarPlaces(int farPlaces)
        {
            this.farPlaces = farPlaces;
        }

        public String toString()
        {
            return "{\"category\":\"" + category + "\",\"score\":\"" + score + "\",\"closePlaces\":\"" + closePlaces + "\",\"mediumPlaces\":\"" + mediumPlaces + "\",\"farPlaces\":\"" + farPlaces + "\"}";
        }
    }


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

    

    private static final double SEARCH_RADIUS = 2.0; // kilometers

    private static final double CLOSE_DISTANCE = 0.5; // kilometers

    private static final double MEDIUM_DISTANCE = 1.0; // kilometers

    private static final double FAR_DISTANCE = 2.0; // kilometers

    private static final int BATCH_SIZE = 25; // Define a batch size for API requests

    @Autowired
    private APIManager apiManager; //Isolates the Google Maps API calls, so that the LocationManager class is not directly dependent on the Google Maps API. 

    public LocationManager() {}


    public LocationManager(APIManager apiManager) {
        this.apiManager = apiManager;
    }

    /**
     * This method retrieves places near the location.
     * @param location - The location to retrieve places near
     * @param test - Whether to use test data
     * @return Map<String, String> - The list of places near the location
     */
    public Map<String, String> getPlaces(String location, boolean test) {
        List<Location> places = retrievePlaces(location, test);
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
     * @param test - Whether to use test data
     * @return List<Location> - The list of places near the location
     */
    private List<Location> retrievePlaces(String location, boolean test) {
        
        List<Location> places = new ArrayList<Location>();
        ExecutorService executor = Executors.newFixedThreadPool(CATEGORY_CONSTANTS.size());
        List<Future<List<Location>>> futures = new ArrayList<>();

        for (PlaceType category : CATEGORY_CONSTANTS.keySet()) {
            Callable<List<Location>> task = () -> apiManager.retrievePlacesOfCategory(location, category, test);
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

        System.out.println("Places Before Walkability Check: ");

        for (Location place : places) {
            System.out.println(place);
        }

        System.out.println("Places After Walkability Check: ");

        // Verify walking distances of places
        places = verifyWalkingDistances(location, places, test);

        for (Location place : places) {
            System.out.println(place);
        }

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
    private List<Location> verifyWalkingDistances(String originAddress, List<Location> places, boolean test) {

        List<Location> verifiedPlaces = new ArrayList<Location>();

        int initialCount = places.size();
        int removedCount = 0;
        int finalCount = 0;

        try
        {

            //API Request: Send Origin Address, and each place's address to Google Maps API to get walking distance
            List<String> placeAddresses = new ArrayList<String>();
            for (Location place : places)
            {
                placeAddresses.add(place.getAddress());
            }

            int totalPlaces = placeAddresses.size();
            for (int i = 0; i < totalPlaces; i += BATCH_SIZE) {
                int end = Math.min(i + BATCH_SIZE, totalPlaces);
                List<String> batch = placeAddresses.subList(i, end);
                List<String> walkingDistances = apiManager.getWalkingDistances(originAddress, batch, test);

                for (int j = 0; j < batch.size(); j++) {
                    Location place = places.get(i + j);
                    String walkingDistance = walkingDistances.get(j);
                    if (Location.parseDistance(walkingDistance) <= SEARCH_RADIUS) {
                        place.setDistance(walkingDistance);
                        verifiedPlaces.add(place);
                    }
                    else
                    {
                        System.out.println("Place " + place.getName() + " removed due to distance: " + walkingDistance + "\n");
                        removedCount++;
                    }
                }
            }

            finalCount = verifiedPlaces.size();

            System.out.println("\n\n Places Removed: " + removedCount + " out of " + initialCount + " total places. Final Count is " + finalCount + "\n\n");

            return verifiedPlaces; 
        }
        catch (Exception e)
        {
            return places;
        }

    }

    /**
     * This method retrieves the score of the location.
     * 
     * The method retrieves the places near the location.
     * 
     * The method calculates the score of the location based on the places near the location.
     * 
     * @param location - The location to retrieve the score of
     * @param test - Whether to use test data
     * @return Map<String, String> - The score of the location
     */
    public Map<String, String> getScore(String location, boolean test)
    {
        List<Location> places = retrievePlaces(location, test);

        Map<String, CategoryScore> scores = new HashMap<String, CategoryScore>();

        for (PlaceType category : CATEGORY_CONSTANTS.keySet())
        {
            scores.put(category.toString(), new CategoryScore(category.toString(), 0.0, 0, 0, 0));
        }

        CategoryScore overall = new CategoryScore("overall", 0.0, 0, 0, 0);

        scores.put("overall", overall);

        for (Location place : places)
        {
            calculateLocationScore(place, scores);
        }

        Map<String, String> result = new HashMap<String, String>();

        double walkability = scores.get("overall").getScore();

        result.put("walkability", String.valueOf(walkability));

        Map<String, String> categoryScores = new HashMap<String, String>();

        for (PlaceType category : CATEGORY_CONSTANTS.keySet())
        {
            categoryScores.put(category.toString(), scores.get(category.toString()).toString());
        }

        result.put("scores", categoryScores.toString());

        return result;
    }

    /**
     * This method calculates the score of the location based on the places near the location.
     * @param location - The location to calculate the score of
     * @param scores - The scores of the location
     */
    private void calculateLocationScore(Location location, Map<String, CategoryScore> scores)
    {
        String[] categories = location.getTypes();

        for(String category : categories)
        {
            PlaceType placeType = getCategory(category);

            if(placeType != null)
            {

                double distance = Location.parseDistance(location.getDistance());

                double distancePenalty = Math.exp(-distance / SEARCH_RADIUS);

                //round to 2 decimal places
                distancePenalty = Math.round(distancePenalty * 100.0) / 100.0;

                
                //update overall score
                CategoryScore overall = scores.get("overall");

                double newScore = overall.getScore() + CATEGORY_CONSTANTS.get(placeType) * distancePenalty;

                //round to 2 decimal places
                newScore = Math.round(newScore * 100.0) / 100.0;
                overall.setScore(newScore);
                scores.put("overall", overall);

                //update category score (no need to consider CategoryConstants, but do need to update close, medium, far counts)
                CategoryScore categoryScore = scores.get(category);
                categoryScore.setScore(categoryScore.getScore() + distancePenalty);

                if (distance <= CLOSE_DISTANCE)
                {
                    categoryScore.setClosePlaces(categoryScore.getClosePlaces() + 1);
                }
                else if (distance <= MEDIUM_DISTANCE)
                {
                    categoryScore.setMediumPlaces(categoryScore.getMediumPlaces() + 1);
                }
                else if (distance <= FAR_DISTANCE)
                {
                    categoryScore.setFarPlaces(categoryScore.getFarPlaces() + 1);
                }

                scores.put(category, categoryScore);
                
            }
        }
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


    public static void main(String[] args) {
        APIManager apiManager = new APIManager();
        LocationManager locationManager = new LocationManager(apiManager);

        Dotenv dotenv = Dotenv.load();
        String testAddress = dotenv.get("TEST_ADDRESS");
        //testAddress = "5 Chome-11-18 Hiyoshi, Kohoku Ward, Yokohama, Kanagawa 223-0061, Japan";
        //testAddress = "2300 Alloway Ct, Virginia Beach, VA 23454";
        //testAddress = "4 Chome-2-8 Shibakoen, Minato City, Tokyo 105-0011, Japan";

        //Map<String, String> mappy = locationManager.getPlaces(testAddress, false);
        System.out.println("\n\n\n");
        System.out.println(locationManager.getScore(testAddress, false));
    }
  
    
}
