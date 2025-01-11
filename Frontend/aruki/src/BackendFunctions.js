import axios from 'axios';

//Java Spring Boot API Backend URL
const baseURL = 'http://localhost:8080/';
const areWeLiveURL = 'areWeLive';
const getPlacesURL = 'getPlaces';
const getScoreURL = 'getScore';

/**
 * Checks if the backend API is live.
 * @returns {Promise<boolean>} - Returns true if the API is live.
 * @throws Will throw an error if the API call fails.
 */
export const areWeLive = async () => {
    try {
        const response = await axios.get(baseURL + areWeLiveURL);
        return response.data;
    } catch (error) {
        console.error("Error checking API status:", error);
        throw new Error("Failed to check API status");  // Improved error message
    }
};

/**
 * Represents a location returned by the Google Maps API via the backend.
 * 
 * Attributes:
 * - name (string): The name of the location.
 * - address (string): The address of the location.
 * - types (string[]): The types of the location.
 * - distance (string): The distance to the location. i.e. "1.2 km"
 */
export class Location {
    /**
     * @param {string} name - The name of the location.
     * @param {string} address - The address of the location.
     * @param {string[]} types - The types of the location.
     * @param {string} distance - The distance to the location.
     */
    constructor(name, address, types, distance) {
        this.name = name;
        this.address = address;
        this.types = types;
        this.distance = distance;
    }
}

/**
 * Fetches places near a given location.
 * @param {string} location - The location to search near.
 * @returns {Promise<Location[]>} - Returns a list of Location objects.
 * @throws Will throw an error if the API call fails or no data is received.
 */
export const getPlaces = async (location) => {
    try {
        const response = await axios.get(baseURL + getPlacesURL, {params: {location: location}});
        
        if (!response.data) {
            throw new Error("No data received from getPlaces API");
        }
        
        let locations = [];
        response.data.forEach(locationData => {
            locations.push(new Location(locationData.name, locationData.address, locationData.types, locationData.distance));
        });

        return locations;
    } catch (error) {
        console.error("Error fetching places:", error);
        throw new Error("Failed to fetch places");  // Improved error message
    }
}

/**
 * Parses a distance string and returns the distance as a float.
 * @param {string} distance - The distance string to parse.
 * @returns {number} - The parsed distance.
 */
export function parseDistance(distance) {
    //remove " km" from the distance string
    return parseFloat(distance.substring(0, distance.length - 3));
}

/**
 * Sorts a list of locations by distance in ascending order.
 * @param {Location[]} locations - The list of locations to sort.
 */
export function locationSort(locations) {
    locations.sort((a, b) => {
        return parseDistance(a.distance) - parseDistance(b.distance);
    });
}

/**
 * Sorts a list of locations by distance in descending order.
 * @param {Location[]} locations - The list of locations to sort.
 */
export function locationSortDESC(locations) {
    locations.sort((a, b) => {
        return parseDistance(b.distance) - parseDistance(a.distance);
    });
}

/**
 * Represents the data of a category score returned by the backend API.
 * 
 * Attributes:
 * - category (string): The category name.
 * - score (number): The score for the category.
 * - closePlaces (number): The number of close places.
 * - mediumPlaces (number): The number of medium-distance places.
 * - farPlaces (number): The number of far places.
 */
export class CategoryScore {
    /**
     * @param {string} category - The category name.
     * @param {number} score - The score for the category.
     * @param {number} closePlaces - The number of close places.
     * @param {number} mediumPlaces - The number of medium-distance places.
     * @param {number} farPlaces - The number of far places.
     */
    constructor(category, score, closePlaces, mediumPlaces, farPlaces) {
        this.category = category;
        this.score = score;
        this.closePlaces = closePlaces;
        this.mediumPlaces = mediumPlaces;
        this.farPlaces = farPlaces;
    }
}

/**
 * Represents the response from the getScore API.
 */
export class ScoreResponse {
    /**
     * @param {number} walkabilityScore - The overall walkability score.
     * @param {CategoryScore[]} categoryScores - The list of category scores.
     */
    constructor(walkabilityScore, categoryScores) {
        this.walkabilityScore = walkabilityScore;
        this.categoryScores = categoryScores;
    }
}

/**
 * Fetches the walkability score for a given location.
 * @param {string} location - The location to get the score for.
 * @returns {Promise<ScoreResponse>} - Returns a ScoreResponse object.
 * @throws Will throw an error if the API call fails or no data is received.
 */
export const getScore = async (location) => {
    try {
        const response = await axios.get(baseURL + getScoreURL, {params: {location: location}});
        
        if (!response.data) {
            throw new Error("No data received from getScore API");
        }

        let categoryScores = [];
        response.data.categoryScores.forEach(categoryScore => {
            categoryScores.push(new CategoryScore(categoryScore.category, categoryScore.score, categoryScore.closePlaces, categoryScore.mediumPlaces, categoryScore.farPlaces));
        });

        return new ScoreResponse(response.data.walkabilityScore, categoryScores);
    } catch (error) {
        console.error("Error fetching score:", error);
        throw new Error("Failed to fetch score");  // Improved error message
    }
}

