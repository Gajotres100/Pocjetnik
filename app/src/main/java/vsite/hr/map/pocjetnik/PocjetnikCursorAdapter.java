package vsite.hr.map.pocjetnik;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

public class PocjetnikCursorAdapter extends CursorAdapter {
    public PocjetnikCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(
                R.layout.kategorija_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView todoTextView = (TextView) view.findViewById(R.id.tvNote);
        int textColumn = cursor.getColumnIndex(TodosContract.TodosEntry.COLUMN_TEXT);
        String text = cursor.getString(textColumn);
        todoTextView.setText(text);
    }
}
