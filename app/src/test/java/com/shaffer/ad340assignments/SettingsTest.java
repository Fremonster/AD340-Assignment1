package com.shaffer.ad340assignments;

import com.shaffer.ad340assignments.entity.Settings;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SettingsTest {

    private Settings mySettings;

    @Before
    public void setUp() {
        mySettings = new Settings();
    }

    @After
    public void teardown() {
        mySettings = null;
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

    @Test
    public void testDefaultDailyReminderTime() {
        assertEquals("00:00", mySettings.getDailyMatchReminderTime());
    }

    @Test
    public void testSetMinAge() {
        mySettings.setMinAge("20");
        assertEquals("20", mySettings.getMinAge());
    }

    @Test
    public void testSetMaxDistance() {
        mySettings.setMaxDistance("15");
        assertEquals("15", mySettings.getMaxDistance());
    }
    @Test
    public void testSetMaxAge() {
        mySettings.setMaxAge("30");
        assertEquals("30", mySettings.getMaxAge());
    }

    @Test
    public void testSetIsPublic() {
        mySettings.setPublic(true);
        assertEquals(true, mySettings.isPublic());
    }

    @Test
    public void testSetDailyReminderTime() {
        mySettings.setDailyMatchReminderTime("12:30");
        assertEquals("12:30", mySettings.getDailyMatchReminderTime());
    }

    @Test
    public void testSetId() {
        mySettings.setId(1);
        assertEquals(1, mySettings.getId());
    }

    @Test
    public void testSetGender() {
        mySettings.setGender("male");
        assertEquals("male", mySettings.getGender());
    }

}
