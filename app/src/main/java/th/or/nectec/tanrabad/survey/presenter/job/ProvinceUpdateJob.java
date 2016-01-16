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

package th.or.nectec.tanrabad.survey.presenter.job;

import th.or.nectec.tanrabad.domain.address.ProvinceRepository;
import th.or.nectec.tanrabad.entity.Province;
import th.or.nectec.tanrabad.survey.service.ProvinceRestService;
import th.or.nectec.tanrabad.survey.service.RestService;

import java.util.ArrayList;

public class ProvinceUpdateJob implements Job {

    public static final int ID = 100003;

    private final ProvinceRepository provinceRepository;

    public ProvinceUpdateJob(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    @Override
    public int id() {
        return ID;
    }

    @Override
    public void execute() throws JobException {
        RestService<Province> service = new ProvinceRestService();
        ArrayList<Province> provinceArrayList = new ArrayList<>();

        do {
            provinceArrayList.addAll(service.getUpdate());
        } while (service.hasNextRequest());

        provinceRepository.updateOrInsert(provinceArrayList);
    }
}