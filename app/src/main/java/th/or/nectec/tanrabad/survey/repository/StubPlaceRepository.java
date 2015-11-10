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

package th.or.nectec.tanrabad.survey.repository;

import th.or.nectec.tanrabad.domain.PlaceRepository;
import th.or.nectec.tanrabad.entity.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StubPlaceRepository implements PlaceRepository {
    @Override
    public List<Place> findPlaces() {
        List<Place> places = new ArrayList<>();

        Place place1 = new Place(UUID.nameUUIDFromBytes("1abc".getBytes()), "บางไผ่");
        place1.setType(Place.TYPE_VILLAGE);

        places.add(place1);

        places.add(new Place(UUID.nameUUIDFromBytes("1abc".getBytes()), "บางไผ่"));
        places.add(new Place(UUID.nameUUIDFromBytes("2bcd".getBytes()), "บางโพธิ์"));
        places.add(new Place(UUID.nameUUIDFromBytes("3def".getBytes()), "บางไทร"));
        return places;
    }
}
