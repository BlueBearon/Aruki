import React, { useContext } from 'react';
import { DarkModeContext } from './BasePage.js';
import { ActiveModeContext } from './BasePage.js';
import clsx from "clsx";

// Tailwind CSS is enabled

function LoadingScreen( { setActivityState } ) {

    const { darkMode } = useContext(DarkModeContext);
    const { activeMode } = useContext(ActiveModeContext);

    return (
        <div className={clsx("h-screen", darkMode ? "bg-gray-800 text-white" : "bg-white")}>
            <div className="container mx-auto">
                <div className="flex flex-col items-center justify-center h-screen">
                <h1 className="text-5xl font-bold">Loading...</h1>
                <p className="text-xl mt-4">Fetching data from the API...</p>
                <button onClick={() => setActivityState(2)} className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">Next</button>
                </div>
            </div>
        </div>
    );
}

export default LoadingScreen;