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
adapter_item_info extends BaseAdapter {

    private Context context;

    public adapter_item_info(Context context) {
        this.context = context;
    }

    //List<namayande> codeLearnChapterList = getDataForListView();
    List<items> codeLearnChapterList = getDataForListView();

    public adapter_item_info() {

    }


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
            LayoutInflater inflater2 = LayoutInflater.from(arg2.getContext());
            arg1 = inflater2.inflate(R.layout.items_info, arg2, false);
        }

        TextView name = (TextView) arg1.findViewById(R.id.nameinfo);
        TextView status = (TextView) arg1.findViewById(R.id.statusinfo);

        final items chapter = codeLearnChapterList.get(arg0);

        name.setText(chapter.name);
        int id = chapter.id;
        int type = chapter.type;
        if (chapter.status == 0 & chapter.id < 400) {
            status.setText("خاموش");
        } else if (chapter.status == 1 & chapter.id < 400) {
            status.setText("روشن");
        } else if (chapter.id > 400 & chapter.id < 500) {
            status.setText(String.valueOf(chapter.status) + "°c");
        } else if (chapter.id > 500 & chapter.id < 600) {
            status.setText(String.valueOf(chapter.status) + "%");
        } else if (chapter.status == 1 & chapter.id > 600) {
            status.setText("باطری");
        } else if (chapter.status == 0 & chapter.id > 600) {
            status.setText("برق");
        }

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
