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
 * APIController
 * 
 * This class is the main controller for the API. It will handle all the requests and responses. 
 * 
 * The API will have the following endpoints:
 * 
 * 1. /areWeLive - This endpoint will be used to check if the API is live.
 * 
 * 2. /getPlaces - This endpoint will be used to get a list of places, how far away they are from the user, and the category of the place (e.g. restaurant, park, etc.).
 * 
 * 3. /getScore - This endpoint will be used to get the walkability score of a location, as well as the score respective to each category of place.
 * 
 * API is powered by Spring Boot and makes extensive use of the Google Maps API.
 * 
 * @version 1.0
 * @author Chase Packer
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
     * If Exception is thrown, send error code corresponding to the error.
     * 301 - API Exception - Error with Google Maps API
     * 302 
     * 
     * @param location - The location of the user/where the user wants to investigate
     * @return ResponseEntity<Map<String, String>> - Map containing the list of locations, distances, and categories of the corresponding places.
     */
    @GetMapping("/getPlaces")
    public ResponseEntity<?> getPlaces(@RequestParam String location)
    {

        if(!googleMapsAPIManager.locationExists(location))
        {
            return new ResponseEntity<>(Map.of("status", "invalid location"), HttpStatus.BAD_REQUEST);
        }


        try{
            List<Location> result = googleMapsAPIManager.getPlaces(location, false);

            return ResponseEntity.ok(result);
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
