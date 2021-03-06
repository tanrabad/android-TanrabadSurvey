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

import org.tanrabad.survey.domain.place.PlaceSubTypeRepository;
import org.tanrabad.survey.entity.lookup.PlaceSubType;
import org.tanrabad.survey.utils.collection.CursorList;
import org.tanrabad.survey.utils.collection.CursorMapper;

import java.util.List;

public class DbPlaceSubTypeRepository extends DbRepository implements PlaceSubTypeRepository {

    public static final String TABLE_NAME = "place_subtype";

    public DbPlaceSubTypeRepository(Context context) {
        super(context);
    }

    @Override
    public List<PlaceSubType> find() {
        SQLiteDatabase db = readableDatabase();
        Cursor placeTypeCursor = db.query(TABLE_NAME, PlaceSubTypeColumn.wildcard(),
                null, null, null, null, null);
        CursorList<PlaceSubType> placeSubTypes = new CursorList<>(placeTypeCursor, getMapper(placeTypeCursor));
        db.close();
        return placeSubTypes;
    }

    @Override
    public PlaceSubType findById(int placeSubTypeId) {
        SQLiteDatabase db = readableDatabase();
        Cursor placeTypeCursor = db.query(TABLE_NAME, PlaceSubTypeColumn.wildcard(),
                PlaceSubTypeColumn.ID + " =?", new String[]{String.valueOf(placeSubTypeId)}, null, null, null);
        PlaceSubType placeSubType = getPlaceSubType(placeTypeCursor);
        db.close();
        return placeSubType;
    }

    @Override
    public List<PlaceSubType> findByPlaceTypeId(int placeTypeId) {
        SQLiteDatabase db = readableDatabase();
        Cursor placeTypeCursor = db.query(TABLE_NAME, PlaceSubTypeColumn.wildcard(),
                PlaceSubTypeColumn.TYPE_ID + " =?", new String[]{String.valueOf(placeTypeId)}, null, null, null);
        CursorList<PlaceSubType> placeSubTypes = new CursorList<>(placeTypeCursor, getMapper(placeTypeCursor));
        db.close();
        return placeSubTypes;
    }

    @Override
    public int getDefaultPlaceSubTypeId(int placeId) {
        PlaceSubType placeSubType = findByPlaceTypeId(placeId).get(0);
        return placeSubType == null ? -1 : placeSubType.getId();
    }

    private PlaceSubType getPlaceSubType(Cursor cursor) {
        if (cursor.moveToFirst()) {
            PlaceSubType placeSubType = getMapper(cursor).map(cursor);
            cursor.close();
            return placeSubType;
        } else {
            cursor.close();
            return null;
        }
    }

    private CursorMapper<PlaceSubType> getMapper(Cursor cursor) {
        return new PlaceSubTypeCursorMapper(cursor);
    }

    @Override
    public boolean save(PlaceSubType placeType) {
        SQLiteDatabase db = writableDatabase();
        boolean success = saveByContentValues(db, placeTypeContentValues(placeType));
        db.close();
        return success;
    }

    @Override
    public boolean update(PlaceSubType placeType) {
        SQLiteDatabase db = writableDatabase();
        boolean success = updateByContentValues(db, placeTypeContentValues(placeType));
        db.close();
        return success;
    }

    @Override
    public boolean delete(PlaceSubType data) {
        return false;
    }

    @Override
    public void updateOrInsert(List<PlaceSubType> updateList) {
        SQLiteDatabase db = writableDatabase();
        db.beginTransaction();
        for (PlaceSubType eachPlaceSubtype : updateList) {
            ContentValues values = placeTypeContentValues(eachPlaceSubtype);
            boolean updated = updateByContentValues(db, values);
            if (!updated)
                saveByContentValues(db, values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    private boolean updateByContentValues(SQLiteDatabase db, ContentValues placeSubType) {
        return db.update(TABLE_NAME, placeSubType, PlaceSubTypeColumn.ID + "=?",
                new String[]{placeSubType.getAsString(PlaceSubTypeColumn.ID)}) > 0;
    }

    private boolean saveByContentValues(SQLiteDatabase db, ContentValues placeSubType) {
        return db.insert(TABLE_NAME, null, placeSubType) != ERROR_INSERT_ID;
    }

    private ContentValues placeTypeContentValues(PlaceSubType placeSubType) {
        ContentValues values = new ContentValues();
        values.put(PlaceSubTypeColumn.ID, placeSubType.getId());
        values.put(PlaceSubTypeColumn.NAME, placeSubType.getName());
        values.put(PlaceSubTypeColumn.TYPE_ID, placeSubType.getPlaceTypeId());
        return values;
    }
}
