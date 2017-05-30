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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jurkin.popularmovies.R;
import jurkin.popularmovies.base.BaseViewHolder;
import jurkin.popularmovies.data.model.Video;

/**
 * Created by Andrej Jurkin on 5/30/17.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    @NonNull
    private List<Video> data;

    VideoAdapter() {
        this.data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gi_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Video video = data.get(position);

        Glide.with(holder.itemView.getContext())
                .load(video.getThumbUrl())
                .into(holder.thumbnailImageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(@Nullable List<Video> data) {
        if (data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data = data;
        }

        notifyDataSetChanged();
    }

    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.thumbnail_image_view)
        ImageView thumbnailImageView;

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
