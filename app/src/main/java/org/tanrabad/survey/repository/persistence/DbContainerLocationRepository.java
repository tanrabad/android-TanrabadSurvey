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

package org.tanrabad.survey.repository.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.tanrabad.survey.domain.survey.ContainerLocationRepository;
import org.tanrabad.survey.entity.lookup.ContainerLocation;
import org.tanrabad.survey.utils.collection.CursorList;
import org.tanrabad.survey.utils.collection.CursorMapper;

import java.util.List;

public class DbContainerLocationRepository extends DbRepository implements ContainerLocationRepository {

    public static final String TABLE_NAME = "container_location";

    public DbContainerLocationRepository(Context context) {
        super(context);
    }

    @Override
    public List<ContainerLocation> find() {
        SQLiteDatabase db = readableDatabase();
        Cursor cursor = db.query(TABLE_NAME, ContainerLocationColumn.wildcard(),
                null, null, null, null, ContainerLocationColumn.ID);
        CursorList<ContainerLocation> containerLocations = new CursorList<>(cursor, getMapper(cursor));
        db.close();
        return containerLocations;
    }

    @Override
    public ContainerLocation findById(int containerTypeId) {
        SQLiteDatabase db = readableDatabase();
        Cursor cursor = db.query(TABLE_NAME, ContainerLocationColumn.wildcard(),
                null, null, null, null, ContainerLocationColumn.ID);
        ContainerLocation containerLocation = getContainerLocation(cursor);
        db.close();
        return containerLocation;
    }

    private ContainerLocation getContainerLocation(Cursor cursor) {
        if (cursor.moveToFirst()) {
            ContainerLocation place = getMapper(cursor).map(cursor);
            cursor.close();
            return place;
        } else {
            cursor.close();
            return null;
        }
    }

    private CursorMapper<ContainerLocation> getMapper(Cursor cursor) {
        return new ContainerLocationCursorMapper(cursor);
    }

    @Override
    public boolean save(ContainerLocation containerLocation) {
        SQLiteDatabase db = writableDatabase();
        boolean success = saveByContentValues(db, containerLocationContentValues(containerLocation));
        db.close();
        return success;
    }

    @Override
    public boolean update(ContainerLocation containerLocation) {
        SQLiteDatabase db = writableDatabase();
        boolean succuss = updateByContentValues(db,
            containerLocationContentValues(containerLocation));
        db.close();
        return succuss;
    }

    @Override
    public boolean delete(ContainerLocation data) {
        return false;
    }

    @Override
    public void updateOrInsert(List<ContainerLocation> updateList) {
        SQLiteDatabase db = writableDatabase();
        db.beginTransaction();
        for (ContainerLocation containerLocation : updateList) {
            ContentValues values = containerLocationContentValues(containerLocation);
            boolean updated = updateByContentValues(db, values);
            if (!updated)
                saveByContentValues(db, values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    private boolean updateByContentValues(SQLiteDatabase db, ContentValues containerLocation) {
        return db.update(TABLE_NAME, containerLocation,
                ContainerLocationColumn.ID + "=?",
                new String[]{containerLocation.getAsString(ContainerLocationColumn.ID)}) > 0;
    }

    private boolean saveByContentValues(SQLiteDatabase db, ContentValues containerLocation) {
        return db.insert(TABLE_NAME, null, containerLocation) != ERROR_INSERT_ID;
    }

    private ContentValues containerLocationContentValues(ContainerLocation containerLocation) {
        ContentValues values = new ContentValues();
        values.put(ContainerLocationColumn.ID, containerLocation.id);
        values.put(ContainerLocationColumn.NAME, containerLocation.name);
        return values;
    }
}
