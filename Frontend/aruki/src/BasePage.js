import React, { useState, createContext, useContext } from 'react';

import TopBar from './TopBar.js';
import Content from './Content.js';

const DarkModeContext = createContext();
const ActiveModeContext = createContext();

function BasePage() {
    const [darkMode, setDarkMode] = useState(false);


    /**
     * Active Mode State
     * walk: Walk Mode
     * score: Score Mode
     * about: About Screen
     */
    const [activeMode, setActiveMode] = useState('walk');

    const toggleDarkMode = () => {
        setDarkMode(!darkMode);
    };

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

/*

                
                    
                    
                               
                

*/