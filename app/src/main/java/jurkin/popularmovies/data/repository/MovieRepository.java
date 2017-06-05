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

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import jurkin.popularmovies.data.model.Movie;
import jurkin.popularmovies.data.model.MovieReview;
import jurkin.popularmovies.data.model.Video;
import jurkin.popularmovies.data.repository.cache.CacheDataSource;
import jurkin.popularmovies.data.repository.local.MovieLocalDataSource;
import jurkin.popularmovies.data.repository.remote.MovieRemoteDataSource;
import rx.Observable;

/**
 * Created by Andrej Jurkin on 5/28/17.
 */

@Singleton
public final class MovieRepository {
    private static final String TAG = "MovieRepository";

    private MovieDataSource remoteData;
    private MovieDataSource localData;
    private MovieDataSource cacheData;

    @Inject
    MovieRepository(MovieRemoteDataSource remoteDataSource, MovieLocalDataSource localDataSource,
                    CacheDataSource cacheDataSource) {
        this.remoteData = remoteDataSource;
        this.localData = localDataSource;
        this.cacheData = cacheDataSource;
    }

    @NonNull
    public Observable<List<Movie>> getPopularMovies() {
        
        Observable<List<Movie>> cachedRemote = remoteData.getPopularMovies()
                .doOnNext(movies -> {
                    localData.saveMovies(movies);
                    cacheData.saveMovies(movies);
                });

        Observable<List<Movie>> cachedLocal = localData.getPopularMovies()
                .doOnNext(movies -> cacheData.saveMovies(movies));

        return Observable.concat(cacheData.getPopularMovies(), cachedLocal, cachedRemote).first();
    }

    @NonNull
    public Observable<List<Movie>> getTopRatedMovies() {
        return remoteData.getTopRatedMovies();
    }

    @NonNull
    public Observable<Movie> getMovie(long movieId) {
        return remoteData.getMovie(movieId);
    }

    @NonNull
    public Observable<List<Video>> getVideos(long movieId) {
        return remoteData.getVideos(movieId);
    }

    @NonNull
    public Observable<List<MovieReview>> getReviews(long movieId) {
        return remoteData.getReviews(movieId);
    }

    @NonNull
    public Observable<List<Movie>> getWatchlist() {
        return Observable.error(new DataSourceNotSupportedException());
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
