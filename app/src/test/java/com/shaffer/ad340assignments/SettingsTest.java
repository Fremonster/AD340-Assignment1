package com.shaffer.ad340assignments;

import com.shaffer.ad340assignments.entity.Settings;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class SettingsTest {

    public static Settings mySettings;

    @BeforeClass
    public static void testSetup() {
        mySettings = new Settings();
    }

    // default min age set to 18
    @Test
    public void testDefaultMinAge() {
        assertEquals("18", mySettings.getMinAge());
    }

    // default max age set to 120
    @Test
    public void testDefaultMaxAge() {
        assertEquals("120", mySettings.getMaxAge());
    }

    // default maxDistance set to 10
    @Test
    public void testDefaultMaxDistance() {
        assertEquals("10", mySettings.getMaxDistance());
    }

    // default for isPublic is false
    @Test
    public void testDefaultIsPublicValue() {
        assertEquals(false, mySettings.isPublic());
    }





}
