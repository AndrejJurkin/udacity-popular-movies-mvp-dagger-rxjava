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

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.Date;

import javax.inject.Inject;

import jurkin.popularmovies.data.repository.local.MovieContract.MovieEntry;
import jurkin.popularmovies.data.repository.local.MovieContract.ReviewEntry;
import jurkin.popularmovies.data.repository.local.MovieContract.VideoEntry;

/**
 * Created by Andrej Jurkin on 5/27/17.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "PopularMovies.db";
    private static int DB_VERSION = 1;

    private static final int BOOLEAN_FALSE = 0;
    private static final int BOOLEAN_TRUE = 1;

    interface Tables {
        String MOVIES = "movies";
        String REVIEWS = "reviews";
        String VIDEOS = "videos";
    }

    interface References {
        String MOVIE_ID = "REFERENCES " + Tables.MOVIES + "(" + MovieEntry.MOVIE_ID + ")";
    }

    @Inject
    public MovieDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.MOVIES + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MovieEntry.MOVIE_ID + " INTEGER NOT NULL,"
                + MovieEntry.MOVIE_TITLE + " TEXT,"
                + MovieEntry.MOVIE_OVERVIEW + " TEXT,"
                + MovieEntry.MOVIE_POPULARITY + " REAL,"
                + MovieEntry.MOVIE_RELEASE_DATE + " INTEGER, "
                + MovieEntry.MOVIE_POSTER_PATH + " TEXT,"
                + MovieEntry.MOVIE_BACKDROP_PATH + " TEXT,"
                + MovieEntry.MOVIE_VOTE_COUNT + " INTEGER,"
                + MovieEntry.MOVIE_VOTE_AVERAGE + " REAL,"
                + MovieEntry.MOVIE_RUNTIME + " INTEGER,"
                + MovieEntry.MOVIE_ORIGINAL_LANGUAGE + " TEXT,"
                + MovieEntry.MOVIE_IN_WATCHLIST + " INTEGER NOT NULL DEFAULT 0,"
                + "UNIQUE (" + MovieEntry.MOVIE_ID + ") ON CONFLICT REPLACE)"
        );

        db.execSQL("CREATE TABLE " + Tables.REVIEWS + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ReviewEntry.REVIEW_ID + " TEXT NOT NULL,"
                + MovieEntry.MOVIE_ID + " INTEGER NOT NULL " + References.MOVIE_ID + ","
                + ReviewEntry.REVIEW_AUTHOR + " TEXT,"
                + ReviewEntry.REVIEW_CONTENT + "TEXT,"
                + ReviewEntry.REVIEW_URL + " TEXT,"
                + "UNIQUE (" + ReviewEntry.REVIEW_ID + ") ON CONFLICT REPLACE)"
        );

        db.execSQL("CREATE TABLE " + Tables.VIDEOS + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + VideoEntry.VIDEO_ID + " TEXT NOT NULL,"
                + MovieEntry.MOVIE_ID + " INTEGER NOT NULL " + References.MOVIE_ID + ","
                + VideoEntry.VIDEO_KEY + " TEXT,"
                + VideoEntry.VIDEO_NAME + " TEXT,"
                + VideoEntry.VIDEO_SITE + " TEXT,"
                + VideoEntry.VIDEO_SIZE + " INTEGER,"
                + VideoEntry.VIDEO_TYPE + " TEXT,"
                + " UNIQUE (" + VideoEntry.VIDEO_ID + ") ON CONFLICT REPLACE)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tables.MOVIES);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.REVIEWS);
        db.execSQL("DROP TABLE IF EXISTS " + Tables.VIDEOS);
        onCreate(db);
    }

    public static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    public static boolean getBoolean(Cursor cursor, String columnName) {
        return getInt(cursor, columnName) == BOOLEAN_TRUE;
    }

    public static long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
    }

    public static int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }

    public static Date getDate(Cursor cursor, String columnName) {
        return new Date(getLong(cursor, columnName));
    }
}
