package com.example.dima.runtracker.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dima.runtracker.R;
import com.example.dima.runtracker.database.RunDatabaseHelper.RunCursor;
import com.example.dima.runtracker.database.SQLiteCursorLoader;
import com.example.dima.runtracker.entity.Run;
import com.example.dima.runtracker.ui.activity.RunActivity;
import com.example.dima.runtracker.utils.RunManager;

/**
 * Created by Dima on 29.02.2016.
 */
public class RunListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private RunCursor mCursor;
    private static final int REQUEST_NEW_RUN = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
//        // Запрос на получение списка серий
//        mCursor = RunManager.get(getActivity()).queryRuns();
//        // Создание адаптера, ссылающегося на этот курсор
//        RunCursorAdapter adapter = new RunCursorAdapter(getActivity(), mCursor);
//        setListAdapter(adapter);

        // Инициализация загрузчика для загрузки списка серий
        getLoaderManager().initLoader(0, null, this);
    }

//    @Override
//    public void onDestroy() {
//        mCursor.close();
//        super.onDestroy();
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.run_list_options, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_item_new_run:
                Intent i = new Intent(getActivity(), RunActivity.class);
                startActivityForResult(i, REQUEST_NEW_RUN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Аргумент id содержит идентификатор серии;
        // CursorAdapter автоматически предоставляет эту информацию.
        Intent i = new Intent(getActivity(), RunActivity.class);
        i.putExtra(RunActivity.EXTRA_RUN_ID, id);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_run:
                Intent i = new Intent(getActivity(), RunActivity.class);
                startActivityForResult(i, REQUEST_NEW_RUN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_NEW_RUN == requestCode) {
//            mCursor.requery();
//            ((RunCursorAdapter) getListAdapter()).notifyDataSetChanged();

            // Перезапуск загрузчика для получения новых серий
            getLoaderManager().restartLoader(0, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new RunListCursorLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Создание адаптера, ссылающегося на этот курсор
        RunCursorAdapter adapter =  new RunCursorAdapter(getActivity(), (RunCursor)data);
        setListAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Прекращение использования курсора (через адаптер)
        setListAdapter(null);
    }

    private static class RunListCursorLoader extends SQLiteCursorLoader {
        public RunListCursorLoader(Context context) {
            super(context);
        }
        @Override
        protected Cursor loadCursor() {
            // Запрос на получение списка серий
            return RunManager.get(getContext()).queryRuns();
        }

    }

    private static class RunCursorAdapter extends CursorAdapter {

        private RunCursor mRunCursor;

        public RunCursorAdapter(Context context, RunCursor cursor) {
            super(context, cursor, 0);
            mRunCursor = cursor;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            // Использование заполнителя макета для получения
            // представления строки
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Получение серии для текущей строки
            Run run = mRunCursor.getRun();

            // Создание текстового представления начальной даты
            TextView startDateTextView = (TextView) view;
            String cellText =
                    context.getString(R.string.cell_text, run.getStartDate());
            startDateTextView.setText(cellText);
        }
    }
}
