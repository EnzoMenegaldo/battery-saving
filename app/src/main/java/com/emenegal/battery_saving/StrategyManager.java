package com.emenegal.battery_saving;


import android.content.Context;

import com.emenegal.battery_saving.annotation.ResourceStrategy;
import com.emenegal.battery_saving.component.AnnotationList;
import com.emenegal.battery_saving.strategy.ICollectionStrategy;
import com.emenegal.battery_saving.strategy.PluggedResourceStrategy;
import com.emenegal.battery_saving.strategy.UnPluggedResourceStrategy;

public class StrategyManager {
    public static final String TAG = StrategyManager.class.getSimpleName();

    public static final StrategyManager INSTANCE = new StrategyManager();

    private ICollectionStrategy strategy;

    //Custom listview to display all the annotated fields
    private AnnotationList annotationList;

    public void setStrategy(ICollectionStrategy strategy) {
        this.strategy = strategy;
    }

    public ICollectionStrategy getStrategy() {
        return strategy;
    }

    /**
     * Set the strategy according to the current charging state.
     * Then register the broadcast receiver to gather the battery state.
     * Set the annotation list if the dev want to use the component
     * @param context
     */
    public void initialize(Context context, AnnotationList annotationList){
       if(Util.isCharging(context))
            strategy = new PluggedResourceStrategy(context);
        else
            strategy = new UnPluggedResourceStrategy(context);

        this.annotationList = annotationList;
        context.registerReceiver(BatteryUsageReceiver.INSTANCE,BatteryUsageReceiver.INSTANCE.getIntentFilter());
    }

    /**
     * Set the strategy according to the current charging state.
     * Then register the broadcast receiver to gather the battery state.
     * @param context
     */
    public void initialize(Context context){
        if(Util.isCharging(context))
            strategy = new PluggedResourceStrategy(context);
        else
            strategy = new UnPluggedResourceStrategy(context);

        context.registerReceiver(BatteryUsageReceiver.INSTANCE,BatteryUsageReceiver.INSTANCE.getIntentFilter());
    }

    /**
     * Unregister the broadcast receiver and close the battery log file.
     * @param context
     */
    public void stop(Context context){
        context.unregisterReceiver(BatteryUsageReceiver.INSTANCE);
    }


    public void updateDisplayedValue(){
        if(annotationList != null)
            annotationList.updateValues();
    }

}