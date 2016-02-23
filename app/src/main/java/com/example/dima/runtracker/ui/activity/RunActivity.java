package com.example.dima.runtracker.ui.activity;

import android.support.v4.app.Fragment;

import com.example.dima.runtracker.ui.fragments.RunFragment;

public class RunActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new RunFragment();
    }
}