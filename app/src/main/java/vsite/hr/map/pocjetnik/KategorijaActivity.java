package vsite.hr.map.pocjetnik;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class KategorijaActivity extends AppCompatActivity {
    String[] itemname ={
            "Home",
            "Work",
            "Family",
            "Sport",
            "Hobbies"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kategorija_aktiviti);

        final ListView lv=(ListView) findViewById(R.id.lvCategories);

        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.kategorija_list_item,
                R.id.tvNote,itemname));


    }
}
