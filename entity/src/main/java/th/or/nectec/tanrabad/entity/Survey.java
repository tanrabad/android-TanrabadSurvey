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

package th.or.nectec.tanrabad.entity;

import org.joda.time.DateTime;
import th.or.nectec.tanrabad.entity.field.Location;
import th.or.nectec.tanrabad.entity.lookup.ContainerType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Survey extends Entity implements LocationEntity, Comparable<Survey> {

    private final User user;
    private final UUID surveyId;

    private Building surveyBuilding;
    private int residentCount;
    private List<SurveyDetail> indoorDetails;
    private List<SurveyDetail> outdoorDetails;
    private DateTime startTimestamp;
    private DateTime finishTimestamp;
    private Location location;

    public Survey(UUID surveyId, User user, Building surveyBuilding) {
        super();
        this.surveyId = surveyId;
        this.user = user;
        this.surveyBuilding = surveyBuilding;
    }

    public void startSurvey() {
        setStartTimestamp(DateTime.now());
    }

    public void finishSurvey() {
        setFinishTimestamp(DateTime.now());
    }

    public DateTime getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(DateTime startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public int getResidentCount() {
        return residentCount;
    }

    public void setResidentCount(int residentCount) {
        this.residentCount = residentCount;
    }

    public Building getSurveyBuilding() {
        return surveyBuilding;
    }

    public void setSurveyBuilding(Building surveyBuilding) {
        this.surveyBuilding = surveyBuilding;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<SurveyDetail> getIndoorDetail() {
        return indoorDetails;
    }

    public void setIndoorDetail(List<SurveyDetail> indoorDetails) {
        this.indoorDetails = indoorDetails;
    }

    public List<SurveyDetail> getOutdoorDetail() {
        return outdoorDetails;
    }

    public void setOutdoorDetail(List<SurveyDetail> outdoorDetails) {
        this.outdoorDetails = outdoorDetails;
    }

    public boolean isFoundLarvae() {
        return isFoundLarvaeInDetails(indoorDetails) || isFoundLarvaeInDetails(outdoorDetails);
    }

    private boolean isFoundLarvaeInDetails(List<SurveyDetail> details) {
        if (details != null) {
            for (SurveyDetail detail : details) {
                if (detail.isFoundLarva()) return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + surveyId.hashCode();
        result = 31 * result + surveyBuilding.hashCode();
        result = 31 * result + residentCount;
        result = 31 * result + (indoorDetails != null ? indoorDetails.hashCode() : 0);
        result = 31 * result + (outdoorDetails != null ? outdoorDetails.hashCode() : 0);
        result = 31 * result + (startTimestamp != null ? startTimestamp.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Survey survey = (Survey) o;

        if (residentCount != survey.residentCount) return false;
        if (!user.equals(survey.user)) return false;
        if (!surveyBuilding.equals(survey.surveyBuilding)) return false;
        if (indoorDetails != null
                ? !indoorDetails.equals(survey.indoorDetails) : survey.indoorDetails != null)
            return false;
        if (outdoorDetails != null
                ? !outdoorDetails.equals(survey.outdoorDetails) : survey.outdoorDetails != null)
            return false;
        return !(startTimestamp != null
                ? !startTimestamp.equals(survey.startTimestamp) : survey.startTimestamp != null);
    }

    @Override
    public String toString() {
        return "Survey{"
                + "user=" + user
                + ", surveyBuilding=" + surveyBuilding
                + ", residentCount=" + residentCount
                + ", indoorDetails=" + indoorDetails
                + ", outdoorDetails=" + outdoorDetails
                + '}';
    }

    @Override
    public int compareTo(Survey other) {
        return this.getFinishTimestamp().compareTo(other.getFinishTimestamp());
    }

    public DateTime getFinishTimestamp() {
        return finishTimestamp;
    }

    public void setFinishTimestamp(DateTime finishTimestamp) {
        this.finishTimestamp = finishTimestamp;
    }

    public UUID getId() {
        return surveyId;
    }

    public static class Builder {

        public static final Building DEFAULT_BUILDING = Building.withName("default");
        public static final User TESTER = User.fromUsername("tester");
        private final List<SurveyDetail> indoor = new ArrayList<>();
        private final List<SurveyDetail> outdoor = new ArrayList<>();
        private final UUID surveyId;
        private int resident;
        private User surveyor = TESTER;
        private Building building = DEFAULT_BUILDING;
        private Location location;
        private DateTime startTimeStamp;
        private DateTime finishTimeStamp;

        public Builder() {
            this(UUID.randomUUID(), TESTER);
        }

        public Builder(UUID surveyId, User surveyor) {
            this.surveyId = surveyId;
            this.surveyor = surveyor;
        }

        public Survey build() {
            Survey survey = new Survey(surveyId, surveyor, building);
            survey.setResidentCount(resident);
            survey.setIndoorDetail(indoor);
            survey.setOutdoorDetail(outdoor);
            survey.setLocation(location);
            survey.setStartTimestamp(startTimeStamp);
            survey.setFinishTimestamp(finishTimeStamp);
            return survey;
        }

        public Builder setBuilding(Building building) {
            this.building = building;
            return this;
        }

        public Builder setResident(int residentCount) {
            resident = residentCount;
            return this;
        }

        public Builder addIndoorDetail(UUID surveydetailid, ContainerType containerType, int total, int foundLarvae) {
            indoor.add(new SurveyDetail(surveydetailid, containerType, total, foundLarvae));
            return this;
        }

        public Builder addOutdoorDetail(UUID surveyDetailId, ContainerType containerType, int total, int foundLarvae) {
            outdoor.add(new SurveyDetail(surveyDetailId, containerType, total, foundLarvae));
            return this;
        }

        public Builder setLocation(Location location) {
            this.location = location;
            return this;
        }

        public Builder setStartTimeStamp(DateTime startTimeStamp) {
            this.startTimeStamp = startTimeStamp;
            return this;
        }

        public Builder setFinishTimeStamp(DateTime finishTimeStamp) {
            this.finishTimeStamp = finishTimeStamp;
            return this;
        }

    }
}
