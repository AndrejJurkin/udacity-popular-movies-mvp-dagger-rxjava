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

package jurkin.popularmovies.data.model;

import android.database.Cursor;

import jurkin.popularmovies.data.repository.MovieRepository;
import jurkin.popularmovies.data.repository.local.MovieContract;
import jurkin.popularmovies.data.repository.local.MovieDbHelper;
import rx.functions.Func1;

import static jurkin.popularmovies.data.repository.local.MovieContract.*;

/**
 * Created by Andrej Jurkin on 5/25/17.
 */

public class MovieReview {

    public static final Func1<Cursor, MovieReview> MAPPER = cursor -> {
        MovieReview review = new MovieReview();
        review.setId(MovieDbHelper.getString(cursor, ReviewEntry.REVIEW_ID));
        review.setAuthor(MovieDbHelper.getString(cursor, ReviewEntry.REVIEW_AUTHOR));
        review.setContent(MovieDbHelper.getString(cursor, ReviewEntry.REVIEW_CONTENT));
        review.setUrl(MovieDbHelper.getString(cursor, ReviewEntry.REVIEW_URL));
        return review;
    };

    private String id;

    private String author;

    private String content;

    private String url;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
