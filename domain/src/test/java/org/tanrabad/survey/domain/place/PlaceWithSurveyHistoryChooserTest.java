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

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.tanrabad.survey.domain.user.UserRepository;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.User;

import java.util.ArrayList;

public class PlaceWithSurveyHistoryChooserTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    private PlaceWithSurveyHistoryListPresenter placeWithSurveyHistoryListPresenter;
    private PlaceRepository placeRepository;
    private UserRepository userRepository;
    private String username;
    private User user;

    @Before
    public void setUp() {
        placeRepository = context.mock(PlaceRepository.class);
        userRepository = context.mock(UserRepository.class);
        placeWithSurveyHistoryListPresenter = context.mock(PlaceWithSurveyHistoryListPresenter.class);
        username = "chn";
        user = User.fromUsername(username);
    }


    @Test
    public void testShowSurveyPlaceList() throws Exception {

        final ArrayList<Place> surveyPlace = new ArrayList<>();
        surveyPlace.add(Place.withName("a"));
        surveyPlace.add(Place.withName("b"));

        context.checking(new Expectations() {
            {
                oneOf(userRepository).findByUsername(with(username));
                will(returnValue(with(user)));
                oneOf(placeRepository).findRecent(with(user));
                will(returnValue(surveyPlace));
                oneOf(placeWithSurveyHistoryListPresenter).displaySurveyPlaceList(surveyPlace);
            }
        });

        PlaceWithSurveyHistoryChooser placeWithSurveyHistoryChooser = new PlaceWithSurveyHistoryChooser(
            userRepository, placeRepository, placeWithSurveyHistoryListPresenter);
        placeWithSurveyHistoryChooser.showSurveyPlaceList(username);
    }

    @Test
    public void testDisplaySurveyPlaceNotFound() throws Exception {
        context.checking(new Expectations() {
            {
                oneOf(userRepository).findByUsername(with(username));
                will(returnValue(user));
                oneOf(placeRepository).findRecent(with(user));
                will(returnValue(null));
                oneOf(placeWithSurveyHistoryListPresenter).displaySurveyPlacesNotFound();
            }
        });

        PlaceWithSurveyHistoryChooser placeWithSurveyHistoryChooser = new PlaceWithSurveyHistoryChooser(
            userRepository, placeRepository, placeWithSurveyHistoryListPresenter);
        placeWithSurveyHistoryChooser.showSurveyPlaceList(username);
    }

    @Test
    public void testAlertUserNotFound() throws Exception {
        context.checking(new Expectations() {
            {
                oneOf(userRepository).findByUsername(with(username));
                will(returnValue(null));
                oneOf(placeWithSurveyHistoryListPresenter).alertUserNotFound();
            }
        });

        PlaceWithSurveyHistoryChooser placeWithSurveyHistoryChooser = new PlaceWithSurveyHistoryChooser(
            userRepository, placeRepository, placeWithSurveyHistoryListPresenter);
        placeWithSurveyHistoryChooser.showSurveyPlaceList(username);
    }
}
