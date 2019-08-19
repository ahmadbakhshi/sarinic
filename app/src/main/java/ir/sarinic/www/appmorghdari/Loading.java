package ir.sarinic.www.appmorghdari;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Loading extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        String languageload = "en";
        Locale locale = new Locale(languageload);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        final ImageView imglogo = findViewById(R.id.imgLogo);
        final TextView brand = findViewById(R.id.txtBrand);
        final TextView name = findViewById(R.id.txtName);

        /* animation */
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imglogo, View.ALPHA, 0, 1);
        objectAnimator.setDuration(1000);
        objectAnimator.setStartDelay(500);
        objectAnimator.start();

        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(brand, View.ALPHA, 0, 1);
        objectAnimator2.setDuration(1000);
        objectAnimator2.setStartDelay(1500);
        objectAnimator2.start();

        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(name, View.ALPHA, 0, 1);
        objectAnimator3.setDuration(1000);
        objectAnimator3.setStartDelay(2000);
        objectAnimator3.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent inent1 = new Intent(Loading.this, MainActivity.class);
                startActivity(inent1);
            }
        }, 3500);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
