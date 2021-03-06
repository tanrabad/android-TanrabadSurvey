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

package org.tanrabad.survey.service.json;

import android.support.annotation.Nullable;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import java.util.UUID;
import org.joda.time.DateTimeZone;
import org.tanrabad.survey.domain.place.PlaceRepository;
import org.tanrabad.survey.domain.user.UserRepository;
import org.tanrabad.survey.entity.Building;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;
import org.tanrabad.survey.utils.time.ThaiDateTimeConverter;

@JsonObject
public class JsonBuilding {

    @JsonField(name = "building_id", typeConverter = UuidTypeConverter.class)
    public UUID buildingId;

    @JsonField(name = "place_id", typeConverter = UuidTypeConverter.class)
    public UUID placeId;

    @JsonField(name = "place_type_id")
    public int placeTypeId;

    @JsonField(name = "name")
    public String buildingName;

    @JsonField
    public GeoJsonPoint location;

    @JsonField(name = "updated_by")
    public String updatedBy;

    @JsonField(name = "update_timestamp")
    public String updateTime;

    @JsonField
    public boolean active = true;

    public static JsonBuilding parse(Building building) {
        JsonBuilding jsonBuilding = new JsonBuilding();
        jsonBuilding.buildingId = building.getId();
        jsonBuilding.placeId = building.getPlace().getId();
        jsonBuilding.buildingName = building.getName();
        jsonBuilding.placeTypeId = building.getPlace().getType();
        jsonBuilding.location = GeoJsonPoint.parse(building.getLocation());
        jsonBuilding.updatedBy = building.getUpdateBy();
        jsonBuilding.updateTime = building.getUpdateTimestamp().withZone(DateTimeZone.UTC).toString();
        return jsonBuilding;
    }

    @Nullable
    public Building getEntity(PlaceRepository placeRepository, UserRepository userRepository) {
        Building building = new Building(buildingId, buildingName);
        Place place = placeRepository.findByUuid(placeId);
        if (place == null)
            return null;
        building.setPlace(place);
        Location location = this.location == null ? null : this.location.getEntity();
        building.setLocation(location);
        building.setUpdateBy(updatedBy);
        building.setUpdateTimestamp(ThaiDateTimeConverter.convert(updateTime).toString());
        return building;
    }
}
