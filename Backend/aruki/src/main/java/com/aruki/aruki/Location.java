package com.aruki.aruki;

import java.util.Arrays;

/**
 * Represents a location as returned by the Google Maps API.
 */
public class Location {

    private String name;
    private String address;
    private String[] types;
    private String distance;

    /**
     * Constructs a Location with the specified name, address, types, and distance.
     *
     * @param name the name of the location
     * @param address the address of the location
     * @param types the types of the location
     * @param distance the distance to the location
     */
    public Location(String name, String address, String[] types, String distance) {
        this.name = name;
        this.address = address;
        this.types = types;
        this.distance = distance;
    }

    /**
     * Constructs a Location with the specified name, address, and types.
     *
     * @param name the name of the location
     * @param address the address of the location
     * @param types the types of the location
     */
    public Location(String name, String address, String[] types) {
        this.name = name;
        this.address = address;
        this.types = types;
        this.distance = null;
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
     * Returns the address of the location.
     *
     * @return the address of the location
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the types of the location.
     *
     * @return the types of the location
     */
    public String[] getTypes() {
        return types;
    }

    /**
     * Returns the distance to the location.
     *
     * @return the distance to the location, or null if not set
     */
    public String getDistance() {
        return distance;
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
     * Sets the address of the location.
     *
     * @param address the new address of the location
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets the types of the location.
     *
     * @param types the new types of the location
     */
    public void setTypes(String[] types) {
        this.types = types;
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
     * Returns a string representation of the location.
     *
     * @return a string representation of the location
     */
    @Override
    public String toString() {

        String typesString = Arrays.toString(types);

        return "{\"name\":\"" + name + "\",\"address\":\"" + address + "\",\"types\":" + typesString + ",\"distance\":" + (distance != null ? "\"" + distance + "\"" : null) + "}";
    }


    /**
     * Parses the distance to a double. The distance is expected to be in the format "123 km" or "123 m" or "123 mi" or "123 ft".
     * 
     * @param distance
     * @return
     */
    public static double parseDistance(String distance) {
        
        // Remove trailing " km" or " m" or " mi" or etc.

        if (distance == null) {
            return 0;
        }

        distance = distance.trim();

        if (distance.endsWith(" km")) {
            distance = distance.substring(0, distance.length() - 3);
        } else if (distance.endsWith(" m")) {
            distance = distance.substring(0, distance.length() - 2);
        } else if (distance.endsWith(" mi")) {
            distance = distance.substring(0, distance.length() - 3);
        } else if (distance.endsWith(" ft")) {
            distance = distance.substring(0, distance.length() - 3);
        } else if (distance.endsWith(" miles")) {
            distance = distance.substring(0, distance.length() - 6);
        } else if (distance.endsWith(" kilometers")) {
            distance = distance.substring(0, distance.length() - 11);
        } else if (distance.endsWith(" meters")) {
            distance = distance.substring(0, distance.length() - 7);
        } else if (distance.endsWith(" feet")) {
            distance = distance.substring(0, distance.length() - 5);
        }

        return Double.parseDouble(distance);
    }


    public boolean equals(Location location) {
        return this.name.equals(location.getName()) && this.address.equals(location.getAddress()) && Arrays.equals(this.types, location.getTypes()) && this.distance.equals(location.getDistance());
    }

    public boolean greaterThan(Location location) {

        if (this.distance == null || location.getDistance() == null) {
            return false;
        }

        return parseDistance(distance) > parseDistance(location.getDistance());
    }

    public boolean lessThan(Location location) {

        if (this.distance == null || location.getDistance() == null) {
            return false;
        }

        return parseDistance(distance) < parseDistance(location.getDistance());
    }

    public boolean greaterThanOrEqualTo(Location location) {

        if (this.distance == null || location.getDistance() == null) {
            return false;
        }

        return parseDistance(distance) >= parseDistance(location.getDistance());
    }

    public boolean lessThanOrEqualTo(Location location) {

        if (this.distance == null || location.getDistance() == null) {
            return false;
        }

        return parseDistance(distance) <= parseDistance(location.getDistance());
    }

    public boolean isNull() {
        return this.name == null && this.address == null && this.types == null && this.distance == null;
    }
    
}
