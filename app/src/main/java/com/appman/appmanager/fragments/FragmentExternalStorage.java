package com.appman.appmanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appman.appmanager.R;

/**
 * Created by rudhraksh.pahade on 29-04-2016.
 */
public class FragmentExternalStorage extends Fragment {

    View mainView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.layout_fragment_internal_storage, container, false);

        return mainView;
    }
}
