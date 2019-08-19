package ir.sarinic.www.appmorghdari.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ir.sarinic.www.appmorghdari.R;
import ir.sarinic.www.appmorghdari.db.DbHelper;
import ir.sarinic.www.appmorghdari.db.adapter_item;
import ir.sarinic.www.appmorghdari.db.items;
import ir.sarinic.www.appmorghdari.ExternalClasses.sms;
import ir.sarinic.www.appmorghdari.ExternalClasses.stringssss;


public class menu extends Fragment {


    DbHelper dbb;
    SQLiteDatabase mydb;
    public static int idItem;
    ListView lstFan, lstHeater, lstHumid;
    adapter_item adpFan, adpHumid, adpHeater;
    EditText txtnameItemAdd, txtcodeItemAdd;
    String phonenumber, sysCode;
    Button btnSaveItemAdd, btnBackItemAdd;
    String itemCode;
    stringssss st = null;
    sms sms;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_menu, container, false);


/**
 * Define --------------------------------------
 */
        dbb = new DbHelper(getActivity());

        st = new stringssss();

        adpHeater = new adapter_item(getActivity());
        adpFan = new adapter_item(getActivity());
        adpHumid = new adapter_item(getActivity());

        lstFan = rootView.findViewById(R.id.lstFan);
        lstHeater = rootView.findViewById(R.id.lstHeater);
        lstHumid = rootView.findViewById(R.id.lstHumid);

        mydb = getContext().openOrCreateDatabase(st.DATABASE_NAME, Context.MODE_PRIVATE, null);
        mydb.execSQL(st.queryCreateTable);

        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);

        LinearLayout menulayout = rootView.findViewById(R.id.menulayout);
        LinearLayout errorlayout = rootView.findViewById(R.id.errorlayout);

        RadioButton radioButtonAuto = rootView.findViewById(R.id.automatic);
        RadioButton radioButtonManual = rootView.findViewById(R.id.manual);
/**
 * ListView Update --------------------------------------
 */
        updatelistView(adpFan, lstFan, 1);
        updatelistView(adpHeater, lstHeater, 2);
        updatelistView(adpHumid, lstHumid, 3);


/**
 * RadioButton manual_automatic ---------------------------------
 */
        final SharedPreferences shp = getActivity().getSharedPreferences("program codes", Context.MODE_PRIVATE);
        phonenumber = shp.getString("phone number", null);
        sysCode = shp.getString("system password", null);
        try {
            int index = Integer.parseInt(shp.getString("system mode", null));
            try {
                ((RadioButton) radioGroup.getChildAt(index)).setChecked(true);
            } catch (Exception e) {
            }
            if (index == 1) {
                menulayout.setVisibility(View.INVISIBLE);
                errorlayout.setVisibility(View.VISIBLE);
            } else {
                menulayout.setVisibility(View.VISIBLE);
                errorlayout.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
        }

        radioButtonAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edi = shp.edit();
                sms = new sms();
                sms.sendSMS(getContext(), "mod=01");

            }
        });

        radioButtonManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sms = new sms();
                sms.sendSMS(getContext(), "mod=00");
            }
        });


/**
 * ListView Click --------------------------------------
 */
        lstFan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listviewClick(parent, position);
            }
        });

        lstHumid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listviewClick(parent, position);
            }
        });

        lstHeater.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listviewClick(parent, position);
            }
        });


