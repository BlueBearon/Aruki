package com.aruki.aruki;


/**
 * CategoryScore
 * 
 * This class is used to store the score of a category of places, as well as the number of places that are close, medium, and far away.
 * 
 * 
 * String category - The category of the places
 * double score - The score of the category
 * int closePlaces - The number of places that are close
 * int mediumPlaces - The number of places that are medium
 * int farPlaces - The number of places that are far
 * 
 * @version 1.0
 * @author Chase Packer
 */
public class CategoryScore
{
    public String category;
    public double score;
    public int closePlaces;
    public int mediumPlaces;
    public int farPlaces;

    public CategoryScore(String category, double score, int closePlaces, int mediumPlaces, int farPlaces)
    {
        this.category = category;
        this.score = score;
        this.closePlaces = closePlaces;
        this.mediumPlaces = mediumPlaces;
        this.farPlaces = farPlaces;
    }

    public double getScore()
    {
        return this.score;
    }

    public int getClosePlaces()
    {
        return this.closePlaces;
    }

    public int getMediumPlaces()
    {
        return this.mediumPlaces;
    }

    public int getFarPlaces()
    {
        return this.farPlaces;
    }


    public void setScore(double score)
    {
        this.score = score;
    }

    public void setClosePlaces(int closePlaces)
    {
        this.closePlaces = closePlaces;
    }

    public void setMediumPlaces(int mediumPlaces)
    {
        this.mediumPlaces = mediumPlaces;
    }

    public void setFarPlaces(int farPlaces)
    {
        this.farPlaces = farPlaces;
    }

    public String toString()
    {
        return "{\"category\":\"" + category + "\",\"score\":\"" + score + "\",\"closePlaces\":\"" + closePlaces + "\",\"mediumPlaces\":\"" + mediumPlaces + "\",\"farPlaces\":\"" + farPlaces + "\"}";
    }
}
