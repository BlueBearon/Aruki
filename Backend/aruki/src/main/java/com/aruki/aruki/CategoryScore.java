package com.aruki.aruki;

/**
 * The {@code CategoryScore} class represents a score associated with a specific category,
 * along with the number of places categorized as close, medium, and far.
 * <p>
 * This class is used to encapsulate the scoring information for different categories,
 * which can be useful in various contexts such as ranking, filtering, or displaying
 * categorized data.
 * </p>
 * <p>
 * Each {@code CategoryScore} object contains:
 * <ul>
 *   <li>A category name ({@code String})</li>
 *   <li>A score value ({@code double})</li>
 *   <li>The number of close places ({@code int})</li>
 *   <li>The number of medium places ({@code int})</li>
 *   <li>The number of far places ({@code int})</li>
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
 *     CategoryScore categoryScore = new CategoryScore("Food", 85.5, 10, 5, 2);
 *     System.out.println(categoryScore);
 * </pre>
 * </p>
 * <p>
 * This class can be used in conjunction with other classes that require categorized
 * scoring information, such as ranking algorithms, data visualization tools, or
 * recommendation systems.
 * </p>
 * 
 * @see java.lang.String
 * @see java.lang.Double
 * @see java.lang.Integer
 */
public class CategoryScore {

    public String category;
    public double score;
    public int closePlaces;
    public int mediumPlaces;
    public int farPlaces;

    /**
     * Constructs a CategoryScore with the specified category, score, and number of places.
     *
     * @param category the category name
     * @param score the score value
     * @param closePlaces the number of close places
     * @param mediumPlaces the number of medium places
     * @param farPlaces the number of far places
     */
    public CategoryScore(String category, double score, int closePlaces, int mediumPlaces, int farPlaces) {
        this.category = category;
        this.score = score;
        this.closePlaces = closePlaces;
        this.mediumPlaces = mediumPlaces;
        this.farPlaces = farPlaces;
    }

    /**
     * Returns the score value of the category.
     *
     * @return the score value of the category
     */
    public double getScore() {
        return this.score;
    }

    /**
     * Returns the number of close places.
     *
     * @return the number of close places
     */
    public int getClosePlaces() {
        return this.closePlaces;
    }

    /**
     * Returns the number of medium places.
     *
     * @return the number of medium places
     */
    public int getMediumPlaces() {
        return this.mediumPlaces;
    }

    /**
     * Returns the number of far places.
     *
     * @return the number of far places
     */
    public int getFarPlaces() {
        return this.farPlaces;
    }

    /**
     * Sets the score value of the category.
     *
     * @param score the new score value of the category
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * Sets the number of close places.
     *
     * @param closePlaces the new number of close places
     */
    public void setClosePlaces(int closePlaces) {
        this.closePlaces = closePlaces;
    }

    /**
     * Sets the number of medium places.
     *
     * @param mediumPlaces the new number of medium places
     */
    public void setMediumPlaces(int mediumPlaces) {
        this.mediumPlaces = mediumPlaces;
    }

    /**
     * Sets the number of far places.
     *
     * @param farPlaces the new number of far places
     */
    public void setFarPlaces(int farPlaces) {
        this.farPlaces = farPlaces;
    }

    /**
     * Returns a string representation of the CategoryScore in JSON format.
     *
     * @return a string representation of the CategoryScore
     */
    @Override
    public String toString() {
        return "{\"category\":\"" + category + "\",\"score\":\"" + score + "\",\"closePlaces\":\"" + closePlaces + "\",\"mediumPlaces\":\"" + mediumPlaces + "\",\"farPlaces\":\"" + farPlaces + "\"}";
    }
}
