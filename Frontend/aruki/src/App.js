import './App.css';
import BasePage from './BasePage.js';
import { Route, BrowserRouter as Router } from 'react-router-dom';

// Tailwind CSS is enabled

/**
 * The main App component that sets up the application layout.
 * 
 * @component
 * @returns {JSX.Element} The rendered component.
 */
function App() {
  return (
    <div className = "flex flex-col h-screen w-screen bg-red-600">
        <BasePage />
    </div>
  );
}

export default App;