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

package org.tanrabad.survey.domain.place;

import java.util.UUID;

import org.tanrabad.survey.entity.Place;

public class PlaceController {

    private final PlaceRepository placeRepository;
    private final PlacePresenter placePresenter;

    public PlaceController(PlaceRepository placeRepository, PlacePresenter placePresenter) {
        this.placeRepository = placeRepository;
        this.placePresenter = placePresenter;
    }

    public void showPlace(UUID placeUuid) {
        Place placeByUuid = placeRepository.findByUuid(placeUuid);
        if (placeByUuid == null) {
            placePresenter.alertPlaceNotFound();
        } else {
            placePresenter.displayPlace(placeByUuid);
        }
    }
}
