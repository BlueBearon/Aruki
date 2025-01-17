package com.aruki.aruki;

// Google Maps API Libraries
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlaceType;
import com.google.maps.model.LatLng;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.google.maps.model.TravelMode;

// Third Party Libraries
import io.github.cdimascio.dotenv.Dotenv; // For loading .env file

// Java Libraries
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The APIManager class is responsible for managing API calls to the Google Maps API and Distance Matrix API.
 * It provides methods to retrieve places of specific categories near a given location and to calculate walking distances 
 * from an origin address to a list of destination addresses. Additionally, it can provide sample data for testing purposes.
 * 
 * <p>This class works with the following Google Maps API classes:
 * <ul>
 *   <li>{@link com.google.maps.GeoApiContext}</li>
 *   <li>{@link com.google.maps.GeocodingApi}</li>
 *   <li>{@link com.google.maps.PlacesApi}</li>
 *   <li>{@link com.google.maps.DistanceMatrixApi}</li>
 *   <li>{@link com.google.maps.model.PlaceType}</li>
 *   <li>{@link com.google.maps.model.LatLng}</li>
 *   <li>{@link com.google.maps.model.DistanceMatrix}</li>
 *   <li>{@link com.google.maps.model.DistanceMatrixElement}</li>
 *   <li>{@link com.google.maps.model.GeocodingResult}</li>
 *   <li>{@link com.google.maps.model.PlacesSearchResponse}</li>
 *   <li>{@link com.google.maps.model.PlacesSearchResult}</li>
 *   <li>{@link com.google.maps.model.TravelMode}</li>
 * </ul>
 * 
 * <p>It also uses the Dotenv library to load environment variables from a .env file, specifically the Google Maps API key.
 * 
 * <p>Usage example:
 * <pre>
 * {@code
 * APIManager apiManager = new APIManager();
 * List<Location> places = apiManager.retrievePlacesOfCategory("New York, NY", PlaceType.RESTAURANT, false);
 * List<String> distances = apiManager.getWalkingDistances("Central Park, New York, NY", Arrays.asList("Times Square, New York, NY"), false);
 * }
 * </pre>
 * 
 * <p>Exceptions:
 * <ul>
 *   <li>{@link java.io.FileNotFoundException} if the .env file is not found</li>
 *   <li>{@link java.lang.RuntimeException} if the API_KEY is not found in the .env file</li>
 *   <li>{@link com.google.maps.errors.ApiException} if an error occurs while making the API request</li>
 *   <li>{@link java.lang.InterruptedException} if the request is interrupted</li>
 *   <li>{@link java.io.IOException} if an I/O error occurs</li>
 * </ul>
 * 
 * <p>Methods:
 * <ul>
 *   <li>{@link #retrievePlacesOfCategory(String, PlaceType, boolean)}: Retrieves a list of places of a specific category near a given location.</li>
 *   <li>{@link #getWalkingDistances(String, List, boolean)}: Retrieves walking distances from an origin address to a list of place addresses.</li>
 *   <li>{@link #locationExists(String)}: Checks if a location exists in the Google Maps API by trying to get the latitude and longitude of the location.</li>
 * </ul>
 * 
 * <p>Sample Data Methods:
 * <ul>
 *   <li>{@link #sampleData_retrievePlacesOfCategory(String, PlaceType)}: Provides sample data mimicking the output from the Google Places API.</li>
 *   <li>{@link #sampleData_getWalkingDistances(String, List)}: Provides sample data mimicking the output from the Google Maps Distance Matrix API.</li>
 * </ul>
 * 
 * <p>Helper Methods:
 * <ul>
 *   <li>{@link #generateAddressList(List)}: Generates a list of addresses from a list of Location objects.</li>
 * </ul>
 * 
 * <p>Main Method:
 * <ul>
 *   <li>{@link #main(String[])}: Main method for testing how the Google Maps API and Distance Matrix API works before implementing the real methods.</li>
 * </ul>
 * 
 * @throws FileNotFoundException if the .env file is not found
 * @throws RuntimeException if the API_KEY is not found in the .env file
 */
public class APIManager {


    private String apiKey;
    private GeoApiContext context;
    private final int searchRadius = 2000; // Search radius in meters


    public APIManager() throws FileNotFoundException, RuntimeException
    {

        this.apiKey = "";
        this.context = null;

        try{
            // Load the .env file
            Dotenv dotenv = Dotenv.load();

            //Check to make sure Dotenv found the .env file
            if (dotenv == null) {
                throw new FileNotFoundException("Error: .env file not found. Please create a .env file in the root directory of the project and add the API_KEY variable to it.");
            }
            else
            {
                System.out.println("Found .env file");
            }

            this.apiKey = dotenv.get("API_KEY");

            //Check to make sure the API Key was found in the .env file
            if (this.apiKey == null) {
                throw new RuntimeException("Error: API_KEY not found in .env file. Please add the API_KEY variable to the .env file with your Google Maps API key.");
            }
            else
            {
                System.out.println("Found API_KEY in .env file");
            }

            this.context = new GeoApiContext.Builder().apiKey(apiKey).build();

        } catch (Exception e) 
        {
            // pass the exception up the chain
            throw e;
        }
    }


    /**
     * Retrieves a list of places of a specific category near a given location.
     * 
     * @param location the location to search near
     * @param category the category of places to search for
     * @param test whether to use sample data for testing
     * @return a list of places matching the category near the location
     * @throws ApiException if an error occurs while making the API request
     * @throws InterruptedException if the request is interrupted
     * @throws IOException if an I/O error occurs
     */
    public List<Location> retrievePlacesOfCategory(String location, PlaceType category, boolean test) throws ApiException, InterruptedException, IOException
    {
        
        if (test) // If testing, use sample data
        {
            return sampleData_retrievePlacesOfCategory(location, category);
        }
        else
        {
            try{

                GeocodingResult[] results = GeocodingApi.geocode(context, location).await(); // Get the latitude and longitude of the location

                if (results.length == 0)
                {
                    return null;
                }

                //Get the latitude and longitude of the location
                LatLng latLng = results[0].geometry.location;

                //location is an address, get the latitude and longitude of the location
                // Get latlng of location, Don't know how

                PlacesSearchResponse response = PlacesApi.nearbySearchQuery(context, latLng).type(category).radius(searchRadius).await(); // Search for places of the specified category near the location
                List<Location> places = new ArrayList<>();
                for (PlacesSearchResult result : response.results) {
                    String address = (result.formattedAddress != null) ? result.formattedAddress : result.vicinity;
                    places.add(new Location(result.name, address, result.types));
                }
                return places;
            } catch (Exception e) {
                throw e;
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
     * @throws ApiException if an error occurs while making the API request
     * @throws InterruptedException if the request is interrupted
     * @throws IOException if an I/O error occurs
     */
    public List<String> getWalkingDistances(String originAddress, List<String> placeAddresses, boolean test) throws ApiException, InterruptedException, IOException
    {

        if (test) 
        {
            return sampleData_getWalkingDistances(originAddress, placeAddresses);
        }
        else
        {
            List<String> distances = new ArrayList<>();


            try {
                DistanceMatrix matrix = DistanceMatrixApi.newRequest(context) // Get walking distances from the origin address to each destination address
                        .origins(originAddress)
                        .destinations(placeAddresses.toArray(new String[0]))
                        .mode(TravelMode.WALKING)
                        .await();

                for (DistanceMatrixElement element : matrix.rows[0].elements) {

                    if(element.distance != null)
                        distances.add(element.distance.humanReadable);
                    else // Absurdly large distance so that it is filtered out
                        distances.add("1000 km");
                        
                }
            } catch (Exception e) {
                throw e;
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
    public static List<Location> sampleData_retrievePlacesOfCategory(String location, PlaceType category) {
        
        // This method should mimic output from the Google Places API
        List<Location> places = new ArrayList<>();
        String[] categories = new String[1];

        // Placeholder Data
        switch(category)
        {
            case PlaceType.RESTAURANT:
                categories[0] = "restaurant";
                places.add(new Location("The Green Leaf Diner", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The Red Tomato", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The Blue Ocean", "123 Pine Road, Example City, 12345", categories));
                break;
            case PlaceType.GROCERY_OR_SUPERMARKET:
                categories[0] = "grocery_or_supermarket";
                places.add(new Location("The Fresh Market", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The Local Market", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The Organic Market", "123 Pine Road, Example City, 12345", categories));
                break;
            case PlaceType.PARK:
                categories[0] = "park";
                places.add(new Location("The Green Park", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The Red Park", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The Blue Park", "123 Pine Road, Example City, 12345", categories));
                break;
            case PlaceType.SCHOOL:
                categories[0] = "school";
                places.add(new Location("The Elementary School", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The Middle School", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The High School", "123 Pine Road, Example City, 12345", categories));
                break;
            case PlaceType.HOSPITAL:
                categories[0] = "pharmacy";
                places.add(new Location("The Local Pharmacy", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The National Pharmacy", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The International Pharmacy", "123 Pine Road, Example City, 12345", categories));
                break;
            case PlaceType.GYM:
                categories[0] = "gym";
                places.add(new Location("The Local Gym", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The National Gym", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The International Gym", "123 Pine Road, Example City, 12345", categories));
                break;
            case PlaceType.LIBRARY:
                categories[0] = "library";
                places.add(new Location("The Local Library", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The National Library", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The International Library", "123 Pine Road, Example City, 12345", categories));
                break;
            case PlaceType.SHOPPING_MALL:
                categories[0] = "shopping_mall";
                places.add(new Location("The Local Mall", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The National Mall", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The International Mall", "123 Pine Road, Example City, 12345", categories));
                break;
            case PlaceType.MOVIE_THEATER:
                categories[0] = "movie_theater";
                places.add(new Location("The Local Theater", "456 Oak Avenue, Example City, 12345", categories));
                places.add(new Location("The National Theater", "789 Maple Street, Example City, 12345", categories));
                places.add(new Location("The International Theater", "123 Pine Road, Example City, 12345", categories));
                break;
            case PlaceType.MUSEUM:
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

        try{
            APIManager apiManager = new APIManager();

            Dotenv dotenv = Dotenv.load();

            boolean test = false;

            final String testAddress = dotenv.get("TEST_ADDRESS");

            final PlaceType[] categories = LocationManager.CATEGORY_CONSTANTS.keySet().toArray(new PlaceType[0]);
        
            List<Location> places = apiManager.retrievePlacesOfCategory(testAddress, categories[0], test);

            for (Location place : places) {
                System.out.println(place);
            }

            System.out.println();

            List<String> placeAddresses = generateAddressList(places);

            for (String placeAddress : placeAddresses) {
                System.out.println(placeAddress);
            }

            System.out.println();

            List<String> walkingDistances = apiManager.getWalkingDistances(testAddress, placeAddresses, test);

            for (String distance : walkingDistances) {
                System.out.println(distance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



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
            System.out.println(location.getAddress());
            addressList.add(location.getAddress());
        }
        return addressList;
    }

    /**
     * Check if a location exists in the Google Maps API. Try to get the latitude and longitude of the location.
     * 
     * If the location does not exist, return false.
     * 
     * Uses the Geocoding API to get the latitude and longitude of the location. If the location does not exist, the Geocoding API will return an empty array.
     *  
     * @param location the location to check
     * @return true if the location exists, false otherwise
     */
    public boolean locationExists(String location)
    {
        try
        {
            GeocodingResult[] results = GeocodingApi.geocode(context, location).await(); // Get the latitude and longitude of the location

            if (results.length == 0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        catch (Exception e)
        {
            return false;
        }

    }

    
}
