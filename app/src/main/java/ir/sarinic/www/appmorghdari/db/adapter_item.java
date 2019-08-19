package ir.sarinic.www.appmorghdari.db;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import ir.sarinic.www.appmorghdari.R;

public class
adapter_item extends BaseAdapter {

    private Context context;

    public adapter_item(Context context) {
        this.context = context;
    }

    //List<namayande> codeLearnChapterList = getDataForListView();
    List<items> codeLearnChapterList = getDataForListView();

    public void clearlist() {
        codeLearnChapterList.clear();
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return codeLearnChapterList.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return codeLearnChapterList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }


    @Override
    public View getView(final int arg0, View arg1, final ViewGroup arg2) {

        if (arg1 == null) {
            LayoutInflater inflater = LayoutInflater.from(arg2.getContext());
            arg1 = inflater.inflate(R.layout.item_menu, arg2, false);
        }

        TextView name = (TextView) arg1.findViewById(R.id.itemNameMenuDisplay);


        final items chapter = codeLearnChapterList.get(arg0);

        name.setText(chapter.name);
        int id = chapter.id;

/*
        final ToggleButton btn = arg1.findViewById(R.id.tgb);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("WrongConstant") SharedPreferences txtseting = context.getSharedPreferences("program codes", Context.MODE_ENABLE_WRITE_AHEAD_LOGGING);
                String phonenumber = (txtseting.getString("phone number", null));
                String code = (txtseting.getString("system password", null));
                if (btn.isChecked()) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phonenumber, null, "*" + code + "*" + "555555" + "*on#", null, null);

                } else {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phonenumber, null, "*" + code + "*" + "555555" + "*off#", null, null);
                }
            }
        });
*/
        return arg1;
    }


    public List<items> getDataForListView() {
        List<items> codeLearnChaptersList = new ArrayList<items>();


        return codeLearnChaptersList;


    }

    public void addtolist(items it) {
        codeLearnChapterList.add(it);
    }

}
