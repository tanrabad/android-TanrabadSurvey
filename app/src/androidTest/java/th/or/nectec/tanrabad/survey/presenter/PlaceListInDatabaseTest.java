/*
 * Copyright (c) 2015 NECTEC
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

package th.or.nectec.tanrabad.survey.presenter;

import android.content.Intent;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import th.or.nectec.tanrabad.survey.R;
import th.or.nectec.tanrabad.survey.TanrabadEspressoTestBase;
import th.or.nectec.tanrabad.survey.presenter.PlaceListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class PlaceListInDatabaseTest extends TanrabadEspressoTestBase {
    public ActivityTestRule<PlaceListActivity> mActivityTestRule
            = new ActivityTestRule<>(PlaceListActivity.class);
    PlaceListActivity mActivity;

    @Before
    public void setUp() {
        Intent intent = new Intent();
        mActivity = mActivityTestRule.launchActivity(intent);
    }

    @Test
    public void defaultPageDefineSurveyPlaceShouldFoundListPlaceAllType() {
        onView(allOf(ViewMatchers.withId(R.id.place_count)
                , withContentDescription(R.string.number_place_list_in_database)))
                .check(matches(withText("8 รายการ")));
        onView(withText(R.string.not_define_place_type))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickCancelButtonAtPromptMustStayOn() {
        textDisplayed("หมู่บ้านพาลาซเซตโต้");
        textDisplayed("โรงพยาบาลกรุงเทพ");
        textDisplayed("วัดป่าภูก้อน");
        textDisplayed(R.string.total);

        onView(withText("หมู่บ้านพาลาซเซตโต้"))
                .perform(click());
        textDisplayed(R.string.start_survey);
        textDisplayed(R.string.survey);
        textDisplayed("หมู่บ้านพาลาซเซตโต้");
        clickCancelButton();

        textDisplayed(R.string.total);
        onView(withText("โรงพยาบาลกรุงเทพ"))
                .perform(click());
        textDisplayed(R.string.start_survey);
        textDisplayed(R.string.survey);
        textDisplayed("โรงพยาบาลกรุงเทพ");
        clickCancelButton();

        textDisplayed(R.string.total);
        onView(withText("วัดป่าภูก้อน"))
                .perform(click());
        textDisplayed(R.string.start_survey);
        textDisplayed(R.string.survey);
        textDisplayed("วัดป่าภูก้อน");
        clickCancelButton();

        textDisplayed("หมู่บ้านพาลาซเซตโต้");
        textDisplayed("โรงพยาบาลกรุงเทพ");
        textDisplayed("วัดป่าภูก้อน");
        textDisplayed(R.string.total);
    }

    @Test
    public void chooseTypeVillageCommunityShouldFoundListPlaceTypeVillageCommunity() {
        onView(withText(R.string.not_define_place_type))
                .perform(click());
        onView(withText(R.string.village_community))
                .perform(click());
        textDisplayed(R.string.village_community);
        onView(allOf(withId(R.id.place_count), withContentDescription(R.string.number_place_list_in_database)))
                .check(matches(withText("2 รายการ")));
        textDisplayed("หมู่บ้านพาลาซเซตโต้");
        textDisplayed("ชุมชนกอล์ฟวิว");
    }

    @Test
    public void chooseTypeWorshipShouldFoundListPlaceTypeWorship() {
        onView(withText(R.string.not_define_place_type))
                .perform(click());
        onView(withText(R.string.worship))
                .perform(click());
        textDisplayed(R.string.worship);
        onView(allOf(withId(R.id.place_count), withContentDescription(R.string.number_place_list_in_database)))
                .check(matches(withText("2 รายการ")));
        textDisplayed("วัดป่าภูก้อน");
        textDisplayed("โบสถ์เซนต์เมรี่");
    }

    @Test
    public void chooseTypeSchoolShouldFoundListPlaceTypeSchool() {
        onView(withText(R.string.not_define_place_type))
                .perform(click());
        onView(withText(R.string.school))
                .perform(click());
        textDisplayed(R.string.school);
        onView(allOf(withId(R.id.place_count), withContentDescription(R.string.number_place_list_in_database)))
                .check(matches(withText("3 รายการ")));
        textDisplayed("โรงเรียนเซนต์เมรี่");
        textDisplayed("โรงเรียนดอนบอสโก");
        textDisplayed("โรงเรียนอนุบาล");
    }

    @Test
    public void chooseTypeHospitalShouldFoundListPlaceTypeHospital() {
        onView(withText(R.string.not_define_place_type))
                .perform(click());
        onView(withText(R.string.hospital))
                .perform(click());
        textDisplayed(R.string.hospital);
        onView(allOf(withId(R.id.place_count), withContentDescription(R.string.number_place_list_in_database)))
                .check(matches(withText("1 รายการ")));
        textDisplayed("โรงพยาบาลกรุงเทพ");
    }

    @Test
    public void chooseTypeFactoryShouldNotFoundListPlace() {
        onView(withText(R.string.not_define_place_type))
                .perform(click());
        onView(withText(R.string.factory))
                .perform(click());
        textDisplayed(R.string.places_not_found);
        textDisplayed(R.string.factory);
    }
}