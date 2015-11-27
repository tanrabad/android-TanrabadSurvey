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
import org.joda.time.Duration;

import java.util.Locale;

public class DurationTimePrinter {

    public static String print(DateTime startTime, DateTime endTime) {
        Duration duration = new Duration(startTime, endTime);
        if (duration.getStandardHours() > 0)
            return String.format(Locale.US, "%d:%02d:%02d",
                    duration.getStandardHours(),
                    duration.getStandardMinutes() % 60,
                    duration.getStandardSeconds() % 60);
        else
            return String.format(Locale.US, "%02d:%02d",
                    duration.getStandardMinutes(),
                    duration.getStandardSeconds() % 60);
    }
}
