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

package th.or.nectec.tanrabad.survey.service;

import android.support.test.InstrumentationRegistry;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class LastUpdatePreferenceTest {


    @Test
    public void testSaveThenGet() throws Exception {
        DateTime saveDateTime = DateTime.parse("2016-01-27T03:59:11.000Z");
        LastUpdatePreference lastUpdatePreference = new LastUpdatePreference(InstrumentationRegistry.getTargetContext(), "test");

        lastUpdatePreference.save(saveDateTime);
        DateTime lastUpdate = lastUpdatePreference.get();

        assertNotNull(lastUpdate);
        assertEquals(saveDateTime.getYear(), lastUpdate.getYear());
        assertEquals(saveDateTime.getDayOfYear(), lastUpdate.getDayOfYear());
        assertEquals(saveDateTime.getHourOfDay(), lastUpdate.getHourOfDay());
    }
}