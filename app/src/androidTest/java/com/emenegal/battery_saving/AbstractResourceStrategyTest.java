package com.emenegal.battery_saving;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.emenegal.battery_saving.annotation.BPrecision;
import com.emenegal.battery_saving.annotation.EPrecision;
import com.emenegal.battery_saving.annotation.IPrecision;
import com.emenegal.battery_saving.annotation.ResourceStrategy;
import com.emenegal.battery_saving.enumeration.DefaultEnum;
import com.emenegal.battery_saving.enumeration.IEnum;
import com.emenegal.battery_saving.method.DefaultMethod;
import com.emenegal.battery_saving.strategy.AbstractResourceStrategy;
import com.emenegal.battery_saving.strategy.PluggedResourceStrategy;
import com.emenegal.battery_saving.strategy.UnPluggedResourceStrategy;

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

    @IPrecision(min = 350 ,max = 4000,method = DefaultMethod.class)
    public static double int1;

    @IPrecision(min = 0 ,max = 1,method = DefaultMethod.class)
    public static double int2;

    @EPrecision(klass = DefaultEnum.class)
    public static IEnum defaultEnum;


    private AbstractResourceStrategy strategy;


    @Test
    public void getFieldTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        strategy = new UnPluggedResourceStrategy(appContext);


        assertEquals(strategy.getbFields().size(),3);
        assertEquals(strategy.geteFields().size(),1);
        assertEquals(strategy.getiFields().size(),2);
    }

    @Test
    public void initUnPluggedResourceStrategyTest(){
        /**
         * Run in the same time these lines to change the battery level of the emulator
         *
         * $telnet localhost 5554 #where 5554 is the number shown on your emulator.
         and then type commands, such as:
         $power display
         $power ac off
         $power status discharging
         $power capacity 30

         or change directly the battery level on the emulator
         */

        Context appContext = InstrumentationRegistry.getTargetContext();

        strategy = new UnPluggedResourceStrategy(appContext);

        assertFalse(bool1);
        assertFalse(bool2);
        assertFalse(bool3);
        assertEquals(int1,1445,0);
        assertEquals(int2,0.3,0);
        assertEquals(defaultEnum,DefaultEnum.Small);

    }

    @Test
    public void initPluggedResourceStrategyTest(){

        Context appContext = InstrumentationRegistry.getTargetContext();
        strategy = new PluggedResourceStrategy(appContext);

        assertTrue(bool1);
        assertTrue(bool2);
        assertFalse(bool3);
        assertEquals(int1,4000,0);
        assertEquals(int2,1,0);
        assertEquals(defaultEnum,DefaultEnum.Large);

    }

    @Test
    public void updateBPrecisionFieldValues() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        strategy = new AbstractResourceStrategy(appContext) {
            @Override
            public void updateStrategy(int batteryLevel) {

            }
        };
        strategy.updateBPrecisionFieldValues();
        assertTrue(bool1);
        assertTrue(bool2);
        assertFalse(bool3);
    }

    @Test
    public void updateBPrecisionFieldValuesAccordingToBatteryLevel() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        strategy = new AbstractResourceStrategy(appContext) {
            @Override
            public void updateStrategy(int batteryLevel) {

            }
        };
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
        Context appContext = InstrumentationRegistry.getTargetContext();
        strategy = new AbstractResourceStrategy(appContext) {
            @Override
            public void updateStrategy(int batteryLevel) {

            }
        };
        strategy.updateIPrecisionFieldValues(50);
        assertEquals(int1,2175,0);
        assertEquals(int2,0.5,0);
    }

    @Test
    public void updateDefaultEnum(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        strategy = new AbstractResourceStrategy(appContext) {
            @Override
            public void updateStrategy(int batteryLevel) {

            }
        };
        strategy.updateEPrecisionFieldValues(0);
        assertEquals(defaultEnum,DefaultEnum.Small);
        strategy.updateEPrecisionFieldValues(90);
        assertEquals(defaultEnum,DefaultEnum.Large);
        strategy.updateEPrecisionFieldValues(50);
        assertEquals(defaultEnum,DefaultEnum.Medium);
        strategy.updateEPrecisionFieldValues(1);
        assertEquals(defaultEnum,DefaultEnum.Small);
    }

    //Set battery state to 30 and not charging
    @Test
    public void StrategyInitializationTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        StrategyManager.INSTANCE.initialize(appContext);

        assertFalse(bool1);
        assertEquals(int1,1445,0);
        assertEquals(int2,0.3,0);
        assertEquals(defaultEnum,DefaultEnum.Small);

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

         or change directly the battery level on the emulator
         */
        Thread.sleep(10000);

        assertFalse(bool1);
        assertEquals(int1,2175,0);
        assertEquals(defaultEnum,DefaultEnum.Medium);

        StrategyManager.INSTANCE.stop(appContext);

    }

    @Test
    public void ChangeStatusToChargingTest() throws InterruptedException {
        Context appContext = InstrumentationRegistry.getTargetContext();

        //Set the battery status as not charging on the emulator
        //And the battery level to 30
        StrategyManager.INSTANCE.initialize(appContext);

        //Change battery status to charging
        Thread.sleep(10000);

        /** Set the status to charging **/

        assertTrue(bool1);
        assertEquals(int1,4000,0);
        assertEquals(defaultEnum,DefaultEnum.Large);

        StrategyManager.INSTANCE.stop(appContext);

    }

    @Test
    public void ChangeStatusToDisChargingTest() throws InterruptedException {
        Context appContext = InstrumentationRegistry.getTargetContext();

        //Set the battery status as charging on the emulator
        //And the battery level to 30
        StrategyManager.INSTANCE.initialize(appContext);

        //Change battery status to charging
        Thread.sleep(10000);

        /** Set the status to charging **/

        assertFalse(bool1);
        assertFalse(bool2);
        assertFalse(bool3);
        assertEquals(int1,1445,0);
        assertEquals(int2,0.3,0);
        assertEquals(defaultEnum,DefaultEnum.Small);

        StrategyManager.INSTANCE.stop(appContext);

    }
}