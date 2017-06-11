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

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import jurkin.popularmovies.R;
import jurkin.popularmovies.base.BaseViewHolder;
import jurkin.popularmovies.data.model.MovieReview;
import rx.Observable;

/**
 * Created by Andrej Jurkin on 6/2/17.
 */

class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private static final int REVIEW_MAX_LINES = 4;

    private List<MovieReview> data;

    private HashSet<Integer> expanedPositions;

    ReviewAdapter() {
        this.data = new ArrayList<>();
        this.expanedPositions = new HashSet<>();
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

        holder.review.post(() -> {
            int lines = holder.review.getLayout().getLineCount();
            int ellipsizedLines = holder.review.getLayout().getEllipsisCount(lines - 1);

            if (ellipsizedLines > 0) {
                holder.readMoreButton.setVisibility(View.VISIBLE);

                holder.readMoreButton.setOnClickListener(v -> {
                    if (expanedPositions.contains(position)) {
                        expanedPositions.remove(position);
                        holder.readMoreButton.setText(R.string.read_more);
                        holder.review.setMaxLines(REVIEW_MAX_LINES);
                    } else {
                        expanedPositions.add(position);
                        holder.readMoreButton.setText(R.string.show_less);
                        holder.readMoreButton.setVisibility(View.VISIBLE);
                        holder.review.setMaxLines(Integer.MAX_VALUE);

                    }
                });
            } else {
                holder.readMoreButton.setVisibility(View.GONE);
            }
        });
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

        @BindView(R.id.read_more_button)
        AppCompatButton readMoreButton;

        ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
