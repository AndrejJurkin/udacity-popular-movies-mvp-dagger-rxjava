/*
 * Copyright 2017 Andrej Jurkin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jurkin.popularmovies.data.repository.local;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;

import jurkin.popularmovies.App;
import jurkin.popularmovies.data.repository.local.MovieDbHelper.Tables;

import static jurkin.popularmovies.data.repository.local.MovieContract.*;

/**
 * Created by Andrej Jurkin on 6/4/17.
 */

public class MovieContentProvider extends ContentProvider {

    private static final int MOVIES = 100;
    private static final int MOVIE_ID = 101;

    private static UriMatcher uriMatcher = buildUriMatcher();

    private MovieDbHelper dbHelper;

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_MOVIES, MOVIES);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_MOVIES + "/#", MOVIE_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;

        switch (uriMatcher.match(uri)) {
            case MOVIES:
                // Falls through
            case MOVIE_ID:
                cursor = db.query(
                        Tables.MOVIES,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (getContext() != null)  {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case MOVIES:
                return MovieEntry.CONTENT_TYPE;
            case MOVIE_ID:
                return MovieEntry.CONTENT_TYPE_ITEM;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (values == null) {
            return null;
        }

        Uri resultUri;

        switch (uriMatcher.match(uri)) {
            case MOVIES:
                    db.insertOrThrow(Tables.MOVIES, null, values);
                    resultUri = MovieEntry.buildMovieUri(values.getAsLong(MovieEntry.MOVIE_ID));
                break;
            default:
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
        }

        if (getContext() != null)  {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return resultUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows;

        switch (uriMatcher.match(uri)) {
            case MOVIES:
                deletedRows = db.delete(Tables.MOVIES, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
        }

        if (deletedRows > 0 && getContext() != null)  {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deletedRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows;

        switch (uriMatcher.match(uri)) {
            case MOVIES:
                updatedRows = db.update(Tables.MOVIES, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
        }

        if (updatedRows > 0 && getContext() != null)  {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updatedRows;
    }
}
