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

import android.database.Cursor;

import jurkin.popularmovies.data.repository.local.MovieContract;
import jurkin.popularmovies.data.repository.local.MovieContract.VideoEntry;
import jurkin.popularmovies.data.repository.local.MovieDbHelper;
import rx.functions.Func1;

/**
 * Created by Andrej Jurkin on 5/25/17.
 */

public class Video {

    private static final String YOUTUBE_THUMB_URL = "https://img.youtube.com/vi/%s/0.jpg";

    public static final Func1<Cursor, Video> MAPPER = cursor -> {
        Video video = new Video();
        video.setId(MovieDbHelper.getString(cursor, VideoEntry.VIDEO_ID));
        video.setKey(MovieDbHelper.getString(cursor, VideoEntry.VIDEO_KEY));
        video.setName(MovieDbHelper.getString(cursor, VideoEntry.VIDEO_NAME));
        video.setSite(MovieDbHelper.getString(cursor, VideoEntry.VIDEO_SITE));
        video.setSize(MovieDbHelper.getInt(cursor, VideoEntry.VIDEO_SIZE));
        video.setType(MovieDbHelper.getString(cursor, VideoEntry.VIDEO_TYPE));
        return video;
    };

    private String id;

    private String key;

    private String name;

    private String site;

    private int size;

    private String type;

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public String getThumbUrl() {
        return String.format(YOUTUBE_THUMB_URL, key);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setType(String type) {
        this.type = type;
    }
}
