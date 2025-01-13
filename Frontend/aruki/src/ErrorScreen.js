import React, { useContext } from 'react';
import { DarkModeContext } from './BasePage.js';



// Tailwind CSS is enabled


function ErrorScreen({ errorData, setActivityState }) {

    const { darkMode } = useContext(DarkModeContext);

    return (
        <div className="flex h-full w-full justify-center items-center">
            <div className="flex flex-col items-center justify-center">
                <h1 className="text-5xl font-bold">Error</h1>
                <p className="text-lg">{errorData.message}</p>
                <button onClick={() => setActivityState(0)} className="mt-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">Back</button>
            </div>
        </div>
    );
}

export default ErrorScreen;