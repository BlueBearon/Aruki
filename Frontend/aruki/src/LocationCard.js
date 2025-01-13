import React, {useContext} from 'react';
import { DarkModeContext } from './BasePage.js';
import { Location, CLOSE_DISTANCE, MEDIUM_DISTANCE } from './BackendFunctions.js';

// Tailwind CSS is enabled

/**
 * This component is responsible for displaying the location's name, address, and distance from the user.
 * 
 * The border color of the distance indicator will change based on the distance.
 * - Green for Close
 * - Yellow for Medium
 * - Red for Far
 * 
 * @param { Location } location
 * @returns LocationCard component that displays the location's name, address, and
 */
function LocationCard({location}) {

    //location.name
    //location.address
    //location.distance

    const { darkMode } = useContext(DarkModeContext);

    const borderColor = (distance) => {
        if (distance <= CLOSE_DISTANCE) {
            return "border-green-500";
        } else if (distance <= MEDIUM_DISTANCE) {
            return "border-yellow-500";
        } else {
            return "border-red-500";
        }
    }

    return (
        <div className={`${darkMode ? "bg-gray-700 text-white" : "bg-white text-black"} ${borderColor(location.distance)} border-2 border-solid rounded-lg p-4 m-4`}>
            <h1 className="text-2xl font-bold">{location.name}</h1>
            <p className="text-lg">{location.address}</p>
            <div className="mt-4">
                <p className="text-lg">Distance: {location.distance}m</p>
            </div>
        </div>
    );

}

export default LocationCard;