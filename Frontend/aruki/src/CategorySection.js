import React, {useContext} from 'react';
import { DarkModeContext } from './BasePage.js';
import { LocationCard } from './LocationCard.js';

// Tailwind CSS is enabled

function CategorySection({ categoryName, locations, viscinities})
{
    // categoryName: string
    // locations: Location[]
    // viscinities: [num of Close Locations, num of Medium Locations, num of Far Locations]

    //Use LocationCard for each location, only needs to pass the location prop

    const { darkMode } = useContext(DarkModeContext);

    return (
        <div className={darkMode ? "bg-gray-700 text-white" : "bg-white"}>
            <div className="flex h-full w-full mx-auto justify-center items-center">
                <div className="flex flex-col items-center justify-center">
                    <h1 className="text-5xl font-bold">{categoryName}</h1>
                    <div className="mt-4">
                        <p className="text-lg">Close: {viscinities[0]}</p>
                        <p className="text-lg">Medium: {viscinities[1]}</p>
                        <p className="text-lg">Far: {viscinities[2]}</p>
                    </div>
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
                        {locations.map((location, index) => <LocationCard location={location} key={index} />)}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default CategorySection;