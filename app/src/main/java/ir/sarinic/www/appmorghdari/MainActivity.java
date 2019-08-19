package ir.sarinic.www.appmorghdari;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Random;

import ir.sarinic.www.appmorghdari.ExternalClasses.NotificationHelper;
import ir.sarinic.www.appmorghdari.ExternalClasses.sharedPrefrencesData;
import ir.sarinic.www.appmorghdari.ExternalClasses.sms;
import ir.sarinic.www.appmorghdari.fragment.Info;
import ir.sarinic.www.appmorghdari.fragment.about;
import ir.sarinic.www.appmorghdari.fragment.menu;
import ir.sarinic.www.appmorghdari.fragment.setting;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Boolean firstTime = null;
    String phonenumber, sysCode;
    public static int refreshpage;
    private BroadcastReceiver mIntentReceiver;
    menu menufragment;
    setting settingfragment;
    private int MY_PERMISSIONS_REQUEST_SMS_RECEIVE = 10;
    ir.sarinic.www.appmorghdari.ExternalClasses.sms sms;
    ir.sarinic.www.appmorghdari.ExternalClasses.sharedPrefrencesData shp;
    NotificationHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS, Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS},
                MY_PERMISSIONS_REQUEST_SMS_RECEIVE);


/**
 * Load ----------------------Start-----------------------
 */
        shp = new sharedPrefrencesData();
        phonenumber = shp.getData(MainActivity.this, "phone number");
        sysCode = shp.getData(MainActivity.this, "system password");

        if (isFirstTime()) {
            String[] key = {"phone number", "system password", "tell1", "tell2", "tell3", "tell4"};
            String[] data = {"", "", "", "", "", ""};
            shp.insertData(MainActivity.this, 6, key, data);
        }


        try {
            Intent intent = getIntent();
            refreshpage = Integer.parseInt(intent.getStringExtra("refresh"));
        } catch (Exception e) {
        }


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        final ImageView btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation anim1 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim1);

                btnRefresh.startAnimation(anim1);

                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(btnRefresh, "rotation", 360);
                objectAnimator.setDuration(1000);
                objectAnimator.start();

                sms = new sms();
                shp = new sharedPrefrencesData();
                sysCode = shp.getData(MainActivity.this, "system password");
                sms.sendSMS(MainActivity.this, "infr" + sysCode);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("SmsMessage.intent.MAIN");
        mIntentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                intent = new Intent(getBaseContext(), MainActivity.class);
                startActivities(new Intent[]{intent});
                finish();
            }
        };
        this.registerReceiver(mIntentReceiver, intentFilter);
    }

    @Override
    protected void onPause() {

        super.onPause();
        this.unregisterReceiver(this.mIntentReceiver);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Info info = new Info();
                    return info;

                case 1:
                    menu menu = new menu();
                    return menu;

                case 2:
                    setting setting = new setting();
                    return setting;

                case 3:
                    about about = new about();
                    return about;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "اطلاعات";
                case 1:
                    return "منو";
                case 2:
                    return "تنظیمات";
                case 3:
                    return "پشتیبانی";
            }
            return null;
        }
    }

    private boolean isFirstTime() {
        if (firstTime == null) {
            SharedPreferences mPreferences = this.getSharedPreferences("first_time", Context.MODE_PRIVATE);
            firstTime = mPreferences.getBoolean("firstTime", true);
            if (firstTime) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean("firstTime", false);
                editor.commit();
            }
        }
        return firstTime;
    }
}
