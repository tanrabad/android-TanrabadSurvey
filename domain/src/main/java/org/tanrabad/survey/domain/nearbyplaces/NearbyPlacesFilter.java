package org.tanrabad.survey.domain.nearbyplaces;

import java.util.List;
import org.tanrabad.survey.entity.Place;
import org.tanrabad.survey.entity.field.Location;
import org.tanrabad.survey.entity.field.LocationBound;

/**
 * Created by CHNCS23 on 8/12/2559.
 */
public interface NearbyPlacesFilter {
    List<Place> findWithoutLocation(List<Place> places);

    List<Place> findInBoundary(List<Place> places, LocationBound locationBoundary);

    List<Place> sortDistance(List<Place> places, Location myLocation);

    List<String> groupingSubdistrictCode(List<Place> places);

    List<Place> weightScoreForPlacesWithoutLocation(List<String> subdistrictCodes, List<Place> placeWithoutLocation);

    List<Place> trimCommonPlaceName(List<Place> places);

    List<Place> findAverageLcsForPlaceWithoutLocation(List<Place> placesWithoutLocation, List<Place> placeWithLocation);
}
