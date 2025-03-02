import React, { useContext, JSX } from 'react';
import { DarkModeContext } from './BasePage';
import { Location, CLOSE_DISTANCE, MEDIUM_DISTANCE, parseDistance } from './BackendFunctions';
import clsx from 'clsx';

/**
 * This component is responsible for displaying the location's name, address, and distance from the user.
 * 
 * The border color of the distance indicator will change based on the distance.
 * - Green for Close
 * - Yellow for Medium
 * - Red for Far
 * 
 * Used in @file CategorySection.tsx
 * 
 * @param {Location} location - The location object containing name, address, and distance.
 * @returns {JSX.Element} LocationCard component that displays the location's name, address, and distance.
 */
function LocationCard({ location }: { location: Location }): JSX.Element {
    const darkModeContext = useContext(DarkModeContext);

    if (!darkModeContext) {
        throw new Error('DarkModeContext must be used within a Provider');
    }

    const { darkMode } = darkModeContext;

    /**
     * Determines the border color based on the distance.
     * 
     * @param {string} distance - The distance to the location.
     * @returns {string} The CSS class for the border color.
     */
    const borderColor = (distance: string): string => {
        if (parseDistance(distance) <= CLOSE_DISTANCE) {
            return "border-green-500";
        } else if (parseDistance(distance) <= MEDIUM_DISTANCE) {
            return "border-yellow-500";
        } else {
            return "border-red-500";
        }
    }

    return (
        <div className={clsx(
            darkMode ? "bg-gray-800 text-white" : "bg-white text-gray-900",
            borderColor(location.distance),
            "border-4 border-solid rounded-lg p-6 m-6 w-1/2 shadow-lg transition-all ease-in-out duration-300"
        )}>
            <h1 className="text-3xl font-semibold mb-2">{location.name}</h1>
            <p className="text-lg text-gray-500 mb-4">{location.address}</p>
            <div className="mb-6">
                <p className="text-lg font-medium">
                    Distance: <span className="font-bold">{location.distance}</span>
                </p>
            </div>
            <a href={`https://www.google.com/maps/search/?api=1&query=${location.address}`} 
               target="_blank" rel="noreferrer" 
               className="mt-4 bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-6 rounded-lg 
                          transition-colors duration-200 ease-in-out transform hover:scale-105">
                View on Google Maps
            </a>
        </div>
    );
}

export default LocationCard;
