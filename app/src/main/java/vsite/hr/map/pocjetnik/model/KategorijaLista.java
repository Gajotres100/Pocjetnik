package vsite.hr.map.pocjetnik.model;

import android.databinding.ObservableArrayList;

import java.io.Serializable;

public class KategorijaLista implements Serializable {
    public ObservableArrayList<Kategorija> ItemList;

    public KategorijaLista() {
        ItemList = new ObservableArrayList<>();
    }

    public KategorijaLista(ObservableArrayList<Kategorija> itemList) {
        ItemList = itemList;
    }
}
