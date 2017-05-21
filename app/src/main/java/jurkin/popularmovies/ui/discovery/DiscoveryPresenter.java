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

import jurkin.popularmovies.api.MovieService;
import jurkin.popularmovies.data.model.Movie;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ajurkin on 5/13/17.
 */
public class DiscoveryPresenter implements DiscoveryContract.Presenter {
    private static final String TAG = "DiscoveryPresenter";

    private DiscoveryContract.View view;
    private MovieService movieService;
    private CompositeSubscription subscriptions;

    @Inject
    DiscoveryPresenter(DiscoveryContract.View view, MovieService movieService) {
        this.view = view;
        this.movieService = movieService;
        this.subscriptions = new CompositeSubscription();

    }

    @Override
    public void subscribe() {
        Log.d(TAG, "Subscribe");
        movieService.getPopularMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    movieResponse -> {
                        Log.d(TAG, "Popular data fetched successfully");
                        view.showMovies(movieResponse.getResults());
                    },
                    throwable -> {
                        Log.e(TAG, "Failed to fetch popular data " + throwable);
                        // TODO: view.showError
                    });
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
    public void setFilterFlags(int flags) {

    }
}
