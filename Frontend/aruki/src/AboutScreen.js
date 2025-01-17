import React from 'react';
import { useContext } from 'react';
import { DarkModeContext } from './BasePage.js';
import { getImage } from './ImageRetrieval';
import clsx from 'clsx';

/**
 * AboutScreen component displays information about the Aruki project,
 * including its purpose, the developer's personal story, and contact information.
 *
 * @component
 * @returns {JSX.Element} The rendered component.
 */
function AboutScreen() {


    const { darkMode } = useContext(DarkModeContext);




  return (
    <div className={clsx("flex flex-col flex-grow m-10", darkMode ? "bg-gray-800 text-white" : "bg-white")}>
      {/* Header Image */}
      {/* Header Image */}
      <div className="flex flex-col justify-center items-center w-full h-72 mb-6 bg-cover bg-center" style={{ backgroundImage: `url(${getImage('about')})` }}>
        {/* Overlay */}
        <div className="inset-0 bg-black opacity-40"></div>

        {/* Content */}
        <h1 className="text-6xl font-bold text-white">About Aruki</h1>
      </div>

      {/* Project Title */}
      <h1 className="text-3xl font-bold mb-4">About Aruki</h1>

      {/* Project Description */}
      <p className="text-lg mb-6">
        Aruki is designed to advocate for the development of walkable cities and better urban planning by showcasing how walkable
        your current location or any other location is. By providing walkability scores and showcasing nearby locations across various
        categories, it helps users see the benefits of more walkable urban environments.
      </p>

      {/* Personal Story */}
      <h2 className="text-2xl font-semibold mb-4">Why I Created Aruki</h2>
      <p className="text-lg mb-6">
        After spending several months studying abroad in Japan, I couldn't help but compare the vibrant, walkable urban areas in Japan
        to the sprawling, car-dependent suburban landscapes I was used to in the United States. This contrast sparked a passion for
        advocating for better urban planning and more walkable cities. Aruki was born from this experience, with the aim of helping
        others understand and explore the walkability of their surroundings.
      </p>

      {/* Sole Developer Info */}
      <h2 className="text-2xl font-semibold mb-4">About the Developer</h2>
      <p className="text-lg mb-6">
        Aruki was developed by <strong>Chase Packer</strong>, a passionate advocate for sustainable urban development and walkability.
      </p>
      <p className="text-lg">
        You can reach me via email: <a href="mailto:packerchase@hotmail.com" className="text-blue-500 hover:underline">packerchase@hotmail.com</a>
      </p>
    </div>
  );
}

export default AboutScreen;
