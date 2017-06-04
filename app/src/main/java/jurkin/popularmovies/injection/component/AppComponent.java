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

package jurkin.popularmovies.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import jurkin.popularmovies.App;
import jurkin.popularmovies.data.repository.local.LocalDataSourceModule;
import jurkin.popularmovies.data.repository.local.MovieContentProvider;
import jurkin.popularmovies.injection.module.UiModule;
import jurkin.popularmovies.injection.module.ApiModule;
import jurkin.popularmovies.injection.module.AppModule;

/**
 * Created by Andrej Jurkin on 5/13/17.
 */

@Singleton
@Component(
        modules = {
                AppModule.class,
                ApiModule.class,
                UiModule.class,
                AndroidInjectionModule.class,
                LocalDataSourceModule.class
        })
public interface AppComponent {

    void inject(App app);

    void inject(MovieContentProvider movieContentProvider);
}
