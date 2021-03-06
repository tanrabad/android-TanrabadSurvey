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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;

import org.tanrabad.survey.R;
import org.tanrabad.survey.TanrabadApp;
import org.tanrabad.survey.domain.building.BuildingWithSurveyStatus;
import org.tanrabad.survey.domain.building.BuildingWithSurveyStatusListPresenter;
import org.tanrabad.survey.domain.place.PlaceController;
import org.tanrabad.survey.domain.place.PlacePresenter;
import org.tanrabad.survey.domain.survey.SurveyBuildingChooser;
import org.tanrabad.survey.entity.Building;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.Survey;
import org.tanrabad.survey.entity.lookup.PlaceType;
import org.tanrabad.survey.job.DeleteDataJob;
import org.tanrabad.survey.job.DownloadJobBuilder;
import org.tanrabad.survey.job.UploadJobRunner;
import org.tanrabad.survey.presenter.view.EmptyLayoutView;
import org.tanrabad.survey.repository.BrokerBuildingRepository;
import org.tanrabad.survey.repository.BrokerPlaceRepository;
import org.tanrabad.survey.repository.BrokerSurveyRepository;
import org.tanrabad.survey.repository.BrokerUserRepository;
import org.tanrabad.survey.service.BuildingRestService;
import org.tanrabad.survey.service.PlaceRestService;
import org.tanrabad.survey.service.SurveyRestService;
import org.tanrabad.survey.utils.alert.Alert;
import org.tanrabad.survey.utils.android.InternetConnection;
import org.tanrabad.survey.utils.prompt.AlertDialogPromptMessage;
import org.tanrabad.survey.utils.prompt.PromptMessage;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class BuildingListActivity extends TanrabadActivity implements BuildingWithSurveyStatusListPresenter,
    PlacePresenter, ActionMode.Callback, View.OnClickListener {

    public static final String PLACE_UUID_ARG = "place_uuid_arg";
    private static final int NEED_REFRESH_REQ_CODE = 31000;
    private ImageButton editBuildingButton;
    private RecyclerView buildingList;
    private BuildingWithSurveyStatusAdapter buildingAdapter;
    private Place place;
    private EmptyLayoutView emptyBuildingsView;
    private SurveyBuildingChooser surveyBuildingChooser = new SurveyBuildingChooser(
        BrokerUserRepository.getInstance(),
        BrokerPlaceRepository.getInstance(),
        BrokerSurveyRepository.getInstance(),
        this);
    private SearchView buildingSearchView;
    private ActionMode actionMode;

    public static void open(Activity activity, String placeUuid) {
        Intent intent = new Intent(activity, BuildingListActivity.class);
        intent.putExtra(BuildingListActivity.PLACE_UUID_ARG, placeUuid);
        activity.startActivityForResult(intent, NEED_REFRESH_REQ_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setupHomeButton();
        setupEditPlaceButton();
        setupEditBuildingButton();
        showPlaceName();
        setupBuildingList();
        setupSearchView();
        setupEmptyLayout();
        loadSurveyBuildingList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_building_menu:
                BuildingFormActivity.startAdd(BuildingListActivity.this, getPlaceUuidFromIntent().toString());
                finish();
                break;
            case R.id.delete_place_menu:
                if (!InternetConnection.isAvailable(this)) {
                    Alert.highLevel().show(R.string.please_enable_internet_before_delete);
                    return true;
                }

                List<Building> buildings = BrokerBuildingRepository.getInstance().findByPlaceUuid(place.getId());
                if (buildings != null) {
                    Alert.highLevel().show(R.string.please_delete_building_in_place);
                    return true;
                }

                startSyncJobs();
                PromptMessage promptMessage = new AlertDialogPromptMessage(this, R.mipmap.ic_delete);

                promptMessage.setOnConfirm(getString(R.string.delete), new PromptMessage.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        deletePlace(place);
                    }
                });
                promptMessage.setOnCancel(getString(R.string.cancel), null);
                promptMessage.show(getString(R.string.delete_place), getString(R.string.delete_place_msg));
                break;

            case R.id.edit_place_menu:
                PlaceFormActivity.startEdit(BuildingListActivity.this, place);
                break;

            case android.R.id.home:
                finishActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deletePlace(Place place) {
        UploadJobRunner jobRunner = new UploadJobRunner();
        jobRunner.setOnSyncFinishListener(new UploadJobRunner.OnSyncFinishListener() {
            @Override
            public void onSyncFinish() {
                setResult(RESULT_OK);
                PlaceListActivity.open(BuildingListActivity.this);
                finish();
            }
        });

        DeleteDataJob<Place> deletePlaceJob = new DeleteDataJob<>(BrokerPlaceRepository.getInstance(),
            new PlaceRestService(), place);
        jobRunner.addJob(deletePlaceJob);
        jobRunner.start();
    }

    private UUID getPlaceUuidFromIntent() {
        String uuid = getIntent().getStringExtra(PLACE_UUID_ARG);
        return UUID.fromString(uuid);
    }

    public void finishActivity() {
        if (buildingAdapter.getItemCount() == 0)
            PlaceListActivity.open(BuildingListActivity.this);
        setResult(RESULT_OK);
        finish();
    }

    private void startSyncJobs() {
        UploadJobRunner jobRunner = new UploadJobRunner();
        jobRunner.setOnSyncFinishListener(new UploadJobRunner.OnSyncFinishListener() {
            @Override
            public void onSyncFinish() {
                loadSurveyBuildingList();
            }
        });
        jobRunner.addJobs(new UploadJobRunner.Builder().getJobs());
        jobRunner.addJobs(new DownloadJobBuilder().getJobs());
        jobRunner.start();
    }

    private void loadSurveyBuildingList() {
        emptyBuildingsView.showProgressBar();
        isPlaceNoLocation = false;
        surveyBuildingChooser.displaySurveyBuildingOf(getPlaceUuidFromIntent().toString(), AccountUtils.getUser());
    }

    private void setupEditPlaceButton() {
        Button editPlaceButton = findViewById(R.id.edit_place);
        editPlaceButton.setOnClickListener(this);
    }

    private void setupEditBuildingButton() {
        editBuildingButton = findViewById(R.id.edit_building);
        editBuildingButton.setOnClickListener(this);
    }

    private void showPlaceName() {
        PlaceController placeController = new PlaceController(BrokerPlaceRepository.getInstance(), this);
        placeController.showPlace(getPlaceUuidFromIntent());
    }

    private void setupBuildingList() {
        buildingAdapter = new BuildingWithSurveyStatusAdapter(this, BuildingIcon.get(place));
        buildingList = findViewById(R.id.building_list);
        buildingList.setAdapter(buildingAdapter);
        buildingList.addItemDecoration(new SimpleDividerItemDecoration(this));
        buildingList.setLayoutManager(new LinearLayoutManager(this));
        buildingAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BuildingWithSurveyStatus building = buildingAdapter.getItem(position);
                finish();
                SurveyActivity.open(BuildingListActivity.this, building.building);
                if (!TextUtils.isEmpty(buildingSearchView.getQuery()))
                    TanrabadApp.action().filterBuilding(buildingSearchView.getQuery().toString());
            }
        });
        buildingAdapter.setOnDeleteBuildingListener(new BuildingWithSurveyStatusAdapter.OnDeleteBuildingListener() {
            @Override
            public void onDeleteBuilding(final Building building) {
                if (InternetConnection.isAvailable(BuildingListActivity.this)) {
                    startSyncJobs();
                    PromptMessage promptMessage = new AlertDialogPromptMessage(
                        BuildingListActivity.this, R.mipmap.ic_delete);
                    promptMessage.setOnConfirm(getString(R.string.delete), new PromptMessage.OnConfirmListener() {
                        @Override
                        public void onConfirm() {
                            deleteBuilding(building);
                        }
                    });
                    promptMessage.setOnCancel(getString(R.string.cancel), null);
                    promptMessage.show(getString(R.string.delete_building), getString(R.string.delete_building_msg));
                }
            }
        });
        RecyclerViewHeader recyclerViewHeader = (RecyclerViewHeader) findViewById(R.id.card_header);
        recyclerViewHeader.attachTo(buildingList, true);
    }

    private void deleteBuilding(Building building) {
        UploadJobRunner runner = new UploadJobRunner();
        runner.setOnSyncFinishListener(new UploadJobRunner.OnSyncFinishListener() {
            @Override
            public void onSyncFinish() {
                loadSurveyBuildingList();
            }
        });

        Survey survey = BrokerSurveyRepository.getInstance().findRecent(
            building, AccountUtils.getUser());
        runner.addJob(new DeleteDataJob<>(BrokerSurveyRepository.getInstance(),
            new SurveyRestService(), survey));

        DeleteDataJob<Building> dataJob = new DeleteDataJob<>(BrokerBuildingRepository.getInstance(),
            new BuildingRestService(), building);
        runner.addJob(dataJob);
        runner.start();
    }

    private void setupSearchView() {
        buildingSearchView = findViewById(R.id.building_search);
        buildingSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchString) {
                TanrabadApp.action().filterBuilding(searchString);
                emptyBuildingsView.showProgressBar();
                surveyBuildingChooser.searchSurveyBuildingOfPlaceByName(searchString,
                    getPlaceUuidFromIntent().toString(),
                    AccountUtils.getUser());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String searchString) {
                emptyBuildingsView.showProgressBar();
                surveyBuildingChooser.searchSurveyBuildingOfPlaceByName(searchString,
                    getPlaceUuidFromIntent().toString(),
                    AccountUtils.getUser());
                return true;
            }
        });
    }

    private void setupEmptyLayout() {
        emptyBuildingsView = findViewById(R.id.empty_layout);
        emptyBuildingsView.setEmptyIcon(place.getType() == PlaceType.VILLAGE_COMMUNITY
            ? R.mipmap.ic_building_home_black : R.mipmap.ic_building_black);
        emptyBuildingsView.setEmptyText(R.string.building_list_not_found);
        emptyBuildingsView.setEmptyButtonText(R.string.add_building, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuildingFormActivity.startAdd(BuildingListActivity.this, getPlaceUuidFromIntent().toString());
            }
        });
    }

    @Override
    public void displayPlace(final Place place) {
        this.place = place;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView placeName = findViewById(R.id.place_name);
                placeName.setText(place.getName());
            }
        });
    }

    @Override
    public void alertBuildingsNotFound() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                emptyBuildingsView.showEmptyLayout();
                emptyBuildingsView.setVisibility(View.VISIBLE);
                TextView buildingCountView = findViewById(R.id.building_count);
                buildingCountView.setVisibility(View.GONE);
                editBuildingButton.setVisibility(View.GONE);
                buildingAdapter.clearData();
                if (actionMode != null)
                    actionMode.finish();
            }
        });
    }

    @Override
    public void displayAllSurveyBuildingList(final List<BuildingWithSurveyStatus> buildings) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Collections.sort(buildings);
                emptyBuildingsView.hide();
                editBuildingButton.setVisibility(View.VISIBLE);
                buildingSearchView.setVisibility(View.VISIBLE);
                buildingAdapter.updateData(buildings);
                buildingList.setAdapter(buildingAdapter);
                TextView buildingCountView = findViewById(R.id.building_count);
                buildingCountView.setText(getString(R.string.format_building_count, buildings.size()));
                buildingCountView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void alertUserNotFound() {
        Alert.lowLevel().show(R.string.user_not_found);
    }

    @Override
    public void alertPlaceNotFound() {
        Alert.highLevel().show(R.string.place_not_found);
    }

    private boolean isPlaceNoLocation = false;

    @Override
    public void onRequirePlaceLocation(Place place) {
        isPlaceNoLocation = true;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setView(R.layout.dialog_message_alert);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(R.string.mark_location, (dialog, which) ->
            PlaceFormActivity.startEdit(BuildingListActivity.this, place));
        alertDialog.setNegativeButton(R.string.close, (dialog, which) -> finishActivity());
        AlertDialog dialog = alertDialog.show();

        TextView messageView = dialog.findViewById(R.id.dialog_message);
        messageView.setText(getString(R.string.place_no_location, place.getName()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_activity_building_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            if (requestCode == PlaceFormActivity.PLACE_FORM_REQ_CODE && isPlaceNoLocation)
                loadSurveyBuildingList(); //check again
            return;
        }

        setResult(RESULT_OK);
        if (InternetConnection.isAvailable(this))
            startSyncJobs();
        switch (requestCode) {
            case BuildingFormActivity.ADD_BUILDING_REQ_CODE:
                loadSurveyBuildingList();
                stopActionMode();
                break;
            case PlaceFormActivity.PLACE_FORM_REQ_CODE:
                showPlaceName();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    private void stopActionMode() {
        if (actionMode != null)
            actionMode.finish();
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.setTitle(R.string.building_edit);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        buildingAdapter.setEditButtonVisibility(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_place:
                PlaceFormActivity.startEdit(BuildingListActivity.this, place);
                break;
            case R.id.edit_building:
                actionMode = BuildingListActivity.this.startSupportActionMode(BuildingListActivity.this);
                buildingAdapter.setEditButtonVisibility(true);
                break;
        }
    }


}
