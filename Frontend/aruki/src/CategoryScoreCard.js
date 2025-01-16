import React, { useContext } from 'react';
import { DarkModeContext } from './BasePage.js';
import { CategoryScore, placeTypes, displayNames } from './BackendFunctions.js';
import clsx from "clsx";

/**
 * 
 * @param { CategoryScore } categoryScore
 * @returns 
 */
function CategoryScoreCard({ categoryScore }) {
    const { darkMode } = useContext(DarkModeContext);

    let categoryName = categoryScore.category;
    categoryName = displayNames[categoryName];
    let score = categoryScore.score;
    let closePlaces = categoryScore.closePlaces;
    let mediumPlaces = categoryScore.mediumPlaces;
    let farPlaces = categoryScore.farPlaces;

    return (
        <div className={clsx(
            "flex flex-col items-center justify-center w-full max-w-lg p-6 rounded-xl pt-8",
            darkMode ? "bg-gray-800 text-white shadow-lg" : "bg-white text-gray-900 shadow-md"
        )}>
            <h1 className="text-4xl font-semibold mb-4">{categoryName}</h1>
            <p className="text-2xl font-bold text-center mb-6">Score: {score}</p>
            <div className="flex flex-row items-center justify-center space-x-4">
                <div className="flex items-center">
                    <div className="h-6 w-6 rounded-full bg-green-500 mr-2"></div>
                    <p className="text-lg font-medium text-green-500">{closePlaces}</p>
                </div>
                <div className="flex items-center">
                    <div className="h-6 w-6 rounded-full bg-yellow-500 mr-2"></div>
                    <p className="text-lg font-medium text-yellow-500">{mediumPlaces}</p>
                </div>
                <div className="flex items-center">
                    <div className="h-6 w-6 rounded-full bg-red-500 mr-2"></div>
                    <p className="text-lg font-medium text-red-500">{farPlaces}</p>
                </div>
            </div>
        </div>
    );
}

export default CategoryScoreCard;
