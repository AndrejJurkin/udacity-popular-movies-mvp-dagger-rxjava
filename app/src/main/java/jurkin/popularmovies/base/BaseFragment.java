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

package jurkin.popularmovies.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by ajurkin on 5/13/17.
 */

public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        log("-> OnViewCreated");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("-> OnCreate");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        log("-> OnAttach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        log("-> OnDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log("-> OnDestroy");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        log("-> OnCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        log("-> OnAttachFragment");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        log("-> OnConfigurationChanged");
    }

    @Override
    public void onPause() {
        super.onPause();
        log("-> OnPause");
    }

    @Override
    public void onStart() {
        super.onStart();
        log("-> OnStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        log("-> OnStop");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        log("-> OnDetach");
    }

    @Override
    public void onResume() {
        super.onResume();
        log("-> OnResume");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        log("-> OnViewStateRestored");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        log("-> OnSaveInstanceState");
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState, int resId) {
        log("-> OnCreateView");
        View view = inflater.inflate(resId, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    private void log(String message) {
        Log.d(TAG, message);
    }
}
