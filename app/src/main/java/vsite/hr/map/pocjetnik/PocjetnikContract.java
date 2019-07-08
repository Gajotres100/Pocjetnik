package vsite.hr.map.pocjetnik;

import android.provider.BaseColumns;

public class PocjetnikContract {
    public static final class PocjetnikEntry implements BaseColumns {
        // Table name
        public static final String TABLE_NAME = "todos";
        //column (field) names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TEXT = "text";
        public static final String COLUMN_CREATED = "created";
        public static final String COLUMN_EXPIRED = "expired";
        public static final String COLUMN_DONE = "done";
        public static final String COLUMN_CATEGORY = "category";
    }

    public static final class KategorijaEntry implements BaseColumns {
        // Table name
        public static final String TABLE_NAME = "categories";
        //column names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_DESCRIPTION = "description";
    }
}
