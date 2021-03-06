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

package org.tanrabad.survey.presenter;

import android.support.annotation.DrawableRes;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.lookup.PlaceType;
import org.tanrabad.survey.R;

class BuildingIcon {

    @DrawableRes
    public static int get(Place place) {
        if (place.getType() == PlaceType.VILLAGE_COMMUNITY) {
            return R.mipmap.ic_building_home_black;
        } else {
            return R.mipmap.ic_building_black;
        }
    }

    @DrawableRes
    public static int getWhite(Place place) {
        if (place.getType() == PlaceType.VILLAGE_COMMUNITY) {
            return R.mipmap.ic_building_home_white;
        } else {
            return R.mipmap.ic_building_white;
        }
    }
}
