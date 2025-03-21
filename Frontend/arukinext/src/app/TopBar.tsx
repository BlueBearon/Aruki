import React, { JSX, useContext } from 'react';
import { DarkModeContext, ActiveModeContext } from './BasePage';
import clsx from "clsx";
import Image from 'next/image';

// Tailwind CSS is enabled

const darkModeImage = '/dark_mode.svg';
const lightModeImage = '/light-mode.svg';

/**
 * TopBar component that displays the top navigation bar with mode toggles.
 * 
 * Used in @file BasePage.tsx
 * 
 * @component
 * @returns {JSX.Element} The rendered component.
 */
function TopBar(): JSX.Element {
    const darkModeContext = useContext(DarkModeContext);
    const activeModeContext = useContext(ActiveModeContext);

    if (!darkModeContext || !activeModeContext) {
        throw new Error('Context must be used within a Provider');
    }

    const { darkMode, toggleDarkMode } = darkModeContext;
    const { activeMode, toggleActiveMode } = activeModeContext; // "walk", "score", "about"

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
            <button onClick={toggleDarkMode} className="flex items-center space-x-2 mr-4">
                {darkMode ? <Image src={lightModeImage} width={32} height={32} alt="Light Mode" /> : <Image src={darkModeImage} width={32} height={32} alt="Dark Mode" />}
                <div className="relative inline-block w-10 mr-2 align-middle select-none transition duration-200 ease-in">
                    <input type="checkbox" name="toggle" id="toggle" checked={darkMode} onChange={toggleDarkMode} className="toggle-checkbox absolute block w-6 h-6 rounded-full bg-white border-4 appearance-none cursor-pointer"/>
                    <label htmlFor="toggle" className="toggle-label block overflow-hidden h-6 rounded-full bg-gray-300 cursor-pointer"></label>
                </div>
            </button>
        </div>
    );
}

export default TopBar;
