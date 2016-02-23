package com.example.dima.runtracker.activity;

import android.support.v4.app.Fragment;

import com.example.dima.runtracker.fragments.RunFragment;

public class RunActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new RunFragment();
    }
}