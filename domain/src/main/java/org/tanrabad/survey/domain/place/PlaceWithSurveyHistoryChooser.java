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

import org.tanrabad.survey.domain.user.UserRepository;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.User;

import java.util.List;

public class PlaceWithSurveyHistoryChooser {
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final PlaceWithSurveyHistoryListPresenter placeWithSurveyStatusChooserPresenter;

    public PlaceWithSurveyHistoryChooser(UserRepository userRepository,
                                         PlaceRepository placeRepository,
                                         PlaceWithSurveyHistoryListPresenter placeWithSurveyStatusChooserPresenter) {

        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
        this.placeWithSurveyStatusChooserPresenter = placeWithSurveyStatusChooserPresenter;
    }

    public void showSurveyPlaceList(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            placeWithSurveyStatusChooserPresenter.alertUserNotFound();
            return;
        }

        List<Place> surveyPlaces = placeRepository.findRecent(user);

        if (surveyPlaces != null && !surveyPlaces.isEmpty()) {
            placeWithSurveyStatusChooserPresenter.displaySurveyPlaceList(surveyPlaces);
        } else {
            placeWithSurveyStatusChooserPresenter.displaySurveyPlacesNotFound();
        }
    }
}
