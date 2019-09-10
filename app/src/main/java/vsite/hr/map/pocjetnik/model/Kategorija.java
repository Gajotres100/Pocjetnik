package vsite.hr.map.pocjetnik.model;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import java.io.Serializable;

public class Kategorija implements Serializable {
    public final ObservableInt catId = new ObservableInt();
    public final ObservableField<String> description =
            new ObservableField<String>();

    public Kategorija () {

    }
    public Kategorija(int i, String d) {
        catId.set(i);
        description.set(d);
    }
}
