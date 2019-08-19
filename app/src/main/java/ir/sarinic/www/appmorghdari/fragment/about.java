package ir.sarinic.www.appmorghdari.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ir.sarinic.www.appmorghdari.R;


public class about extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_about, container, false);


/**
 * Define -------------------Start----------------------
 */

        TextView phone = rootView.findViewById(R.id.aboutphone);
        TextView tell1 = rootView.findViewById(R.id.abouttell1);
        TextView tell2 = rootView.findViewById(R.id.abouttell2);
        TextView web = rootView.findViewById(R.id.aboutweb);


/**
 * Call Click -------------------Start----------------------
 */
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call("+981133251498");
            }
        });

        tell1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call("+989112272562");
            }
        });

        tell2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call("09114241662");
            }
        });

/**
 * Browse Click -------------------Start----------------------
 */
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browse("https://www.sarinic.ir");
            }
        });


        return rootView;
    }

    /**
     * Call Class -------------------Start----------------------
     */
    public void call(String number) {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null)));
    }


    /**
     * Browse Class -------------------Start----------------------
     */
    public void browse(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }


}
