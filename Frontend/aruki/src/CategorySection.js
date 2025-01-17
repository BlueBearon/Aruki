import React, { useContext } from "react";
import { DarkModeContext } from "./BasePage.js";
import LocationCard from "./LocationCard.js";
import { getImage } from "./ImageRetrieval.js";
import clsx from "clsx";

// Tailwind CSS is enabled

/**
 * Component for displaying a category section with a header and a list of locations.
 *
 * @param {Object} props - The component props.
 * @param {string} props.categoryName - The name of the category.
 * @param {Array} props.locations - The list of locations to display.
 * @param {Array} props.viscinities - The list of viscinities to display in the header.
 * @returns {JSX.Element|null} The rendered component or null if no locations exist.
 */
function CategorySection({ categoryName, locations, viscinities }) {
  const { darkMode } = useContext(DarkModeContext);
  const backgroundImage = getImage(categoryName);

  // Return null if no locations exist
  if (locations.length === 0) {
    return null;
  }

  return (
    <div
      className={clsx(
        "flex flex-col justify-center items-center mt-4 w-full overflow-y-auto",
        darkMode ? "bg-gray-800 text-white" : "bg-white"
      )}
    >
      {/* Category Header with Background Image */}
      <div
        className="relative flex flex-col items-center justify-center w-full h-[50vh] p-6"
        style={{
          backgroundImage: `url(${backgroundImage})`,
          backgroundSize: "cover",
          backgroundPosition: "center",
        }}
      >
        {/* Overlay for better readability */}
        <div className="absolute inset-0 bg-black opacity-50"></div>

        {/* Header Content */}
        <div className="relative text-center text-white">
          <h1 className="text-6xl font-bold p-6">{categoryName}</h1>
          <div className="flex flex-row items-center justify-center mt-2">
            <p className="text-6xl font-bold text-green-400">{viscinities[0]}</p>
            <div className="h-16 w-1 bg-gray-300 mx-2"></div>
            <p className="text-6xl font-bold text-yellow-400">{viscinities[1]}</p>
            <div className="h-16 w-1 bg-gray-300 mx-2"></div>
            <p className="text-6xl font-bold text-red-400">{viscinities[2]}</p>
          </div>
        </div>
      </div>
      
      <div className="grid grid-cols-1 gap-4 w-full place-items-center overflow-y-auto">
        {locations.map((location, index) => (
          <LocationCard location={location} key={index} />
        ))}
      </div>
    </div>
  );
}

export default CategorySection;