package com.aruki.aruki;

import java.util.Arrays;

/**
 * The {@code Location} class represents a location as returned by the Google Maps API,
 * including its name, address, types, and distance.
 * <p>
 * This class is used to encapsulate the location information, which can be useful in
 * various contexts such as mapping, navigation, or displaying location data.
 * </p>
 * <p>
 * Each {@code Location} object contains:
 * <ul>
 *   <li>A name ({@code String})</li>
 *   <li>An address ({@code String})</li>
 *   <li>Types of the location ({@code String[]})</li>
 *   <li>The distance to the location ({@code String})</li>
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
 *     Location location = new Location("Central Park", "New York, NY", new String[]{"park", "tourist_attraction"}, "5 km");
 *     System.out.println(location);
 * </pre>
 * </p>
 * <p>
 * This class can be used in conjunction with other classes that require location
 * information, such as mapping applications, navigation systems, or location-based services.
 * </p>
 * 
 * @see java.lang.String
 * @see java.util.Arrays
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
     * @param distance the distance string to parse
     * @return the parsed distance as a double
     */
    public static double parseDistance(String distance) {
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

    /**
     * Compares this location to another location for equality.
     *
     * @param location the location to compare to
     * @return {@code true} if the locations are equal, {@code false} otherwise
     */
    public boolean equals(Location location) {
        return this.name.equals(location.getName()) && this.address.equals(location.getAddress()) && Arrays.equals(this.types, location.getTypes()) && this.distance.equals(location.getDistance());
    }

    /**
     * Compares the distance of this location to another location for equality.
     *
     * @param location the location to compare to
     * @return {@code true} if the distances are equal, {@code false} otherwise
     */
    public boolean distanceEquals(Location location) {
        if (this.distance == null || location.getDistance() == null) {
            return false;
        }
        return parseDistance(distance) == parseDistance(location.getDistance());
    }

    /**
     * Compares the distance of this location to another location for inequality.
     *
     * @param location the location to compare to
     * @return {@code true} if the distances are not equal, {@code false} otherwise
     */
    public boolean distanceNotEquals(Location location) {
        if (this.distance == null || location.getDistance() == null) {
            return false;
        }
        return parseDistance(distance) != parseDistance(location.getDistance());
    }

    /**
     * Checks if the distance of this location is greater than another location.
     *
     * @param location the location to compare to
     * @return {@code true} if this location's distance is greater, {@code false} otherwise
     */
    public boolean greaterThan(Location location) {
        if (this.distance == null || location.getDistance() == null) {
            return false;
        }
        return parseDistance(distance) > parseDistance(location.getDistance());
    }

    /**
     * Checks if the distance of this location is less than another location.
     *
     * @param location the location to compare to
     * @return {@code true} if this location's distance is less, {@code false} otherwise
     */
    public boolean lessThan(Location location) {
        if (this.distance == null || location.getDistance() == null) {
            return false;
        }
        return parseDistance(distance) < parseDistance(location.getDistance());
    }

    /**
     * Checks if the distance of this location is greater than or equal to another location.
     *
     * @param location the location to compare to
     * @return {@code true} if this location's distance is greater than or equal, {@code false} otherwise
     */
    public boolean greaterThanOrEqualTo(Location location) {
        if (this.distance == null || location.getDistance() == null) {
            return false;
        }
        return parseDistance(distance) >= parseDistance(location.getDistance());
    }

    /**
     * Checks if the distance of this location is less than or equal to another location.
     *
     * @param location the location to compare to
     * @return {@code true} if this location's distance is less than or equal, {@code false} otherwise
     */
    public boolean lessThanOrEqualTo(Location location) {
        if (this.distance == null || location.getDistance() == null) {
            return false;
        }
        return parseDistance(distance) <= parseDistance(location.getDistance());
    }

    /**
     * Checks if all fields of this location are null.
     *
     * @return {@code true} if all fields are null, {@code false} otherwise
     */
    public boolean isNull() {
        return this.name == null && this.address == null && this.types == null && this.distance == null;
    }
}
