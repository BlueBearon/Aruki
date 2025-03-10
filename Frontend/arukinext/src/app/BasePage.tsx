"use client";

import React, { useState, createContext, useContext, ReactNode, JSX } from 'react';
import clsx from 'clsx';

import TopBar from './TopBar';
import Content from './Content';

/**
 * Context for managing dark mode state.
 * @type {React.Context<{darkMode: boolean, toggleDarkMode: () => void}>}
 */
const DarkModeContext = createContext<{
  darkMode: boolean;
  toggleDarkMode: () => void;
} | undefined>(undefined);

/**
 * Context for managing active mode state.
 * @type {React.Context<{activeMode: string, toggleActiveMode: (mode: string) => void}>}
 */
const ActiveModeContext = createContext<{
  activeMode: string;
  toggleActiveMode: (mode: string) => void;
} | undefined>(undefined);

/**
 * BasePage component that provides the main layout and context providers.
 * 
 * Used in @file App.tsx
 * 
 * @component
 * @returns {JSX.Element} The rendered component.
 */
function BasePage(): JSX.Element {
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
  const toggleActiveMode = (mode: string) => {
    setActiveMode(mode);
  };

  return (
    <div className= {clsx("flex flex-col flex-grow min-h-screen", darkMode ? "bg-gray-800 text-white" : "bg-white text-black")}>
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

// <div className="flex flex-col flex-grow bg-white">