package com.emenegal.battery_saving.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.emenegal.battery_saving.Util;
import com.emenegal.battery_saving.annotation.BPrecision;
import com.emenegal.battery_saving.annotation.EPrecision;
import com.emenegal.battery_saving.annotation.IPrecision;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class AnnotationList extends ListView {

    AnnotationAdapter adapter;
    ArrayList<Field> fields;

    public AnnotationList(Context context) {
        super(context);
        initialize();
    }

    public AnnotationList(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public AnnotationList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    public AnnotationList(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }

    private void initialize(){
        fields = new ArrayList<>();
        fields.addAll(Util.getAnnotatedFields(BPrecision.class));
        fields.addAll(Util.getAnnotatedFields(IPrecision.class));
        fields.addAll(Util.getAnnotatedFields(EPrecision.class));

        adapter = new AnnotationAdapter(this.getContext(),fields);
        setAdapter( adapter );
    }

    public void updateValues(){
        adapter.notifyDataSetChanged();
    }
}
