package vsite.hr.map.pocjetnik.Data;

import android.provider.BaseColumns;

public class PocjetnikContract {
    public static final class PocjetnikEntry implements BaseColumns {

        public static final String TABLE_NAME = "todos";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TEXT = "text";
        public static final String COLUMN_CREATED = "created";
        public static final String COLUMN_EXPIRED = "expired";
        public static final String COLUMN_DONE = "done";
        public static final String COLUMN_CATEGORY = "category";
    }

    public static final class KategorijaEntry implements BaseColumns {

        public static final String TABLE_NAME = "categories";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_DESCRIPTION = "description";
    }
}
