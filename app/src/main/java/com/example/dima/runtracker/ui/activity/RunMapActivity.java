package com.example.dima.runtracker.ui.activity;

import android.support.v4.app.Fragment;

import com.example.dima.runtracker.ui.fragments.RunMapFragment;

/**
 * Created by Dima on 29.02.2016.
 */
public class RunMapActivity extends SingleFragmentActivity {
    /** Ключ для передачи идентификатора серии в формате long */
    public static final String EXTRA_RUN_ID =
            "com.bignerdranch.android.runtracker.run_id";

    @Override
    protected Fragment createFragment() {
        long runId = getIntent().getLongExtra(EXTRA_RUN_ID, -1);
        if (runId != -1) {
            return RunMapFragment.newInstance(runId);
        } else {
            return new RunMapFragment();
        }
    }
}
