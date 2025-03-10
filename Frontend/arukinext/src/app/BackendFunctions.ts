import axios from 'axios';

/*
Backend production domain is:

aruki-hhdubgd4gqg6dwgr.eastus-01.azurewebsites.net
*/

// Java Spring Boot API Backend URL
const developmentURL = 'http://localhost:8080/';
const productionURL = 'https://aruki-hhdubgd4gqg6dwgr.eastus-01.azurewebsites.net/';

const areWeLiveURL = 'areWeLive';
const getPlacesURL = 'getPlaces';
const getScoreURL = 'getScore';

export const CLOSE_DISTANCE = 0.5; // 500 meters
export const MEDIUM_DISTANCE = 1.0; // 1 km

/**
 * Determines the base URL to use for the backend API.
 * @returns {string} - The base URL to use for the backend API.
 * @throws Will throw an error if the base URL cannot be determined.
 */
function whatIsTheBaseURL(): string {
    // When running locally, Frontend is running on localhost:3000 and Backend is running on localhost:8080
    if (typeof window !== 'undefined' && window.location.href.includes("localhost:3000")) {
        return developmentURL;
    } else {
        // If productionURL is still '', then use the developmentURL
        if (!productionURL) {
            return developmentURL;
        } else {
            return productionURL;
        }
    }
}

/**
 * Checks if the backend API is live.
 * @returns {Promise<boolean>} - Returns true if the API is live.
 * @throws Will throw an error if the API call fails.
 */
export const areWeLive = async (): Promise<boolean> => {
    try {
        const response = await axios.get(whatIsTheBaseURL() + areWeLiveURL);
        return response.data;
    } catch (error) {
        if (error instanceof Error) {
            console.error("Error checking if API is live:", error.message);
            throw new Error(`Failed to check if API is live: ${error.message}`);
        } else {
            console.error("Error checking if API is live:", error);
            throw new Error(`Failed to check if API is live: ${error}`);
        }
        
    }
};

/**
 * Represents a location returned by the Google Maps API via the backend.
 * @class
 */
export class Location {
    name: string;
    address: string;
    types: string[];
    distance: string;

