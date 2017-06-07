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
    private static final int MOVIE_ID_REVIEWS = 102;
    private static final int MOVIE_ID_VIDEOS = 103;

    private static final int REVIEWS = 200;
    private static final int REVIEW_ID = 201;

    private static final int VIDEOS = 300;
    private static final int VIDEO_ID = 301;

    private static UriMatcher uriMatcher = buildUriMatcher();

    @Inject
    MovieDbHelper dbHelper;

    @Inject
    ContentResolver contentResolver;

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Movies
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_MOVIES, MOVIES);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_MOVIES + "/#", MOVIE_ID);
        uriMatcher.addURI(CONTENT_AUTHORITY,
                PATH_MOVIES + "/" + PATH_REVIEWS + "/*", MOVIE_ID_REVIEWS);
        uriMatcher.addURI(CONTENT_AUTHORITY,
                PATH_MOVIES + "/" + PATH_VIDEOS + "/*", MOVIE_ID_VIDEOS);

        // Reviews
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_REVIEWS, REVIEWS);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_REVIEWS + "/*", REVIEW_ID);

        // Videos
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_VIDEOS, VIDEOS);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_VIDEOS + "/*", VIDEO_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
//        App.get().getAppComponent().inject(this);

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case MOVIES:
                return MovieEntry.CONTENT_TYPE;
            case MOVIE_ID:
                return MovieEntry.CONTENT_TYPE_ITEM;
            case MOVIE_ID_VIDEOS:
                return VideoEntry.CONTENT_TYPE;
            case MOVIE_ID_REVIEWS:
                return ReviewEntry.CONTENT_TYPE;
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
            case REVIEWS:
                db.insertOrThrow(Tables.REVIEWS, null, values);
                resultUri = ReviewEntry.buildReviewUri(values.getAsString(ReviewEntry.REVIEW_ID));
                break;
            case VIDEOS:
                db.insertOrThrow(Tables.VIDEOS, null, values);
                resultUri = ReviewEntry.buildReviewUri(values.getAsString(VideoEntry.VIDEO_ID));
                break;
            default:
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
        }

        contentResolver.notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
