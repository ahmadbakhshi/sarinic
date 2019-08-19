package ir.sarinic.www.appmorghdari.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Toast;


import ir.sarinic.www.appmorghdari.ExternalClasses.sharedPrefrencesData;
import ir.sarinic.www.appmorghdari.MainActivity;
import ir.sarinic.www.appmorghdari.R;
import ir.sarinic.www.appmorghdari.ExternalClasses.sms;

import static android.content.Context.NOTIFICATION_SERVICE;

public class setting extends Fragment {


    String phonenumber, sysCode;
    sharedPrefrencesData shp;
    sms sms;

    @SuppressLint("WrongConstant")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_setting, container, false);


/**
 * Define --------------------------------------------------------
 */
        Button btnsave = rootView.findViewById(R.id.btnSaveSetting);
        Button btnTellChange = rootView.findViewById(R.id.btnTellChange);
        Button btnPassChange = rootView.findViewById(R.id.btnPassChange);

        final EditText Pass = rootView.findViewById(R.id.txtSystemPass);
        final EditText tell = rootView.findViewById(R.id.txtTellSet);


/**
 * Loading --------------------------------------------------------
 */
        shp = new sharedPrefrencesData();
        try {
            phonenumber = shp.getData(getContext(), "phone number");
            sysCode = shp.getData(getContext(), "system password");

            Pass.setText(sysCode);
            tell.setText(phonenumber);
        } catch (Exception e) {
        }


/**
 * Save setting software --------------------------------------------------------
 */
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] keys = {"phone number", "system password"};
                String[] datainput = {tell.getText().toString(), Pass.getText().toString()};

                if (datainput[1].length() != 4) {
                    Toast toast = Toast.makeText(getActivity(), "تعداد کارکتر های رمز باید 4 رقم باشد.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 50);
                    toast.show();
                } else if (datainput[0].length() != 11) {
                    Toast toast = Toast.makeText(getActivity(), "شماره موبایل به درستی وارد نشد است.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 50);
                    toast.show();
                } else {
                    shp = new sharedPrefrencesData();
                    shp.insertData(getContext(), 2, keys, datainput);

                    Toast toast = Toast.makeText(getActivity(), "با موفقیت ذخیره شد", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 50);
                    toast.show();
                }
            }
        });


/**
 * btn click  --------------------------------------------------------
 */
        btnTellChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.setCancelable(false);
                dialog.onBackPressed();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.setting_tell_change);
                dialog.show();

                Button btnCancelTellChange = dialog.findViewById(R.id.btnBackTellChange);
                Button btnSaveTellChange = dialog.findViewById(R.id.btnSaveTellChange);

                final EditText txttell1 = dialog.findViewById(R.id.txttell1);
                final EditText txttell2 = dialog.findViewById(R.id.txttell2);
                final EditText txttell3 = dialog.findViewById(R.id.txttell3);
                final EditText txttell4 = dialog.findViewById(R.id.txttell4);

                shp = new sharedPrefrencesData();
                txttell1.setText(shp.getData(getContext(), "tell1"));
                txttell2.setText(shp.getData(getContext(), "tell2"));
                txttell3.setText(shp.getData(getContext(), "tell3"));
                txttell4.setText(shp.getData(getContext(), "tell4"));

                btnCancelTellChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnSaveTellChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] tel = {txttell1.getText().toString(), txttell2.getText().toString(), txttell3.getText().toString(), txttell4.getText().toString()};

                        for (int i = 0; i < 4; i++) {
                            if (tel[i].length() != 11) {
                                Toast toast = Toast.makeText(getActivity(), "شماره موبایل به درستی وارد نشد است.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP, 0, 50);
                                toast.show();
                            } else {
                                sms = new sms();
                                sms.sendSMS(getContext(), "tel" + String.valueOf(i) + sysCode + tel[i]);
                            }
                        }
                        dialog.dismiss();
                    }
                });
            }
        });


        btnPassChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setCancelable(false);

                dialog.onBackPressed();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.setting_pass_change);
                dialog.show();

                final EditText txtoldpass = dialog.findViewById(R.id.txtoldpass);
                final EditText txtnewpass = dialog.findViewById(R.id.txtnewpass);
                Button btnCancelPassChange = dialog.findViewById(R.id.btnBackPassChange);

                btnCancelPassChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Button btnSavePassChange = dialog.findViewById(R.id.btnSavePassChange);
                btnSavePassChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String messag = txtoldpass.getText().toString() + txtnewpass.getText().toString();
                        if (messag.length() != 8) {
                            Toast toast = Toast.makeText(getActivity(), "رمز حتما باید 4 رقمی باشد", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 50);
                            toast.show();
                        } else {
                            sms = new sms();
                            sms.sendSMS(getContext(), "pass" + messag);
                            dialog.dismiss();
                        }
                    }
                });
            }
        });


        return rootView;
    }
}
