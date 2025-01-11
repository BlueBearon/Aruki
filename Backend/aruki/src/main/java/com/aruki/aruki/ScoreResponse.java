package com.aruki.aruki;

import java.util.List;

/**
 * ScoreResponse
 * 
 * This class is used to store the walkability score of a location, as well as the score of each category of places.
 * 
 * double walkabilityScore - The walkability score of the location
 * List<CategoryScore> categoryScores - The scores of each category of places
 */
public class ScoreResponse {

    private double walkabilityScore;
    private List<CategoryScore> categoryScores;


    public ScoreResponse(double walkabilityScore, List<CategoryScore> categoryScores)
    {
        this.walkabilityScore = walkabilityScore;
        this.categoryScores = categoryScores;
    }

    public ScoreResponse(double walkabilityScore)
    {
        this.walkabilityScore = walkabilityScore;
        this.categoryScores = null;
    }

    public ScoreResponse()
    {
        this.walkabilityScore = 0;
        this.categoryScores = null;
    }

    public double getWalkabilityScore()
    {
        return this.walkabilityScore;
    }

    public void setWalkabilityScore(double walkabilityScore)
    {
        this.walkabilityScore = walkabilityScore;
    }

    public void addToWalkabilityScore(double walkabilityScore)
    {
        this.walkabilityScore += walkabilityScore;
    }

    public List<CategoryScore> getCategoryScores()
    {
        return this.categoryScores;
    }

    public void setCategoryScores(List<CategoryScore> categoryScores)
    {
        this.categoryScores = categoryScores;
    }

    public void addCategoryScore(CategoryScore categoryScore)
    {
        this.categoryScores.add(categoryScore);
    }

    public void addCategoryScores(List<CategoryScore> categoryScores)
    {
        this.categoryScores.addAll(categoryScores);
    }


    public String toString()
    {
        //JSON format
        return "{ \"walkability\": \"" + this.walkabilityScore + "\", \"scores\": " + this.categoryScores.toString() + " }";
        
    }

    
}
