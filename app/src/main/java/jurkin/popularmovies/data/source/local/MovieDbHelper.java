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

package jurkin.popularmovies.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import jurkin.popularmovies.data.source.local.MoviePersistenceContract.MovieEntry;

/**
 * Created by ajurkin on 5/27/17.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "PopularMovies.db";
    private static int DB_VERSION = 1;

    interface Tables {
        String MOVIES = "movies";
    }

    public MovieDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.MOVIES + " ("
                + BaseColumns._ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MovieEntry.ID + " INTEGER NOT NULL,"
                + MovieEntry.TITLE + " TEXT,"
                + MovieEntry.OVERVIEW + " TEXT,"
                + MovieEntry.POPULARITY + " REAL,"
                + MovieEntry.RELEASE_DATE + " INTEGER, "
                + MovieEntry.POSTER_PATH + " TEXT,"
                + MovieEntry.BACKDROP_PATH + " TEXT,"
                + MovieEntry.VOTE_COUNT + " INTEGER,"
                + MovieEntry.VOTE_AVERAGE + " REAL,"
                + MovieEntry.RUNTIME + " INTEGER,"
                + MovieEntry.ORIGINAL_LANGUAGE + " TEXT,"
                + "UNIQUE (" + MovieEntry.ID + ") ON CONFLICT REPLACE)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
