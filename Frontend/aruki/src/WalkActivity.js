import React, { useState, useContext } from 'react';
import { DarkModeContext } from './BasePage.js';
import AddressScreen from './AddressScreen.js';
import LoadingScreen from './LoadingScreen.js';
import WalkResultScreen from './WalkResultScreen.js';
import clsx from "clsx";

// Tailwind CSS is enabled

function WalkActivity() {

    const { darkMode } = useContext(DarkModeContext);

    // Activity State (specific to walk mode) 0: start page, awaiting user to type in address | 1: Loading page, fetching data from API | 2: Walk Result page, displaying the results
    const [activityState, setActivityState] = useState(0);
    
    // Address State
    const [address, setAddress] = useState('');

    // Walk Data State
    const [walkData, setWalkData] = useState(null);
    
    return (
        <div 
            className={clsx("flex h-full w-full justify-center items-center outline outline-blue-700", darkMode ? "bg-gray-800 text-white" : "bg-white")}
            
        >
            <div className="flex h-full w-full mx-auto justify-center items-center">
                {activityState === 0 && <AddressScreen mode="walk" setAddress={setAddress} setActivityState={setActivityState} />}
                {activityState === 1 && <LoadingScreen setActivityState={setActivityState} />}
                {activityState === 2 && <WalkResultScreen walkData={walkData} setActivityState={setActivityState} />}
            </div>
        </div>
    );
}

export default WalkActivity;