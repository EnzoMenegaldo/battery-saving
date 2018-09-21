package com.emenegal.battery_saving;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.emenegal.battery_saving.component.AnnotationList;
import com.emenegal.battery_saving.strategy.PluggedResourceStrategy;
import com.emenegal.battery_saving.strategy.UnPluggedResourceStrategy;

import java.net.URL;
import java.net.URLClassLoader;

public class BatteryUsageReceiver extends BroadcastReceiver{

    public static final BatteryUsageReceiver INSTANCE = new BatteryUsageReceiver();
    public static int battery_level = 0;
    private IntentFilter intentFilter ;
    private int oldBatteryLevel;

    private int oldStatus;
    private int currentStatus;


    private BatteryUsageReceiver(){
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        oldBatteryLevel = -1;
        oldStatus = -1;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case Intent.ACTION_BATTERY_CHANGED:

                currentStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                if (oldStatus != currentStatus) {
                    if(currentStatus == BatteryManager.BATTERY_STATUS_CHARGING)
                        StrategyManager.INSTANCE.setStrategy(new PluggedResourceStrategy(context));
                    else
                        StrategyManager.INSTANCE.setStrategy(new UnPluggedResourceStrategy(context));
                }


                battery_level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                if(battery_level != oldBatteryLevel){
                    oldBatteryLevel = battery_level;
                    StrategyManager.INSTANCE.getStrategy().updateStrategy(battery_level);
                    StrategyManager.INSTANCE.updateDisplayedValue();
                }

                break;
        }
    }

    public IntentFilter getIntentFilter() {
        return intentFilter;
    }

    public void setIntentFilter(IntentFilter intentFilter) {
        this.intentFilter = intentFilter;
    }

}
