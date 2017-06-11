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

import android.content.ContentValues;

import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import jurkin.popularmovies.data.model.Movie;
import jurkin.popularmovies.data.model.MovieReview;
import jurkin.popularmovies.data.model.Video;
import jurkin.popularmovies.data.repository.MovieDataSource;
import jurkin.popularmovies.data.repository.local.MovieContract.MovieEntry;
import jurkin.popularmovies.data.repository.local.Query.Clause;
import rx.Observable;

import static jurkin.popularmovies.data.repository.local.MovieDbHelper.*;

/**
 * Created by Andrej Jurkin on 5/28/17.
 */

@Singleton
public class MovieLocalDataSource implements MovieDataSource {

    private BriteDatabase db;

    @Inject
    public MovieLocalDataSource(BriteDatabase db) {
        this.db = db;
    }

    @Override
    public Observable<List<Movie>> getPopularMovies() {
        return db.createQuery(Tables.MOVIES, Query.POPULAR_MOVIES)
                .mapToList(Movie.MAPPER)
                .flatMap(Observable::just);
    }

    @Override
    public Observable<List<Movie>> getTopRatedMovies() {
        return db.createQuery(Tables.MOVIES, Query.TOP_RATED)
                .mapToList(Movie.MAPPER)
                .flatMap(Observable::just);
    }

    @Override
    public Observable<Movie> getMovie(long movieId) {
        return db.createQuery(Tables.MOVIES, Query.MOVIE_ID, String.valueOf(movieId))
                .mapToOne(Movie.MAPPER)
                .flatMap(Observable::just);
    }

    @Override
    public Observable<List<Video>> getVideos(long movieId) {
        return db.createQuery(Tables.VIDEOS, Query.VIDEOS_MOVIE_ID, String.valueOf(movieId))
                .mapToList(Video.MAPPER)
                .flatMap(Observable::just);
    }

    @Override
    public Observable<List<MovieReview>> getReviews(long movieId) {
        return db.createQuery(Tables.MOVIES, Query.REVIEWS_MOVIE_ID, String.valueOf(movieId))
                .mapToList(MovieReview.MAPPER)
                .flatMap(Observable::just);
    }

    @Override
    public Observable<List<Movie>> getWatchlist() {
        return db.createQuery(Tables.MOVIES, Query.WATCHLIST)
                .mapToList(Movie.MAPPER)
                .flatMap(Observable::just)
                .first();
    }

    @Override
    public Observable<Void> addToWatchlist(long movieId) {
        ContentValues cv = new ContentValues();
        cv.put(MovieEntry.MOVIE_IN_WATCHLIST, 1);
        String[] args = { String.valueOf(movieId) };

        db.update(Tables.MOVIES, cv, MovieEntry.MOVIE_ID + " = ? ", args);

        return Observable.just(null);
    }

    @Override
    public Observable<Void> removeFromWatchlist(long movieId) {
        ContentValues cv = new ContentValues();

        cv.put(MovieEntry.MOVIE_IN_WATCHLIST, 0);
        String[] args = { String.valueOf(movieId) };

        db.update(Tables.MOVIES, cv, MovieEntry.MOVIE_ID + " = ? ", args);

        return Observable.just(null);
    }

    @Override
    public Observable<Void> saveMovies(List<Movie> movies) {
        BriteDatabase.Transaction transaction = db.newTransaction();

        try {
            movies.forEach(this::saveMovie);
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }

        return Observable.just(null);
    }

    @Override
    public Observable<Void> saveMovie(Movie movie) {
        ContentValues cv = Movie.toContentValues(movie);
        long id = db.insert(Tables.MOVIES, cv);

        if (id == -1) {
            db.update(Tables.MOVIES, cv, Clause.EQUALS_MOVIE_ID, movie.getByIdArgs());
        }

        return Observable.just(null);
    }

    @Override
    public Observable<Void> saveReviews(List<MovieReview> reviews) {
        return null;
    }

    @Override
    public Observable<Void> saveReview(MovieReview review) {
        return null;
    }

    @Override
    public Observable<Void> saveVideos(List<Video> videos) {
        return null;
    }

    @Override
    public Observable<Void> saveVideo(Video video) {
        return null;
    }


}
