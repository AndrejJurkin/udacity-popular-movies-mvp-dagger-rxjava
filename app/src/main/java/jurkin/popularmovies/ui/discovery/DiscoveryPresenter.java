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

import javax.inject.Inject;

import jurkin.popularmovies.R;
import jurkin.popularmovies.data.model.Movie;
import jurkin.popularmovies.data.repository.MovieRepository;
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

    private MovieRepository movieRepository;
    private CompositeSubscription subscriptions;

    @Inject
    DiscoveryPresenter(DiscoveryContract.View view, MovieRepository movieRepository) {
        this.view = view;
        this.subscriptions = new CompositeSubscription();
        this.movieRepository = movieRepository;
    }

    @Override
    public void subscribe() {
        loadPopularMovies();
        view.setActionBarTitle(R.string.sort_most_popular);
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void unsubscribeDataSubscriptions() {
        this.subscriptions.unsubscribe();
    }

    @Override
    public void onMovieClicked(Movie movie) {
        this.view.showDetail(movie);
    }

    @Override
    public void onPopularMoviesClicked() {
        loadPopularMovies();
        view.setActionBarTitle(R.string.sort_most_popular);
    }

    @Override
    public void onTopRatedMoviesClicked() {
        loadTopRatedMovies();
        view.setActionBarTitle(R.string.sort_top_rated);
    }

    @Override
    public void onWatchlistClick() {
        loadWatchlist();
        view.setActionBarTitle(R.string.watchlist);
    }

    private void loadPopularMovies() {
        // TODO: show loading indicator
        subscriptions.add(movieRepository.getPopularMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> {
                    Log.d(TAG, "Popular data fetched successfully");
                    view.showMovies(movies);
                }, throwable -> {
                    Log.e(TAG, "Failed to fetch popular movies " + throwable);
                    // TODO: view.showError
                }));
    }

    private void loadTopRatedMovies() {
        // TODO: show loading indicator
        subscriptions.add(movieRepository.getTopRatedMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> {
                    Log.d(TAG, "Top rated movies fetched successfully.");
                    view.showMovies(movies);
                }, throwable -> {
                    Log.e(TAG, "Failed to fetch popular movies" + throwable);
                    // TODO: view.showErro
                }));
    }

    private void loadWatchlist() {

    }
}
