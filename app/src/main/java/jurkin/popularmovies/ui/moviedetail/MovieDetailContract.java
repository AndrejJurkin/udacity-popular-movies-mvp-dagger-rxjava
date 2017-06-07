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

import java.util.List;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import jurkin.popularmovies.api.MovieService;
import jurkin.popularmovies.base.BasePresenter;
import jurkin.popularmovies.base.BaseView;
import jurkin.popularmovies.data.model.Movie;
import jurkin.popularmovies.data.model.MovieReview;
import jurkin.popularmovies.data.model.Video;
import jurkin.popularmovies.data.repository.MovieRepository;

/**
 * Created by Andrej Jurkin on 5/18/17.
 */

public interface MovieDetailContract {

    interface View extends BaseView<Presenter> {

        void showAddToFavoritesButton();

        void hideAddToFavoritesButton();

        void setTitle(String title);

        void setDescription(String description);

        void setMainImage(String src);

        void setPosterImage(String src);

        void setUserRating(String userRating);

        void setMovieDetailSummary(String summary);

        void showVideos(List<Video> videos);

        void playVideo(String videoUrl);

        void showReviews(List<MovieReview> reviews);
    }

    interface Presenter extends BasePresenter {

        void onAddToWatchlistClick();

        void onVideoClicked(Video video);
    }

    @Module
    class MovieDetailModule {
        View view;
        Movie movie;

        MovieDetailModule(View view, Movie movie) {
            this.view = view;
            this.movie = movie;
        }

        @Provides
        View providesView() {
            return view;
        }

        @Provides
        Movie providesMovie() {
            return movie;
        }

        @Provides
        Presenter providesPresenter(View view, Movie movie, MovieRepository movieRepository) {
            return new MovieDetailPresenter(view, movie, movieRepository);
        }
    }

    @Subcomponent(modules = MovieDetailModule.class)
    interface FragmentComponent extends AndroidInjector<MovieDetailFragment> {

        @Subcomponent.Builder
        abstract class Builder extends AndroidInjector.Builder<MovieDetailFragment> {
            public abstract Builder movieDetailModule(MovieDetailModule movieDetailModule);

            @Override
            public void seedInstance(MovieDetailFragment fragment) {
                Movie movie = null;

                if (fragment.getArguments() != null) {
                    movie = fragment.getArguments().getParcelable(MovieDetailFragment.ARG_MOVIE);
                }

                if (movie == null) {
                    throw new NullPointerException("Movie passed to fragment is null.");
                }

                movieDetailModule(new MovieDetailModule(fragment, movie));
            }
        }
    }

}
