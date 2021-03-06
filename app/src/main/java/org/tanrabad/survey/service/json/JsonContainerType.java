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

package org.tanrabad.survey.service.json;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import org.tanrabad.survey.entity.lookup.ContainerType;

@JsonObject
public class JsonContainerType {

    @JsonField(name = "container_type_id")
    public int containerTypeId;

    @JsonField(name = "container_type_name")
    public String containerTypeName;

    @JsonField(name = "order_number")
    public int orderNumber;

    public ContainerType getEntity() {
        return new ContainerType(containerTypeId, containerTypeName, orderNumber);
    }
}
