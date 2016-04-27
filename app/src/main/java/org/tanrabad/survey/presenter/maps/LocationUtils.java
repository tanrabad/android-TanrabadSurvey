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

package org.tanrabad.survey.presenter.maps;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import org.tanrabad.survey.entity.field.Location;


public final class LocationUtils {

    private LocationUtils() {
    }

    public static String convertLocationToJson(Location location) {
        Gson gson = new Gson();
        return gson.toJson(location);
    }

    public static Location convertJsonToLocation(String locationJson) {
        Gson gson = new Gson();
        return gson.fromJson(locationJson, Location.class);
    }

    public static LatLng convertLocationToLatLng(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    public static LatLng convertLocationToLatLng(android.location.Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }
}
