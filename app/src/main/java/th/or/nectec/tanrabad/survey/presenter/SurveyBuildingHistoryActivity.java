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

package th.or.nectec.tanrabad.survey.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import th.or.nectec.tanrabad.domain.entomology.HouseIndex;
import th.or.nectec.tanrabad.domain.place.PlaceController;
import th.or.nectec.tanrabad.domain.place.PlacePresenter;
import th.or.nectec.tanrabad.domain.survey.SurveyBuildingHistoryController;
import th.or.nectec.tanrabad.domain.survey.SurveyBuildingPresenter;
import th.or.nectec.tanrabad.entity.Place;
import th.or.nectec.tanrabad.entity.Survey;
import th.or.nectec.tanrabad.survey.R;
import th.or.nectec.tanrabad.survey.TanrabadApp;
import th.or.nectec.tanrabad.survey.job.AbsJobRunner;
import th.or.nectec.tanrabad.survey.job.Job;
import th.or.nectec.tanrabad.survey.job.SyncJobBuilder;
import th.or.nectec.tanrabad.survey.presenter.view.EmptyLayoutView;
import th.or.nectec.tanrabad.survey.repository.BrokerPlaceRepository;
import th.or.nectec.tanrabad.survey.repository.BrokerSurveyRepository;
import th.or.nectec.tanrabad.survey.repository.StubUserRepository;
import th.or.nectec.tanrabad.survey.utils.alert.Alert;
import th.or.nectec.tanrabad.survey.utils.android.InternetConnection;
import th.or.nectec.tanrabad.survey.utils.showcase.BaseShowcase;
import th.or.nectec.tanrabad.survey.utils.showcase.ShowcaseFactory;

import java.util.List;
import java.util.UUID;

public class SurveyBuildingHistoryActivity extends TanrabadActivity implements SurveyBuildingPresenter, PlacePresenter {

    public static final String PLACE_UUID_ARG = "place_uuid_arg";

    private TextView placeName;
    private ImageButton surveyMoreBuildingButton;

    private SurveyBuildingHistoryAdapter surveyBuildingHistoryAdapter;
    private Place place;
    private EmptyLayoutView emptyLayoutView;

    public static void open(Activity activity, Place placeData) {
        Intent intent = new Intent(activity, SurveyBuildingHistoryActivity.class);
        intent.putExtra(SurveyBuildingHistoryActivity.PLACE_UUID_ARG, placeData.getId().toString());
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_building_list);
        setupHomeButton();
        setupView();
        showPlaceInfo();
        setupBuildingHistoryList();
        setupEmptyLayout();
        showSurveyBuildingHistoryList();

