package com.example.dima.runtracker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dima.runtracker.R;
import com.example.dima.runtracker.ui.fragments.RunListFragment;

/**
 * Created by Dima on 29.02.2016.
 */
public class RunListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new RunListFragment();
    }



}
