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
import android.support.test.rule.ActivityTestRule;
import org.junit.Before;
import org.junit.Test;
import th.or.nectec.tanrabad.survey.R;
import th.or.nectec.tanrabad.survey.TanrabadEspressoTestBase;

import java.util.UUID;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class AddBuildingTypeVillageCommunityTest extends TanrabadEspressoTestBase {
    public ActivityTestRule<BuildingAddActivity> mActivityTestRule
            = new ActivityTestRule<>(BuildingAddActivity.class);
    BuildingAddActivity mActivity;

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra("place_uuid_arg", UUID.nameUUIDFromBytes("67UIP".getBytes()).toString());
        mActivity = mActivityTestRule.launchActivity(intent);
    }

    @Test
    public void openAddBuildingTypeVillageCommunityPageShouldFoundBuildingTypeVillageCommunityPage() {
        textDisplayed("เพิ่มอาคาร");
        textDisplayed("ชุมชนกอล์ฟวิว");
        textDisplayed(R.string.save);
        textDisplayed(R.string.house_no);
        textDisplayed(R.string.building_location);
        textDisplayed(R.string.define_building_location);
        onView(withId(R.id.add_marker))
                .check(matches(isDisplayed()));
        onView(withText(R.string.save))
                .perform(click());
        textDisplayed(R.string.please_define_house_no);
        onView(withText(R.string.got_it))
                .perform(click());
    }
}