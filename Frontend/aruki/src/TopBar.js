import React, { useContext } from 'react';
import { DarkModeContext } from './BasePage.js';

// Tailwind CSS is enabled

function TopBar() {

    const { darkMode, toggleDarkMode } = useContext(DarkModeContext);

    return (
        <div className="bg-gray-800 text-white p-4">
            <h1 className="text-2xl">Aruki</h1>
            <button onClick={toggleDarkMode} className="bg-gray-700 hover:bg-gray-900 text-white font-bold py-2 px-4 rounded mt-2">
                {darkMode ? 'Light' : 'Dark'} Mode
            </button>
        </div>
    );
}

export default TopBar;