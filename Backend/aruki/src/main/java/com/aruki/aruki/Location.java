package com.aruki.aruki;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        return "{\"name\":\"" + name + "\",\"address\":\"" + address + "\",\"types\":" + types + ",\"distance\":" + (distance != null ? "\"" + distance + "\"" : null) + "}";
    }
    
}
