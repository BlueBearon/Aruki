package com.aruki.aruki;

import java.util.List;

/**
 * The {@code ScoreResponse} class represents the walkability score of a location,
 * along with the scores of each category of places.
 * <p>
 * This class is used to encapsulate the scoring information for a location, which can be useful in
 * various contexts such as ranking, filtering, or displaying walkability data.
 * </p>
 * <p>
 * Each {@code ScoreResponse} object contains:
 * <ul>
 *   <li>A walkability score ({@code double})</li>
 *   <li>A list of category scores ({@code List<CategoryScore>})</li>
 * </ul>
 * </p>
 * <p>
 * This class provides getter and setter methods for each field, allowing for
 * easy manipulation and retrieval of the data. Additionally, it overrides the
 * {@code toString} method to provide a JSON-like string representation of the object.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     List<CategoryScore> categoryScores = new ArrayList<>();
 *     categoryScores.add(new CategoryScore("Food", 85.5, 10, 5, 2));
 *     ScoreResponse scoreResponse = new ScoreResponse(90.0, categoryScores);
 *     System.out.println(scoreResponse);
 * </pre>
 * </p>
 * <p>
 * This class can be used in conjunction with other classes that require walkability
 * scoring information, such as ranking algorithms, data visualization tools, or
 * recommendation systems.
 * </p>
 * 
 * @see java.lang.String
 * @see java.util.List
 */
public class ScoreResponse {

    private double walkabilityScore;
    private List<CategoryScore> categoryScores;

    /**
     * Constructs a ScoreResponse with the specified walkability score and category scores.
     *
     * @param walkabilityScore the walkability score of the location
     * @param categoryScores the scores of each category of places
     */
    public ScoreResponse(double walkabilityScore, List<CategoryScore> categoryScores) {
        this.walkabilityScore = walkabilityScore;
        this.categoryScores = categoryScores;
    }

    /**
     * Constructs a ScoreResponse with the specified walkability score.
     *
     * @param walkabilityScore the walkability score of the location
     */
    public ScoreResponse(double walkabilityScore) {
        this.walkabilityScore = walkabilityScore;
        this.categoryScores = null;
    }

    /**
     * Constructs a ScoreResponse with default values.
     */
    public ScoreResponse() {
        this.walkabilityScore = 0;
        this.categoryScores = null;
    }

    /**
     * Returns the walkability score of the location.
     *
     * @return the walkability score of the location
     */
    public double getWalkabilityScore() {
        return this.walkabilityScore;
    }

    /**
     * Sets the walkability score of the location.
     *
     * @param walkabilityScore the new walkability score of the location
     */
    public void setWalkabilityScore(double walkabilityScore) {
        this.walkabilityScore = walkabilityScore;
    }

    /**
     * Adds to the walkability score of the location.
     *
     * @param walkabilityScore the value to add to the walkability score
     */
    public void addToWalkabilityScore(double walkabilityScore) {
        this.walkabilityScore += walkabilityScore;
    }

    /**
     * Returns the category scores of the location.
     *
     * @return the category scores of the location
     */
    public List<CategoryScore> getCategoryScores() {
        return this.categoryScores;
    }

    /**
     * Sets the category scores of the location.
     *
     * @param categoryScores the new category scores of the location
     */
    public void setCategoryScores(List<CategoryScore> categoryScores) {
        this.categoryScores = categoryScores;
    }

    /**
     * Adds a category score to the list of category scores.
     *
     * @param categoryScore the category score to add
     */
    public void addCategoryScore(CategoryScore categoryScore) {
        this.categoryScores.add(categoryScore);
    }

    /**
     * Adds a list of category scores to the existing list of category scores.
     *
     * @param categoryScores the list of category scores to add
     */
    public void addCategoryScores(List<CategoryScore> categoryScores) {
        this.categoryScores.addAll(categoryScores);
    }

    /**
     * Returns a string representation of the ScoreResponse in JSON format.
     *
     * @return a string representation of the ScoreResponse
     */
    @Override
    public String toString() {
        return "{ \"walkability\": \"" + this.walkabilityScore + "\", \"scores\": " + this.categoryScores.toString() + " }";
    }
}
