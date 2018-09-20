package com.emenegal.battery_saving;

import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.LocalBroadcastManager;

import com.emenegal.battery_saving.annotation.BPrecision;
import com.emenegal.battery_saving.annotation.EPrecision;
import com.emenegal.battery_saving.annotation.IPrecision;
import com.emenegal.battery_saving.annotation.ResourceStrategy;
import com.emenegal.battery_saving.enumeration.DefaultEnum;
import com.emenegal.battery_saving.enumeration.IEnum;
import com.emenegal.battery_saving.method.DefaultMethod;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@ResourceStrategy
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @BPrecision(value = true, threshold = 50)
    public static boolean bool;

    @IPrecision(lower = 350 ,higher = 4000,method = DefaultMethod.class)
    public static double num;

    @EPrecision(klass = DefaultEnum.class)
    public static IEnum anEnum;

    @Test
    public void StrategyInitializationTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        StrategyManager.INSTANCE.initialize(appContext);
       // appContext.unregisterReceiver(BatteryUsageReceiver.INSTANCE);

        assertTrue(bool);
        assertEquals(num,350,0);
        assertEquals(anEnum,DefaultEnum.Small);

        StrategyManager.INSTANCE.stop(appContext);
    }

    /**
     * Test to use on the emulator
     * We cannot simulate a battery level changing in a real device
     */
    @Test
    public void BatteryStateChangeTest() throws InterruptedException {
        Context appContext = InstrumentationRegistry.getTargetContext();

        StrategyManager.INSTANCE.initialize(appContext);

        /**
         * Run in the same time these lines to change the battery level of the emulator
         *
         * $telnet localhost 5554 #where 5554 is the number shown on your emulator.
             and then type commands, such as:
             $power display
             $power ac off
             $power status discharging
             $power capacity 50
         */
        Thread.sleep(10000);

        assertFalse(bool);
        assertEquals(num,2175,0);
        assertEquals(anEnum,DefaultEnum.Medium);

        StrategyManager.INSTANCE.stop(appContext);

    }
}
