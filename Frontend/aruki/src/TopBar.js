import React, { useContext } from 'react';
import { DarkModeContext, ActiveModeContext } from './BasePage.js';
import clsx from "clsx";


// Tailwind CSS is enabled

function TopBar() {

    const { darkMode, toggleDarkMode } = useContext(DarkModeContext);
    const { activeMode, toggleActiveMode } = useContext(ActiveModeContext); // "walk", "score", "about"

    return (
        <div className={clsx("flex justify-between p-4", darkMode ? "bg-gray-800 text-white" : "bg-white")}>
            <h1 className="text-3xl text-blue-700 font-code">Aruki</h1>
            <div className={clsx("flex border rounded-full overflow-hidden", darkMode ? "border-gray-700" : "border-gray-300")}>
                <button 
                    className={clsx("flex-1 px-4 py-2", activeMode === 'walk' ? "bg-blue-500 text-white" : darkMode ? "text-gray-300 hover:bg-gray-700" : "text-gray-700 hover:bg-gray-300")}
                    onClick = {() => toggleActiveMode('walk')}
                >
                    Walk
                </button>
                <button 
                    className={clsx("flex-1 px-4 py-2", activeMode === 'score' ? "bg-blue-500 text-white" : darkMode ? "text-gray-300 hover:bg-gray-700" : "text-gray-700 hover:bg-gray-300")}
                    onClick = {() => toggleActiveMode('score')}
                >
                    Score
                </button>
                <button 
                    className={clsx("flex-1 px-4 py-2", activeMode === 'about' ? "bg-blue-500 text-white" : darkMode ?  "text-gray-300 hover:bg-gray-700" : "text-gray-700 hover:bg-gray-300")}
                    onClick = {() => toggleActiveMode('about')}
                >
                    About
                </button>
            </div>
            {/*Light/Dark mode switch toggle*/}
            <button onClick={toggleDarkMode} className="float-right">
                {darkMode ? 'Light' : 'Dark'} Mode
            </button>
        </div>
    );
}

export default TopBar;