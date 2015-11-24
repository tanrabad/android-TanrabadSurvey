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

package th.or.nectec.tanrabad.survey.utils.time;

import org.joda.time.DateTime;

/**
 * Created by blaze on 11/24/2015 AD.
 */
class DaysAgoPrinter implements TimePrettyPrinter {
    private final CurrentTimer currentTimer;

    public DaysAgoPrinter(CurrentTimer currentTimer) {
        this.currentTimer = currentTimer;
    }

    @Override
    public String print(long timeAgoInMills) {
        DateTime currentTimeInMills = new DateTime(currentTimer.getInMills());
        DateTime agoDateTime = new DateTime(timeAgoInMills);

        if (currentTimeInMills.getDayOfYear() - agoDateTime.getDayOfYear() == 1) {
            DateTime dateTime = new DateTime(timeAgoInMills);
            return String.format("เมื่อวาน %02d:%02d", dateTime.getHourOfDay(), dateTime.getMinuteOfHour());
        }
        return null;
    }
}
