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

import jurkin.popularmovies.data.repository.local.MovieContract.MovieEntry;
import jurkin.popularmovies.data.repository.local.MovieDbHelper.Tables;

/**
 * Created by Andrej Jurkin on 6/5/17.
 */

interface Query {

    String POPULAR_MOVIES = "SELECT * FROM " + Tables.MOVIES
            + " ORDER BY " + MovieEntry.MOVIE_POPULARITY + " DESC";

    String TOP_RATED = "SELECT * FROM " + Tables.MOVIES
            + " ORDER BY " + MovieEntry.MOVIE_VOTE_AVERAGE + " DESC";

    String WATCHLIST = "SELECT * FROM " + Tables.MOVIES
            + " WHERE " + MovieEntry.MOVIE_IN_WATCHLIST + " = ? ";

    String MOVIE_ID = "SELECT * FROM " + Tables.MOVIES
            + " WHERE " + MovieEntry.MOVIE_ID + " = ? ";

    String REVIEWS_MOVIE_ID = "SELECT * FROM " + Tables.REVIEWS
            + " WHERE " + MovieEntry.MOVIE_ID + " = ? ";

    String VIDEOS_MOVIE_ID = "SELECT * FROM " + Tables.VIDEOS
            + " WHERE " + MovieEntry.MOVIE_ID + " = ? ";

}
