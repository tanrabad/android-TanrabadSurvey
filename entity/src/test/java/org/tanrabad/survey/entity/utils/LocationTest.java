/*
 * Copyright (c) 2016 NECTEC
 *   National Electronics and Computer Technology Center, Thailand
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tanrabad.survey.entity.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.tanrabad.survey.entity.field.Location;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class LocationTest {
    private static final double DELTA = 0.0001;
    private static final double LAT = 14.078606;
    private static final double LONG = 100.603120;
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    private final Location location = new Location(LAT, LONG);

    @Test
    public void testGetLatitude() {
        assertEquals(LAT, location.getLatitude(), DELTA);
    }

    @Test
    public void testGetLongitude() {
        assertEquals(LONG, location.getLongitude(), DELTA);
    }

    @Test
    public void locationWithDifferentLatitudeMustNotEquals() {
        Location anotherLocation = new Location(15.078606, LONG);

        assertNotEquals(location, anotherLocation);
    }

    @Test
    public void locationWithDifferentLongitudeMustNotEquals() {
        Location anotherLocation = new Location(LAT, 179.603120);

        assertNotEquals(location, anotherLocation);
    }

    @Test
    public void locationTheSameLatitudeLongitudeMustEquals() {
        Location sameLocation = new Location(LAT, LONG);

        assertEquals(location, sameLocation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutOfRangeLatitude() throws Exception {
        new Location(-90.1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutOfRangeLongitude() throws Exception {
        new Location(0, 180.1f);
    }

    @Test public void testPlaceInBoundary() {
        Location testLocation = new Location(5, 5);
        Location minimumLocation = new Location(0, 0);
        Location maximumLocation = new Location(10, 10);
        assertTrue(testLocation.isLocationInsideBoundary(minimumLocation, maximumLocation));
    }

    @Test public void testPlaceOutBoundary() {
        Location testLocation = new Location(3, 12);
        Location minimumLocation = new Location(0, 0);
        Location maximumLocation = new Location(10, 10);
        assertFalse(testLocation.isLocationInsideBoundary(minimumLocation, maximumLocation));
    }

    @Test public void testPlaceAtLatBoundOfBoundary() {
        Location testLocation = new Location(0, 3);
        Location minimumLocation = new Location(0, 0);
        Location maximumLocation = new Location(10, 10);
        assertTrue(testLocation.isLocationInsideBoundary(minimumLocation, maximumLocation));
    }

    @Test public void testPlaceAtLngBoundOfBoundary() {
        Location testLocation = new Location(5, 10);
        Location minimumLocation = new Location(0, 0);
        Location maximumLocation = new Location(10, 10);
        assertTrue(testLocation.isLocationInsideBoundary(minimumLocation, maximumLocation));
    }
}
