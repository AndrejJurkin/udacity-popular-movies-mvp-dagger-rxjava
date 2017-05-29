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

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import jurkin.popularmovies.R;
import jurkin.popularmovies.base.BasePresenterFragment;
import jurkin.popularmovies.data.model.Movie;

/**
 * Created by ajurkin on 5/14/17.
 */

public class MovieDetailFragment extends BasePresenterFragment<MovieDetailContract.Presenter>
        implements MovieDetailContract.View {

    public static final String ARG_MOVIE = "arg_movie";

    @BindView(R.id.main_image)
    ImageView mainImage;

    @BindView(R.id.movie_poster)
    ImageView posterImage;

    @BindView(R.id.movie_title)
    TextView movieTitle;

    @BindView(R.id.movie_description)
    TextView movieDescription;

    @BindView(R.id.movie_rating)
    TextView movieRating;

    @BindView(R.id.movie_detail_summary)
    TextView movieDetailSummary;

    @BindView(R.id.add_to_favorites_button)
    AppCompatButton addToFavoritesButton;

    public static MovieDetailFragment newInstance(Movie movie) {
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);

        movieDetailFragment.setArguments(args);
        return movieDetailFragment;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState, R.layout.fragment_movie_detail);
    }

    @Inject
    @Override
    public void setPresenter(MovieDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showAddToFavoritesButton() {

    }

    @Override
    public void hideAddToFavoritesButton() {

    }

    @Override
    public void setTitle(String title) {
        this.movieTitle.setText(title);
    }

    @Override
    public void setDescription(String description) {
        this.movieDescription.setText(description);
    }

    @Override
    public void setMainImage(String src) {
        Glide.with(getActivity()).load(src).into(mainImage);
    }

    @Override
    public void setPosterImage(String src) {
        Glide.with(getActivity()).load(src).into(posterImage);
    }

    @Override
    public void setUserRating(String userRating) {
        this.movieRating.setText(userRating);
    }

    @Override
    public void setMovieDetailSummary(String summary) {
        this.movieDetailSummary.setText(summary);
    }

    @OnClick
    @SuppressWarnings("unused")
    public void onAddToFavoritesButtonClick() {
        this.presenter.onAddToFavoritesButtonClick();
    }
}
