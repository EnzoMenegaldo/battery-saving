package com.emenegal.battery_saving;

import com.emenegal.battery_saving.annotation.BPrecision;
import com.emenegal.battery_saving.annotation.IPrecision;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.*;

public class UtilTest {

    @BPrecision(true)
    public static boolean aBoolean;

    @BPrecision(true)
    public static boolean anotherBoolean;

    @BPrecision(true)
    public boolean nonStaticBoolean;

    @IPrecision(min = 10,max = 100)
    public static double aInterval;


    @Test
    public void getAnnotatedFields() {
        List<Field> bAnnotations = Util.getAnnotatedFields(this.getClass(),BPrecision.class);
        assertEquals(bAnnotations.size(),2);
        List<Field> iAnnotations = Util.getAnnotatedFields(this.getClass(),IPrecision.class);
        assertEquals(iAnnotations.size(),1);
    }
}