package com.example.dima.runtracker.utils.receivers;

import android.content.Context;
import android.location.Location;

import com.example.dima.runtracker.utils.RunManager;

/**
 * Created by Dima on 23.02.2016.
 */
public class TrackingLocationReceiver extends LocationReceiver {
    @Override
    public void onLocationReceived(Context c, Location loc) {
        RunManager.get(c).insertLocation(loc);
    }
}
