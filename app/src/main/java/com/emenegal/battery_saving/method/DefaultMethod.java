package com.emenegal.battery_saving.method;

import com.emenegal.battery_saving.annotation.IPrecision;

public class DefaultMethod implements IMethod {

    @Override
    public double execute(int batteryLevel,IPrecision annotation) {
        return (annotation.max()-annotation.min())*batteryLevel/100+annotation.min();
    }

}
