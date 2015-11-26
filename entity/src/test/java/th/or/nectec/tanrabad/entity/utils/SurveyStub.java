/*
 * Copyright (c) 2015 NECTEC
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

package th.or.nectec.tanrabad.entity.utils;

import th.or.nectec.tanrabad.entity.Building;
import th.or.nectec.tanrabad.entity.ContainerType;
import th.or.nectec.tanrabad.entity.Survey;
import th.or.nectec.tanrabad.entity.User;

public class SurveyStub {


    public static final User SURVEYOR = User.fromUsername("surveyor");

    public static Survey withoutLarvae(Building building) {
        return new Survey.Builder(SURVEYOR).setBuilding(building).addIndoorDetail(ContainerTypeStub.น้ำใช้, 20, 0).build();

    }

    public static Survey withLarvae(Building building) {
        return new Survey.Builder(SURVEYOR)
                .setBuilding(building)
                .addIndoorDetail(ContainerTypeStub.แจกัน, 5, 2)
                .addOutdoorDetail(ContainerTypeStub.กากใบพืช, 10, 2)
                .build();
    }

    public static Survey withResult(int total, int foundLarvae) {
        return withResult(ContainerTypeStub.น้ำใช้, total, foundLarvae);
    }

    public static Survey withResult(ContainerType containerType, int total, int foundLarvae) {
        return new Survey.Builder(SURVEYOR)
                .setBuilding(Building.withName("1"))
                .addIndoorDetail(containerType, total, foundLarvae)
                .build();
    }

    public static Survey withIndoorOutdoorResult(int indoorTotal, int indoorFound, int outdoorTotal, int outdoorFound) {
        return new Survey.Builder(SURVEYOR)
                .setBuilding(Building.withName("1"))
                .addIndoorDetail(ContainerTypeStub.น้ำใช้, indoorTotal, indoorFound)
                .addOutdoorDetail(ContainerTypeStub.อ่างบัว_ไม้น้ำ, outdoorTotal, outdoorFound)
                .build();
    }
}