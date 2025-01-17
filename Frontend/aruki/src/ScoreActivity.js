import React, { useState, useContext } from 'react';
import { DarkModeContext } from './BasePage.js';
import AddressScreen from './AddressScreen.js';
import LoadingScreen from './LoadingScreen.js';
import ScoreResultScreen from './ScoreResultScreen.js';
import ErrorScreen from './ErrorScreen.js';

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

    // Error Data
    const [errorData, setErrorData] = useState(null);
    
    return (
        <div className={clsx("flex flex-col justify-center flex-grow max-h-full", darkMode ? "bg-gray-800 text-white" : "bg-white")}>
            {activityState === 0 && <AddressScreen mode="score" setAddress={setAddress} setActivityState={setActivityState} />}
            {activityState === 1 && <LoadingScreen setActivityState={setActivityState} setData={setScoreData} setErrorData={setErrorData} address={address} />}
            {activityState === 2 && <ScoreResultScreen scoreData={scoreData} setActivityState={setActivityState} />}
            {activityState === 3 && <ErrorScreen errorData={errorData} setActivityState={setActivityState} />}
        </div>
    );
}

export default ScoreActivity;