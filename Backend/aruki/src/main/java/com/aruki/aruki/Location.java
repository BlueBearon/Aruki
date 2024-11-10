package com.aruki.aruki;

/**
 * Represents a location found using the Google Maps API.
 */
public class Location {

    private String name;
    private String googleMapsUniqueID;
    private String address;
    private String distance;
    private String walkingDistance;
    private String category;
    
    /**
     * Constructs a new Location with the specified name, distance, and category.
     *
     * @param name the name of the location
     * @param googleMapsUniqueID the unique ID of the location
     * @param address the address of the location
     * @param distance the distance to the location
     * @param walkingDistance the walking distance to the location
     * @param category the category of the location
     */
    public Location(String name, String googleMapsUniqueID, String address, String distance, String walkingDistance, String category) {
        this.name = name;
        this.googleMapsUniqueID = googleMapsUniqueID;
        this.address = address;
        this.distance = distance;
        this.walkingDistance = walkingDistance;
        this.category = category;
    }

    //Constructor without walking distance
    /**
     * Constructs a new Location with the specified name, distance, and category.
     * @param name the name of the location
     * @param googleMapsUniqueID the unique ID of the location
     * @param address the address of the location
     * @param distance the distance to the location
     * @param category the category of the location
     */
    public Location(String name, String googleMapsUniqueID, String address, String distance, String category) {
        this.name = name;
        this.googleMapsUniqueID = googleMapsUniqueID;
        this.address = address;
        this.distance = distance;
        this.walkingDistance = null; // Ensure walkingDistance is null
        this.category = category;
    }

    /**
     * Returns the name of the location.
     *
     * @return the name of the location
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the googleMapsUniqueID of the location.
     *
     * @return the googleMapsUniqueID of the location
     */
    public String getGoogleMapsUniqueID() {
        return googleMapsUniqueID;
    }

    /**
     * Returns the address of the location.
     *
     * @return the address of the location
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the distance to the location.
     *
     * @return the distance to the location
     */
    public String getDistance() {
        return distance;
    }

    /**
     * Returns the walking distance to the location.
     *
     * @return the walking distance to the location
     */
    public String getWalkingDistance() {
        return walkingDistance;
    }



    /**
     * Returns the category of the location.
     *
     * @return the category of the location
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the name of the location.
     *
     * @param name the new name of the location
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the googleMapsUniqueID of the location.
     *
     * @param googleMapsUniqueID the new googleMapsUniqueID of the location
     */
    public void setGoogleMapsUniqueID(String googleMapsUniqueID) {
        this.googleMapsUniqueID = googleMapsUniqueID;
    }

    /**
     * Sets the address of the location.
     *
     * @param address the new address of the location
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets the distance to the location.
     *
     * @param distance the new distance to the location
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    /**
     * Sets the walking distance to the location.
     *
     * @param walkingDistance the new walking distance to the location
     */
    public void setWalkingDistance(String walkingDistance) {
        this.walkingDistance = walkingDistance;
    }

    /**
     * Sets the category of the location.
     *
     * @param category the new category of the location
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Returns a string representation of the location in JSON format.
     *
     * @return a string representation of the location
     */
    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name + "\"," +
                "\"googleMapsUniqueID\":\"" + googleMapsUniqueID + "\"," +
                "\"address\":\"" + address + "\"," +
                "\"distance\":\"" + distance + "\"," +
                "\"walkingDistance\":\"" + walkingDistance + "\"," +
                "\"category\":\"" + category + "\"" +
                "}";
    }
}
