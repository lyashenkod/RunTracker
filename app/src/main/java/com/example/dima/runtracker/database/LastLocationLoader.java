package com.example.dima.runtracker.database;

import android.content.Context;
import android.location.Location;

import com.example.dima.runtracker.utils.RunManager;

/**
 * Created by Dima on 29.02.2016.
 */
public class LastLocationLoader extends DataLoader<Location> {
    private long mRunId;

    public LastLocationLoader(Context context, long runId) {
        super(context);
        mRunId = runId;
    }
    @Override
    public Location loadInBackground() {
        return RunManager.get(getContext()).getLastLocationForRun(mRunId);
    }
}
