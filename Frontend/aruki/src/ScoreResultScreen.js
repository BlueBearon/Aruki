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
        <div className={clsx("flex h-full w-full justify-center items-center", darkMode ? "bg-gray-800 text-white" : "bg-white")}>
            <div className="flex h-full w-full mx-auto justify-center items-center">
                <div className="flex flex-col items-center justify-center">
                    <h1 className="text-5xl font-bold">Walkability Score: {scoreData.walkabilityScore}</h1>
                    {scoreData.categoryScores.map((categoryScore, index) => <CategoryScoreCard categoryScore={categoryScore} key={index} />)}
                    <button onClick={() => setActivityState(0)} className="mt-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">Back</button>
                </div>
            </div>
        </div>
    );
}

export default ScoreResultScreen;