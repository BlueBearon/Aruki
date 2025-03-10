import React, { JSX } from 'react';

/**
 * ErrorScreen component displays an error message and a back button.
 * 
 * 
 * Used in @file WalkActivity.tsx @file ScoreActivity.tsx
 * 
 * @param {Object} props - The component props.
 * @param {Object} props.errorData - The error data object containing the error message.
 * @param {Function} props.setActivityState - Function to set the activity state.
 * @returns {JSX.Element} The rendered component.
 */
function ErrorScreen({ errorData, setActivityState }: { errorData: Error, setActivityState: (state: number) => void }): JSX.Element {

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
