package com.example.dima.runtracker.database;

import android.content.Context;

import com.example.dima.runtracker.entity.Run;
import com.example.dima.runtracker.utils.RunManager;

/**
 * Created by Dima on 29.02.2016.
 */
public class RunLoader extends DataLoader<Run> {
    private long mRunId;

    public RunLoader(Context context, long runId) {
        super(context);
        mRunId = runId;
    }

    @Override
    public Run loadInBackground() {
        return RunManager.get(getContext()).getRun(mRunId);
    }
}
