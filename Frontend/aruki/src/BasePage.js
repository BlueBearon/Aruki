import React, { useState, createContext, useContext } from 'react';

import TopBar from './TopBar.js';
import Content from './Content.js';

const DarkModeContext = createContext();

function BasePage() {
    const [darkMode, setDarkMode] = useState(false);

    const toggleDarkMode = () => {
        setDarkMode(!darkMode);
    };

    return (
        <DarkModeContext.Provider value={{ darkMode, toggleDarkMode }}>
            <div className={darkMode ? 'dark' : ''}>
                <TopBar />
                <Content />           
             </div>
        </DarkModeContext.Provider>
    );
}

export { DarkModeContext };
export default BasePage;