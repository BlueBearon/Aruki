import React, { useContext } from 'react';
import { DarkModeContext } from './BasePage.js';
import { ActiveModeContext } from './BasePage.js';
import { Location, placeTypes, displayNames } from './BackendFunctions.js';
import CategorySection from './CategorySection.js';
import clsx from "clsx";

// Tailwind CSS is enabled

/**
 * WalkResultScreen component displays the results of a walk.
 * 
 * 
 * Used in @file WalkActivity.js
 * 
 * @param {Object} props - The properties object.
 * @param {Object} props.walkData - The data related to the walk.
 * @param {Location[][]} props.walkData.locations - Array of arrays of locations, each array corresponds to a category.
 * @param {number[]} props.walkData.viscinities - Array of numbers representing the number of close, medium, and far locations of all categories.
 * @param {number[][]} props.walkData.viscinitiesByCategories - Array of arrays of numbers representing the number of close, medium, and far locations for each category.
 * @param {Function} props.setActivityState - Function to set the activity state.
 * @returns {JSX.Element} The WalkResultScreen component.
 */
function WalkResultScreen({ walkData, setActivityState }) {
    
    const { darkMode } = useContext(DarkModeContext);

    // walkData: {locations: Location[][], viscinities: number[], viscinitiesByCategories: number[][]} *Each array has the same length, as they are all related to the same category*
    // viscinities: [num of Close Locations, num of Medium Locations, num of Far Locations] of all Categories
    // Location[0]: List of Locations of Category 0
    // viscinitiesByCategories[0]: [num of Close Locations, num of Medium Locations, num of Far Locations] of Category 0

    // Use CategorySection for each category, only needs to pass the categoryName, locations, and viscinities
    // Category name is displayNames mapped from placeTypes[index]
    
    // Just standard flex

    return (
        <div className={clsx("flex flex-col flex-wrap max-h-full", darkMode ? "bg-gray-800 text-white" : "bg-white")}>
            <div className="flex flex-col items-center max-h-full">
                <button onClick={() => setActivityState(0)} className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded w-1/4">Back</button>
                {walkData.locations.map((locations, index) => <CategorySection categoryName={displayNames[placeTypes[index]]} locations={locations} viscinities={walkData.viscinitiesByCategories[index]} key={index} />)}
                <button onClick={() => setActivityState(0)} className="mt-8 mb-8 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded w-1/4">Back</button>
            </div>
        </div>
    );
}

export default WalkResultScreen;