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

package jurkin.popularmovies.ui.moviedetail;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.Calendar;

import javax.inject.Inject;

import jurkin.popularmovies.R;
import jurkin.popularmovies.data.model.Movie;
import jurkin.popularmovies.data.model.Video;
import jurkin.popularmovies.data.repository.MovieRepository;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Andrej Jurkin on 5/21/17.
 */

public class MovieDetailPresenter implements MovieDetailContract.Presenter {
    private static final String TAG = "MovieDetailPresenter";

    private Movie movie;
    private MovieDetailContract.View view;
    private MovieRepository movieRepository;
    private CompositeSubscription subscriptions;

    @Inject
    MovieDetailPresenter(MovieDetailContract.View view, Movie movie, MovieRepository movieRepository) {
        this.view = view;
        this.movie = movie;
        this.movieRepository = movieRepository;
        this.subscriptions = new CompositeSubscription();
    }

    @Override
    public void unsubscribeView() {
        this.subscriptions.unsubscribe();
    }

    @Override
    public void unsubscribeData() {

    }

    @Override
    public void subscribe() {
        this.loadMovieDetails();

        this.view.setMainImage(movie.getFullBackdropPath());
        this.view.setPosterImage(movie.getFullPosterPath());

        this.loadMovieVideos();
        this.loadMovieReviews();
    }

    @Override
    public void onAddToWatchlistClick() {
        if (movie.isInWatchlist()) {
            this.movieRepository.removeFromWatchlist(movie.getId());
            this.view.setWatchlistButtonText(R.string.add_to_watchlist);
        } else {
            this.movieRepository.addToWatchlist(movie.getId());
            this.view.setWatchlistButtonText(R.string.remove_from_watchlist);
        }
    }

    @Override
    public void onVideoClicked(Video video) {
        String videoUrl = String.format("https://www.youtube.com/watch?v=%s", video.getKey());
        view.playVideo(videoUrl);
    }

    private void loadMovieDetails() {
        this.subscriptions.add(movieRepository.getMovie(movie.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movieDetails -> {
                            Log.d(TAG, "Movie details fetched successfully");
                            this.movie = movieDetails;
                            updateMovieDetails();
                        },
                        throwable -> Log.e(TAG, "Failed to fetch movie details: " + throwable)));
    }

    private void loadMovieVideos() {
        this.subscriptions.add(movieRepository.getVideos(movie.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(videos -> {
                    Log.d(TAG, "Videos fetched successfully");
                    this.view.showVideos(videos);
                }, throwable -> {
                    Log.e(TAG, "Failed to fetch videos: " + throwable);
                    // TODO: show error
                }));
    }

    private void loadMovieReviews() {
        this.subscriptions.add(movieRepository.getReviews(movie.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reviews -> {
                    Log.d(TAG, "Reviews fetches successfully");
                    this.view.showReviews(reviews);
                }, throwable -> {
                    Log.e(TAG, "Failed to fetch movie reviews" + throwable);
                    // TODO: show error
                })
        );
    }

    private void updateMovieDetails() {
        this.view.setTitle(movie.getTitle());
        this.view.setDescription(movie.getOverview());
        this.view.setUserRating(String.format("%.1f (%d)",
                movie.getVoteAverage(), movie.getVoteCount()));

        int watchlistButtonText = movie.isInWatchlist() ?
                R.string.remove_from_watchlist : R.string.add_to_watchlist;
        this.view.setWatchlistButtonText(watchlistButtonText);

        Calendar releaseDate = Calendar.getInstance();
        releaseDate.setTime(movie.getReleaseDate());

        String summary = String.format("%s, %s, %s mins",
                movie.getOriginalLanguage().toUpperCase(),
                releaseDate.get(Calendar.YEAR),
                movie.getRuntime());

        this.view.setMovieDetailSummary(summary);
    }
}
