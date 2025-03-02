import React, { useContext, JSX } from 'react';
import { DarkModeContext } from './BasePage';
import CategoryScoreCard from './CategoryScoreCard';
import clsx from "clsx";

/**
 * Component to display the score results.
 * 
 * Used in @file ScoreActivity.tsx
 * 
 * @param {Object} props - The component props.
 * @param {Object} props.scoreData - The score data.
 * @param {number} props.scoreData.walkabilityScore - The walkability score.
 * @param {Array} props.scoreData.categoryScores - The category scores.
 * @param {Function} props.setActivityState - Function to set the activity state.
 * @returns {JSX.Element} The rendered component.
 */
function ScoreResultScreen({ scoreData, setActivityState }: { scoreData: { walkabilityScore: number, categoryScores: any[] }, setActivityState: (state: number) => void }): JSX.Element {
    const darkModeContext = useContext(DarkModeContext);

    if (!darkModeContext) {
        throw new Error('DarkModeContext must be used within a Provider');
    }

    const { darkMode } = darkModeContext;

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
