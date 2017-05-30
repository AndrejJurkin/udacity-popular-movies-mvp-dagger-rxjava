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

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import jurkin.popularmovies.api.MovieService;
import jurkin.popularmovies.base.BasePresenter;
import jurkin.popularmovies.base.BaseView;
import jurkin.popularmovies.data.model.Movie;
import jurkin.popularmovies.data.repository.MovieRepository;

/**
 * Created by Andrej Jurkin on 5/13/17.
 */

public interface DiscoveryContract {

    interface View extends BaseView<Presenter> {
        void showMovies(List<Movie> movies);

        void showDetail(Movie movie);

        void setActionBarTitle(int resId);
    }

    interface Presenter extends BasePresenter {
        void onMovieClicked(Movie movie);

        void onPopularMoviesClicked();

        void onTopRatedMoviesClicked();
    }

    @Module
    class ActivityModule {

        @Provides
        DiscoveryFragment providesDiscoveryFragment() {
            return DiscoveryFragment.newInstance();
        }
    }

    @Module
    class FragmentModule {

        DiscoveryFragment fragment;

        FragmentModule(DiscoveryFragment fragment) {
            this.fragment = fragment;
        }

        @Provides
        Context providesContext() {
            return fragment.getContext();
        }

        @Provides
        View providesView() {
            return fragment;
        }

        @Provides
        RecyclerView.LayoutManager providesLayoutManager(Context context) {
            return new GridLayoutManager(context, 2);
        }

        @Provides
        Presenter providesPresenter(View view, MovieRepository movieRepository) {
            return new DiscoveryPresenter(view, movieRepository);
        }
    }

    @Subcomponent(modules = ActivityModule.class)
    interface ActivityComponent extends AndroidInjector<DiscoveryActivity> {

        @Subcomponent.Builder
        abstract class Builder extends AndroidInjector.Builder<DiscoveryActivity> {}
    }

    @Subcomponent(modules = FragmentModule.class)
    interface FragmentComponent extends AndroidInjector<DiscoveryFragment> {

        @Subcomponent.Builder
        abstract class Builder extends AndroidInjector.Builder<DiscoveryFragment> {

            public abstract Builder fragmentModule(FragmentModule fragmentModule);

            @Override
            public void seedInstance(DiscoveryFragment fragment) {
                fragmentModule(new FragmentModule(fragment));
            }
        }
    }
}
