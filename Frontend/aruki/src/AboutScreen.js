import React, { useContext } from 'react';
import { DarkModeContext } from './BasePage.js';
import { ActiveModeContext } from './BasePage.js';
import clsx from "clsx";

// Tailwind CSS is enabled

function AboutScreen() {

    const { darkMode } = useContext(DarkModeContext);
    const { activeMode } = useContext(ActiveModeContext);

    return (
        <div className={clsx("p-4", darkMode ? "bg-gray-800 text-white" : "bg-white")}>
            <div className="container mx-auto">
                <div className="flex flex-col items-center justify-center h-screen">
                <h1 className="text-5xl font-bold">About</h1>
                <p className="text-xl mt-4">This is the about page.</p>
                </div>
            </div>
        </div>
    );
}

export default AboutScreen;