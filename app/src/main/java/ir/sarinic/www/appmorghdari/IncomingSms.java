package ir.sarinic.www.appmorghdari;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


import ir.sarinic.www.appmorghdari.ExternalClasses.sms;
import ir.sarinic.www.appmorghdari.db.DbHelper;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;


public class IncomingSms extends BroadcastReceiver {


    DbHelper dbb;
    SQLiteDatabase mydb;
    String DATABASE_NAME = "sarinic_db";
    String TABLE_NAME = "tbl_items";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    int type = 0;
    int id = 0;
    int status = 0;
    String idname;
    sms sms;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(SMS_RECEIVED)) {

            final Bundle bundle = intent.getExtras();
            if (bundle != null) {
                sms = new sms();
                sms.reciveSMS(context, bundle);
            }
        }
    }
}
