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

import android.content.ContentResolver;
import android.content.ContentValues;
import android.text.method.MovementMethod;

import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import jurkin.popularmovies.data.model.Movie;
import jurkin.popularmovies.data.model.MovieDetails;
import jurkin.popularmovies.data.model.MovieResponse;
import jurkin.popularmovies.data.model.MovieReview;
import jurkin.popularmovies.data.model.Video;
import jurkin.popularmovies.data.repository.MovieDataSource;
import jurkin.popularmovies.data.repository.MovieRepository;
import jurkin.popularmovies.data.repository.local.MoviePersistenceContract.MovieEntry;
import rx.Observable;

/**
 * Created by Andrej Jurkin on 5/28/17.
 */

@Singleton
public class MovieLocalDataSource implements MovieDataSource {

    private BriteDatabase db;
    private ContentResolver contentResolver;

    @Inject
    public MovieLocalDataSource(BriteDatabase db, ContentResolver contentResolver) {
        this.db = db;
        this.contentResolver = contentResolver;
    }

    @Override
    public Observable<List<Movie>> getPopularMovies() {
        return null;
    }

    @Override
    public Observable<List<Movie>> getTopRatedMovies() {
        return null;
    }

    @Override
    public Observable<MovieDetails> getMovieDetails(long movieId) {
        return null;
    }

    @Override
    public Observable<List<Video>> getVideos(long movieId) {
        return null;
    }

    @Override
    public Observable<List<MovieReview>> getReviews(long movieId) {
        return null;
    }

    @Override
    public Observable<List<Movie>> getWatchlist() {
        return null;
    }

    @Override
    public Observable<Void> addToWatchlist(long movieId) {
        ContentValues cv = new ContentValues();
        cv.put(MovieEntry.MOVIE_IN_WATCHLIST, 1);
        String[] args = { String.valueOf(movieId) };

        db.update(MovieDbHelper.Tables.MOVIES, cv, MovieEntry.MOVIE_ID + "=?", args);

        return Observable.just(null);
    }

    @Override
    public Observable<Void> removeFromWatchlist(long movieId) {
        ContentValues cv = new ContentValues();
        cv.put(MovieEntry.MOVIE_IN_WATCHLIST, 0);
        String[] args = { String.valueOf(movieId) };

        db.update(MovieDbHelper.Tables.MOVIES, cv, MovieEntry.MOVIE_ID + "=?", args);

        return Observable.just(null);
    }
}
