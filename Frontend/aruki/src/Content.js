import React, { useContext } from 'react';
import { DarkModeContext } from './BasePage.js';
import { ActiveModeContext } from './BasePage.js';
import clsx from "clsx";

// Tailwind CSS is enabled

function Content() {

    const { darkMode } = useContext(DarkModeContext);
    const { activeMode } = useContext(ActiveModeContext);

    return (
        <div className={clsx("p-4", darkMode ? "bg-gray-800 text-white" : "bg-white")}>
            {activeMode === 'walk' && <div>Walk</div>}
            {activeMode === 'score' && <div>Score</div>}
            {activeMode === 'about' && <div>About</div>}
        </div>
    );
}

export default Content;