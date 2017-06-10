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

package jurkin.popularmovies.data.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Singleton;

import jurkin.popularmovies.data.model.Movie;
import jurkin.popularmovies.data.model.MovieReview;
import jurkin.popularmovies.data.model.Video;
import jurkin.popularmovies.data.repository.local.MovieLocalDataSource;
import jurkin.popularmovies.data.repository.remote.MovieRemoteDataSource;
import rx.Observable;

/**
 * Created by Andrej Jurkin on 5/28/17.
 */

@Singleton
public final class MovieRepository {

    private MovieDataSource remoteData;
    private MovieDataSource localData;

    @Inject
    MovieRepository(MovieRemoteDataSource remoteDataSource, MovieLocalDataSource localDataSource) {
        this.remoteData = remoteDataSource;
        this.localData = localDataSource;
    }

    @NonNull
    public Observable<List<Movie>> getPopularMovies() {
        return remoteData.getPopularMovies()
                .doOnNext(
                        // Cache movies into local db
                        movies -> localData.saveMovies(movies))
                .onErrorResumeNext(
                        // If remote request fails, get cached data from db
                        throwable -> localData.getPopularMovies());
    }

    @NonNull
    public Observable<List<Movie>> getTopRatedMovies() {
        return remoteData.getTopRatedMovies()
                .doOnNext(movies -> localData.saveMovies(movies))
                .onErrorResumeNext(throwable -> localData.getTopRatedMovies());
    }

    @NonNull
    public Observable<Movie> getMovie(long movieId) {
        remoteData.getMovie(movieId)
                .subscribe(movie -> localData.saveMovie(movie), throwable -> {});

        return localData.getMovie(movieId);
    }

    @NonNull
    public Observable<List<Video>> getVideos(long movieId) {
        return remoteData.getVideos(movieId)
                .doOnNext(videos -> localData.saveVideos(videos))
                .onErrorResumeNext(throwable -> localData.getVideos(movieId));
    }

    @NonNull
    public Observable<List<MovieReview>> getReviews(long movieId) {
        return remoteData.getReviews(movieId)
                .doOnNext(reviews -> localData.saveReviews(reviews))
                .onErrorResumeNext(throwable -> localData.getReviews(movieId));
    }

    @NonNull
    public Observable<List<Movie>> getWatchlist() {
        return localData.getWatchlist();
    }

    @NonNull
    public Observable<Void> addToWatchlist(long movieId) {
        return localData.addToWatchlist(movieId);
    }

    @NonNull
    public Observable<Void> removeFromWatchlist(long movieId) {
        return localData.removeFromWatchlist(movieId);
    }
}
