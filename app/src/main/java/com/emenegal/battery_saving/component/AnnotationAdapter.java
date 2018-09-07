package com.emenegal.battery_saving.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emenegal.battery_saving.R;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class AnnotationAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<Field> annotations;
    private static LayoutInflater inflater=null;


     public AnnotationAdapter(Context applicationContext, ArrayList<Field> annotations) {
        this.context = applicationContext;
        this.annotations = annotations;
        this.inflater = (LayoutInflater.from(applicationContext));
     }

    @Override
    public int getCount() {
        return annotations.size();
    }

    @Override
    public Object getItem(int i) {
        return annotations.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder {
        protected TextView name;
        protected TextView type;
        protected TextView value;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;


        if(convertView == null) {
            convertView = inflater.inflate(R.layout.annotation_list_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.annotation_name);
            viewHolder.type = (TextView) convertView.findViewById(R.id.annotation_type);
            viewHolder.value = (TextView) convertView.findViewById(R.id.annotation_value);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Field currentField = (Field)getItem(position);

        viewHolder.name.setText(currentField.getName());

        Class<?> type = currentField.getType();
        viewHolder.type.setText(type.getName());

        try {
            viewHolder.value.setText(currentField.get(null)+"");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}