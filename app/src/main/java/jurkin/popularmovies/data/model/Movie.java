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

package jurkin.popularmovies.data.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import jurkin.popularmovies.App;
import jurkin.popularmovies.data.repository.local.MovieContract.MovieEntry;
import jurkin.popularmovies.data.repository.local.MovieDbHelper;
import rx.functions.Func1;

/**
 * Created by Andrej Jurkin on 5/12/17.
 */

public class Movie implements Parcelable {

    public static final Func1<Cursor, Movie> MAPPER = cursor -> {
        Movie movie = new Movie();
        movie.setId(MovieDbHelper.getLong(cursor, MovieEntry.MOVIE_ID));
        movie.setTitle(MovieDbHelper.getString(cursor, MovieEntry.MOVIE_TITLE));
        movie.setOverview(MovieDbHelper.getString(cursor, MovieEntry.MOVIE_OVERVIEW));
        movie.setPopularity(MovieDbHelper.getLong(cursor, MovieEntry.MOVIE_POPULARITY));
        movie.setPosterPath(MovieDbHelper.getString(cursor, MovieEntry.MOVIE_POSTER_PATH));
        movie.setBackdropPath(MovieDbHelper.getString(cursor, MovieEntry.MOVIE_BACKDROP_PATH));
        movie.setVoteCount(MovieDbHelper.getInt(cursor, MovieEntry.MOVIE_VOTE_COUNT));
        movie.setVoteAverage(MovieDbHelper.getFloat(cursor, MovieEntry.MOVIE_VOTE_AVERAGE));
        movie.setReleaseDate(MovieDbHelper.getDate(cursor, MovieEntry.MOVIE_RELEASE_DATE));
        movie.setOriginalLanguage(MovieDbHelper.getString(cursor, MovieEntry.MOVIE_ORIGINAL_LANGUAGE));
        movie.setRuntime(MovieDbHelper.getInt(cursor, MovieEntry.MOVIE_RUNTIME));
        movie.setInWatchlist(MovieDbHelper.getBoolean(cursor, MovieEntry.MOVIE_IN_WATCHLIST));
        return movie;
    };

    public static final Func1<Movie, ContentValues> CONTENT_MAPPER = Movie::toContentValues;

    public static ContentValues toContentValues(Movie movie) {
        ContentValues cv = new ContentValues();
        cv.put(MovieEntry.MOVIE_ID, movie.getId());
        cv.put(MovieEntry.MOVIE_TITLE, movie.getTitle());
        cv.put(MovieEntry.MOVIE_OVERVIEW, movie.getOverview());
        cv.put(MovieEntry.MOVIE_POPULARITY, movie.getPopularity());
        cv.put(MovieEntry.MOVIE_POSTER_PATH, movie.getPosterPath());
        cv.put(MovieEntry.MOVIE_BACKDROP_PATH, movie.getBackdropPath());
        cv.put(MovieEntry.MOVIE_VOTE_COUNT, movie.getVoteCount());
        cv.put(MovieEntry.MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
        cv.put(MovieEntry.MOVIE_RELEASE_DATE, movie.getReleaseDate().getTime());
        cv.put(MovieEntry.MOVIE_ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
        cv.put(MovieEntry.MOVIE_RUNTIME, movie.getRuntime());
        return cv;
    }

    private long id;

    private String title;

    private String overview;

    private float popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("vote_average")
    private float voteAverage;

    @SerializedName("release_date")
    private Date releaseDate;

    @SerializedName("original_language")
    private String originalLanguage;

    private long revenue;

    private int runtime;

    private boolean isInWatchlist;

    protected Movie(Parcel in) {
        id = in.readLong();
        title = in.readString();
        overview = in.readString();
        popularity = in.readFloat();
        posterPath = in.readString();
        backdropPath = in.readString();
        voteCount = in.readInt();
        voteAverage = in.readFloat();
        originalLanguage = in.readString();
        revenue = in.readLong();
        runtime = in.readInt();
        isInWatchlist = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeFloat(popularity);
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeInt(voteCount);
        dest.writeFloat(voteAverage);
        dest.writeString(originalLanguage);
        dest.writeLong(revenue);
        dest.writeInt(runtime);
        dest.writeByte((byte) (isInWatchlist ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getFullPosterPath() {
        return App.IMAGE_BASE_URL + posterPath;
    }

    public String getFullBackdropPath() {
        return App.BACKDROP_BASE_URL + backdropPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public long getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public boolean isInWatchlist() {
        return isInWatchlist;
    }

    public void setInWatchlist(boolean inWatchlist) {
        isInWatchlist = inWatchlist;
    }

    public String[] getByIdArgs() {
        return new String[]{String.valueOf(id)};
    }

    public Movie() {

    }

}
