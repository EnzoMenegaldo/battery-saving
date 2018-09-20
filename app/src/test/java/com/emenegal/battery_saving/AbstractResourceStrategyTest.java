package com.emenegal.battery_saving;

import com.emenegal.battery_saving.annotation.ResourceStrategy;
import com.emenegal.battery_saving.enumeration.IEnum;
import com.emenegal.battery_saving.annotation.BPrecision;
import com.emenegal.battery_saving.annotation.EPrecision;
import com.emenegal.battery_saving.strategy.AbstractResourceStrategy;
import com.emenegal.battery_saving.annotation.IPrecision;
import com.emenegal.battery_saving.method.DefaultMethod;
import com.emenegal.battery_saving.enumeration.DefaultEnum;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

@ResourceStrategy
public class AbstractResourceStrategyTest {

    @BPrecision(value = true, threshold = 50)
    public static boolean bool1;

    @BPrecision(value = true, threshold = 75)
    public static boolean bool2;

    @BPrecision(value = false, threshold = 25)
    public static boolean bool3;

    @IPrecision(lower = 350 ,higher = 4000,method = DefaultMethod.class)
    public static double int1;

    @IPrecision(lower = 0 ,higher = 1,method = DefaultMethod.class)
    public static double int2;

    @EPrecision(klass = DefaultEnum.class)
    public static IEnum defaultEnum;


    private AbstractResourceStrategy strategy;

    @Before
    public void init(){
        strategy = new AbstractResourceStrategy() {
            @Override
            public void updateStrategy(int batteryLevel) {

            }
        };
    }

    @Test
    public void initialization(){
        assertEquals(strategy.getbFields().size(),3);
        assertEquals(strategy.geteFields().size(),1);
        assertEquals(strategy.getiFields().size(),2);
    }

    @Test
    public void updateBPrecisionFieldValues() {
        strategy.updateBPrecisionFieldValues();
        assertTrue(bool1);
        assertTrue(bool2);
        assertFalse(bool3);
    }

    @Test
    public void updateBPrecisionFieldValuesAccordingToBatteryLevel() {
        strategy.updateBPrecisionFieldValues(90);
        assertTrue(bool1);
        assertTrue(bool2);
        assertFalse(bool3);
        strategy.updateBPrecisionFieldValues(70);
        assertTrue(bool1);
        assertFalse(bool2);
        assertFalse(bool3);
        strategy.updateBPrecisionFieldValues(10);
        assertFalse(bool1);
        assertFalse(bool2);
        assertTrue(bool3);
    }


    @Test
    public void updateIPrecisionDefaultMethod() {
        strategy.updateIPrecisionFieldValues(50);
        assertEquals(int1,2175,0);
        assertEquals(int2,0.5,0);
    }

    @Test
    public void updateDefaultEnum(){
        assertEquals(defaultEnum,DefaultEnum.Small);
        strategy.updateEPrecisionFieldValues(90);
        assertEquals(defaultEnum,DefaultEnum.Large);
        strategy.updateEPrecisionFieldValues(50);
        assertEquals(defaultEnum,DefaultEnum.Medium);
        strategy.updateEPrecisionFieldValues(1);
        assertEquals(defaultEnum,DefaultEnum.Small);
    }
}