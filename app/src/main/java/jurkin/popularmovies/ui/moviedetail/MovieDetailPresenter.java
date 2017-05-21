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

import java.util.Calendar;

import javax.inject.Inject;

import jurkin.popularmovies.data.model.Movie;

/**
 * Created by ajurkin on 5/21/17.
 */

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private Movie movie;
    private MovieDetailContract.View view;

    @Inject
    MovieDetailPresenter(MovieDetailContract.View view, Movie movie) {
        this.view = view;
        this.movie = movie;
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    @SuppressLint("DefaultLocale")
    public void subscribe() {
        this.view.setTitle(movie.getTitle());
        this.view.setDescription(movie.getOverview());
        this.view.setMainImage(movie.getFullBackdropPath());
        this.view.setPosterImage(movie.getFullPosterPath());
        this.view.setUserRating(String.format("%.1f (%d)",
                movie.getVoteAverage(), movie.getVoteCount()));

        Calendar releaseDate = Calendar.getInstance();
        releaseDate.setTime(movie.getReleaseDate());
        this.view.showReleaseDate("EN, " + releaseDate.get(Calendar.YEAR));

    }

    @Override
    public void onAddToFavoritesButtonClick() {

    }
}
