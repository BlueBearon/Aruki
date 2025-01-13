import React, {useContext} from 'react';
import { DarkModeContext } from './BasePage.js';
import { CategoryScore } from './CategoryScore.js';

// Tailwind CSS is enabled

/**
 * 
 * @param { CategoryScore } categoryScore
 * @returns 
 */
function CategoryScoreCard( { categoryScore })
{

    let categoryName = categoryScore.category;
    let score = categoryScore.score;
    let closePlaces = categoryScore.closePlaces;
    let mediumPlaces = categoryScore.mediumPlaces;
    let farPlaces = categoryScore.farPlaces;


    return (
        <div className="flex flex-col items-center justify-center">
            <h1 className="text-5xl font-bold">{categoryName}</h1>
            <div className="mt-4">
                <p className="text-lg">Score: {score}</p>
                <p className="text-lg">Close: {closePlaces}</p>
                <p className="text-lg">Medium: {mediumPlaces}</p>
                <p className="text-lg">Far: {farPlaces}</p>
            </div>
        </div>
    );

}

export default CategoryScoreCard;