import React, { useContext } from 'react';
import { DarkModeContext } from './BasePage.js';
import { ActiveModeContext } from './BasePage.js';
import CategoryScoreCard from './CategoryScoreCard.js';
import clsx from "clsx";

// Tailwind CSS is enabled

/**
 * Component to display the score results.
 * 
 * Used in @file ScoreActivity.js
 * 
 * @param {Object} props - The component props.
 * @param {Object} props.scoreData - The score data.
 * @param {number} props.scoreData.walkabilityScore - The walkability score.
 * @param {Array} props.scoreData.categoryScores - The category scores.
 * @param {Function} props.setActivityState - Function to set the activity state.
 * @returns {JSX.Element} The rendered component.
 */
function ScoreResultScreen({ scoreData, setActivityState }) {
    const { darkMode } = useContext(DarkModeContext);

    // scoreData: {walkabilityScore: number, categoryScores: CategoryScore[]}

    // Use CategoryScoreCard for each category, only needs to pass the categoryScore

    // Just standard flex

    return (
        <div className="grid grid-cols-1 w-full place-items-center mt-4 h-full p-4 rounded-l gap-4">
            {/* Back Button */}
            <button
            onClick={() => setActivityState(0)}
            className="mt-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded w-1/4"
            >
            Back
            </button>
            {/* Title */}
            <h1 className="text-7xl font-bold text-center mt-8">
            Walkability Score
            </h1>
            {/* Walkability Score */}
            <h2 className="text-7xl font-bold mt-4 text-center mb-8">
            {scoreData.walkabilityScore}
            </h2>
            {/* Category Scores */}
            {scoreData.categoryScores.map((categoryScore, index) => (
                <CategoryScoreCard categoryScore={categoryScore} key={index} />
            ))}
            {/* Back Button */}
            <button
            onClick={() => setActivityState(0)}
            className="mt-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded w-1/4"
            >
            Back
            </button>
        </div>
    );
}

export default ScoreResultScreen;