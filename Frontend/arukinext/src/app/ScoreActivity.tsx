import React, { useState, useContext, JSX } from 'react';
import { DarkModeContext } from './BasePage';
import AddressScreen from './AddressScreen';
import LoadingScreen from './LoadingScreen';
import ScoreResultScreen from './ScoreResultScreen';
import ErrorScreen from './ErrorScreen';
import clsx from "clsx";

// Tailwind CSS is enabled

/**
 * ScoreActivity component handles the different states of the score activity.
 * It manages the state transitions between the address input, loading, score result, and error screens.
 * 
 * Used in @file Content.tsx
 *
 * @component
 * @returns {JSX.Element} The rendered component.
 */
function ScoreActivity(): JSX.Element {
    const darkModeContext = useContext(DarkModeContext);

    if (!darkModeContext) {
        throw new Error('DarkModeContext must be used within a Provider');
    }

    const { darkMode } = darkModeContext;

    // Activity State (specific to score mode) 0: start page, awaiting user to type in address | 1: Loading page, fetching data from API | 2: Score Result page, displaying the results
    const [activityState, setActivityState] = useState(0);

    // Address State
    const [address, setAddress] = useState('');

    // Score Data State
    const [scoreData, setScoreData] = useState<any>(null);

    // Error Data
    const [errorData, setErrorData] = useState<Error | null>(null);
    
    return (
        <div className={clsx("flex flex-col justify-center flex-grow max-h-full", darkMode ? "bg-gray-800 text-white" : "bg-white")}>
            {activityState === 0 && <AddressScreen mode="score" setAddress={setAddress} setActivityState={setActivityState} />}
            {activityState === 1 && <LoadingScreen setActivityState={setActivityState} setData={setScoreData} setErrorData={setErrorData} address={address} />}
            {activityState === 2 && <ScoreResultScreen scoreData={scoreData} setActivityState={setActivityState} />}
            {activityState === 3 && errorData && <ErrorScreen errorData={errorData} setActivityState={setActivityState} />}
        </div>
    );
}

export default ScoreActivity;
