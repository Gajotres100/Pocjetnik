package vsite.hr.map.pocjetnik;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import vsite.hr.map.pocjetnik.Data.PocjetnikContract.KategorijaEntry;
import vsite.hr.map.pocjetnik.Data.PocjetnikContract.PocjetnikEntry;

import vsite.hr.map.pocjetnik.Data.PocjetnikContract;
import vsite.hr.map.pocjetnik.Data.PocjetnikQueryHandler;
import vsite.hr.map.pocjetnik.model.Kategorija;
import vsite.hr.map.pocjetnik.model.KategorijaLista;
import vsite.hr.map.pocjetnik.model.Pocjetnik;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    static final int ALL_RECORDS = -1;
    static final int ALL_CATEGORIES = -1;
    private static final int URL_LOADER = 0;
    Cursor cursor;
    PocjetnikCursorAdapter adapter;
    KategorijaLista list = new KategorijaLista();
    Spinner spinner;
    KategorijeListAdapter categoryAdapter;

    private void updateTodo() {
        int id = 2;
        String[] args = {String.valueOf(id)};
        ContentValues values = new ContentValues();
        values.put(PocjetnikEntry.COLUMN_TEXT, "Call Mr Clark Kent");
        int numRows = getContentResolver().update(PocjetnikEntry.CONTENT_URI, values,
                PocjetnikEntry._ID + "=?", args);

    }

    private void deleteTodo(int id) {

        String[] args = {String.valueOf(id)};
        if (id == ALL_RECORDS) {
            args = null;
        }
        PocjetnikQueryHandler handler = new PocjetnikQueryHandler(
                this.getContentResolver());
        handler.startDelete(1, null,
                PocjetnikEntry.CONTENT_URI, PocjetnikEntry._ID + " =?", args);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner=(Spinner) findViewById(R.id.spinCategories);
        getLoaderManager().initLoader(URL_LOADER, null, this);
        setCategories();
        final ListView lv = (ListView) findViewById(R.id.lvTodos);
        adapter = new PocjetnikCursorAdapter(this, cursor, false);
        lv.setAdapter(adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {

                cursor = (Cursor) adapterView.getItemAtPosition(pos);

                int todoId = cursor.getInt(
                        cursor.getColumnIndex(PocjetnikEntry._ID));
                String todoText = cursor.getString(
                        cursor.getColumnIndex(PocjetnikEntry.COLUMN_TEXT));
                String todoExpireDate = cursor.getString(
                        cursor.getColumnIndex(PocjetnikEntry.COLUMN_EXPIRED));
                int todoDone = cursor.getInt(
                        cursor.getColumnIndex(PocjetnikEntry.COLUMN_DONE));
                String todoCreated = cursor.getString(
                        cursor.getColumnIndex(PocjetnikEntry.COLUMN_CREATED));
                String todoCategory = cursor.getString(cursor.getColumnIndex(PocjetnikEntry.COLUMN_CATEGORY));

                boolean boolDone = (todoDone == 1) ? true : false;
                Pocjetnik pocjetnik = new Pocjetnik (todoId,todoText, todoCreated, todoExpireDate, boolDone,
                        todoCategory);
                Intent intent = new Intent(MainActivity.this, PocjetnikActivity.class);
                intent.putExtra("pocjetnik", pocjetnik);
                intent.putExtra("categories", list);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pocjetnik pocjetnik = new Pocjetnik (0,"", "", "", false, "0");
                Intent intent = new Intent(MainActivity.this, PocjetnikActivity.class);

                intent.putExtra("pocjetnik", pocjetnik);
                intent.putExtra("categories", list);
                startActivity(intent);

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                       int position, long id) {

                if (position >= 0) {
                    getLoaderManager().restartLoader(URL_LOADER, null,
                            MainActivity.this);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void createTestTodos() {

        for (int i = 1; i<=20; i++) {
            ContentValues values = new ContentValues();
            values.put(PocjetnikEntry.COLUMN_TEXT, "Todo Item #" + i);
            values.put(PocjetnikEntry.COLUMN_CATEGORY, 1);
            values.put(PocjetnikEntry.COLUMN_CREATED, "2019-01-02");
            values.put(PocjetnikEntry.COLUMN_EXPIRED, "2019-08-08");
            int done = (i%2 == 1) ? 1 : 0;
            values.put(PocjetnikEntry.COLUMN_DONE, done);
            PocjetnikQueryHandler handler = new PocjetnikQueryHandler(
                    this.getContentResolver());
            handler.startInsert(1, null, PocjetnikEntry.CONTENT_URI,
                    values );
        }
    }

    private void CreateCategory() {
        ContentValues values = new ContentValues();
        values.put(PocjetnikContract.KategorijaEntry.COLUMN_DESCRIPTION, "Work");
        Uri uri = getContentResolver().insert(PocjetnikContract.KategorijaEntry.CONTENT_URI,
                values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_categories) {
            Intent intent = new Intent(MainActivity.this, KategorijeActivity.class);
            startActivity(intent);

        }
        if (id == R.id.action_delete_all_todos) {
            deleteTodo(ALL_RECORDS);
        }

        if (id == R.id.action_create_test_data) {
            createTestTodos();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setCategories() {
        final PocjetnikQueryHandler categoriesHandler = new PocjetnikQueryHandler(
                this.getContentResolver()) {
            @Override
            protected void onQueryComplete(int token, Object cookie,
                                           Cursor cursor) {
                if ((cursor != null)) {
                    int i = 0;
                    list.ItemList.add(i, new Kategorija(ALL_CATEGORIES, "All Categories"));
                    i++;
                    while (cursor.moveToNext()) {
                        list.ItemList.add(i, new Kategorija(
                                cursor.getInt(0),
                                cursor.getString(1)
                        ));
                        i++;
                    }
                }
            }
        };
        categoriesHandler.startQuery(1, null, PocjetnikContract.KategorijaEntry.CONTENT_URI,
                null, null, null,
                PocjetnikContract.KategorijaEntry.COLUMN_DESCRIPTION);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {PocjetnikEntry.COLUMN_TEXT,
                PocjetnikEntry.TABLE_NAME + "." + PocjetnikEntry._ID,
                PocjetnikEntry.COLUMN_CREATED,
                PocjetnikEntry.COLUMN_EXPIRED,
                PocjetnikEntry.COLUMN_DONE,
                PocjetnikEntry.COLUMN_CATEGORY,
                PocjetnikContract.KategorijaEntry.TABLE_NAME + "." +
                        PocjetnikContract.KategorijaEntry.COLUMN_DESCRIPTION};
        String selection;
        String[] arguments = new String[1];

        if (spinner.getSelectedItemId()< 0) {
            selection = null;
            arguments = null;
        }
        else {
            selection = PocjetnikEntry.COLUMN_CATEGORY + "=?";
            arguments[0] = String.valueOf(spinner.getSelectedItemId());
        }
        return new CursorLoader(
                this,
                PocjetnikEntry.CONTENT_URI,
                projection,
                selection, arguments, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        adapter.swapCursor(data);
        if (categoryAdapter == null) {
            categoryAdapter = new KategorijeListAdapter(list.ItemList);
            spinner.setAdapter(categoryAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
