package vsite.hr.map.pocjetnik;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.widget.ListView;

import vsite.hr.map.pocjetnik.model.Kategorija;

public class KategorijaListBinder {
    @BindingAdapter("bind:items")
    public static void bindList(ListView view,
                                ObservableArrayList<Kategorija> list) {
        KategorijeListAdapter adapter;
        if (list == null) {
            adapter = new KategorijeListAdapter();
        }
        else {
            adapter = new KategorijeListAdapter(list);
        }
        view.setAdapter(adapter);
    }
}
