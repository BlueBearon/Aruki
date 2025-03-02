import React, { useContext, useEffect, JSX } from 'react';
import { DarkModeContext, ActiveModeContext } from './BasePage';
import { getLocations, getScores } from './BackendFunctions';
import clsx from "clsx";

/**
 * This component is responsible for displaying the loading screen when the user is waiting for the data to be fetched from the API.
 * 
 * The user will only see a loading graphic. In the background:
 * 
 * - If activeMode is 'walk', the component will call getLocations() (async) to fetch the data from the API.
 * - If activeMode is 'score', the component will call getScores() (async) to fetch the data from the API.
 * 
 * Upon successful fetching of the data, the component will call setData() to set the data to the parent component.
 * Then, the component will call setActivityState(2) to switch to the next screen.
 * If there is an error during fetching, the component will call setActivityState(3) to switch to the error screen.
 * 
 * 
 * Used in @file WalkActivity.tsx @file ScoreActivity.tsx
 * 
 * @component
 * @param {Object} props - The props for the component.
 * @param {Function} props.setActivityState - Function to set the activity state.
 * @param {Function} props.setData - Function to set the fetched data.
 * @param {Function} props.setErrorData - Function to set the error data.
 * @param {string} props.address - The address to fetch data for.
 * @returns {JSX.Element} The loading screen component.
 */
function LoadingScreen({ setActivityState, setData, setErrorData, address }: { setActivityState: (state: number) => void, setData: (data: any) => void, setErrorData: (error: Error) => void, address: string }): JSX.Element {
    const darkModeContext = useContext(DarkModeContext);
    const activeModeContext = useContext(ActiveModeContext);

    if (!darkModeContext || !activeModeContext) {
        throw new Error('Context must be used within a Provider');
    }

    const { darkMode } = darkModeContext;
    const { activeMode } = activeModeContext;

    useEffect(() => {
        const fetchData = async () => {
          try {
            if (activeMode === 'walk') {
              const locations = await getLocations(address);
              setData(locations);
            } 
            else if (activeMode === 'score') {
              const scores = await getScores(address);
              setData(scores);
            }
            setActivityState(2); // Proceed to the next screen
          } 
          catch (error) {
            console.error('Error fetching data:', error);
            setErrorData(error as Error);
            setActivityState(3); // Go to the error screen
          }
        };
    
        fetchData();
    }, []); // Run only once on mount

    // Loading Screen graphic
    return (
        <div className={clsx("flex", "justify-center", "items-center", "h-full", "w-full", darkMode ? "bg-gray-800" : "bg-white")}>
            <div className="flex flex-col items-center">
                <div className="animate-spin rounded-full h-32 w-32 border-t-2 border-b-2 border-gray-900"></div>
                <div className={clsx("text-lg", darkMode ? "text-gray-300" : "text-gray-900", "mt-4")}>Loading...</div>
            </div>
        </div>
    );
}

export default LoadingScreen;