    /**
     * @param {string} name - The name of the location.
     * @param {string} address - The address of the location.
     * @param {string[]} types - The types of the location.
     * @param {string} distance - The distance to the location.
     */
    constructor(name: string, address: string, types: string[], distance: string) {
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
const getPlaces = async (location: string): Promise<Location[]> => {
    if (location === undefined) {
        throw new Error("Location is undefined, is not properly being passed to getPlaces");
    }

    if (location === "test") {
        location = "1029 Sandoval Drive, Virginia Beach, VA 23454";
    }

    console.log("Location sent to getPlaces: " + location);
    try {
        const response = await axios.get(whatIsTheBaseURL() + getPlacesURL, { params: { location: location } });

        if (!response.data) {
            throw new Error("No data received from getPlaces API");
        }

        const locations: Location[] = [];
        response.data.forEach((locationData: Location) => {
            locations.push(new Location(locationData.name, locationData.address, locationData.types, locationData.distance));
        });

        return locations;
    } catch (error) {
        if (error instanceof Error) {
            console.error("Error checking if API is live:", error.message);
            throw new Error(`Failed to check if API is live: ${error.message}`);
        } else {
            console.error("Error checking if API is live:", error);
            throw new Error(`Failed to check if API is live: ${error}`);
        }
    }
}

/**
 * Parses a distance string and returns the distance as a float.
 * @param {string} distance - The distance string to parse.
 * @returns {number} - The parsed distance.
 */
export function parseDistance(distance: string): number {
    return parseFloat(distance.substring(0, distance.length - 3));
}

/**
 * Converts a distance in kilometers to miles.
 * @param {string} km - The distance in kilometers.
 * @returns {string} - The distance in miles.
 */
export function kmToMiles(km: string): string {
    const kmNumber = parseDistance(km);
    const milesNumber = kmNumber * 0.621371;
    return milesNumber.toFixed(2) + " miles";
}

/**
 * Sorts a list of locations by distance in ascending order.
 * @param {Location[]} locations - The list of locations to sort.
 */
export function locationDistanceSortASC(locations: Location[]): void {
    locations.sort((a, b) => {
        return parseDistance(a.distance) - parseDistance(b.distance);
    });
}

/**
 * Sorts a list of locations by distance in descending order.
 * @param {Location[]} locations - The list of locations to sort.
 */
export function locationDistanceSortDESC(locations: Location[]): void {
    locations.sort((a, b) => {
        return parseDistance(b.distance) - parseDistance(a.distance);
    });
}

/**
 * Sorts the combined list of locations into a list of locations for each category.
 * @param {Location[]} locations - The list of locations to sort.
 * @param {string[]} categories - The list of categories to sort by.
 * @returns {Location[][]} - Returns a list of locations for each category.
 */
function locationCategorySort(locations: Location[], categories: string[]): Location[][] {
    const sortedLocations: Location[][] = Array(categories.length).fill([]).map(() => []);

    locations.forEach(location => {
        categories.forEach((category, index) => {
            if (location.types.includes(category)) {
                sortedLocations[index].push(location);
            }
        });
    });

    return sortedLocations;
}

/**
 * Counts the number of locations in each distance vicinity.
 * @param {Location[]} locations - The list of locations to count.
 * @returns {number[]} - Returns an array of three numbers: [closePlaces, mediumPlaces, farPlaces].
 */
function countLocationVicinities(locations: Location[]): number[] {
    let closePlaces = 0;
    let mediumPlaces = 0;
    let farPlaces = 0;

    locations.forEach(location => {
        const distance = parseDistance(location.distance);
        if (distance <= CLOSE_DISTANCE) {
            closePlaces++;
        } else if (distance <= MEDIUM_DISTANCE) {
            mediumPlaces++;
        } else {
            farPlaces++;
        }
    });

    return [closePlaces, mediumPlaces, farPlaces];
}

/**
 * Counts the number of locations in each distance vicinity for each category.
 * @param {Location[][]} sortedLocations - The list of locations sorted by category.
 * @returns {number[][]} - Returns a 2D array of the number of locations in each distance vicinity for each category.
 */
function countLocationVicinitiesByCategories(sortedLocations: Location[][]): number[][] {
    const viscinities: number[][] = [];

    sortedLocations.forEach(locations => {
        viscinities.push(countLocationVicinities(locations));
    });

    return viscinities;
}

/**
 * List of Google Maps API Place Types used in the project.
 * @type {string[]}
 */
export const placeTypes: string[] = [
    "grocery_or_supermarket",
    "restaurant",
    "park",
    "school",
    "pharmacy",
    "gym",
    "library",
    "shopping_mall",
    "movie_theater",
    "museum"
];

export const displayNames: { [key: string]: string } = {
    "grocery_or_supermarket": "Grocery Store",
    "restaurant": "Restaurant",
    "park": "Park",
    "school": "School",
    "pharmacy": "Pharmacy",
    "gym": "Gym",
    "library": "Library",
    "shopping_mall": "Shopping Mall",
    "movie_theater": "Movie Theater",
    "museum": "Museum"
}


/**
 * Represents the response from the getPlaces API.
 */
export class LocationResponse {
    locations: Location[][];
    viscinities: number[];
    viscinitiesByCategories: number[][];
    
    /**
     * @param {Location[][]} locations
     * @param {number[]} viscinities
     * @param {number[][]} viscinitiesByCategories
     */
    constructor(locations: Location[][], viscinities: number[], viscinitiesByCategories: number[][]) {
        this.locations = locations;
        this.viscinities = viscinities;
        this.viscinitiesByCategories = viscinitiesByCategories;
    }

}


/**
 * Combines all the steps to get the locations, sort them by distance, and sort them by category,
 * counts the number of locations in each distance vicinity, and returns the results.
 * @param {string} location - The location to search near.
 * @returns {Promise<{locations: Location[][], viscinities: number[], viscinitiesByCategories: number[][]}>} - Returns an object with the locations, viscinities, and viscinitiesByCategories.
 * @throws Will throw an error if the API call fails.
 */
export const getLocations = async (location: string): Promise<{ locations: Location[][], viscinities: number[], viscinitiesByCategories: number[][] }> => {
    try {
        const locations = await getPlaces(location);

        // Sort the locations by distance in ascending order
        locationDistanceSortASC(locations);

        // Sort the locations by category
        const sortedLocations = locationCategorySort(locations, placeTypes);

        // Count the number of locations in each distance vicinity
        const viscinities = countLocationVicinities(locations);

        // Count the number of locations in each distance vicinity for each category
        const viscinitiesByCategories = countLocationVicinitiesByCategories(sortedLocations);

        const response = new LocationResponse(sortedLocations, viscinities, viscinitiesByCategories);
        return response;
    } catch (error) {
        if (error instanceof Error) {
            console.error("Error checking if API is live:", error.message);
            throw new Error(`Failed to check if API is live: ${error.message}`);
        } else {
            console.error("Error checking if API is live:", error);
            throw new Error(`Failed to check if API is live: ${error}`);
        }
    }
}

/**
 * Represents the data of a category score returned by the backend API.
 * @class
 */
export class CategoryScore {
    category: string;
    score: number;
    closePlaces: number;
    mediumPlaces: number;
    farPlaces: number;

    /**
     * @param {string} category - The category name.
     * @param {number} score - The score for the category.
     * @param {number} closePlaces - The number of close places.
     * @param {number} mediumPlaces - The number of medium-distance places.
     * @param {number} farPlaces - The number of far places.
     */
    constructor(category: string, score: number, closePlaces: number, mediumPlaces: number, farPlaces: number) {
        this.category = category;
        this.score = score;
        this.closePlaces = closePlaces;
        this.mediumPlaces = mediumPlaces;
        this.farPlaces = farPlaces;
    }
}

/**
 * Represents the response from the getScore API.
 * @class
 */
export class ScoreResponse {
    walkabilityScore: number;
    categoryScores: CategoryScore[];

    /**
     * @param {number} walkabilityScore - The overall walkability score.
     * @param {CategoryScore[]} categoryScores - The list of category scores.
     */
    constructor(walkabilityScore: number, categoryScores: CategoryScore[]) {
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
export const getScores = async (location: string): Promise<ScoreResponse> => {
    try {
        if (location === undefined) {
            throw new Error("Location is undefined, is not properly being passed to getPlaces");
        }

        if (location === "test") {
            location = "1029 Sandoval Drive, Virginia Beach, VA 23454";
        }

        const response = await axios.get(whatIsTheBaseURL() + getScoreURL, { params: { location: location } });

        if (!response.data) {
            throw new Error("No data received from getScore API");
        }

        const categoryScores: CategoryScore[] = [];
        response.data.categoryScores.forEach((categoryScore: CategoryScore) => {
            categoryScores.push(new CategoryScore(categoryScore.category, categoryScore.score, categoryScore.closePlaces, categoryScore.mediumPlaces, categoryScore.farPlaces));
        });

        return new ScoreResponse(response.data.walkabilityScore, categoryScores);
    } catch (error) {
        if (error instanceof Error) {
            console.error("Error checking if API is live:", error.message);
            throw new Error(`Failed to check if API is live: ${error.message}`);
        } else {
            console.error("Error checking if API is live:", error);
            throw new Error(`Failed to check if API is live: ${error}`);
        }
    }
}
