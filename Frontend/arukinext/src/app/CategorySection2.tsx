import React, { useContext, JSX } from "react";
import { DarkModeContext } from "./BasePage";
import LocationCard from "./LocationCard";
import { getImage } from "./ImageRetrieval";
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
function CategorySection2({
  categoryName,
  locations,
  viscinities,
}: {
  categoryName: string;
  locations: any[];
  viscinities: number[];
}): JSX.Element | null {
  const darkModeContext = useContext(DarkModeContext);

  if (!darkModeContext) {
    throw new Error("DarkModeContext must be used within a Provider");
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
        "flex flex-col items-center w-full max-w-screen-lg mt-6 px-4",
        darkMode ? "bg-gray-900 text-white" : "bg-white"
      )}
    >
      {/* Category Header with Background Image */}
      <div
        className="relative flex flex-col items-center justify-center w-full p-6 rounded-xl shadow-lg overflow-hidden transition-all"
        style={{
          backgroundImage: `url(${backgroundImage})`,
          backgroundSize: "cover",
          backgroundPosition: "center",
        }}
      >
        {/* Overlay for better readability */}
        <div className="absolute inset-0 bg-black opacity-60 rounded-xl"></div>

        {/* Header Content */}
        <div className="relative text-center text-white">
          <h1 className="text-3xl md:text-5xl font-extrabold p-4">{categoryName}</h1>
          <div className="flex flex-wrap items-center justify-center mt-2 gap-4">
            <p className="text-3xl md:text-5xl font-bold text-green-400">{viscinities[0]}</p>
            <div className="h-12 w-1 bg-gray-300 bg-opacity-60"></div>
            <p className="text-3xl md:text-5xl font-bold text-yellow-400">{viscinities[1]}</p>
            <div className="h-12 w-1 bg-gray-300 bg-opacity-60"></div>
            <p className="text-3xl md:text-5xl font-bold text-red-400">{viscinities[2]}</p>
          </div>
        </div>
      </div>

      {/* Locations Grid */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 w-full mt-6">
        {locations.map((location, index) => (
          <LocationCard location={location} key={index} />
        ))}
      </div>
    </div>
  );
}

export default CategorySection2;
