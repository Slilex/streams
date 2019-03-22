package ua.procamp.streams.arrays;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * This class is developed by Ivanov Alexey (mrSlilex@gmail.com) on 22.03.2019
 */

public class InterimArrayTest {

    @Test
    public void  createInterimArrayDefaultCapacity() throws NoSuchFieldException, IllegalAccessException {
        InterimArray interimArray = new InterimArray();
        Field field = InterimArray.class.getDeclaredField("capacity");
        field.setAccessible   (true);
        int act  =  (int) field.get  (interimArray);
        int exp = 0;
        assertEquals(exp, act);
    }

    @Test
    public void  createInterimArrayOtherCapacity() throws NoSuchFieldException, IllegalAccessException {
        InterimArray interimArray = new InterimArray(95);
        Field field = InterimArray.class.getDeclaredField("capacity");
        field.setAccessible   (true);
        int act  =  (int) field.get  (interimArray);
        int exp = 95;
        assertEquals(exp, act);
    }

    @Test
    public void  createInterimArrayWithsValues() throws NoSuchFieldException, IllegalAccessException {
        InterimArray interimArray = new InterimArray(1,2,3,5,-5,-4,-7,0);
        Object[] expArray = new Integer[]{1,2,3,5,-5,-4,-7,0};
        Object[] actArray =  interimArray.toArray();
        Field field = InterimArray.class.getDeclaredField("capacity");
        field.setAccessible   (true);
        int act  =  (int) field.get  (interimArray);
        int exp = 8;
        assertEquals(exp, act);

        assertArrayEquals(expArray,actArray);
    }

    @Test
    public void  createInterimArrayWithCapacityAndValues() throws NoSuchFieldException, IllegalAccessException {
        int n = 100;
        InterimArray interimArray = new InterimArray(n,5);
        Object[] expArray = new Integer[n];
      for (int i = 0; i <n ; i++) {
          expArray[i] = 5;
      }
        Object[] actArray =  interimArray.toArray();
        Field field = InterimArray.class.getDeclaredField("capacity");
        field.setAccessible   (true);
        int act  =  (int) field.get  (interimArray);
        int exp = 100;
        assertEquals(exp, act);

        assertArrayEquals(expArray,actArray);
    }

    @Test
    public void returnSizeArrayAndAddFunction() throws NoSuchFieldException, IllegalAccessException {
        int size = 112;
        InterimArray interimArray = new InterimArray();
        for (int i = 0; i < size ; i++) {
            interimArray.add(i);
        }
        int act  = interimArray.size();
        int exp = size;
        assertEquals(exp, act);

    }

    @Test
    public void getPosition(){
        InterimArray<Integer> interimArray = new InterimArray(1,2,3,5,-5,-4,-7,0);
        Integer act = interimArray.get(5);
        Integer exp = -4;
        assertTrue(act.equals(exp));

    }


}