package vsite.hr.map.pocjetnik.Data;

import android.net.Uri;
import android.provider.BaseColumns;

public class PocjetnikContract {

    public static final String CONTENT_AUTHORITY = "vsite.hr.map.pocjetnik.pocjetnikprovider";
    public static final String PATH_TODOS="todos";
    public static final String PATH_CATEGORIES="categories";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class PocjetnikEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TODOS);

        public static final String TABLE_NAME = "todos";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TEXT = "text";
        public static final String COLUMN_CREATED = "created";
        public static final String COLUMN_EXPIRED = "expired";
        public static final String COLUMN_DONE = "done";
        public static final String COLUMN_CATEGORY = "category";
    }

    public static final class KategorijaEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CATEGORIES);

        public static final String TABLE_NAME = "categories";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_DESCRIPTION = "description";
    }
}
