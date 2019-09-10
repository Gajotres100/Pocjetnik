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


import vsite.hr.map.pocjetnik.Data.PocjetnikQueryHandler;
import vsite.hr.map.pocjetnik.databinding.ActivityPocjetnikBinding;
import vsite.hr.map.pocjetnik.model.Pocjetnik;

public class PocjetnikActivity extends AppCompatActivity {
    Pocjetnik pocjetnik;
    PocjetnikQueryHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocjetnik);

        handler = new PocjetnikQueryHandler(getContentResolver());

        ActivityPocjetnikBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_pocjetnik);

        Intent intent = getIntent();
        pocjetnik = (Pocjetnik)intent.getSerializableExtra("pocjetnik");

    }
}
