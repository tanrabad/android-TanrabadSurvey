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

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.tanrabad.survey.entity.Building;
import org.tanrabad.survey.entity.Survey;
import org.tanrabad.survey.entity.SurveyDetail;
import org.tanrabad.survey.entity.User;
import org.tanrabad.survey.entity.lookup.ContainerType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(JUnit4.class)
public class SurveyEqualOrNotEqualTest {

    private static final int RESIDENT = 2250;
    private final User user1 = User.fromUsername("Tom50");
    private final Building building1 = Building.withName("โรงเรียนเซนต์เมรี่");
    private final Survey survey1 = new Survey(UUID.randomUUID(), user1, building1);

    @Test
    public void surveyWithDifferentUserMustNotEquals() {
        Survey survey2 = new Survey(UUID.randomUUID(), User.fromUsername("Janie09"), building1);
        assertNotEquals(survey1, survey2);
    }

    @Test
    public void surveyWithDifferentBuildingMustNotEquals() {
        Survey survey2 = new Survey(UUID.randomUUID(), user1, Building.withName("โรงเรียนดอนบอสโก"));
        assertNotEquals(survey1, survey2);
    }

    @Test
    public void surveyWithDifferentStartTimestampMustNotEquals() {
        survey1.setStartTimestamp(DateTime.parse("2014-09-16"));
        Survey survey2 = new Survey(UUID.randomUUID(), user1, building1);
        survey2.setStartTimestamp(DateTime.parse("2015-09-28"));
        assertNotEquals(survey1, survey2);
    }

    @Test
    public void surveyWithDifferentResidentMustNotEquals() {
        survey1.setResidentCount(RESIDENT);
        Survey survey2 = new Survey(UUID.randomUUID(), user1, building1);
        survey2.setResidentCount(3000);
        assertNotEquals(survey1, survey2);
    }

    @Test
    public void surveyWithDifferentIndoorDetailMustNotEquals() {
        survey1.setIndoorDetail(surveyDetails1());
        Survey survey2 = new Survey(UUID.randomUUID(), user1, building1);
        survey2.setIndoorDetail(surveyDetails2());
        assertNotEquals(survey1, survey2);
    }

    private List<SurveyDetail> surveyDetails1() {
        ContainerType containerType1 = new ContainerType(1, "น้ำใช้");
        ContainerType containerType2 = new ContainerType(8, "กากใบพืช");
        ContainerType containerType3 = new ContainerType(7, "ยางรถยนต์เก่า");

        SurveyDetail detail1 = new SurveyDetail(UUID.randomUUID(), containerType1, 5, 1);
        SurveyDetail detail2 = new SurveyDetail(UUID.randomUUID(), containerType2, 4, 0);
        SurveyDetail detail3 = new SurveyDetail(UUID.randomUUID(), containerType3, 4, 2);

        ArrayList<SurveyDetail> detailArrayList = new ArrayList<>();
        detailArrayList.add(detail1);
        detailArrayList.add(detail2);
        detailArrayList.add(detail3);
        return detailArrayList;
    }

    private List<SurveyDetail> surveyDetails2() {
        ContainerType containerType1 = new ContainerType(1, "น้ำใช้");
        ContainerType containerType2 = new ContainerType(8, "กากใบพืช");
        ContainerType containerType3 = new ContainerType(7, "ยางรถยนต์เก่า");

        SurveyDetail detail1 = new SurveyDetail(UUID.randomUUID(), containerType1, 9, 8);
        SurveyDetail detail2 = new SurveyDetail(UUID.randomUUID(), containerType2, 7, 3);
        SurveyDetail detail3 = new SurveyDetail(UUID.randomUUID(), containerType3, 2, 1);

        ArrayList<SurveyDetail> detailArrayList = new ArrayList<>();
        detailArrayList.add(detail1);
        detailArrayList.add(detail2);
        detailArrayList.add(detail3);
        return detailArrayList;
    }

    @Test
    public void surveyWithDifferentOutdoorDetailMustNotEquals() {
        survey1.setOutdoorDetail(surveyDetails1());
        Survey survey2 = new Survey(UUID.randomUUID(), user1, building1);
        survey2.setOutdoorDetail(surveyDetails2());
        assertNotEquals(survey1, survey2);
    }

    @Test
    public void surveyWithTheSameAllFieldMustEquals() {
        survey1.setStartTimestamp(DateTime.now());
        survey1.setResidentCount(RESIDENT);
        survey1.setIndoorDetail(surveyDetails1());
        survey1.setOutdoorDetail(surveyDetails2());
        Survey survey2 = new Survey(UUID.randomUUID(), user1, building1);
        survey2.setStartTimestamp(survey1.getStartTimestamp());
        survey2.setResidentCount(survey1.getResidentCount());
        survey2.setIndoorDetail(survey1.getIndoorDetail());
        survey2.setOutdoorDetail(survey1.getOutdoorDetail());
        assertEquals(survey1, survey2);
    }
}
