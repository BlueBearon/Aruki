package com.aruki.aruki;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {

    static final String[] types = {"Category1", "Category2"};
    static final String[] types2 = {"New Category1", "New Category2"};

    @Test
    public void testLocationConstructorWithDistance() {
        Location location = new Location("Place", "123 Street", types, "10km");
        assertEquals("Place", location.getName());
        assertEquals("123 Street", location.getAddress());
        assertEquals(Arrays.asList("Category1", "Category2"), location.getTypes());
        assertEquals("10km", location.getDistance());
    }

    @Test
    public void testLocationConstructorWithoutDistance() {
        Location location = new Location("Place", "123 Street", types);
        assertEquals("Place", location.getName());
        assertEquals("123 Street", location.getAddress());
        assertEquals(Arrays.asList("Category1", "Category2"), location.getTypes());
        assertNull(location.getDistance());
    }

    @Test
    public void testSettersAndGetters() {
        Location location = new Location("Place", "123 Street", types, "10km");
        location.setName("New Place");
        location.setAddress("456 Avenue");
        location.setTypes(types2);
        location.setDistance("20km");

        assertEquals("New Place", location.getName());
        assertEquals("456 Avenue", location.getAddress());
        assertEquals(Arrays.asList("New Category1", "New Category2"), location.getTypes());
        assertEquals("20km", location.getDistance());
    }

    @Test
    public void testToString() {
        Location location = new Location("Place", "123 Street", types, "10km");
        String expected = "{\"name\":\"Place\",\"address\":\"123 Street\",\"types\":[Category1, Category2],\"distance\":\"10km\"}";
        assertEquals(expected, location.toString());
    }
}
