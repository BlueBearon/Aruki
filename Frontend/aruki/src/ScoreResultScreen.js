import React, { useContext } from 'react';
import { DarkModeContext } from './BasePage.js';
import { ActiveModeContext } from './BasePage.js';
import clsx from "clsx";

// Tailwind CSS is enabled

function ScoreResultScreen({ scoreData, setActivityState }) {

    const { darkMode } = useContext(DarkModeContext);

    return (
        <div className={clsx("h-screen", darkMode ? "bg-gray-800 text-white" : "bg-white")}>
            <div className="container mx-auto">
                <div className="flex flex-col items-center justify-center h-screen">
                <h1 className="text-5xl font-bold">Score Result</h1>
                <p className="text-xl mt-4">This is the score result screen.</p>
                <button onClick={() => setActivityState(0)} className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">Back</button>
                </div>
            </div>
        </div>
    );
}

export default ScoreResultScreen;