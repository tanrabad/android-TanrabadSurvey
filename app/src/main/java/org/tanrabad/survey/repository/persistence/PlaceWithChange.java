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

package org.tanrabad.survey.repository.persistence;

import org.tanrabad.survey.entity.Place;

import java.util.UUID;

public class PlaceWithChange extends Place {

    private final int changeStatus;

    public PlaceWithChange(UUID buildingId, String placeName, int changeStatus) {
        super(buildingId, placeName);
        this.changeStatus = changeStatus;
    }

    public boolean isNotSynced() {
        return changeStatus != ChangedStatus.UNCHANGED;
    }
}