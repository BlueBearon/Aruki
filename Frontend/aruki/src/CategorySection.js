import React, {useContext} from 'react';
import { DarkModeContext } from './BasePage.js';
import LocationCard from './LocationCard.js';
import clsx from 'clsx';

// Tailwind CSS is enabled

function CategorySection({ categoryName, locations, viscinities})
{
    // categoryName: string
    // locations: Location[]
    // viscinities: [num of Close Locations, num of Medium Locations, num of Far Locations]

    //Use LocationCard for each location, only needs to pass the location prop

    const { darkMode } = useContext(DarkModeContext);


    //if category has no locations, return null
    if(locations.length === 0)
    {
        return null;
    }

    return (
        <div className= {clsx("flex flex-grow h-full w-full justify-center items-center", darkMode ? "bg-gray-800 text-white" : "bg-white")}>
            <div className="flex h-full w-full mx-auto justify-center items-center mt-4">
                <div className="flex flex-col items-center justify-center">
                    <h1 className="text-5xl font-bold mb-4">{categoryName}</h1>
                    <div className="flex flex-row items-center justify-center">
                        <p className="text-lg text-green-500">{viscinities[0]}</p>
                        <div className="h-6 w-1 bg-gray-300 mx-2"></div>
                        <p className="text-lg text-yellow-500">{viscinities[1]}</p>
                        <div className="h-6 w-1 bg-gray-300 mx-2"></div>
                        <p className="text-lg text-red-500">{viscinities[2]}</p>
                    </div>
                    <div className="flex flex-col items-center justify-center mt-4">
                        {locations.map((location, index) => <LocationCard location={location} key={index} />)}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default CategorySection;