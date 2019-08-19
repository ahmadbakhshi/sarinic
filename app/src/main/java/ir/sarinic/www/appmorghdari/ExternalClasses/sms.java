package ir.sarinic.www.appmorghdari.ExternalClasses;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import ir.sarinic.www.appmorghdari.MainActivity;
import ir.sarinic.www.appmorghdari.R;
import ir.sarinic.www.appmorghdari.db.DbHelper;
import ir.sarinic.www.appmorghdari.fragment.setting;

public class sms {


    String phonenumber, sysCode;
    int type = 0;
    int id = 0;
    int status = 0;
    String idname;
    DbHelper dbb;
    sharedPrefrencesData shp;

    public sms() {

    }

    public void sendSMS(Context context, String Message) {

        shp = new sharedPrefrencesData();
        phonenumber = shp.getData(context, "phone number");
        sysCode = shp.getData(context, "system password");

        if (phonenumber.length() > 0 & sysCode.length() > 0) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phonenumber, null, Message, null, null);
        } else {
            Toast.makeText(context, "شماره موبایل یا رمز سیستم در نرم افزار ثبت نشده است", Toast.LENGTH_SHORT).show();
        }
    }


    public void reciveSMS(Context context, Bundle bundle) {
        final Object[] pdusObj = (Object[]) bundle.get("pdus");


        for (int i = 0; i < pdusObj.length; i++) {

            SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);

            String phoneNumber = currentMessage.getDisplayOriginatingAddress();
            String message = currentMessage.getDisplayMessageBody();
            int count = message.length();

            for (int ii = 0; ii < count; ii++) {

                try {
                    char typechar = message.charAt(ii);
                    char[] idchar = {message.charAt(ii), message.charAt(ii + 1), message.charAt(ii + 2)};
                    char[] idcharname = {message.charAt(ii + 1), message.charAt(ii + 2)};
                    char[] statuschar = {message.charAt(ii + 4), message.charAt(ii + 5)};

                    String typename = String.valueOf(typechar);
                    try {
                        idname = String.valueOf(idcharname);
                        type = Integer.parseInt(String.valueOf(typechar));
                        id = Integer.parseInt(String.valueOf(idchar));
                        status = Integer.parseInt(String.valueOf(statuschar));
                    } catch (Exception e) {
                    }


                    try {
                        if (typename.equals("m")) {
                            char[] systemType = {message.charAt(ii + 4), message.charAt(ii + 5)};
                            String[] key = {"system mode"};
                            String[] data = {String.valueOf(systemType)};

                            shp.insertData(context, 1, key, data);
                        } else if (typename.equals("1")) {
                            dbb = new DbHelper(context);
                            try {
                                dbb.insertItem(id, "فن " + idname, status, type);
                            } catch (Exception e) {
                            }
                            dbb.updateSms(id, status);

                        } else if (typename.equals("2")) {
                            dbb = new DbHelper(context);
                            try {
                                dbb.insertItem(id, "هیتر " + idname, status, type);
                            } catch (Exception e) {
                            }
                            dbb.updateSms(id, status);

                        } else if (typename.equals("3")) {
                            dbb = new DbHelper(context);
                            try {
                                dbb.insertItem(id, "رطوبت ساز " + idname, status, type);
                            } catch (Exception e) {
                            }
                            dbb.updateSms(id, status);

                        } else if (typename.equals("4")) {
                            dbb = new DbHelper(context);
                            try {
                                dbb.insertItem(id, "دماسنج " + idname, status, type);
                            } catch (Exception e) {
                            }
                            dbb.updateSms(id, status);

                        } else if (typename.equals("5")) {
                            dbb = new DbHelper(context);
                            try {
                                dbb.insertItem(id, "رطوبت سنج " + idname, status, type);
                            } catch (Exception e) {
                            }
                            dbb.updateSms(id, status);

                        } else if (typename.equals("6")) {
                            dbb = new DbHelper(context);

                            try {
                                dbb.insertItem(id, "تغذیه", status, type);
                            } catch (Exception e) {
                            }
                            dbb.updateSms(id, status);

                            String m = "";
                            if (status == 1) {
                                m = "نرم افزار سارینیک : برق سیستم قطع شده است و در حالت باطری می یاشد.";
                            } else if (status == 0) {
                                m = "نرم افزار سارینیک :سیستم در حالت برق شهری می باشد.";
                            }
                            Toast.makeText(context, m, Toast.LENGTH_LONG).show();
                        } else if (typename.equals("p")) {

                            char[] chekchar = {message.charAt(ii + 2), message.charAt(ii + 3), message.charAt(ii + 4), message.charAt(ii + 5)};
                            String chek = String.valueOf(chekchar);
                            if (chek.equals("eror")) {
                                Toast.makeText(context, "پسورد تغییر نکرد و با مشکل روبرو شده است", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "پسورد با موفقیت عوض شد", Toast.LENGTH_LONG).show();
                            }
                        } else if (typename.equals("t")) {

                            String[] keys = {"tell1", "tell2", "tell3", "tell4"};
                            String[] datas = {"", "", "", ""};
                            shp = new sharedPrefrencesData();
                            shp.insertData(context, 4, keys, datas);

                            for (int j = ii; j < count; j++) {
                                char idtelchar = message.charAt(j + 1);
                                char[] tchar = {message.charAt(j + 3), message.charAt(j + 4), message.charAt(j + 5), message.charAt(j + 6), message.charAt(j + 7), message.charAt(j + 8),
                                        message.charAt(j + 9), message.charAt(j + 10), message.charAt(j + 11), message.charAt(j + 12), message.charAt(j + 13)};

                                String idtel = String.valueOf(idtelchar);
                                String tell = String.valueOf(tchar);

                                String[] key = {"tell" + idtel};
                                String[] data = {String.valueOf(tell)};

                                shp.insertData(context, 1, key, data);

                                j = j + 14;
                            }
                            break;
                        }
                    } catch (Exception e) {
                    }

                } catch (Exception e) {
                }

                int dd = ii + 6;
                ii = dd;
            }
            Intent in = new Intent("SmsMessage.intent.MAIN").
                    putExtra("get_msg", 1);

            context.sendBroadcast(in);

        }
    }
}
