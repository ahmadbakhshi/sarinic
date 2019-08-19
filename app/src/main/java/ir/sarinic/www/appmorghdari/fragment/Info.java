package ir.sarinic.www.appmorghdari.fragment;

import android.Manifest;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ir.sarinic.www.appmorghdari.R;
import ir.sarinic.www.appmorghdari.db.adapter_item_info;
import ir.sarinic.www.appmorghdari.db.items;
import ir.sarinic.www.appmorghdari.ExternalClasses.stringssss;


public class Info extends Fragment {


    adapter_item_info adp;
    SQLiteDatabase mydb;
    ListView lstinfo;
    stringssss st;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_info, container, false);

        st = new stringssss();

        mydb = getContext().openOrCreateDatabase(st.DATABASE_NAME, Context.MODE_PRIVATE, null);
        mydb.execSQL(st.queryCreateTable);


        lstinfo = rootView.findViewById(R.id.lstinfo);
        adp = new adapter_item_info(getContext());
        adp.clearlist();
        Cursor c = mydb.rawQuery("select * from tbl_items order by TYPE", null);
        while (c.moveToNext()) {
            items mn = new items();
            mn.name = c.getString(1);
            mn.id = c.getInt(0);
            mn.status = c.getInt(2);
            adp.addtolist(mn);
            adp.notifyDataSetChanged();
        }
        lstinfo.setAdapter(adp);

        return rootView;
    }

}
