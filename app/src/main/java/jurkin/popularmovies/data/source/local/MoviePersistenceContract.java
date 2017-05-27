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

import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by ajurkin on 5/27/17.
 */

public final class MoviePersistenceContract {

    private MoviePersistenceContract() {}

    public interface MovieColumns {
        String ID = "movie_id";
        String TITLE = "title";
        String OVERVIEW = "overview";
        String POPULARITY = "popularity";
        String RELEASE_DATE = "release_date";
        String POSTER_PATH = "poster_path";
        String BACKDROP_PATH = "backdrop_path";
        String VOTE_COUNT = "vote_count";
        String VOTE_AVERAGE = "vote_average";
    }

    public static abstract class MovieEntry implements BaseColumns, MovieColumns {
        public static final String TABLE_NAME = "movie";
    }

}
