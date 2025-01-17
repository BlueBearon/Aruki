import walkImage from './assets/city_background.jpg';
import scoreImage from './assets/city_background.jpg';
import groceryStoreImage from './assets/grocery_store.jpg';
import shoppingMallImage from './assets/shopping_mall.jpg';
import libraryImage from './assets/library.jpg';
import pharmacyImage from './assets/pharmacy.jpg';
import gymImage from './assets/gym.jpg';
import restaruantImage from './assets/restaurant.jpeg';
import museumImage from './assets/museum.jpg';
import parkImage from './assets/park.jpg';
import schoolImage from './assets/school.jpg';
import movieTheaterImage from './assets/movie_theater.jpeg';


export function getImage(placeName)
{
    switch(placeName)
    {
        case 'Grocery Store':
            return groceryStoreImage;
        case 'Shopping Mall':
            return shoppingMallImage;
        case 'Library':
            return libraryImage;
        case 'Pharmacy':
            return pharmacyImage;
        case 'Gym':
            return gymImage;
        case 'Restaurant':
            return restaruantImage;
        case 'Museum':
            return museumImage;
        case 'Park':
            return parkImage;
        case 'School':
            return schoolImage;
        case 'Movie Theater':
            return movieTheaterImage;
        default:
            return walkImage;
    }
}