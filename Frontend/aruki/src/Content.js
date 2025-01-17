import React, { useContext } from 'react';
import { DarkModeContext } from './BasePage.js';
import { ActiveModeContext } from './BasePage.js';
import WalkActivity from './WalkActivity.js';
import ScoreActivity from './ScoreActivity.js';
import AboutScreen from './AboutScreen.js';
import clsx from "clsx";

// Tailwind CSS is enabled

/**
 * Content component that renders different activities based on the active mode.
 * It also applies dark mode styling based on the context.
 *
 * @component
 * @returns {JSX.Element} The rendered component.
 */
function Content() {
    const { darkMode } = useContext(DarkModeContext);
    const { activeMode } = useContext(ActiveModeContext);

    return (
        <div className={clsx("flex flex-col justify-center flex-grow max-h-9/10 ", darkMode ? "bg-gray-800 text-white" : "bg-white")}>
            {activeMode === 'walk' && <WalkActivity />}
            {activeMode === 'score' && <ScoreActivity />}
            {activeMode === 'about' && <AboutScreen />}
        </div>
    );
}

export default Content;

/*


*/