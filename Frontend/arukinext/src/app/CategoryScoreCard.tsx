import React, { useContext, JSX } from 'react';
import { DarkModeContext } from './BasePage';
import { CategoryScore, displayNames } from './BackendFunctions';
import { getImage } from './ImageRetrieval';
import clsx from "clsx";

/**
 * Component to display a category score card with background image, score, and breakdown of places.
 * 
 * Used in @file ScoreResultScreen.tsx
 * 
 * @param {Object} props - The properties object.
 * @param {CategoryScore} props.categoryScore - The category score object containing details about the category and scores.
 * @returns {JSX.Element} The rendered category score card component.
 */
function CategoryScoreCard({ categoryScore }: { categoryScore: CategoryScore }): JSX.Element {
    const darkModeContext = useContext(DarkModeContext);

    if (!darkModeContext) {
        throw new Error('DarkModeContext must be used within a Provider');
    }

    const { darkMode } = darkModeContext;

    let categoryName = categoryScore.category;
    categoryName = displayNames[categoryName];
    const score = categoryScore.score;
    const closePlaces = categoryScore.closePlaces;
    const mediumPlaces = categoryScore.mediumPlaces;
    const farPlaces = categoryScore.farPlaces;

    let backgroundImage = getImage(categoryName);

    return (
      <div
        className="relative flex items-center justify-between w-screen h-[50vh] p-6 overflow-hidden"
        style={{
          backgroundImage: `url(${backgroundImage})`,
          backgroundSize: "cover",
          backgroundPosition: "center",
        }}
      >
        {/* Overlay */}
        <div className="absolute inset-0 bg-black opacity-40"></div>

        {/* Content */}
        <div className="relative flex items-center w-full px-6">
          {/* Left Side: Category Name */}
          <h1 className="flex-1 text-6xl font-bold text-white">{categoryName}</h1>

          {/* Center: Score */}
          <h2 className="text-6xl font-bold text-white text-center">{score}</h2>

          {/* Right Side: Breakdown */}
          <div className="flex-1 flex flex-row items-center justify-end space-x-4">
            <p className="text-6xl font-bold text-green-400">{closePlaces}</p>
            <div className="h-20 w-1 bg-gray-300"></div>
            <p className="text-6xl font-bold text-yellow-400">{mediumPlaces}</p>
            <div className="h-20 w-1 bg-gray-300"></div>
            <p className="text-6xl font-bold text-red-400">{farPlaces}</p>
            <div className="h-20 w-1 bg-gray-300"></div>
            <p className="text-6xl font-bold text-white">
              {closePlaces + mediumPlaces + farPlaces}
            </p>
          </div>
        </div>
      </div>
    );
}

export default CategoryScoreCard;
