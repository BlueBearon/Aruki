import React, { useContext, JSX } from 'react';
import { DarkModeContext } from './BasePage';
import { Location, placeTypes, displayNames } from './BackendFunctions';
import CategorySection from './CategorySection';
import clsx from "clsx";

/**
 * WalkResultScreen component displays the results of a walk.
 * 
 * 
 * Used in @file WalkActivity.tsx
 * 
 * @param {Object} props - The properties object.
 * @param {Object} props.walkData - The data related to the walk.
 * @param {Location[][]} props.walkData.locations - Array of arrays of locations, each array corresponds to a category.
 * @param {number[]} props.walkData.viscinities - Array of numbers representing the number of close, medium, and far locations of all categories.
 * @param {number[][]} props.walkData.viscinitiesByCategories - Array of arrays of numbers representing the number of close, medium, and far locations for each category.
 * @param {Function} props.setActivityState - Function to set the activity state.
 * @returns {JSX.Element} The WalkResultScreen component.
 */
function WalkResultScreen({ walkData, setActivityState }: { walkData: { locations: Location[][], viscinities: number[], viscinitiesByCategories: number[][] }, setActivityState: (state: number) => void }): JSX.Element {
    const darkModeContext = useContext(DarkModeContext);

    if (!darkModeContext) {
        throw new Error('DarkModeContext must be used within a Provider');
    }

    const { darkMode } = darkModeContext;

    return (
        <div className={clsx("flex flex-col flex-wrap max-h-full", darkMode ? "bg-gray-800 text-white" : "bg-white")}>
            <div className="flex flex-col items-center max-h-full">
                <button onClick={() => setActivityState(0)} className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded w-1/4">Back</button>
                {walkData.locations.map((locations, index) => (
                    <CategorySection 
                        categoryName={displayNames[placeTypes[index]]} 
                        locations={locations} 
                        viscinities={walkData.viscinitiesByCategories[index]} 
                        key={index} 
                    />
                ))}
                <button onClick={() => setActivityState(0)} className="mt-8 mb-8 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded w-1/4">Back</button>
            </div>
        </div>
    );
}

export default WalkResultScreen;
