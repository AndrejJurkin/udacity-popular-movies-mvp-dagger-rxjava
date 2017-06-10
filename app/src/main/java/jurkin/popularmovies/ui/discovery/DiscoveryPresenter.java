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
import jurkin.popularmovies.data.model.Movie;
import jurkin.popularmovies.data.repository.MovieRepository;
import jurkin.popularmovies.ui.moviedetail.MovieDetailContract;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ajurkin on 5/13/17.
 */
class DiscoveryPresenter implements DiscoveryContract.Presenter {
    private static final String TAG = "DiscoveryPresenter";

    private static final int CONTENT_TYPE_POPULAR_MOVIES = 1;
    private static final int CONTENT_TYPE_TOP_RATED = 2;
    private static final int CONTENT_TYPE_WATCHLIST = 4;

    private DiscoveryContract.View view;
    private MovieRepository movieRepository;
    private CompositeSubscription subscriptions;

    private boolean isViewSubscribed;
    private boolean isViewLoaded;

    private int contentType;

    @Inject
    DiscoveryPresenter(DiscoveryContract.View view, MovieRepository movieRepository) {
        this.subscriptions = new CompositeSubscription();
        this.movieRepository = movieRepository;
        this.view = view;
        this.contentType = CONTENT_TYPE_POPULAR_MOVIES;
    }


    @Override
    public void setView(DiscoveryContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        isViewSubscribed = true;

        if (!isViewLoaded) {
            isViewLoaded = true;
            loadContent();
            view.setActionBarTitle(R.string.sort_most_popular);
        }
    }

    @Override
    public void unsubscribeView() {
        isViewSubscribed = false;
    }

    @Override
    public void unsubscribeData() {
        this.subscriptions.clear();
    }

    @Override
    public void onMovieClicked(Movie movie) {
        this.view.showDetail(movie);
    }

    @Override
    public void onPopularMoviesClicked() {
        loadPopularMovies();
        view.setActionBarTitle(R.string.sort_most_popular);
        this.contentType = CONTENT_TYPE_POPULAR_MOVIES;

    }

    @Override
    public void onTopRatedMoviesClicked() {
        loadTopRatedMovies();
        view.setActionBarTitle(R.string.sort_top_rated);
        this.contentType = CONTENT_TYPE_TOP_RATED;
    }

    @Override
    public void onWatchlistClick() {
        loadWatchlist();
        view.setActionBarTitle(R.string.watchlist);
        this.contentType = CONTENT_TYPE_WATCHLIST;

    }

    @Override
    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    @Override
    public int getContentType() {
        return contentType;
    }

    private void loadContent() {
        switch (contentType) {
            case CONTENT_TYPE_POPULAR_MOVIES:
                loadPopularMovies();
                break;
            case CONTENT_TYPE_TOP_RATED:
                loadTopRatedMovies();
                break;
            case CONTENT_TYPE_WATCHLIST:
                loadWatchlist();
            default:
                break;
        }
    }

    private void loadPopularMovies() {
        subscriptions.add(movieRepository.getPopularMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .filter(movies -> isViewSubscribed)
                .subscribe(movies -> {
                    Log.d(TAG, "Popular data fetched successfully");
                    view.showMovies(movies);
                }, throwable -> {
                    Log.e(TAG, "Failed to fetch popular movies " + throwable);
                    // TODO: view.showError
                }));
    }

    private void loadTopRatedMovies() {
        subscriptions.add(movieRepository.getTopRatedMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .filter(movies -> isViewSubscribed)
                .subscribe(movies -> {
                    Log.d(TAG, "Top rated movies fetched successfully.");
                    view.showMovies(movies);
                }, throwable -> {
                    Log.e(TAG, "Failed to fetch popular movies " + throwable);
                    // TODO: view.showError
                }));
    }

    private void loadWatchlist() {
        subscriptions.add(movieRepository.getWatchlist()
                .observeOn(AndroidSchedulers.mainThread())
                .filter(movies -> isViewSubscribed)
                .subscribe(movies -> {
                    // TODO: show empty state
                    view.showMovies(movies);
                }, throwable -> Log.e(TAG, "Failed to load watch list " + throwable)));
    }
}
