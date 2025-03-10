import React, { useContext, JSX } from "react";
import { DarkModeContext } from "./BasePage";
import { CategoryScore, displayNames } from "./BackendFunctions";
import { getImage } from "./ImageRetrieval";
import clsx from "clsx";

/**
 * Displays a category score card with a split background (left: text, right: image).
 * 
 * @param {Object} props - The properties object.
 * @param {CategoryScore} props.categoryScore - The category score object containing details about the category and scores.
 * @returns {JSX.Element} The rendered category score card component.
 */
function CategoryScoreCard({ categoryScore }: { categoryScore: CategoryScore }): JSX.Element {
  const darkModeContext = useContext(DarkModeContext);

  if (!darkModeContext) {
    throw new Error("DarkModeContext must be used within a Provider");
  }

  const { darkMode } = darkModeContext;

  const categoryName = displayNames[categoryScore.category];
  const { score, closePlaces, mediumPlaces, farPlaces } = categoryScore;
  const totalPlaces = closePlaces + mediumPlaces + farPlaces;

  const backgroundImage = getImage(categoryName);

  return (
    <div
      className={clsx(
        "relative flex w-full h-full max-w-4xl min-h-72 rounded-3xl shadow-2xl overflow-hidden transition-transform duration-300 transform hover:scale-105 border-1 border-solid"
      )}
    >
      {/* Left Side - Category Name & Score */}
      <div className="flex flex-col justify-center items-center w-1/2 p-6">
        <h1 className="lg:text-4xl md:text-3xl font-bold text-center mb-5">{categoryName}</h1>
        <div className= "p-4 w-3/4">
          <h2 className="lg:text-5xl md:text-4xl font-code mt-2 text-center">{score}</h2>
        </div>
      </div>

      {/* Right Side - Background Image & Breakdown */}
      <div className="relative flex flex-col justify-center items-center w-1/2 p-6"
        style={{
          backgroundImage: `url(${backgroundImage})`,
          backgroundSize: "cover",
          backgroundPosition: "center",
        }}
      >
        {/* Gradient Overlay for readability (fades from left to transparent) */}
        <div className="absolute inset-0 bg-gradient-to-l from-black/80 to-transparent"></div>

        {/* Score Breakdown */}
        <div className="relative flex flex-row text-center text-white text-3xl font-bold z-10 p-4">
          <p className="text-green-400 font- mx-2 lg:text-5xl md:text-4xl">{closePlaces}</p>
          <div className="w-0.5 h-12 bg-gray-300 mx-2"></div>
          <p className="text-yellow-400 mx-2 lg:text-5xl md:text-4xl">{mediumPlaces}</p>
          <div className="w-0.5 h-12 bg-gray-300 mx-2"></div>
          <p className="text-red-400 mx-2 lg:text-5xl md:text-4xl">{farPlaces}</p>
          <div className="w-0.5 h-12 bg-gray-300 mx-2 "></div>
          <p className="text-white mx-2 lg:text-5xl md:text-4xl">{totalPlaces}</p>
        </div>
      </div>
    </div>
  );
}

export default CategoryScoreCard;
