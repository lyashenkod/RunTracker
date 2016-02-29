package com.example.dima.runtracker.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.dima.runtracker.ui.fragments.RunFragment;

public class RunActivity extends SingleFragmentActivity {

    public static final String EXTRA_RUN_ID =
            "com.bignerdranch.android.runtracker.run_id";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Скрытие заголовка окна.
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        // Скрытие панели состояния и прочего оформления уровня ОС
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected Fragment createFragment() {
//        return new RunFragment();

        long runId = getIntent().getLongExtra(EXTRA_RUN_ID, -1);
        if (runId != -1) {
            return RunFragment.newInstance(runId);
        } else {
            return new RunFragment();
        }

    }

}