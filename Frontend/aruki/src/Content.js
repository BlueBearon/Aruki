import React, { useContext } from 'react';
import { DarkModeContext } from './BasePage.js';

// Tailwind CSS is enabled

function Content() {

    const { darkMode } = useContext(DarkModeContext);

    return (
        <div className="p-4">
            <h2 className="text-2xl">Welcome to Aruki!</h2>
            <p className="mt-4">Aruki is a simple web application that allows you to keep track of the places you've visited. It's a great way to remember the places you've been to and share your experiences with others.</p>
            <p className="mt-4">To get started, click on the "Add Place" button to add a new place to your list. You can also click on the "View Places" button to see all the places you've visited.</p>
            <p className="mt-4">Enjoy using Aruki!</p>
        </div>
    );
}

export default Content;