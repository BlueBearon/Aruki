import React, { useContext, useEffect } from 'react';
import { DarkModeContext } from './BasePage.js';
import { ActiveModeContext } from './BasePage.js';
import { getLocations, getScores } from './BackendFunctions.js';
import clsx from "clsx";

// Tailwind CSS is enabled


/**
 * This component is responsible for displaying the loading screen when the user is waiting for the data to be fetched from the API.
 * 
 * The user will only see a loading graphic. In the background:
 * 
 * If activeMode is 'walk', the component will call getLocations() (async) to fetch the data from the API.
 * 
 * If activeMode is 'score', the component will call getScores() (async) to fetch the data from the API.
 * 
 * Upon successful fetching of the data, the component will call setData() to set the data to the parent component.
 * 
 * Then, the component will call setActivityState(2) to switch to the next screen.
 * 
 * If there is an error during fetching, the component will call setActivityState(3) to switch to the error screen.
 * 
 * @param {*} param0 
 * @returns 
 */
function LoadingScreen( { setActivityState, setData, setErrorData } ) {

    const { darkMode } = useContext(DarkModeContext);
    const { activeMode } = useContext(ActiveModeContext);

    useEffect(() => {
        const fetchData = async () => {
          try {
            if (activeMode === 'walk') {
              const locations = await getLocations();
              setData(locations);
            } 
            else if (activeMode === 'score') {
              const scores = await getScores();
              setData(scores);
            }
            setActivityState(2); // Proceed to the next screen
          } 
          catch (error) {
            console.error('Error fetching data:', error);
            setErrorData(error);
            setActivityState(3); // Go to the error screen
          }
        };
    
        fetchData();
    }, []); // Run only once on mount

    // Loading Screen graphic
    return (
        <div className={clsx("flex", "justify-center", "items-center", "h-screen", "w-screen", darkMode ? "bg-gray-800" : "bg-white")}>
            <div className="flex flex-col items-center">
                <div className="animate-spin rounded-full h-32 w-32 border-t-2 border-b-2 border-gray-900"></div>
                <div className={clsx("text-lg", darkMode ? "text-gray-300" : "text-gray-900", "mt-4")}>Loading...</div>
            </div>
        </div>
    );

}

export default LoadingScreen;