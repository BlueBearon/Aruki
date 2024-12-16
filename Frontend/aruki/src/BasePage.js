import React, { useState, createContext, useContext } from 'react';

import TopBar from './TopBar.js';
import Content from './Content.js';

const DarkModeContext = createContext();
const ActiveModeContext = createContext();

function BasePage() {
    const [darkMode, setDarkMode] = useState(false);
    const [activeMode, setActiveMode] = useState('walk');

    const toggleDarkMode = () => {
        setDarkMode(!darkMode);
    };

    const toggleActiveMode = (mode) => {
        setActiveMode(mode);
    }

    return (
        <DarkModeContext.Provider value={{ darkMode, toggleDarkMode }}>
            <ActiveModeContext.Provider value={{ activeMode, toggleActiveMode }}>
                <div className= "flex flex-col align-items-center h-screen">
                    <TopBar />
                    <Content />           
                </div>
            </ActiveModeContext.Provider>
        </DarkModeContext.Provider>
    );
}

export { DarkModeContext, ActiveModeContext };
export default BasePage;