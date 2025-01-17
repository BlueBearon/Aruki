import React, { useContext } from 'react';
import { DarkModeContext, ActiveModeContext } from './BasePage.js';
import clsx from "clsx";
import darkModeImage from './assets/dark_mode.svg';
import lightModeImage from './assets/light-mode.svg';



// Tailwind CSS is enabled

function TopBar() {

    const { darkMode, toggleDarkMode } = useContext(DarkModeContext);
    const { activeMode, toggleActiveMode } = useContext(ActiveModeContext); // "walk", "score", "about"

    return (
        <div className={clsx("flex justify-between p-4 h-[10vh]", darkMode ? "bg-gray-800 text-white" : "bg-white")}>
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
            <button onClick={toggleDarkMode} className="flex items-center space-x-2 mr-4">
                {darkMode ? <img src={lightModeImage} className="w-8 h-8 fill-white" alt="Light Mode" /> : <img src={darkModeImage} className="w-8 h-8 fill-black" alt="Dark Mode" />}
                <div className="relative inline-block w-10 mr-2 align-middle select-none transition duration-200 ease-in">
                    <input type="checkbox" name="toggle" id="toggle" checked={darkMode} onChange={toggleDarkMode} className="toggle-checkbox absolute block w-6 h-6 rounded-full bg-white border-4 appearance-none cursor-pointer"/>
                    <label htmlFor="toggle" className="toggle-label block overflow-hidden h-6 rounded-full bg-gray-300 cursor-pointer"></label>
                </div>
            </button>
        </div>
    );
}

export default TopBar;

/*




*/