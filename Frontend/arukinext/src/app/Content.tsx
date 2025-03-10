import React, { useContext, JSX } from 'react';
import { DarkModeContext, ActiveModeContext } from './BasePage';
import WalkActivity from './WalkActivity';
import ScoreActivity from './ScoreActivity';
import AboutScreen from './AboutScreen';
import clsx from "clsx";

// Tailwind CSS is enabled

/**
 * Content component that renders different activities based on the active mode.
 * It also applies dark mode styling based on the context.
 * 
 * Used in @file BasePage.tsx
 *
 * @component
 * @returns {JSX.Element} The rendered component.
 */
function Content(): JSX.Element {
    const darkModeContext = useContext(DarkModeContext);
    const activeModeContext = useContext(ActiveModeContext);

    if (!darkModeContext || !activeModeContext) {
        throw new Error('Context must be used within a Provider');
    }

    const { darkMode } = darkModeContext;
    const { activeMode } = activeModeContext;

    return (
        <div className={clsx("flex flex-col justify-center flex-grow", darkMode ? "bg-gray-800 text-white" : "bg-white")}>
            {activeMode === 'walk' && <WalkActivity />}
            {activeMode === 'score' && <ScoreActivity />}
            {activeMode === 'about' && <AboutScreen />}
        </div>
    );
}

export default Content;
