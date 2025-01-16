import React, { useContext } from 'react';
import { DarkModeContext } from './BasePage.js';
import { ActiveModeContext } from './BasePage.js';
import WalkActivity from './WalkActivity.js';
import ScoreActivity from './ScoreActivity.js';
import AboutScreen from './AboutScreen.js';
import clsx from "clsx";

// Tailwind CSS is enabled

function Content() {

    const { darkMode } = useContext(DarkModeContext);
    const { activeMode } = useContext(ActiveModeContext);

    return (
        <div className={clsx("flex justify-center h-full w-full", darkMode ? "bg-gray-800 text-white" : "bg-white")}>
            <div className="flex h-full w-full items-center justify-center">
                {activeMode === 'walk' && <WalkActivity />}
                {activeMode === 'score' && <ScoreActivity />}
                {activeMode === 'about' && <AboutScreen />}
            </div>
        </div>
    );
}

export default Content;