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

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Andrej Jurkin on 5/27/17.
 */

public final class MovieContract {

    public static final String CONTENT_AUTHORITY = "jurkin.popularmovies.provider";
    public static final Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String CONTENT_TYPE_APP = "popularmovies.provider";
    private static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd." + CONTENT_TYPE_APP;
    private static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/vnd." + CONTENT_TYPE_APP;

    public static final String PATH_MOVIES = "movies";

    interface MovieColumns {
        String MOVIE_ID = "movie_movie_id";
        String MOVIE_TITLE = "movie_title";
        String MOVIE_OVERVIEW = "movie_overview";
        String MOVIE_POPULARITY = "movie_popularity";
        String MOVIE_RELEASE_DATE = "movie_release_date";
        String MOVIE_POSTER_PATH = "movie_poster_path";
        String MOVIE_BACKDROP_PATH = "movie_backdrop_path";
        String MOVIE_VOTE_COUNT = "movie_vote_count";
        String MOVIE_VOTE_AVERAGE = "movie_vote_average";
        String MOVIE_ORIGINAL_LANGUAGE = "movie_original_language";
        String MOVIE_RUNTIME = "movie_runtime";
        String MOVIE_IN_WATCHLIST = "movie_in_watchlist";
    }

    interface ReviewColumns {
        String REVIEW_ID = "review_id";
        String REVIEW_AUTHOR = "review_author";
        String REVIEW_CONTENT = "review_content";
        String REVIEW_URL = "review_url";
    }

    interface VideoColumns {
        String VIDEO_ID = "video_id";
        String VIDEO_KEY = "video_key";
        String VIDEO_NAME = "video_name";
        String VIDEO_SITE = "video_site";
        String VIDEO_SIZE = "video_size";
        String VIDEO_TYPE = "video_type";
    }

    public static class MovieEntry implements BaseColumns, MovieColumns {
        static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH_MOVIES).build();

        static final String CONTENT_TYPE = makeContentDirType("movie");
        static final String CONTENT_TYPE_ITEM = makeContentItemType("movie");

        static Uri buildMovieUri(long movieId) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(movieId)).build();
        }
    }

    public static class ReviewEntry implements BaseColumns, ReviewColumns, MovieColumns {

    }

    public static class VideoEntry implements BaseColumns, VideoColumns, MovieColumns {

    }

    private static String makeContentDirType(String id) {
        if (id != null) {
            return CONTENT_TYPE_DIR + id;
        } else {
            return null;
        }
    }

    private static String makeContentItemType(String id) {
        if (id != null) {
            return CONTENT_TYPE_ITEM + id;
        } else {
            return null;
        }
    }

    private MovieContract() {}
}
