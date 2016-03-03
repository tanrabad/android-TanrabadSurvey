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

package th.or.nectec.tanrabad.survey.repository.persistence;

public class OrganizationColumn {
    public static final String ID = "org_id";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String SUBDISTRICT_CODE = "subdistrict_code";
    public static final String HEALTH_REGION_CODE = "health_region_code";

    public static String[] wildcard() {
        return new String[]{ID, NAME, ADDRESS, SUBDISTRICT_CODE, SUBDISTRICT_CODE, HEALTH_REGION_CODE};
    }
}
