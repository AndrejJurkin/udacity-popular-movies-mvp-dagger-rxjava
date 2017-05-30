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

package jurkin.popularmovies.data.repository.local;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import jurkin.popularmovies.data.model.Movie;
import jurkin.popularmovies.data.model.MovieResponse;
import jurkin.popularmovies.data.model.MovieReview;
import jurkin.popularmovies.data.model.Video;
import jurkin.popularmovies.data.repository.MovieRepository;
import rx.Observable;

/**
 * Created by Andrej Jurkin on 5/28/17.
 */

@Singleton
public class MovieLocalDataSource  {

    @Inject
    public MovieLocalDataSource() {
        
    }
}
