package com.emenegal.battery_saving.strategy;

import android.content.Context;

public class PluggedResourceStrategy extends AbstractResourceStrategy {

    public PluggedResourceStrategy(Context context) {
      super(context);
      initFieldValues(100);
    }

    @Override
    public void updateStrategy(int battery_level) {
        //Do nothing
    }

}
