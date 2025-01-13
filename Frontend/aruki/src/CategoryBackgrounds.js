


const displayNames = {
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


const GROCERY_IMAGE = '';
const RESTAURANT_IMAGE = '';
const PARK_IMAGE = '';
const SCHOOL_IMAGE = '';
const PHARMACY_IMAGE = '';
const GYM_IMAGE = '';
const LIBRARY_IMAGE = '';
const SHOPPING_MALL_IMAGE = '';
const MOVIE_THEATER_IMAGE = '';
const MUSEUM_IMAGE = '';



/**
 * 
 * 
 * @param {string} categoryName
 * 
 */
export function getBackground(categoryName)
{

    switch (displayName){
        case "Grocery Store":
            return GROCERY_IMAGE;
        case "Restaurant":
            return RESTAURANT_IMAGE;
        case "Park":
            return PARK_IMAGE;
        case "School":
            return SCHOOL_IMAGE;
        case "Pharmacy":
            return PHARMACY_IMAGE;
        case "Gym":
            return GYM_IMAGE;
        case "Library":
            return LIBRARY_IMAGE;
        case "Shopping Mall":
            return SHOPPING_MALL_IMAGE;
        case "Movie Theater":
            return MOVIE_THEATER_IMAGE;
        case "Museum":
            return MUSEUM_IMAGE;
        default:
            return null;
    }

}