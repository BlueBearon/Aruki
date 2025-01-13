import React, { useContext } from 'react';
import { DarkModeContext } from './BasePage.js';
import { ActiveModeContext } from './BasePage.js';
import { Location, placeTypes, displayNames } from './BackendFunctions.js';
import CategorySection from './CategorySection.js';
import clsx from "clsx";

// Tailwind CSS is enabled

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
        <div className={clsx("flex h-full w-full justify-center items-center", darkMode ? "bg-gray-800 text-white" : "bg-white")}>
            <div className="flex h-full w-full mx-auto justify-center items-center">
                <div className="flex flex-col items-center justify-center">
                    {walkData.locations.map((locations, index) => <CategorySection categoryName={displayNames[placeTypes[index]]} locations={locations} viscinities={walkData.viscinitiesByCategories[index]} key={index} />)}
                    <button onClick={() => setActivityState(0)} className="mt-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">Back</button>
                </div>
            </div>
        </div>
    );


}

export default WalkResultScreen;