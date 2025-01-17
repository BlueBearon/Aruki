import React, { useState, useContext } from 'react';
import { DarkModeContext } from './BasePage.js';
import AddressScreen from './AddressScreen.js';
import LoadingScreen from './LoadingScreen.js';
import WalkResultScreen from './WalkResultScreen.js';
import ErrorScreen from './ErrorScreen.js';
import clsx from "clsx";

// Tailwind CSS is enabled

/**
 * WalkActivity component handles the walk mode activity flow.
 * It manages different states of the activity and renders corresponding screens.
 *
 * @component
 * @returns {JSX.Element} The rendered component.
 */
function WalkActivity() {

    const { darkMode } = useContext(DarkModeContext);

    /**
     * Activity State (specific to walk mode)
     * 0: start page, awaiting user to type in address
     * 1: Loading page, fetching data from API
     * 2: Walk Result page, displaying the results
     * 3: Error page, displaying error message
     * 
     * @type {number}
     */
    const [activityState, setActivityState] = useState(0);
    
    /**
     * Address State
     * 
     * @type {string}
     */
    const [address, setAddress] = useState(''); 

    /**
     * Walk Data State
     * 
     * @type {Promise<{locations: Location[][], viscinities: number[], viscinitiesByCategories: number[][]}> | null}
     */
    const [walkData, setWalkData] = useState(null); 

    /**
     * Error Data
     * 
     * @type {Error | null}
     */
    const [errorData, setErrorData] = useState(null); 
    
    return (
        <div className={clsx("flex flex-col justify-center flex-grow max-h-full", darkMode ? "bg-gray-800 text-white" : "bg-white")}>
            {activityState === 0 && <AddressScreen mode="walk" setAddress={setAddress} setActivityState={setActivityState} />}
            {activityState === 1 && <LoadingScreen setActivityState={setActivityState} setData={setWalkData} setErrorData={setErrorData} address={address} />}
            {activityState === 2 && <WalkResultScreen walkData={walkData} setActivityState={setActivityState} />}
            {activityState === 3 && <ErrorScreen errorData={errorData} setActivityState={setActivityState} />}
        </div>
    );
}

export default WalkActivity;
