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

package jurkin.popularmovies;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.os.BuildCompat;

import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasDispatchingActivityInjector;
import dagger.android.HasDispatchingFragmentInjector;
import dagger.android.support.HasDispatchingSupportFragmentInjector;
import jurkin.popularmovies.injection.component.AppComponent;
import jurkin.popularmovies.injection.component.DaggerAppComponent;
import jurkin.popularmovies.injection.module.ApiModule;
import jurkin.popularmovies.injection.module.AppModule;

/**
 * Created by ajurkin on 5/13/17.
 */

public class App extends Application implements HasDispatchingActivityInjector, HasDispatchingSupportFragmentInjector {
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";
    public static final String BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w1280";

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingFragmentInjector;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule(BASE_URL, BuildConfig.TMDB_API_KEY))
                .build();

        appComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingFragmentInjector;
    }
}
