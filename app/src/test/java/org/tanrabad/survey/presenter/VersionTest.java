/*
 * Copyright (c) 2019 NECTEC
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

package org.tanrabad.survey.presenter;

import org.junit.Test;

import static org.junit.Assert.*;

public class VersionTest {

    @Test
    public void compareTo() {
        Version v1_1_1 = new Version("1.1.1-120");

        assertEquals(0, v1_1_1.compareTo(new Version("1.1.1")));
        assertEquals(-1, v1_1_1.compareTo(new Version("2.1.0")));
        assertEquals(1, v1_1_1.compareTo(new Version("0.1.0")));

        assertEquals(-1, v1_1_1.compareTo(new Version("1.2.0")));
        assertEquals(1, v1_1_1.compareTo(new Version("1.0.0")));

        assertEquals(-1, v1_1_1.compareTo(new Version("1.1.5")));
        assertEquals(1, v1_1_1.compareTo(new Version("1.1.0")));
    }

    @Test
    public void handleVPrefix() {
        Version v2 = new Version("v2.0.0");

        assertEquals(2, v2.major);
        assertEquals(0, v2.minor);
        assertEquals(0, v2.patch);
    }
}
