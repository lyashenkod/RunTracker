package com.example.dima.runtracker.ui.fragments;

import android.content.res.Resources;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dima.runtracker.R;
import com.example.dima.runtracker.database.RunDatabaseHelper;
import com.example.dima.runtracker.utils.LocationListCursorLoader;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Date;

/**
 * Created by Dima on 29.02.2016.
 */
public class RunMapFragment extends SupportMapFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String ARG_RUN_ID = "RUN_ID";
    private static final int LOAD_LOCATIONS = 0;

    private GoogleMap mGoogleMap;
    private RunDatabaseHelper.LocationCursor mLocationCursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Проверка идентификатора серии в аргументе и поиск серии
        Bundle args = getArguments();
        if (args != null) {
            long runId = args.getLong(ARG_RUN_ID, -1);
            if (runId != -1) {
                LoaderManager lm = getLoaderManager();
                lm.initLoader(LOAD_LOCATIONS, args, this);
            }
        }
    }

    public static RunMapFragment newInstance(long runId) {
        Bundle args = new Bundle();
        args.putLong(ARG_RUN_ID, runId);
        RunMapFragment rf = new RunMapFragment();
        rf.setArguments(args);
        return rf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);

        // Сохранение ссылки на GoogleMap
        mGoogleMap = getMap();
        // Вывод местоположения пользователя
        mGoogleMap.setMyLocationEnabled(true);

        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        long runId = args.getLong(ARG_RUN_ID, -1);
        return new LocationListCursorLoader(getActivity(), runId);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mLocationCursor = (RunDatabaseHelper.LocationCursor) cursor;
        updateUI();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Завершение работы с данными
        mLocationCursor.close();
        mLocationCursor = null;
    }

    private void updateUI() {
        if (mGoogleMap == null || mLocationCursor == null)
            return;

        // Создание накладки с позициями серии.
        // Создаем ломаную со всеми точками.
        PolylineOptions line = new PolylineOptions();
        // Также создается объект LatLngBounds для масштабирования по размерам.
        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
        // Перебор позиций
        mLocationCursor.moveToFirst();
        while (!mLocationCursor.isAfterLast()) {
            Location loc = mLocationCursor.getLocation();
            LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());

            Resources r = getResources();
            // Если это первая позиция, добавить маркер
            if (mLocationCursor.isFirst()) {
                String startDate = new Date(loc.getTime()).toString();
                MarkerOptions startMarkerOptions = new MarkerOptions()
                        .position(latLng)
                        .title(r.getString(R.string.run_start))
                        .snippet(r.getString(R.string.run_started_at_format, startDate));
                mGoogleMap.addMarker(startMarkerOptions);
            } else if (mLocationCursor.isLast()) {
                // Если это последняя позиция, которая не является
                // также первой, добавить маркер
                String endDate = new Date(loc.getTime()).toString();
                MarkerOptions finishMarkerOptions = new MarkerOptions()
                        .position(latLng)
                        .title(r.getString(R.string.run_finish))
                        .snippet(r.getString(R.string.run_finished_at_format, endDate));
                mGoogleMap.addMarker(finishMarkerOptions);
            }
            line.add(latLng);
            latLngBuilder.include(latLng);
            mLocationCursor.moveToNext();
        }
        // Добавление маршрута на карту
        mGoogleMap.addPolyline(line);
        // Масштабирование карты по маршруту с дополнительными отступами // В качестве ограничивающего прямоугольника выбирается
        // размер текущего экрана.
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        // Построение инструкции перемещения для камеры карты.
        LatLngBounds latLngBounds = latLngBuilder.build();
        CameraUpdate movement = CameraUpdateFactory.newLatLngBounds(latLngBounds,
                display.getWidth(), display.getHeight(), 300);

          mGoogleMap.moveCamera(movement);
    }
}