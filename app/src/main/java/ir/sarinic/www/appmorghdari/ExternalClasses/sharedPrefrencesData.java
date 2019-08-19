package ir.sarinic.www.appmorghdari.ExternalClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class sharedPrefrencesData {

    public SharedPreferences shp;

    public sharedPrefrencesData() {
    }


    public String getData(Context context, String key) {
        shp = context.getSharedPreferences("program codes", Context.MODE_PRIVATE);
        return shp.getString(key, null);
    }

    public void insertData(Context context, int i, String[] key, String[] data) {
        shp = context.getSharedPreferences("program codes", Context.MODE_PRIVATE);
        SharedPreferences.Editor edi = shp.edit();
        for (int j = 0; j < i; j++) {
            edi.putString(key[j], data[j]);
        }
        edi.commit();
    }

}
