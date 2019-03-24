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

    @Test(expected = IllegalArgumentException.class)
    public void  createInterimArrayWithCapacityLessNull(){
        InterimArray interimArray = new InterimArray(-2);
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
    public void returnSizeArrayAndAddFunction(){
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
    @SuppressWarnings("unchecked")
    public void getPosition(){
        InterimArray<Integer> interimArray = new InterimArray(1,2,3,5,-5,-4,-7,0);
        Integer act = interimArray.get(5);
        Integer exp = -4;

        assertEquals(act, exp);

    }

    @Test
    @SuppressWarnings("unchecked")
    public void remove(){
        InterimArray actArray = new InterimArray(1,2,3,5,10,-5,-4,-7,0);
        actArray.remove(4);
        InterimArray expArray = new InterimArray(1,2,3,5,-5,-4,-7,0);
        Object[] act = actArray.toArray();
        Object[] exp = expArray.toArray();

        assertArrayEquals(exp,act);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void addReturnTrue(){
        InterimArray interimArray = new InterimArray();

        assertTrue(interimArray.add(1));
    }

    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("unchecked")
    public void illegalPositionOnSet(){
        InterimArray interimArray = new InterimArray(5,6,8,8);
        interimArray.set(10,5);

    }

    @Test
    @SuppressWarnings("unchecked")
    public void SetValue(){
        InterimArray interimArray = new InterimArray(5,6,8,8);
        final Integer exp =99;
        interimArray.set(2,exp);
        Integer act = (Integer) interimArray.get(2);

        assertEquals(exp,act);
    }


    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("unchecked")
    public void capacityNullOnFill(){
        InterimArray interimArray = new InterimArray(0);
        interimArray.fill(5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    @SuppressWarnings("unchecked")
    public void positionLargerOrEqualsCapacityOnRemove() {
        InterimArray interimArray = new InterimArray(58);
        interimArray.remove(58);

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResizeArrayOnAdd() throws NoSuchFieldException, IllegalAccessException {
        final int SIZE = 16;
        int exp = (SIZE * 3) / 2 + 1;  // 25
        InterimArray interimArray = new InterimArray(SIZE);
        interimArray.add(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17);

        Field field = InterimArray.class.getDeclaredField("data");
        field.setAccessible   (true);
        Object [] data  = (Object[]) field.get  (interimArray);
        int act = data.length;
        assertTrue(act == exp);

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testReSizeArrayOnRemove() throws NoSuchFieldException, IllegalAccessException {
        int SIZE = 100000;
        InterimArray interimArray = new InterimArray();
        for (int i = 0; i < SIZE ; i++) {
            interimArray.add(i);
        }
        for (int i = SIZE*2/3; i != 0; i--) {
            interimArray.remove(i);
        }
        Field field = InterimArray.class.getDeclaredField("data");
        field.setAccessible   (true);
        Object [] data  = (Object[]) field.get  (interimArray);
        int act = data.length;
        int exp = 66192;

        assertTrue(act == exp);
    }

    @Test
    public void iteratorTestHasNextArray(){
        InterimArray actArray = new InterimArray();
        assertFalse(actArray.iterator().hasNext());
        actArray.add(6);

        assertTrue(actArray.iterator().hasNext());
        assertTrue(actArray.iterator().hasNext());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void iteratorTestNextOnEmptyArray (){
        InterimArray actArray = new InterimArray();

        actArray.iterator().next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void iteratorTestRemoveOnEmptyArray (){
        InterimArray actArray = new InterimArray();

        actArray.iterator().remove();
    }


    @Test
    @SuppressWarnings("unchecked")
    public void contraktHashCodeAndEquals(){
        InterimArray actArray = new InterimArray(2,5,6);
        int act = actArray.hashCode();
        InterimArray expArray = new InterimArray(2,5,6);
        int exp = expArray.hashCode();

        assertTrue(exp == act);

        assertTrue(actArray.equals(expArray));

        expArray.add(1);
        exp = expArray.hashCode();

        assertFalse(exp== act);

        assertFalse(actArray.equals(expArray));
    }

}