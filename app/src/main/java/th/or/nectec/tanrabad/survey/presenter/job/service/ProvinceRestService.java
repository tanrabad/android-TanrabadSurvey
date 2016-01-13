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

package th.or.nectec.tanrabad.survey.presenter.job.service;

import com.bluelinelabs.logansquare.LoganSquare;
import com.squareup.okhttp.Request;
import th.or.nectec.tanrabad.entity.Province;
import th.or.nectec.tanrabad.survey.presenter.job.service.http.Header;
import th.or.nectec.tanrabad.survey.presenter.job.service.jsonentity.JsonProvince;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProvinceRestService extends BaseRestService<Province> {

    LastUpdate lastUpdate;

    public ProvinceRestService() {
        this(BASE_API, new LastUpdatePreference());
    }

    public ProvinceRestService(String apiBaseUrl, LastUpdate lastUpdate) {
        this.apiBaseUrl = apiBaseUrl;
        this.lastUpdate = lastUpdate;
    }

    @Override
    protected Request makeRequest() {
        return new Request.Builder()
                .get()
                .url(provinceUrl())
                .header(Header.IF_MODIFIED_SINCE, lastUpdate.get().toDateTimeISO().toString())
                .build();
    }

    public String provinceUrl() {
        return apiBaseUrl + getPath();
    }

    @Override
    protected List<Province> toJson(String responseBody) {
        ArrayList<Province> provinceList = new ArrayList<>();
        try {
            List<JsonProvince> jsonProvinceList = LoganSquare.parseList(responseBody, JsonProvince.class);
            for (JsonProvince eachJsonProvince : jsonProvinceList) {
                provinceList.add(eachJsonProvince.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return provinceList;
    }

    @Override
    protected String getPath() {
        return "/province";
    }
}
