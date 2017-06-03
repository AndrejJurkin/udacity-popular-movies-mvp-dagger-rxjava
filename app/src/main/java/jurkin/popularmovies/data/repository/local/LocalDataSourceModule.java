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

import android.app.Application;
import android.content.ContentResolver;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

/**
 * Created by Andrej Jurkin on 6/3/17.
 */

@Module
public final class LocalDataSourceModule {

    @Provides
    @Singleton
    SqlBrite providesSqlBrite() {
        return new SqlBrite.Builder().build();
    }

    @Provides
    @Singleton
    BriteDatabase providesBriteDatabase(SqlBrite sqlBrite, MovieDbHelper dbHelper) {
        BriteDatabase db = sqlBrite.wrapDatabaseHelper(dbHelper, Schedulers.io());
        db.setLoggingEnabled(true);
        return db;
    }

    @Provides
    @Singleton
    ContentResolver providesContentResolver(Application application) {
        return application.getContentResolver();
    }
}
