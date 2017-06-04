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

package jurkin.popularmovies.data.repository.remote;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import jurkin.popularmovies.api.MovieService;
import jurkin.popularmovies.data.model.Movie;
import jurkin.popularmovies.data.model.MovieReview;
import jurkin.popularmovies.data.model.Video;
import jurkin.popularmovies.data.repository.DataSourceNotSupportedException;
import jurkin.popularmovies.data.repository.MovieDataSource;
import rx.Observable;

/**
 * Created by Andrej Jurkin on 5/28/17.
 */

@Singleton
public final class MovieRemoteDataSource implements MovieDataSource {

    private MovieService movieService;

    @Inject
    MovieRemoteDataSource(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public Observable<List<Movie>> getPopularMovies() {
        return movieService.getPopularMovies()
                .flatMap(movieResponse -> Observable.just(movieResponse.getResults()));
    }

    @Override
    public Observable<List<Movie>> getTopRatedMovies() {
        return movieService.getTopRatedMovies()
                .flatMap(movieResponse -> Observable.just(movieResponse.getResults()));
    }

    @Override
    public Observable<Movie> getMovie(long movieId) {
        return movieService.getMovie(movieId);
    }

    @Override
    public Observable<List<Video>> getVideos(long movieId) {
        return movieService.getVideos(movieId)
                .flatMap(movieVideosResponse -> Observable.just(movieVideosResponse.getVideos()));
    }

    @Override
    public Observable<List<MovieReview>> getReviews(long movieId) {
        return movieService.getMovieReviews(movieId)
                .flatMap(movieReviewsResponse -> Observable.just(movieReviewsResponse.getReviews()));
    }

    @Override
    public Observable<List<Movie>> getWatchlist() {
        return Observable.error(new DataSourceNotSupportedException());
    }

    @Override
    public Observable<Void> addToWatchlist(long movieId) {
        return Observable.error(new DataSourceNotSupportedException());
    }

    @Override
    public Observable<Void> removeFromWatchlist(long movieId) {
        return Observable.error(new DataSourceNotSupportedException());
    }
}
