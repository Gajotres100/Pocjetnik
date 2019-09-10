package vsite.hr.map.pocjetnik;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import vsite.hr.map.pocjetnik.Data.PocjetnikContract;
import vsite.hr.map.pocjetnik.Data.PocjetnikQueryHandler;
import vsite.hr.map.pocjetnik.databinding.KategorijaAktivitiBinding;
import vsite.hr.map.pocjetnik.model.Kategorija;
import vsite.hr.map.pocjetnik.model.KategorijaLista;

public class KategorijeActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>{

    protected Kategorija category;
    protected KategorijaLista categories;
    private static final int URL_LOADER = 0;
    ObservableArrayList<Kategorija> list;
    Cursor cursor;
    KategorijeListAdapter adapter;
    KategorijaAktivitiBinding binding;
    PocjetnikQueryHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = getLayoutInflater();
        binding = DataBindingUtil.setContentView(this, R.layout.kategorija_aktiviti);
        getLoaderManager().initLoader(URL_LOADER, null, this);

        handler =  new PocjetnikQueryHandler(getContentResolver());

        ListView myList = (ListView)findViewById(R.id.lvCategories);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                category = categories.ItemList.get(position);
                binding.setCategory(category);
            }
        });

        final Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                category = new Kategorija();
                binding.setCategory(category);
            }
        });

        final Button btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(KategorijeActivity.this)
                        .setTitle("Obriši odabranu kategoriju?")
                        .setMessage("Potvrdi te")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //delete
                                categories.ItemList.remove(category);
                                String [] args = new String[1];
                                Uri uri =  Uri.withAppendedPath(PocjetnikContract
                                        .KategorijaEntry.CONTENT_URI, String.valueOf(category.catId.get()));
                                handler.startDelete(1, null, uri
                                        , null, null);
                                category = null;
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        final Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(category != null && category.catId.get()!= 0) {

                    Log.d("Save Click", "update");
                    String [] args = new String[1];
                    ContentValues values = new ContentValues();
                    values.put(PocjetnikContract.KategorijaEntry.COLUMN_DESCRIPTION,
                            category.description.get());
                    args[0] = String.valueOf(category.catId.get());
                    handler.startUpdate(1, null, PocjetnikContract.KategorijaEntry.CONTENT_URI,
                            values, PocjetnikContract.KategorijaEntry._ID + "=?", args);
                }
                else if(category != null && category.catId.get() == 0) {

                    ContentValues values = new ContentValues();
                    values.put(PocjetnikContract.KategorijaEntry.COLUMN_DESCRIPTION,
                            category.description.get());
                    handler.startInsert(1, null, PocjetnikContract.KategorijaEntry.CONTENT_URI,
                            values );
                }
            }
        });
    }
    @Override
    public void onResume(){
        getLoaderManager().restartLoader(URL_LOADER, null, this);
        super.onResume();
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {PocjetnikContract.KategorijaEntry.TABLE_NAME
                + "." + PocjetnikContract.KategorijaEntry._ID,
                PocjetnikContract.KategorijaEntry.COLUMN_DESCRIPTION};
        return new CursorLoader(
                this,
                PocjetnikContract.KategorijaEntry.CONTENT_URI,
                projection,
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        final ListView lv=(ListView) findViewById(R.id.lvCategories);
        list = new ObservableArrayList<>();
        int i=0;


        data.moveToPosition(-1);
        while (data.moveToNext())
        {
            list.add(i, new Kategorija(
                    data.getInt(0),
                    data.getString(1)
            ));
            i++;
        }
        adapter = new KategorijeListAdapter(list);
        lv.setAdapter(adapter);


        category = new Kategorija();
        categories = new KategorijaLista(list);
        binding.setCategories(categories);
        binding.setCategory(category);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.list = null;
    }
}
