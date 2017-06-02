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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jurkin.popularmovies.R;
import jurkin.popularmovies.base.BaseViewHolder;
import jurkin.popularmovies.data.model.MovieReview;

/**
 * Created by Andrej Jurkin on 6/2/17.
 */

class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<MovieReview> data;

    ReviewAdapter() {
        this.data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.li_review, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieReview review = data.get(position);

        holder.author.setText(review.getAuthor());
        holder.review.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<MovieReview> data) {
        if (data == null) {
            data = new ArrayList<>();
        }

        this.data = data;
        notifyDataSetChanged();
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.author)
        TextView author;

        @BindView(R.id.review)
        TextView review;

        ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
