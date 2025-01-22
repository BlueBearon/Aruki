import React, { useState, useContext } from 'react';
import { DarkModeContext } from './BasePage.js';
import clsx from "clsx";
import walkImage from './assets/city_background.jpg';
import scoreImage from './assets/city_background.jpg';

// Tailwind CSS is enabled

/**
 * AddressScreen component
 * 
 * Used in @file WalkActivity.js @file ScoreActivity.js
 * 
 * @param {Object} props - Component properties
 * @param {string} props.mode - The mode of the screen, either 'walk' or 'score'
 * @param {function} props.setAddress - Function to set the address
 * @param {function} props.setActivityState - Function to set the activity state
 * @returns {JSX.Element} The rendered component
 */
function AddressScreen({ mode, setAddress, setActivityState }) {

    const { darkMode } = useContext(DarkModeContext);

    const [address, setThisAddress] = useState('');


    const walkModeMessage = `
        In Walk Mode, you can enter an address to view a list of locations within walking distance. 
        These locations are categorized by type, such as restaurants, parks, and schools. Each location
        will have a colored border (green, yellow, or red) based on its distance from the address, with
        green being the closest and red being the farthest.
    `;

    const scoreModeMessage = `
        In Score Mode, you can enter an address to view a score for that location. 
        The score is determined by the number of nearby locations, their distances, 
        and their types. You will also see individual scores for each category of location.
    `;

    /**
     * Submits the address and updates the activity state
     */
    const submitAddress = () => {

        setAddress(address);
        setActivityState(1);
    }
    
    return (
        <div className={clsx("flex flex-col items-center justify-center flex-grow", darkMode ? "bg-gray-800 text-white" : "bg-white")} style={{backgroundImage: `url(${mode === 'walk' ? walkImage : scoreImage})`, backgroundSize: 'cover', backgroundPosition: 'center'}}>
            <div className = {clsx("flex flex-col items-center w-1/2 shadow-lg  border border-gray-300 rounded-lg p-4", darkMode ? "bg-gray-700 text-white" : "bg-white")}>
                <div className="space-y-4 flex flex-col items-center">
                    <h1 className="text-5xl font-code tracking-wide text-blue-600">Aruki</h1>
                    <h2 className="text-2xl font-code text-blue-600 mb-4">For a Walkable Future</h2>

                    <h2 className="text-3xl">{mode === 'walk' ? 'Walk Mode' : 'Score Mode'}</h2>

                    <p className="text-lg leading-relaxed p-4 ml-5 mr-5">
                        {mode === 'walk' ? walkModeMessage : scoreModeMessage}
                    </p>
                </div>
                
                <div className="w-full mx-auto flex flex-col items-center">
                    <h1 className="text-2xl">Enter Address</h1>
                    <input 
                        type="text" 
                        className={clsx("w-3/4 p-2 mt-2 mb-4 rounded-lg shadow-sm focus:outline-none focus:ring fcous:ring-blue-300", darkMode ? "bg-gray-500 text-white" : "bg-gray-300")}
                        value={address}
                        onChange={(e) => setThisAddress(e.target.value)}
                    />
                    <button
                        className={clsx(
                            "w-1/4 p-2 mt-2 rounded-lg shadow-md hover:scale-105 transition-transform duration-200",
                            darkMode ? "bg-blue-500 text-white hover:bg-blue-400" : "bg-blue-700 text-white hover:bg-blue-600"
                        )}
                        onClick={submitAddress}
                        >
                        Submit
                    </button>
                </div>
            </div>
        </div>
    );

}


export default AddressScreen;