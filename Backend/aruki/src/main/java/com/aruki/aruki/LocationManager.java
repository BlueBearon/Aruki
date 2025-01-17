package com.aruki.aruki;

import java.io.IOException;
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

import com.google.maps.errors.ApiException;
import com.google.maps.model.PlaceType;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * The <code>LocationManager</code> class is responsible for managing and retrieving location-based data.
 * It interacts with the Google Maps API through the <code>APIManager</code> class to fetch places of various categories
 * near a specified location. The class also verifies walking distances to these places and calculates
 * a walkability score for the location based on the proximity and importance of nearby places.
 * 
 * <p>
 * The class uses multithreading to handle API requests efficiently and processes the results to provide
 * a comprehensive analysis of the location's accessibility to essential and non-essential services.
 * </p>
 * 
 * <p>
 * Key functionalities include:
 * <ul>
 *   <li>Retrieving places of different categories near a specified location</li>
 *   <li>Verifying walking distances to these places</li>
 *   <li>Calculating a walkability score based on the proximity and importance of nearby places</li>
 * </ul>
 * </p>
 * 
 * <p>
 * The class interacts with the following classes:
 * <ul>
 *   <li>{@link APIManager} - To isolate and handle Google Maps API calls</li>
 *   <li>{@link Location} - To represent individual places and their attributes</li>
 *   <li>{@link ScoreResponse} - To encapsulate the walkability score and category scores</li>
 *   <li>{@link CategoryScore} - To represent scores for individual categories of places</li>
 * </ul>
 * </p>
 * 
 * <p>
 * The class also uses constants to define weights for different categories of places and thresholds for
 * distance calculations.
 * </p>
 */
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

    private static final double SEARCH_RADIUS = 2.0; // kilometers
    private static final double CLOSE_DISTANCE = 0.5; // kilometers
    private static final double MEDIUM_DISTANCE = 1.0; // kilometers
    private static final double FAR_DISTANCE = 2.0; // kilometers
    private static final int BATCH_SIZE = 25; // Define a batch size for API requests

    @Autowired
    private APIManager apiManager; // Isolates the Google Maps API calls, so that the LocationManager class is not directly dependent on the Google Maps API. 

    public LocationManager() {}

    public LocationManager(APIManager apiManager) {
        this.apiManager = apiManager;
    }

    /**
     * Retrieves places near the specified location.
     * 
     * @param location The location to retrieve places near
     * @param test Whether to use test data
     * @return List of places near the location
     * @throws ApiException If there is an error with the API request
     * @throws InterruptedException If the thread is interrupted
     * @throws IOException If there is an I/O error
     */
    public List<Location> getPlaces(String location, boolean test) throws ApiException, InterruptedException, IOException {
        List<Location> places = retrievePlaces(location, test);
        return places;
    }

    /**
     * Retrieves places of each category near the specified location.
     * 
     * The method uses the Google Maps API to retrieve places of each category near the location.
     * The method then verifies the walking distances of the places.
     * 
     * @param location The location to retrieve places near
     * @param test Whether to use test data
     * @return List of places near the location
     * @throws ApiException If there is an error with the API request
     * @throws InterruptedException If the thread is interrupted
     * @throws IOException If there is an I/O error
     */
    private List<Location> retrievePlaces(String location, boolean test) throws ApiException, InterruptedException, IOException {
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
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof ApiException) {
                    throw (ApiException) cause;
                } else if (cause instanceof IOException) {
                    throw (IOException) cause;
                } else if (cause instanceof InterruptedException) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                    throw (InterruptedException) cause;
                } else {
                    throw new RuntimeException(cause);
                }
            }
        }

        executor.shutdown();

        // Verify walking distances of places
        places = verifyWalkingDistances(location, places, test);

        return places;
    }

    /**
     * Verifies the walking distances of the places in the location.
     * 
     * This function is necessary as distance as the crow flies often does not equal the actual walking distance.
     * If the walking distance is greater than the maximum search radius, the place is removed from the list of places.
     * 
     * @param originAddress The address of the origin
     * @param places The list of places to verify the walking distances of
     * @param test Whether to use test data
     * @return List of places with verified walking distances
     * @throws ApiException If there is an error with the API request
     * @throws InterruptedException If the thread is interrupted
     * @throws IOException If there is an I/O error
     */
    private List<Location> verifyWalkingDistances(String originAddress, List<Location> places, boolean test) throws ApiException, InterruptedException, IOException {
        List<Location> verifiedPlaces = new ArrayList<Location>();

        try {
            int numBatches = (int) Math.ceil((double) places.size() / BATCH_SIZE);
            ExecutorService executor = Executors.newFixedThreadPool(numBatches);
            List<Future<List<Location>>> futures = new ArrayList<>();

            for (int i = 0; i < places.size(); i += BATCH_SIZE) {
                int start = i;
                int end = Math.min(start + BATCH_SIZE, places.size());
                Callable<List<Location>> task = () -> verifyWalkingDistancesWithThreadsSublist(originAddress, places, start, end, test);
                futures.add(executor.submit(task));
            }

            for (Future<List<Location>> future : futures) {
                try {
                    verifiedPlaces.addAll(future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    if (cause instanceof ApiException) {
                        throw (ApiException) cause;
                    } else if (cause instanceof IOException) {
                        throw (IOException) cause;
                    } else if (cause instanceof InterruptedException) {
                        Thread.currentThread().interrupt(); // Restore interrupted status
                        throw (InterruptedException) cause;
                    } else {
                        throw new RuntimeException(cause);
                    }
                }
            }

            executor.shutdown();
            return verifiedPlaces; 
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Verifies the walking distances of a sublist of places in the location.
     * 
     * @param originAddress The address of the origin
     * @param places The list of places to verify the walking distances of
     * @param start The start index of the sublist
     * @param end The end index of the sublist
     * @param test Whether to use test data
     * @return List of places with verified walking distances
     */
    private List<Location> verifyWalkingDistancesWithThreadsSublist(String originAddress, List<Location> places, int start, int end, boolean test) {
        List<Location> verifiedPlaces = new ArrayList<Location>();
        List<Location> sublist = places.subList(start, end);

        try {
            List<String> placeAddresses = new ArrayList<String>();
            for (Location place : sublist) {
                placeAddresses.add(place.getAddress());
            }

            List<String> walkingDistances = apiManager.getWalkingDistances(originAddress, placeAddresses, test);

            for (int j = 0; j < sublist.size(); j++) {
                Location place = sublist.get(j);
                String walkingDistance = walkingDistances.get(j);
                if (Location.parseDistance(walkingDistance) <= SEARCH_RADIUS) {
                    place.setDistance(walkingDistance);
                    verifiedPlaces.add(place);
                }
            }

            return verifiedPlaces;
        } catch (Exception e) {
            return sublist;
        }
    }

    /**
     * Retrieves the score of the location.
     * 
     * The method retrieves the places near the location.
     * The method calculates the score of the location based on the places near the location.
     * 
     * @param location The location to retrieve the score of
     * @param test Whether to use test data
     * @return ScoreResponse The score of the location
     * @throws ApiException If there is an error with the API request
     * @throws InterruptedException If the thread is interrupted
     * @throws IOException If there is an I/O error
     */
    public ScoreResponse getScore(String location, boolean test) throws ApiException, InterruptedException, IOException {
        List<Location> places = retrievePlaces(location, test);

        ScoreResponse result = new ScoreResponse();
        Map<String, CategoryScore> scores = new HashMap<String, CategoryScore>();

        for (PlaceType category : CATEGORY_CONSTANTS.keySet()) {
            scores.put(category.toString(), new CategoryScore(category.toString(), 0.0, 0, 0, 0));
        }

        CategoryScore overall = new CategoryScore("overall", 0.0, 0, 0, 0);
        scores.put("overall", overall);

        for (Location place : places) {
            calculateLocationScore(place, scores);
        }

        double walkability = scores.get("overall").getScore();
        result.setWalkabilityScore(walkability);

        List<CategoryScore> categoryScores = new ArrayList<CategoryScore>();
        for (PlaceType category : CATEGORY_CONSTANTS.keySet()) {
            categoryScores.add(scores.get(category.toString()));
        }

        result.setCategoryScores(categoryScores);
        return result;
    }

    /**
     * Calculates the score of the location based on the places near the location.
     * 
     * @param location The location to calculate the score of
     * @param scores The scores of the location
     */
    private void calculateLocationScore(Location location, Map<String, CategoryScore> scores) {
        String[] categories = location.getTypes();

        for (String category : categories) {
            PlaceType placeType = getCategory(category);

            if (placeType != null) {
                double distance = Location.parseDistance(location.getDistance());
                double distancePenalty = Math.exp(-distance / SEARCH_RADIUS);

                // Round to 2 decimal places
                distancePenalty = Math.round(distancePenalty * 100.0) / 100.0;

                // Update overall score
                CategoryScore overall = scores.get("overall");
                double newScore = overall.getScore() + CATEGORY_CONSTANTS.get(placeType) * distancePenalty;

                // Round to 2 decimal places
                newScore = Math.round(newScore * 100.0) / 100.0;
                overall.setScore(newScore);
                scores.put("overall", overall);

                // Update category score (no need to consider CategoryConstants, but do need to update close, medium, far counts)
                CategoryScore categoryScore = scores.get(category);
                double newCategoryScore = Math.round((categoryScore.getScore() + distancePenalty) * 100.0) / 100.0;
                categoryScore.setScore(newCategoryScore);

                if (distance <= CLOSE_DISTANCE) {
                    categoryScore.setClosePlaces(categoryScore.getClosePlaces() + 1);
                } else if (distance <= MEDIUM_DISTANCE) {
                    categoryScore.setMediumPlaces(categoryScore.getMediumPlaces() + 1);
                } else if (distance <= FAR_DISTANCE) {
                    categoryScore.setFarPlaces(categoryScore.getFarPlaces() + 1);
                }

                scores.put(category, categoryScore);
            }
        }
    }

    /**
     * Retrieves the category of a place.
     * 
     * @param type The type of the place
     * @return PlaceType The category of the place
     */
    private PlaceType getCategory(String type) {
        for (PlaceType category : CATEGORY_CONSTANTS.keySet()) {
            if (category.toString().equals(type)) {
                return category;
            }
        }
        return null;
    }

    /**
     * Checks if the location exists.
     * 
     * @param location The location to check
     * @return boolean True if the location exists, false otherwise
     */
    public boolean locationExists(String location) {
        return apiManager.locationExists(location);
    }

    public static void main(String[] args) {
        try {
            APIManager apiManager = new APIManager();
            LocationManager locationManager = new LocationManager(apiManager);

            Dotenv dotenv = Dotenv.load();
            String testAddress = dotenv.get("TEST_ADDRESS");

            System.out.println(locationManager.getPlaces(testAddress, false));
            System.out.println("\n\n\n");
            System.out.println(locationManager.getScore(testAddress, false));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
