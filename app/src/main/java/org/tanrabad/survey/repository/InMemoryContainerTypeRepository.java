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

package org.tanrabad.survey.repository;

import org.tanrabad.survey.domain.survey.ContainerTypeRepository;
import org.tanrabad.survey.domain.survey.ContainerTypeRepositoryException;
import org.tanrabad.survey.entity.lookup.ContainerType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

final class InMemoryContainerTypeRepository implements ContainerTypeRepository {


    private static InMemoryContainerTypeRepository instance;
    private Map<Integer, ContainerType> containerTypes;

    private InMemoryContainerTypeRepository() {
        containerTypes = new ConcurrentHashMap<>();
    }

    public static InMemoryContainerTypeRepository getInstance() {
        if (instance == null)
            instance = new InMemoryContainerTypeRepository();
        return instance;
    }

    @Override
    public List<ContainerType> find() {
        ArrayList<ContainerType> queryContainerType = new ArrayList<>(containerTypes.values());
        Collections.sort(queryContainerType);
        return queryContainerType;
    }

    @Override
    public ContainerType findById(int containerTypeId) {
        return containerTypes.get(containerTypeId);
    }

    @Override
    public void updateOrInsert(List<ContainerType> updateList) {
        for (ContainerType containerType : updateList) {
            try {
                update(containerType);
            } catch (ContainerTypeRepositoryException pre) {
                save(containerType);
            }
        }
    }

    public boolean save(ContainerType containerType) {
        containerTypes.put(containerType.getId(), containerType);
        return true;
    }

    public boolean update(ContainerType containerType) {
        containerTypes.put(containerType.getId(), containerType);
        return true;
    }

    @Override
    public boolean delete(ContainerType containerType) {
        if (!containerTypes.containsKey(containerType.getId())) {
            throw new ContainerTypeRepositoryException();
        }
        containerTypes.remove(containerType.getId());
        return true;
    }
}
