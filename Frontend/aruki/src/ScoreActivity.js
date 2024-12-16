import React, { useState, useContext } from 'react';
import { DarkModeContext } from './BasePage.js';
import AddressScreen from './AddressScreen.js';
import LoadingScreen from './LoadingScreen.js';
import ScoreResultScreen from './ScoreResultScreen.js';
import scoreImage from "./city_background.jpg";

import clsx from "clsx";


// Tailwind CSS is enabled

function ScoreActivity() {

    const { darkMode } = useContext(DarkModeContext);

    // Activity State (specific to score mode) 0: start page, awaiting user to type in address | 1: Loading page, fetching data from API | 2: Score Result page, displaying the results
    const [activityState, setActivityState] = useState(0);

    // Address State
    const [address, setAddress] = useState('');

    // Score Data State
    const [scoreData, setScoreData] = useState(null);
    
    return (
        <div className={clsx("flex h-full w-full justify-center items-center outline outline-blue-700", darkMode ? "bg-gray-800 text-white" : "bg-white")}>
            <div className="flex h-full w-full mx-auto justify-center items-center">
                {activityState === 0 && <AddressScreen mode="score" setAddress={setAddress} setActivityState={setActivityState} />}
                {activityState === 1 && <LoadingScreen setActivityState={setActivityState} />}
                {activityState === 2 && <ScoreResultScreen scoreData={scoreData} setActivityState={setActivityState} />}
            </div>
        </div>
    );
}

export default ScoreActivity;