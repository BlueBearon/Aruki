import React, { useState, createContext, useContext } from 'react';

import TopBar from './TopBar.js';
import Content from './Content.js';

/**
 * Context for managing dark mode state.
 * @type {React.Context<{darkMode: boolean, toggleDarkMode: function}>}
 */
const DarkModeContext = createContext();

/**
 * Context for managing active mode state.
 * @type {React.Context<{activeMode: string, toggleActiveMode: function}>}
 */
const ActiveModeContext = createContext();

/**
 * BasePage component that provides the main layout and context providers.
 * @component
 * @returns {JSX.Element} The rendered component.
 */
function BasePage() {
    const [darkMode, setDarkMode] = useState(false);

    /**
     * Active Mode State
     * walk: Walk Mode
     * score: Score Mode
     * about: About Screen
     */
    const [activeMode, setActiveMode] = useState('walk');

    /**
     * Toggles the dark mode state.
     */
    const toggleDarkMode = () => {
        setDarkMode(!darkMode);
    };

    /**
     * Sets the active mode state.
     * @param {string} mode - The mode to set as active.
     */
    const toggleActiveMode = (mode) => {
        setActiveMode(mode);
    }

    return (
        <div className="flex flex-col flex-grow bg-white">
            <DarkModeContext.Provider value={{ darkMode, toggleDarkMode }}>
                <ActiveModeContext.Provider value={{ activeMode, toggleActiveMode }}>
                    <TopBar />
                    <Content />
                </ActiveModeContext.Provider>
            </DarkModeContext.Provider>
        </div>
    );
}

export { DarkModeContext, ActiveModeContext };
export default BasePage;