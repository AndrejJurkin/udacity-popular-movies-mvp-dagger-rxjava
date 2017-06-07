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

package jurkin.popularmovies.data.repository.cache;

import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import jurkin.popularmovies.data.model.Movie;
import jurkin.popularmovies.data.model.MovieReview;
import jurkin.popularmovies.data.model.Video;
import jurkin.popularmovies.data.repository.MovieDataSource;
import rx.Observable;

/**
 * Created by Andrej Jurkin on 6/5/17.
 */

public class CacheDataSource implements MovieDataSource {

    // Dummy cache
    private List<Movie> moviesCache;

    @Inject
    public CacheDataSource() {

    }

    @Override
    public Observable<List<Movie>> getPopularMovies() {
        return Observable.just(moviesCache);
    }

    @Override
    public Observable<List<Movie>> getTopRatedMovies() {
        return Observable.just(moviesCache);
    }

    @Override
    public Observable<Movie> getMovie(long movieId) {
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
        return null;
    }

    @Override
    public Observable<Void> removeFromWatchlist(long movieId) {
        return null;
    }

    @Override
    public Observable<Void> saveMovies(List<Movie> movies) {
        return null;
    }

    @Override
    public Observable<Void> saveMovie(Movie movie) {
        return null;
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