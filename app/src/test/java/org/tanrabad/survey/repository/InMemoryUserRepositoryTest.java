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

package org.tanrabad.survey.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tanrabad.survey.entity.User;

import static org.junit.Assert.assertEquals;


public class InMemoryUserRepositoryTest {

    private static final String DPC_USER = "dpc-user";
    private static InMemoryUserRepository userRepository = InMemoryUserRepository.getInstance();


    @Before
    public void setUp() {
        userRepository.save(getDpcUser());
    }

    private static User getDpcUser() {
        User dpcUser = new User(DPC_USER);
        dpcUser.setFirstname("ซาร่า");
        dpcUser.setLastname("คิดส์");
        dpcUser.setEmail("sara.k@gmail.com");
        return dpcUser;
    }

    @After
    public void tearDown() {
        userRepository.delete(getDpcUser());
    }

    @Test
    public void testEveryCallGetInstanceMustGotSameInstance() throws Exception {
        assertEquals(userRepository, InMemoryUserRepository.getInstance());
    }

    @Test
    public void testFindUserName() throws Exception {
        assertEquals(getDpcUser(), userRepository.findByUsername(DPC_USER));
    }

    @Test
    public void testUpdate() throws Exception {
        User user = new User(DPC_USER);
        userRepository.update(user);
        assertEquals(user, userRepository.findByUsername(DPC_USER));
    }
}
