package vsite.hr.map.pocjetnik.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import vsite.hr.map.pocjetnik.Data.PocjetnikContract;
import vsite.hr.map.pocjetnik.Data.PocjetnikContract.KategorijaEntry;
import vsite.hr.map.pocjetnik.Data.PocjetnikContract.PocjetnikEntry;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pocjetnikapp.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CATEGORIES_CREATE=
            "CREATE TABLE " + KategorijaEntry.TABLE_NAME + " (" +
                    KategorijaEntry._ID + " INTEGER PRIMARY KEY, " +
                    KategorijaEntry.COLUMN_DESCRIPTION + " TEXT " +
                    ")";
    private static final String TABLE_TODOS_CREATE =
            "CREATE TABLE " + PocjetnikEntry.TABLE_NAME + " (" +
                    PocjetnikEntry._ID + " INTEGER PRIMARY KEY, " +
                    PocjetnikEntry.COLUMN_TEXT + " TEXT, " +
                    PocjetnikEntry.COLUMN_CREATED + " TEXT default CURRENT_TIMESTAMP, " +
                    PocjetnikContract.PocjetnikEntry.COLUMN_EXPIRED + " TEXT, " +
                    PocjetnikEntry.COLUMN_DONE + " INTEGER, " +
                    PocjetnikEntry.COLUMN_CATEGORY + " INTEGER, " +
                    " FOREIGN KEY("+ PocjetnikEntry.COLUMN_CATEGORY + ") REFERENCES " +
                    PocjetnikEntry.TABLE_NAME +
                    "(" + PocjetnikEntry._ID +") " + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CATEGORIES_CREATE);
        db.execSQL(TABLE_TODOS_CREATE);
        //drugi laptop
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PocjetnikEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + KategorijaEntry.TABLE_NAME);
        onCreate(db);
    }
}
