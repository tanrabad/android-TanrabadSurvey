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

package org.tanrabad.survey.presenter.view;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.tanrabad.survey.R;
import org.tanrabad.survey.entity.Organization;
import org.tanrabad.survey.entity.User;
import org.tanrabad.survey.job.UploadJobRunner;
import org.tanrabad.survey.presenter.AboutActivity;
import org.tanrabad.survey.presenter.AccountUtils;
import org.tanrabad.survey.presenter.LoginActivity;
import org.tanrabad.survey.presenter.PreferenceActivity;
import org.tanrabad.survey.repository.BrokerOrganizationRepository;
import org.tanrabad.survey.utils.UserDataManager;
import org.tanrabad.survey.utils.alert.Alert;
import org.tanrabad.survey.utils.android.InternetConnection;

public class MainActivityNavigation {

    public static void setup(final Activity activity) {
        NavigationView navigationView = (NavigationView) activity.findViewById(R.id.navigation);
        navigationView.setItemIconTintList(null);
        setupHeaderView(navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.trb_watch:
                        Intent trbWatch = new Intent(Intent.ACTION_VIEW, Uri.parse("https://watch.tanrabad.org"));
                        activity.startActivity(trbWatch);
                        break;
                    case R.id.trb_report:
                        Intent trbReport = new Intent(Intent.ACTION_VIEW, Uri.parse("https://report.tanrabad.org"));
                        activity.startActivity(trbReport);
                        break;
                    case R.id.trb_bi:
                        Intent trbBi = new Intent(Intent.ACTION_VIEW, Uri.parse("https://bi.tanrabad.org"));
                        activity.startActivity(trbBi);
                        break;
                    case R.id.manual:
                        Intent manual = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://tanrabad.gitbooks.io/survey-manual/content/index.html"));
                        activity.startActivity(manual);
                        break;
                    case R.id.about:
                        AboutActivity.open(activity);
                        break;
                    case R.id.preferences:
                        PreferenceActivity.open(activity);
                        break;
                    case R.id.logout:
                        if (!InternetConnection.isAvailable(activity)) {
                            Alert.highLevel().show(R.string.please_connect_internet_before_logout);
                            return false;
                        }

                        UploadJobRunner uploadJob = new UploadJobRunner();
                        uploadJob.addJobs(new UploadJobRunner.Builder().getJobs());
                        uploadJob.setOnSyncFinishListener(new UploadJobRunner.OnSyncFinishListener() {
                            @Override
                            public void onSyncFinish() {
                                AccountUtils.clear();
                                UserDataManager.clearAll(activity);
                                Intent backToLogin = new Intent(activity, LoginActivity.class);
                                activity.startActivity(backToLogin);
                                activity.finish();
                            }
                        });
                        uploadJob.start();

                        break;
                }
                return false;
            }
        });


        activity.findViewById(R.id.drawer_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    private static void setupHeaderView(NavigationView navigationView) {
        View header = navigationView.getHeaderView(0);

        final User user = AccountUtils.getUser();
        ImageView avatarImageView = (ImageView) header.findViewById(R.id.avatar_icon);
        avatarImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Alert.lowLevel().show(user.getApiFilter());
                return true;
            }
        });

        TextView userNameTextView = (TextView) header.findViewById(R.id.username);
        userNameTextView.setText(user.getUsername());

        TextView userFullNameTextView = (TextView) header.findViewById(R.id.user_fullname);
        userFullNameTextView.setText(String.format("%s %s", user.getFirstname(), user.getLastname()));

        Organization organization = BrokerOrganizationRepository.getInstance().findById(user.getOrganizationId());
        TextView organizationTextView = (TextView) header.findViewById(R.id.organization);
        organizationTextView.setText(organization.getName());
    }
}
