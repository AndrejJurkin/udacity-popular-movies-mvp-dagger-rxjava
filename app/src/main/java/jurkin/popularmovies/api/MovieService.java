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

package jurkin.popularmovies.api;

import jurkin.popularmovies.data.model.MovieDetails;
import jurkin.popularmovies.data.model.MovieResponse;
import jurkin.popularmovies.data.model.MovieReviewsResponse;
import jurkin.popularmovies.data.model.MovieVideosResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ajurkin on 5/12/17.
 */

public interface MovieService {

    @GET("movie/popular")
    Observable<MovieResponse> getPopularMovies();

    @GET("movie/top_rated")
    Observable<MovieResponse> getTopRatedMovies();

    @GET("movie/{id}/videos")
    Observable<MovieVideosResponse> getVideos(@Path("id") long id);

    @GET("movie/{id}/reviews")
    Observable<MovieReviewsResponse> getMovieReviews(@Path("id") long id);

    @GET("movie/{id}")
    Observable<MovieDetails> getMovieDetails(@Path("id") long id);

}
