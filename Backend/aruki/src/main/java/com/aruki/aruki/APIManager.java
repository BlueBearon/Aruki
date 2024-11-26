package com.aruki.aruki;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

/**
 * APIManager is responsible for managing API calls to the Google Maps API and Distance Matrix API and providing sample data if necessary.
 */
public class APIManager {

    /**
     * Retrieves a list of places of a specific category near a given location.
     * 
     * @param location the location to search near
     * @param category the category of places to search for
     * @param test whether to use sample data for testing
     * @return a list of places matching the category near the location
     */
    public List<Location> retrievePlacesOfCategory(String location, String category, boolean test) {
        // TODO: Implement real version of this method
        
        if (test) 
        {
            return sampleData_retrievePlacesOfCategory(location, category);
        }
        else
        {
            // Function is not implemented yet, give detailed explanation
            throw new UnsupportedOperationException("retrievePlacesOfCategory() is not implemented yet.");
        }
    }

    /**
     * Retrieves walking distances from an origin address to a list of place addresses.
     * 
     * @param originAddress the starting address
     * @param placeAddresses the list of destination addresses
     * @param test whether to use sample data for testing
     * @return a list of walking distances to each destination address
     */
    public List<String> getWalkingDistances(String originAddress, List<String> placeAddresses, boolean test) {
        // TODO: Implement real version of this method

        if (test) 
        {
            return sampleData_getWalkingDistances(originAddress, placeAddresses);
        }
        else
        {
            // Function is not implemented yet, give detailed explanation
            throw new UnsupportedOperationException("getWalkingDistances() is not implemented yet.");
        }

    }

    private static final RandomGenerator random = RandomGenerator.getDefault();

    /**
     * Provides sample data mimicking the output from the Google Places API.
     * 
     * @param location the location to search near
     * @param category the category of places to search for
     * @return a list of sample places matching the category near the location
     */
    public static List<Location> sampleData_retrievePlacesOfCategory(String location, String category) {
        
        // This method should mimic output from the Google Places API
        List<Location> places = new ArrayList<>();
        List<String> categories = new ArrayList<>();

        // Placeholder Data
        switch(category)
        {
            case "restaurant":
                categories.add("restaurant");
                places.add(new Location("The Green Leaf Diner", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The Red Tomato", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The Blue Ocean", "123 Pine Road, Example City, 12345", categories));
                break;
            case "grocery_or_supermarket":
                categories.add("grocery_or_supermarket");
                places.add(new Location("The Fresh Market", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The Local Market", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The Organic Market", "123 Pine Road, Example City, 12345", categories));
                break;
            case "park":
                categories.add("park");
                places.add(new Location("The Green Park", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The Red Park", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The Blue Park", "123 Pine Road, Example City, 12345", categories));
                break;
            case "school":
                categories.add("school");
                places.add(new Location("The Elementary School", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The Middle School", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The High School", "123 Pine Road, Example City, 12345", categories));
                break;
            case "pharmacy":
                categories.add("pharmacy");
                places.add(new Location("The Local Pharmacy", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The National Pharmacy", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The International Pharmacy", "123 Pine Road, Example City, 12345", categories));
                break;
            case "gym":
                categories.add("gym");
                places.add(new Location("The Local Gym", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The National Gym", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The International Gym", "123 Pine Road, Example City, 12345", categories));
                break;
            case "library":
                categories.add("library");
                places.add(new Location("The Local Library", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The National Library", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The International Library", "123 Pine Road, Example City, 12345", categories));
                break;
            case "shopping_mall":
                categories.add("shopping_mall");
                places.add(new Location("The Local Mall", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The National Mall", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The International Mall", "123 Pine Road, Example City, 12345", categories));
                break;
            case "movie_theater":
                categories.add("movie_theater");
                places.add(new Location("The Local Theater", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The National Theater", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The International Theater", "123 Pine Road, Example City, 12345", categories));
                break;
            case "museum":
                categories.add("museum");
                places.add(new Location("The Local Museum", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The National Museum", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The International Museum", "123 Pine Road, Example City, 12345", categories));
                break;
            default:
                break;
            

        }

        return places;

    }

    /**
     * Provides sample data mimicking the output from the Google Maps Distance Matrix API.
     * 
     * @param originAddress the starting address
     * @param placeAddresses the list of destination addresses
     * @return a list of sample walking distances to each destination address
     */
    public static List<String> sampleData_getWalkingDistances(String originAddress, List<String> placeAddresses) {
        
        // This method should mimic output from the Google Maps Distance Matrix API

        List<String> walkingDistances = new ArrayList<>();

        // Placeholder Data (units are in miles because America F*** Yeah!)
        for (String placeAddress : placeAddresses) {
            walkingDistances.add(String.valueOf(random.nextDouble(0.1, 2.0)));
        }

        return walkingDistances;
        
    }



    
}
