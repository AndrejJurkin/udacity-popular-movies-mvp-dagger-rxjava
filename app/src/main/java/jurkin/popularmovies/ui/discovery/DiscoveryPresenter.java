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

package jurkin.popularmovies.ui.discovery;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import jurkin.popularmovies.R;
import jurkin.popularmovies.api.MovieService;
import jurkin.popularmovies.data.model.Movie;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ajurkin on 5/13/17.
 *
 * TODO: Add a data source layer
 */
class DiscoveryPresenter implements DiscoveryContract.Presenter {
    private static final String TAG = "DiscoveryPresenter";

    private DiscoveryContract.View view;
    private MovieService movieService;
    private CompositeSubscription subscriptions;

    private List<Movie> popularMovies;
    private List<Movie> topRatedMovies;

    @Inject
    DiscoveryPresenter(DiscoveryContract.View view, MovieService movieService) {
        this.view = view;
        this.movieService = movieService;
        this.subscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        if (popularMovies == null) {
            fetchPopularMovies(true);
            view.setActionBarTitle(R.string.sort_most_popular);
        }
    }

    @Override
    public void unsubscribe() {
        this.subscriptions.unsubscribe();
    }

    @Override
    public void onMovieClicked(Movie movie) {
        this.view.showDetail(movie);
    }

    @Override
    public void onPopularMoviesClicked() {
        fetchPopularMovies(false);
        view.setActionBarTitle(R.string.sort_most_popular);
    }

    @Override
    public void onTopRatedMoviesClicked() {
        fetchTopRatedMovies(false);
        view.setActionBarTitle(R.string.sort_top_rated);
    }

    private void fetchPopularMovies(boolean forceRefresh) {
        // TODO: show loading indicator

        if (popularMovies == null || forceRefresh) {
            Subscription getPopularMoviesSubscription = movieService.getPopularMovies()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            movieResponse -> {
                                Log.d(TAG, "Popular data fetched successfully");
                                popularMovies = movieResponse.getResults();
                                view.showMovies(popularMovies);
                            },
                            throwable -> {
                                Log.e(TAG, "Failed to fetch popular movies " + throwable);
                                // TODO: view.showError
                            });

            subscriptions.add(getPopularMoviesSubscription);
        } else {
            view.showMovies(popularMovies);
        }
    }

    private void fetchTopRatedMovies(boolean forceRefresh) {
        // TODO: show loading indicator

        if (topRatedMovies == null || forceRefresh) {
            Subscription getTopRatedMoviesSubscription = movieService.getTopRatedMovies()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            movieResponse -> {
                                Log.d(TAG, "Top rated movies fetched successfully");
                                topRatedMovies = movieResponse.getResults();
                                view.showMovies(topRatedMovies);
                            },
                            throwable -> {
                                Log.e(TAG, "Failed to fetch top rated movies " + throwable);
                                // TODO: view.showError
                            });

            subscriptions.add(getTopRatedMoviesSubscription);
        } else {
            view.showMovies(topRatedMovies);
        }
    }
}