/**
 * ListView LongClick --------------------------------------
 */
        lstFan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                listviewLongclick(adpFan, parent, position, lstFan, 1);
                return true;
            }
        });

        lstHeater.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                listviewLongclick(adpHeater, parent, position, lstHeater, 2);
                return true;
            }
        });

        lstHumid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                listviewLongclick(adpHumid, parent, position, lstHumid, 3);
                return true;
            }
        });


        return rootView;
    }


    /**
     * ListView Update --------------------------------------
     */
    public void updatelistView(adapter_item adapter_item, ListView listView, int type) {

        adapter_item.clearlist();
        Cursor c = mydb.rawQuery("select * from tbl_items where TYPE =" + type, null);
        while (c.moveToNext()) {
            items mn = new items();
            mn.name = c.getString(1);
            mn.id = c.getInt(0);
            mn.status = c.getInt(2);
            adapter_item.addtolist(mn);
            adapter_item.notifyDataSetChanged();
        }
        listView.setAdapter(adapter_item);
    }


    /**
     * ListView LongClick --------------------------------------
     */
    public void listviewLongclick(final adapter_item adapter_item, AdapterView<?> parent, int position, final ListView listView, final int type) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.onBackPressed();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_delete_menu);
        dialog.show();

        Button btnedit = dialog.findViewById(R.id.btnEdit);
        Button btndelete = dialog.findViewById(R.id.btnDelete);

        final items items = (items) parent.getAdapter().getItem(position);

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                final Dialog dialog2 = new Dialog(getContext());
                dialog2.setCancelable(false);
                dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog2.setContentView(R.layout.item_menu_add);
                dialog2.show();

                txtcodeItemAdd = dialog2.findViewById(R.id.txtCodeItemAdd);
                txtnameItemAdd = dialog2.findViewById(R.id.txtNameItemAdd);
                btnSaveItemAdd = dialog2.findViewById(R.id.btnSaveItemAdd);
                btnBackItemAdd = dialog2.findViewById(R.id.btnBackItemAdd);

                SQLiteDatabase mydb = getActivity().openOrCreateDatabase(st.DATABASE_NAME, Context.MODE_PRIVATE, null);
                String sql = "select * from " + st.TABLE_NAME + " where ID = " + items.id;
                Cursor c = mydb.rawQuery(sql, null);

                while (c.moveToNext()) {
                    items items = new items();
                    items.id = c.getInt(0);
                    items.name = c.getString(1);

                    adapter_item.addtolist(items);

                    idItem = items.id;

                    txtnameItemAdd.setText(items.name.toString());
                    txtcodeItemAdd.setText(String.valueOf(items.id).toString());
                }

                btnBackItemAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.dismiss();
                        updatelistView(adapter_item, listView, type);
                    }
                });

                btnSaveItemAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbb = new DbHelper(getContext());
                        dbb.updateItem(txtnameItemAdd.getText().toString(), idItem, Integer.parseInt(txtcodeItemAdd.getText().toString()));
                        dialog2.dismiss();
                        updatelistView(adapter_item, listView, type);
                    }
                });
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbb = new DbHelper(getContext());
                dbb.deleteItem(items.id);
                dialog.dismiss();
                updatelistView(adapter_item, listView, type);
            }
        });
    }


    /**
     * ListView Click --------------------------------------
     */
    public void listviewClick(AdapterView<?> parent, int position) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.on_off_menu);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.show();


        final SharedPreferences txtseting = getActivity().getSharedPreferences("program codes", Context.MODE_PRIVATE);
        phonenumber = txtseting.getString("phone number", null);
        sysCode = txtseting.getString("system password", null);
        final items items = (items) parent.getAdapter().getItem(position);

        Button btnOn = dialog.findViewById(R.id.btnOn);
        Button btnOff = dialog.findViewById(R.id.btnOff);

        SQLiteDatabase mydb = getActivity().openOrCreateDatabase(st.DATABASE_NAME, Context.MODE_PRIVATE, null);
        String sql = "select * from " + st.TABLE_NAME + " where ID = " + items.id;
        Cursor c = mydb.rawQuery(sql, null);

        while (c.moveToNext()) {
            items ii = new items();
            ii.id = c.getInt(0);
            ii.name = c.getString(1);

            itemCode = String.valueOf(ii.id);
        }

        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sms = new sms();
                sms.sendSMS(getContext(), itemCode + "=01");
                dialog.dismiss();
            }
        });

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sms = new sms();
                sms.sendSMS(getContext(), itemCode + "=00");
                dialog.dismiss();
            }
        });
    }
}
