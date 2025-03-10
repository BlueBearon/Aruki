import React, { useContext, JSX } from "react";
import { DarkModeContext } from "./BasePage";
import LocationCard from "./LocationCard";
import { getImage } from "./ImageRetrieval";
import { Location } from "./BackendFunctions"
import clsx from "clsx";

/**
 * Component for displaying a category section with a header and a list of locations.
 * 
 * Used in @file WalkResultScreen.tsx
 *
 * @param {Object} props - The component props.
 * @param {string} props.categoryName - The name of the category.
 * @param {Array} props.locations - The list of locations to display.
 * @param {Array} props.viscinities - The list of viscinities to display in the header.
 * @returns {JSX.Element|null} The rendered component or null if no locations exist.
 */
function CategorySection({ categoryName, locations, viscinities }: { categoryName: string, locations: Location[], viscinities: number[] }): JSX.Element | null {
  const darkModeContext = useContext(DarkModeContext);

  if (!darkModeContext) {
    throw new Error('DarkModeContext must be used within a Provider');
  }

  const { darkMode } = darkModeContext;
  const backgroundImage = getImage(categoryName);

  // Return null if no locations exist
  if (locations.length === 0) {
    return null;
  }

  return (
    <div
      className={clsx(
        "flex flex-col justify-center items-center mt-12 w-full p-6",
        darkMode ? "bg-gray-800 text-white" : "bg-white"
      )}
    >
      {/* Category Header with Background Image */}
      <div
        className="relative flex flex-col items-center justify-center w-full max-w-full p-6 rounded-3xl shadow-lg overflow-hidden "
        style={{
          backgroundImage: `url(${backgroundImage})`,
          backgroundSize: "cover",
          backgroundPosition: "center",
        }}
      >
        {/* Overlay for better readability */}
        <div className="absolute inset-0 bg-gradient-to-l from-black/80 to-transparent"></div>

        {/* Header Content */}
        <div className="relative text-center text-white">
          <h1 className="text-4xl md:text-6xl font-extrabold p-6 ">{categoryName}</h1>
          <div className="flex flex-row items-center justify-center mt-2">
            <p className="text-4xl md:text-6xl font-bold text-green-400 ">{viscinities[0]}</p>
            <div className="h-16 w-1 bg-gray-300 mx-2 bg-opacity-60"></div>
            <p className="text-4xl md:text-6xl font-bold text-yellow-400 ">{viscinities[1]}</p>
            <div className="h-16 w-1 bg-gray-300 mx-2 bg-opacity-60 "></div>
            <p className="text-4xl md:text-6xl font-bold text-red-400">{viscinities[2]}</p>
          </div>
        </div>
      </div>
      
      <div className="grid md:grid-cols-2 lg:grid-cols-3 sm:grid-cols-1 gap-4 w-11/12 place-items-center">
        {locations.map((location, index) => (
          <div key={index} className="w-full h-7/8">
            <LocationCard location={location} />
          </div>
        ))}
      </div>
    </div>
  );
}

export default CategorySection;
