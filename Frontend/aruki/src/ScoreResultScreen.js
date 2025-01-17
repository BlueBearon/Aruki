import React, { useContext } from 'react';
import { DarkModeContext } from './BasePage.js';
import { ActiveModeContext } from './BasePage.js';
import CategoryScoreCard from './CategoryScoreCard.js';
import clsx from "clsx";

// Tailwind CSS is enabled

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

/*

                




                {scoreData.categoryScores.map((categoryScore, index) => <CategoryScoreCard categoryScore={categoryScore} key={index} />)}


<div className={clsx("flex flex-col justify-center flex-wrap max-h-full", darkMode ? "bg-gray-800 text-white" : "bg-yellow-500")}>
            <div className="flex flex-col justify-center items-center">
                <button onClick={() => setActivityState(0)} className="mt-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">Back</button>
                <h1 className="text-5xl font-bold mt-4">Walkability Score: {scoreData.walkabilityScore}</h1>
                <div className="grid grid-cols-1 gap-4 w-full place-items-center overflow-y-auto">
                    {scoreData.categoryScores.map((categoryScore, index) => <CategoryScoreCard categoryScore={categoryScore} key={index} />)}
                </div>
                <button onClick={() => setActivityState(0)} className="mt-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">Back</button>
            </div>
        </div>

*/