package com.emenegal.battery_saving;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.emenegal.battery_saving.annotation.BPrecision;
import com.emenegal.battery_saving.annotation.EPrecision;
import com.emenegal.battery_saving.annotation.IPrecision;
import com.emenegal.battery_saving.annotation.ResourceStrategy;
import com.emenegal.battery_saving.enumeration.IEnum;

import org.atteo.classindex.ClassIndex;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Util {

    /**
     * Retrieve all static fields which are annotated with @annotation
     * @param klass
     * @param annotation
     * @return a list containing all the fields.
     */
    public static List<Field> getAnnotatedFields(Class<?> klass, Class<? extends Annotation> annotation){
        List<Field> fields = new ArrayList<>();
        for (Field field : klass.getDeclaredFields()) {
            if (field.isAnnotationPresent(annotation)) {
                if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                    fields.add(field);
                }
            }
        }
        return fields;
    }

    /**
     * Retrieve all static fields which are annotated with @annotation in the project
     * A class containing such fields needs to be annotated with @ResourceStrategy
     * @param annotation
     * @return a list containing all the fields.
     */
    public static List<Field> getAnnotatedFields(Class<? extends Annotation> annotation){
        List<Field> fields = new ArrayList<>();

        for (Class<?> klass : ClassIndex.getAnnotated(ResourceStrategy.class))
            fields.addAll(getAnnotatedFields(klass,annotation));

        return fields;
    }



    /**
     * Return true if the phone is currently charging else return false
     * @param context
     * @return
     */
    public static boolean isCharging(Context context){
        BatteryManager bm = (BatteryManager)context.getSystemService(Context.BATTERY_SERVICE);
        boolean b = bm.isCharging();
        return bm.isCharging();
    }

    /**
     * Return current battery level
     */
    public static int getCurrentBatteryLevel(Context context){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        return batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

    }

}
