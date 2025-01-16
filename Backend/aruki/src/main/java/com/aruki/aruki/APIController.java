package com.aruki.aruki;

import org.springframework.web.bind.annotation.RestController;

import com.google.maps.errors.ApiException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The {@code APIController} class is a REST controller that handles HTTP requests for various endpoints related to location-based services.
 * <p>
 * This controller provides endpoints to:
 * <ul>
 *   <li>Check if the API is live.</li>
 *   <li>Get a list of places near a specified location, including their distances and categories.</li>
 *   <li>Get the walkability score of a location, along with scores for different categories of places.</li>
 * </ul>
 * </p>
 * <p>
 * The controller uses the {@code LocationManager} class to interact with the Google Maps API and retrieve the necessary data.
 * It handles various exceptions that may occur during the API calls and returns appropriate HTTP status codes and messages.
 * </p>
 * <p>
 * Each endpoint is mapped to a specific HTTP GET request using the {@code @GetMapping} annotation.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     // Check if the API is live
 *     ResponseEntity<Map<String, String>> response = apiController.areWeLive();
 *     System.out.println(response);
 * </pre>
 * </p>
 * <p>
 * This controller can be used in conjunction with other classes that require location-based services, such as recommendation systems, data visualization tools, or ranking algorithms.
 * </p>
 * 
 * @see LocationManager
 * @see ApiException
 * @see IOException
 * @see InterruptedException
 * @see ResponseEntity
 * @see GetMapping
 * @see RequestParam
 * @see RestController
 */
@RestController
public class APIController {



    @Autowired
    private LocationManager googleMapsAPIManager;

    /**
     * This method is used to check if the API is live.
     * 
     * @return ResponseEntity<Map<String, String>> This returns a map with the status of the API.
     */
    @GetMapping("/areWeLive")
    public ResponseEntity<Map<String, String>> areWeLive()
    {
        return new ResponseEntity<>(Map.of("status", "ok"), HttpStatus.OK);
    }

    /**
     * This method is used to get a list of places, how far away they are from the user, and the category of the place (e.g. restaurant, park, etc.).
     * 
     * Inputs the location pamater into the Google Maps API to get said information
     * 
     * Example Response: { "places": [ { "name": "McDonalds", "distance": "0.5 miles", "category": "restaurant" }, { "name": "Central Park", "distance": "1.2 miles", "category": "park" } ] }
     * 
     * @param location - The location of the user/where the user wants to investigate
     * @return ResponseEntity<Map<String, String>> - Map containing the list of locations, distances, and categories of the corresponding places.
     */
    @GetMapping("/getPlaces")
    public ResponseEntity<?> getPlaces(@RequestParam String location)
    {
        System.out.println("*********************************");
        System.out.println("Recieved getPlaces request for location: " + location + "\n");

        if(!googleMapsAPIManager.locationExists(location))
        {
            System.out.println("Invalid location: " + location);
            System.out.println("*********************************");
            return new ResponseEntity<>(Map.of("status", "invalid location"), HttpStatus.BAD_REQUEST);
        }


        try{
            List<Location> result = googleMapsAPIManager.getPlaces(location, false);

            System.out.println("Successfully retrieved places for location: " + location + "\n");
            System.out.println(result + "\n");
            System.out.println("*********************************");

            return ResponseEntity.ok(result);
        }
        catch (ApiException e)
        {

            System.out.println("API Exception: " + e.getMessage());
            System.out.println("*********************************");


            return new ResponseEntity<>(Map.of("status", "API Exception"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (InterruptedException e)
        {

            System.out.println("Interrupted Exception: " + e.getMessage());
            System.out.println("*********************************");

            return new ResponseEntity<>(Map.of("status", "interrupted"), HttpStatus.SERVICE_UNAVAILABLE);
        }
        catch (IOException e)
        {

            System.out.println("IO Exception: " + e.getMessage());
            System.out.println("*********************************");

            return new ResponseEntity<>(Map.of("status", "IO Exception"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e) {

            System.out.println("Unknown Exception: " + e.getMessage());
            System.out.println("*********************************");

            return new ResponseEntity<>(Map.of("status", "unknown error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * This method is used to get the walkability score of a location, as well as the score respective to each category of place.
     * 
     * Inputs the location parameter into the Google Maps API to get said information
     * 
     * Example Response: { "walkability": "7.5", "scores": [ { "category": "restaurant", "score": "8.2", "closePlaces" : "5", "mediumPlaces" : "3", "farPlaces" : "2" }, { "category": "park", "score": "9.1", "closePlaces" : "3", "mediumPlaces" : "4", "farPlaces" : "1" } ] }
     * 
     * @param location - The location of the user/where the user wants to investigate
     * @return ResponseEntity<Map<String, String>> - Map containing the walkability score of the location, as well as the score respective to each category of place.
     */
    @GetMapping("/getScore")
    public ResponseEntity<?> getScore(@RequestParam String location)
    {
        if(!googleMapsAPIManager.locationExists(location))
        {
            return new ResponseEntity<>(Map.of("status", "invalid location"), HttpStatus.BAD_REQUEST);
        }

        try
        {
            ScoreResponse places = googleMapsAPIManager.getScore(location, false);

            return ResponseEntity.ok(places);
        }
        catch (ApiException e)
        {
            return new ResponseEntity<>(Map.of("status", "API Exception"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (InterruptedException e)
        {
            return new ResponseEntity<>(Map.of("status", "interrupted"), HttpStatus.SERVICE_UNAVAILABLE);
        }
        catch (IOException e)
        {
            return new ResponseEntity<>(Map.of("status", "IO Exception"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e) {
            return new ResponseEntity<>(Map.of("status", "unknown error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    /**
     * This method is used to return a placeholder response for endpoints that have not been implemented yet.
     * 
     * @return ResponseEntity<Map<String, String>> - Map containing the status of the API.
     */
    private ResponseEntity<Map<String, String>> notImplementedOverride()
    {
        return new ResponseEntity<>(Map.of("status", "placeholder"), HttpStatus.OK);
    }


    
    
}
