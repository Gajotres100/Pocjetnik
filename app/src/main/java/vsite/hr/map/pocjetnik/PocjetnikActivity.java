package vsite.hr.map.pocjetnik;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;


import vsite.hr.map.pocjetnik.Data.PocjetnikContract;
import vsite.hr.map.pocjetnik.Data.PocjetnikQueryHandler;
import vsite.hr.map.pocjetnik.databinding.ActivityPocjetnikBinding;
import vsite.hr.map.pocjetnik.model.Kategorija;
import vsite.hr.map.pocjetnik.model.KategorijaLista;
import vsite.hr.map.pocjetnik.model.Pocjetnik;

public class PocjetnikActivity extends AppCompatActivity {
    Pocjetnik pocjetnik;
    PocjetnikQueryHandler handler;
    Spinner spinner;
    KategorijaLista list;
    KategorijeListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = 0;
        handler = new PocjetnikQueryHandler(getContentResolver());

        ActivityPocjetnikBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_pocjetnik);
        Intent intent = getIntent();
        pocjetnik = (Pocjetnik)intent.getSerializableExtra("pocjetnik");
        binding.setPocjetnik(pocjetnik);

        list = (KategorijaLista) intent.getSerializableExtra("categories");
        adapter = new KategorijeListAdapter(list.ItemList);
        spinner = (Spinner) findViewById(R.id.spCategories);
        spinner.setAdapter(adapter);

        if (Integer.valueOf(pocjetnik.category.get()) == 0) {
            position = 1;
        }
        else {
            for (Kategorija cat : list.ItemList) {
                if (Integer.valueOf(cat.catId.get()) == Integer.valueOf(pocjetnik.category.get())) {
                    break;
                }
                position++;
            }
            spinner.setSelection(position);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_pocjetnik, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_todo) {
            new AlertDialog.Builder(PocjetnikActivity.this)
                    .setTitle(getString(R.string.delete_todo_dialog_title))
                    .setMessage(getString(R.string.delete_todo_dialog))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.
                            OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            Uri uri =  Uri.withAppendedPath(
                                    PocjetnikContract.PocjetnikEntry.CONTENT_URI,
                                    String.valueOf(pocjetnik.id.get()));
                            String selection = PocjetnikContract.PocjetnikEntry._ID + "=?";
                            String[] arguments = new String[1];
                            arguments[0] = String.valueOf(pocjetnik.id.get());
                            handler.startDelete(1, null, uri
                                    , selection, arguments);
                            Intent intent = new Intent(PocjetnikActivity.this,
                                    MainActivity.class);
                            startActivity(intent);
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onPause() {
        super.onPause();
        String [] args = new String[1];
        PocjetnikQueryHandler handler =  new PocjetnikQueryHandler(getContentResolver());

        Kategorija cat = (Kategorija)spinner.getSelectedItem();
        int catId = cat.catId.get();
        ContentValues values = new ContentValues();
        values.put(PocjetnikContract.PocjetnikEntry.COLUMN_TEXT, pocjetnik.text.get());
        values.put(PocjetnikContract.PocjetnikEntry.COLUMN_CATEGORY, catId);
        values.put(PocjetnikContract.PocjetnikEntry.COLUMN_DONE, pocjetnik.done.get());
        values.put(PocjetnikContract.PocjetnikEntry.COLUMN_EXPIRED, pocjetnik.expired.get());
        if(pocjetnik != null && pocjetnik.id.get() != 0) {
            args[0] = String.valueOf(pocjetnik.id.get());
            handler.startUpdate(1,null,PocjetnikContract.PocjetnikEntry.CONTENT_URI, values,
                    PocjetnikContract.PocjetnikEntry._ID + "=?", args);
        }
        else if(pocjetnik != null && pocjetnik.id.get() == 0) {
            handler.startInsert(1,null,PocjetnikContract.PocjetnikEntry.CONTENT_URI, values);
        }
    }
}