        if (InternetConnection.isAvailable(this))
            SyncJobBuilder.build(new SurveyUpdateJob()).start();
    }

    private void setupView() {
        placeName = (TextView) findViewById(R.id.place_name);
        surveyMoreBuildingButton = (ImageButton) findViewById(R.id.survey_more_building_button);
        surveyMoreBuildingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                BuildingListActivity.open(SurveyBuildingHistoryActivity.this, getPlaceUuidFromIntent());
            }
        });
        startSurveyMoreBuildingButtonAnimation();
        displaySurveyMoreBuildingShowcase();
    }

    private String getPlaceUuidFromIntent() {
        return getIntent().getStringExtra(PLACE_UUID_ARG);
    }

    private void startSurveyMoreBuildingButtonAnimation() {
        Animation moreBuildingAnim = AnimationUtils.loadAnimation(this, R.anim.survey_more_building_button);
        surveyMoreBuildingButton.startAnimation(moreBuildingAnim);
    }

    private void displaySurveyMoreBuildingShowcase() {
        BaseShowcase showcase = ShowcaseFactory.viewShowcase(R.id.survey_more_building_button);
        showcase.setTitle(getString(R.string.showcase_survey_more_building_title));
        showcase.setMessage(getString(R.string.showcase_survey_more_building));
        //showcase.display();
    }

    private void showPlaceInfo() {
        PlaceController placeController = new PlaceController(BrokerPlaceRepository.getInstance(), this);
        placeController.showPlace(UUID.fromString(getPlaceUuidFromIntent()));
    }

    private void setupBuildingHistoryList() {
        surveyBuildingHistoryAdapter = new SurveyBuildingHistoryAdapter(this,
                BuildingIconWhiteMapping.getBuildingIcon(place));
        RecyclerView surveyBuildingHistoryList = (RecyclerView) findViewById(R.id.survey_building_history_list);
        surveyBuildingHistoryList.setAdapter(surveyBuildingHistoryAdapter);
        surveyBuildingHistoryList.setLayoutManager(new LinearLayoutManager(this));
        surveyBuildingHistoryList.addItemDecoration(new SimpleDividerItemDecoration(this));
        surveyBuildingHistoryAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Survey survey = surveyBuildingHistoryAdapter.getItem(position);
                SurveyActivity.open(SurveyBuildingHistoryActivity.this, survey.getSurveyBuilding());
            }
        });
        RecyclerViewHeader recyclerViewHeader = (RecyclerViewHeader) findViewById(R.id.card_header);

        recyclerViewHeader.attachTo(surveyBuildingHistoryList, true);
    }

    private void setupEmptyLayout() {
        emptyLayoutView = (EmptyLayoutView) findViewById(R.id.empty_layout);
        emptyLayoutView.setEmptyButtonVisibility(false);
        emptyLayoutView.setEmptyText(R.string.survey_building_history_not_found);
    }

    private void showSurveyBuildingHistoryList() {
        SurveyBuildingHistoryController surveyBuildingHistoryController = new SurveyBuildingHistoryController(
                new StubUserRepository(),
                BrokerPlaceRepository.getInstance(),
                BrokerSurveyRepository.getInstance(),
                this);
        surveyBuildingHistoryController.showSurveyBuildingOf(getPlaceUuidFromIntent(),
                AccountUtils.getUser().getUsername());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.finish:
                TanrabadApp.action().finishSurvey(place, true);
                openMainActivity();
                break;
            case android.R.id.home:
                TanrabadApp.action().finishSurvey(place, false);
                openMainActivity();
                break;
        }
        return true;
    }

    private void openMainActivity() {
        Intent intent = new Intent(SurveyBuildingHistoryActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void displayPlace(Place place) {
        this.place = place;
        placeName.setText(place.getName());
        ImageView icon = (ImageView) findViewById(R.id.building_icon);
        icon.setImageResource(BuildingIconMapping.getBuildingIcon(place));
    }

    @Override
    public void alertUserNotFound() {
        Alert.highLevel().show(R.string.user_not_found);
    }

    @Override
    public void alertPlaceNotFound() {
        Alert.highLevel().show(R.string.place_not_found);
    }

    @Override
    public void displaySurveyBuildingsNotFound() {
        finish();
        BuildingListActivity.open(SurveyBuildingHistoryActivity.this, getPlaceUuidFromIntent());
    }

    @Override
    public void displaySurveyBuildingList(List<Survey> surveys) {
        emptyLayoutView.setVisibility(View.GONE);
        TextView cardSubhead = (TextView) findViewById(R.id.place_subhead);
        HouseIndex hi = new HouseIndex(surveys);
        hi.calculate();
        cardSubhead.setText(Html.fromHtml(
                getString(R.string.format_house_survey, hi.getTotalSurveyHouse(), hi.getFoundLarvaeHouse())));
        surveyBuildingHistoryAdapter.updateData(surveys);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_activity_survey_building_history, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        TanrabadApp.action().finishSurvey(place, false);
        openMainActivity();
    }

    public class SurveyUpdateJob extends AbsJobRunner {

        @Override
        protected void onJobError(Job errorJob, Exception exception) {
            super.onJobError(errorJob, exception);
            TanrabadApp.log(exception);
        }

        @Override
        protected void onJobStart(Job startingJob) {

        }

        @Override
        protected void onRunFinish() {
            if (errorJobs() == 0) {
                Alert.mediumLevel().show(R.string.upload_data_success);
            } else {
                Alert.mediumLevel().show(R.string.upload_data_failure);
            }
            showSurveyBuildingHistoryList();
        }
    }
}


