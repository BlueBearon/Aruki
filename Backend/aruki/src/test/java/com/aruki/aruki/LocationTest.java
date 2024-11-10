package com.aruki.aruki;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {

    @Test
    public void testLocationConstructorWithWalkingDistance() {
        Location location = new Location("Place", "ID123", "123 Street", "10km", "5km", "Category");
        assertEquals("Place", location.getName());
        assertEquals("ID123", location.getGoogleMapsUniqueID());
        assertEquals("123 Street", location.getAddress());
        assertEquals("10km", location.getDistance());
        assertEquals("5km", location.getWalkingDistance());
        assertEquals("Category", location.getCategory());
    }

    @Test
    public void testLocationConstructorWithoutWalkingDistance() {
        Location location = new Location("Place", "ID123", "123 Street", "10km", "Category");
        assertEquals("Place", location.getName());
        assertEquals("ID123", location.getGoogleMapsUniqueID());
        assertEquals("123 Street", location.getAddress());
        assertEquals("10km", location.getDistance());
        assertNull(location.getWalkingDistance());
        assertEquals("Category", location.getCategory());
    }

    @Test
    public void testSettersAndGetters() {
        Location location = new Location("Place", "ID123", "123 Street", "10km", "5km", "Category");
        location.setName("New Place");
        location.setGoogleMapsUniqueID("ID456");
        location.setAddress("456 Avenue");
        location.setDistance("20km");
        location.setWalkingDistance("10km");
        location.setCategory("New Category");

        assertEquals("New Place", location.getName());
        assertEquals("ID456", location.getGoogleMapsUniqueID());
        assertEquals("456 Avenue", location.getAddress());
        assertEquals("20km", location.getDistance());
        assertEquals("10km", location.getWalkingDistance());
        assertEquals("New Category", location.getCategory());
    }

    @Test
    public void testToString() {
        Location location = new Location("Place", "ID123", "123 Street", "10km", "5km", "Category");
        String expected = "{\"name\":\"Place\",\"googleMapsUniqueID\":\"ID123\",\"address\":\"123 Street\",\"distance\":\"10km\",\"walkingDistance\":\"5km\",\"category\":\"Category\"}";
        assertEquals(expected, location.toString());
    }
}
