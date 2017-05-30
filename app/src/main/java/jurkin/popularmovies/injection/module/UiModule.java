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

package jurkin.popularmovies.injection.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;
import jurkin.popularmovies.ui.discovery.DiscoveryActivity;
import jurkin.popularmovies.ui.discovery.DiscoveryContract;
import jurkin.popularmovies.ui.discovery.DiscoveryFragment;
import jurkin.popularmovies.ui.moviedetail.MovieDetailContract;
import jurkin.popularmovies.ui.moviedetail.MovieDetailFragment;


/**
 * Created by Andrej Jurkin on 5/14/17.
 */

@SuppressWarnings("unused")
@Module(subcomponents = {
        DiscoveryContract.ActivityComponent.class,
        DiscoveryContract.FragmentComponent.class,
        MovieDetailContract.FragmentComponent.class
})
public abstract class UiModule {

    @Binds
    @IntoMap
    @ActivityKey(DiscoveryActivity.class)
    abstract AndroidInjector.Factory<? extends Activity>
        bindMainActivityInjectorFactory(DiscoveryContract.ActivityComponent.Builder builder);

    @Binds
    @IntoMap
    @FragmentKey(DiscoveryFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment>
        bindDiscoveryFragmentInjectorFactory(DiscoveryContract.FragmentComponent.Builder builder);

    @Binds
    @IntoMap
    @FragmentKey(MovieDetailFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment>
        bindMovieDetailFragmentInjectorFactory(MovieDetailContract.FragmentComponent.Builder builder);
}


