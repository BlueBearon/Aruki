package com.aruki.aruki;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @RequestMapping("/areWeLive")
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
    @RequestMapping("/getPlaces")
    public ResponseEntity<Map<String, String>> getPlaces(@RequestParam String location)
    {

        Map<String, String> result = googleMapsAPIManager.getPlaces(location);

        //return new ResponseEntity<>(result, HttpStatus.OK);
        return notImplementedOverride();

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
    @RequestMapping("/getScore")
    public ResponseEntity<Map<String, String>> getScore(@RequestParam String location)
    {

        Map<String, String> result = googleMapsAPIManager.getScore(location);

        //return new ResponseEntity<>(result, HttpStatus.OK);
        return notImplementedOverride();

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
