package vsite.hr.map.pocjetnik;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import vsite.hr.map.pocjetnik.Data.PocjetnikContract;

public class PocjetnikCursorAdapter extends CursorAdapter {
    public PocjetnikCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(
                R.layout.pocjetnik_list_item, parent, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView todoTextView = (TextView) view.findViewById(R.id.tvText);
        int textColumn = cursor.getColumnIndex(PocjetnikContract.PocjetnikEntry.COLUMN_TEXT);
        String text = cursor.getString(textColumn);
        todoTextView.setText(text);
    }
}
