package com.aruki.aruki;

// Google Maps API Libraries
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlaceType;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.google.maps.model.TravelMode;

// Java Libraries
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

/**
 * APIManager is responsible for managing API calls to the Google Maps API and Distance Matrix API and providing sample data if necessary.
 */
public class APIManager {


    private final String apiKey = "YOUR_API_KEY";
    private final GeoApiContext context;


    public APIManager(){
        this.context = new GeoApiContext.Builder().apiKey(apiKey).build();
    }


    /**
     * Retrieves a list of places of a specific category near a given location.
     * 
     * @param location the location to search near
     * @param category the category of places to search for
     * @param test whether to use sample data for testing
     * @return a list of places matching the category near the location
     */
    public List<Location> retrievePlacesOfCategory(String location, String category, boolean test) {
        
        if (test) 
        {
            return sampleData_retrievePlacesOfCategory(location, category);
        }
        else
        {
            try{
                // category is a string for the PlaceType
                PlacesSearchResponse response = PlacesApi.textSearchQuery(context, location).type(PlaceType.valueOf(category)).await();
                List<Location> places = new ArrayList<>();
                for (PlacesSearchResult result : response.results) {
                    places.add(new Location(result.name, result.formattedAddress, result.types));
                }
                return places;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
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

        if (test) 
        {
            return sampleData_getWalkingDistances(originAddress, placeAddresses);
        }
        else
        {
            List<String> distances = new ArrayList<>();
            try {
                DistanceMatrix matrix = DistanceMatrixApi.newRequest(context)
                        .origins(originAddress)
                        .destinations(placeAddresses.toArray(new String[0]))
                        .mode(TravelMode.WALKING)
                        .await();

                for (DistanceMatrixRow row : matrix.rows) {
                    distances.add(row.elements[0].distance.humanReadable);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return distances;
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
        String[] categories = new String[1];

        // Placeholder Data
        switch(category)
        {
            case "restaurant":
                categories[0] = "restaurant";
                places.add(new Location("The Green Leaf Diner", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The Red Tomato", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The Blue Ocean", "123 Pine Road, Example City, 12345", categories));
                break;
            case "grocery_or_supermarket":
                categories[0] = "grocery_or_supermarket";
                places.add(new Location("The Fresh Market", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The Local Market", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The Organic Market", "123 Pine Road, Example City, 12345", categories));
                break;
            case "park":
                categories[0] = "park";
                places.add(new Location("The Green Park", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The Red Park", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The Blue Park", "123 Pine Road, Example City, 12345", categories));
                break;
            case "school":
                categories[0] = "school";
                places.add(new Location("The Elementary School", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The Middle School", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The High School", "123 Pine Road, Example City, 12345", categories));
                break;
            case "pharmacy":
                categories[0] = "pharmacy";
                places.add(new Location("The Local Pharmacy", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The National Pharmacy", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The International Pharmacy", "123 Pine Road, Example City, 12345", categories));
                break;
            case "gym":
                categories[0] = "gym";
                places.add(new Location("The Local Gym", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The National Gym", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The International Gym", "123 Pine Road, Example City, 12345", categories));
                break;
            case "library":
                categories[0] = "library";
                places.add(new Location("The Local Library", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The National Library", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The International Library", "123 Pine Road, Example City, 12345", categories));
                break;
            case "shopping_mall":
                categories[0] = "shopping_mall";
                places.add(new Location("The Local Mall", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The National Mall", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The International Mall", "123 Pine Road, Example City, 12345", categories));
                break;
            case "movie_theater":
                categories[0] = "movie_theater";
                places.add(new Location("The Local Theater", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The National Theater", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The International Theater", "123 Pine Road, Example City, 12345", categories));
                break;
            case "museum":
                categories[0] = "museum";
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


    /**
     * Main method for testing how the Google Maps API and Distance Matrix API works before implementing the real methods.
     */
    public static void main(String args[])
    {
        APIManager apiManager = new APIManager();

        final String testAddress = "1029 Sandoval Drive, Virginia Beach, VA 23454";

        final double searchRadius = 2.0; //miles

        final String[] categories = LocationManager.CATEGORY_CONSTANTS.keySet().toArray(new String[0]);

        


    }
 

    /**
     * Generate a list of addresses from a list of Location objects
     * 
     * @param locations the list of Location objects
     * @return a list of addresses
     */
    private static List<String> generateAddressList(List<Location> locations)
    {
        List<String> addressList = new ArrayList<>();
        for (Location location : locations) {
            addressList.add(location.getAddress());
        }
        return addressList;
    }

    
}
