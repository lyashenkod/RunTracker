package com.example.dima.runtracker.utils;

import android.content.Context;
import android.database.Cursor;

import com.example.dima.runtracker.database.SQLiteCursorLoader;

/**
 * Created by Dima on 29.02.2016.
 */
public class LocationListCursorLoader extends SQLiteCursorLoader {
    private long mRunId;

    public LocationListCursorLoader(Context c, long runId) {
        super(c);
        mRunId = runId;
    }

    @Override
    protected Cursor loadCursor() {
        return RunManager.get(getContext()).queryLocationsForRun(mRunId);
    }
}
