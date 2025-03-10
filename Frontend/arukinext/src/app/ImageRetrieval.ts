import walkImage from './assets/city_background.jpg';
import groceryStoreImage from './assets/grocery_store.jpg';
import shoppingMallImage from './assets/shopping_mall.jpg';
import libraryImage from './assets/library.jpg';
import pharmacyImage from './assets/pharmacy.jpg';
import gymImage from './assets/gym.jpg';
import restaurantImage from './assets/restaurant.jpeg';
import museumImage from './assets/museum.jpg';
import parkImage from './assets/park.jpg';
import schoolImage from './assets/school.jpg';
import movieTheaterImage from './assets/movie_theater.jpeg';

/**
 * Retrieves the image associated with a given place name.
 * 
 * @param {string} placeName - The name of the place to retrieve the image for.
 * @returns {string} The path to the image associated with the given place name.
 */
export function getImage(placeName: string): string {
    switch (placeName) {
        case 'Grocery Store':
            return groceryStoreImage.src;
        case 'Shopping Mall':
            return shoppingMallImage.src;
        case 'Library':
            return libraryImage.src;
        case 'Pharmacy':
            return pharmacyImage.src;
        case 'Gym':
            return gymImage.src;
        case 'Restaurant':
            return restaurantImage.src;
        case 'Museum':
            return museumImage.src;
        case 'Park':
            return parkImage.src;
        case 'School':
            return schoolImage.src;
        case 'Movie Theater':
            return movieTheaterImage.src;
        default:
            return walkImage.src;
    }
}
