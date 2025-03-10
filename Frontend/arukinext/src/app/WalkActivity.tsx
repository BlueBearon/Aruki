import React, { useState, useContext, JSX } from 'react';
import { DarkModeContext } from './BasePage';
import AddressScreen from './AddressScreen';
import LoadingScreen from './LoadingScreen';
import WalkResultScreen from './WalkResultScreen';
import ErrorScreen from './ErrorScreen';
import clsx from "clsx";
import { ScoreResponse, LocationResponse } from './BackendFunctions';

type DataResponse = LocationResponse | ScoreResponse | null;



// Tailwind CSS is enabled

/**
 * WalkActivity component handles the walk mode activity flow.
 * It manages different states of the activity and renders corresponding screens.
 * 
 * Used in @file Content.tsx
 *
 * @component
 * @returns {JSX.Element} The rendered component.
 */
function WalkActivity(): JSX.Element {
    const darkModeContext = useContext(DarkModeContext);

    if (!darkModeContext) {
        throw new Error('DarkModeContext must be used within a Provider');
    }

    const { darkMode } = darkModeContext;

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
    const [walkData, setWalkData] = useState<DataResponse | null>(null);

    /**
     * Error Data
     * 
     * @type {Error | null}
     */
    const [errorData, setErrorData] = useState<Error | null>(null); 
    
    return (
        <div className={clsx("flex flex-col justify-center flex-grow", darkMode ? "bg-gray-800 text-white" : "bg-white")}>
            {activityState === 0 && <AddressScreen mode="walk" setAddress={setAddress} setActivityState={setActivityState} />}
            {activityState === 1 && <LoadingScreen setActivityState={setActivityState} setData={setWalkData} setErrorData={setErrorData} address={address} />}
            {activityState === 2 && walkData && <WalkResultScreen walkData={walkData as LocationResponse} setActivityState={setActivityState} />}
            {activityState === 3 && errorData && <ErrorScreen errorData={errorData} setActivityState={setActivityState} />}
        </div>
    );
}

export default WalkActivity;
