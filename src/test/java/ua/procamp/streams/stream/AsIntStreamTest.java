package ua.procamp.streams.stream;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class is developed by Ivanov Alexey (mrSlilex@gmail.com) on 22.03.2019
 */

public class AsIntStreamTest {

    private static final int MAX = Integer.MAX_VALUE;
    private static final int MIN = Integer.MIN_VALUE;



    @Test
    public void ofEmptyValues() {
        IntStream str = AsIntStream.of();

        int[] exp = {};
        int[] act = str.toArray();

        assertArrayEquals(exp, act);
    }

    @Test
    public void ofSimpleValues() {
        IntStream str = AsIntStream.of(1, 2, 3);

        int[] exp = {1, 2, 3};
        int[] act = str.toArray();

        assertArrayEquals(exp, act);
    }

    @Test
    public void ofExtrimalValues() {
        IntStream str = AsIntStream.of(MAX, MIN, MIN, MAX, MAX, MIN);

        int[] exp = {MAX, MIN, MIN, MAX, MAX, MIN};
        int[] act = str.toArray();

        assertArrayEquals(exp, act);
    }

    @Test(expected = IllegalArgumentException.class)
    public void averageOnIAException() {
        IntStream str = AsIntStream.of();
        str.average();
    }

    @Test
    public void averageOnSimpleValues() {
        IntStream str = AsIntStream.of(598, -785, 5, 9195, -5959, 0, -121, 365);

        double exp = 412.25;
        double act = str.average();

        assertEquals(exp, act, 1e-10);
    }

    @Test
    public void averageOnExtrimalValues() {
        IntStream str = AsIntStream.of(MAX, MIN, MIN, MAX, MAX, MIN);

        double exp = -0.5;
        double act = str.average();

        assertEquals(exp, act, 1e-10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void maxOnIAException() {
        IntStream str = AsIntStream.of();
        str.max();
    }

    @Test
    public void maxOnSimpleValues() {
        IntStream str = AsIntStream.of(10,9,8,7,6,5,4,3,2,1,0,-1,-12);

        int exp = 10;
        int act = str.max().intValue();

        assertEquals(exp, act);
    }

    @Test
    public void maxOnExtrimalValues() {
        IntStream str = AsIntStream.of(MAX, 555, -555, 1488, MIN, 15864, 95995);

        int exp = MAX;
        int act = str.max().intValue();

        assertEquals(exp, act);
    }

    @Test(expected = IllegalArgumentException.class)
    public void minOnIAException() {
        IntStream str = AsIntStream.of();
        str.min();
    }

    @Test
    public void minOnSimpleValues() {
        IntStream str = AsIntStream.of(10,9,8,7,6,5,4,3,2,1,0,-1);

        int exp =-1;
        int act = str.min().intValue();

        assertEquals(exp, act);
    }

    @Test
    public void minOnExtrimalValues() {
        IntStream str = AsIntStream.of(MAX, 555, -555, 1488, MIN, 15864, 95995);

        int exp = MIN;
        int act = str.min().intValue();

        assertEquals(exp, act);
    }

    @Test
    public void countOnEmptyStream() {
        IntStream str = AsIntStream.of();

        long exp = 0;
        long act = str.count();

        assertEquals(exp, act);
    }

    @Test
    public void countOnSomeValues() {
        IntStream str = AsIntStream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        long exp = 11;
        long act = str.count();

        assertEquals(exp, act);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sumOnIAException() {
        IntStream str = AsIntStream.of();
        str.sum();
    }

    @Test
    public void sumOnSimpleValues() {
        IntStream str = AsIntStream.of(666, -666, 20, -20, 12, -14, -12, 14);

        double exp = .0;
        double act = str.sum();

        assertEquals(exp, act, 1e-10);
    }

    @Test
    public void sumOnExtrimalValues() {
        IntStream str = AsIntStream.of(MAX, 121, 144, -MAX, 9412, -5671);

        double exp = 4006.0;
        double act = str.sum();

        assertEquals(exp, act, 1e-10);
    }

    @Test
    public void toArrayOnEmptyStream() {
        IntStream str = AsIntStream.of();

        int[] exp = {};
        int[] act = str.toArray();

        assertArrayEquals(exp, act);
    }

    @Test
    public void toArrayOnSomeValues() {
        IntStream str = AsIntStream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        int[] exp = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] act = str.toArray();

        assertArrayEquals(exp, act);
    }

    @Test
    public void forEachOnEmptyValues() {

        class tmpClass {
            private int value = 0;

            public int getValue() {
                return this.value;
            }

            public void add(int delta) {
                this.value += delta;
            }
        }

        IntStream str = AsIntStream.of();

        tmpClass tmp = new tmpClass();
        str.forEach(tmp::add);

        int exp = 0;
        int act = tmp.getValue();

        assertEquals(exp, act);
    }

    @Test
    public void forEachOnSomeValues() {

        class tmpClass {
            private int value = 0;

            public int getValue() {
                return this.value;
            }

            public void add(int delta) {
                this.value += delta;
            }
        }

        IntStream str = AsIntStream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        tmpClass tmp = new tmpClass();
        str.forEach(tmp::add);

        int exp = 45;
        int act = tmp.getValue();

        assertEquals(exp, act);
    }

    @Test
    public void filterOnEmptyValues() {
        IntStream str = AsIntStream.of();
        str.filter(value -> (value & value + 1 ) == value);

        int[] exp = {};
        int[] act = str.toArray();

        assertArrayEquals(exp, act);
    }

    @Test
    public void filterOnSomeValues() {
        IntStream str = AsIntStream.of(0, 1, 2, 3, 4, 5);
        str.filter(value -> (value & value + 1 ) == value);

        int[] exp = {0, 2, 4};
        int[] act = str.toArray();

        assertArrayEquals(exp, act);
    }

    @Test
    public void filterOnSomeValuesWithAllInFilter() {
        IntStream str = AsIntStream.of(666, 666, 666, 666, 666);
        str.filter(value -> (value & value + 1 ) == value);

        int[] exp = {666, 666, 666, 666, 666};
        int[] act = str.toArray();

        assertArrayEquals(exp, act);
    }

    @Test
    public void mapOnEmptyValues() {
        IntStream str = AsIntStream.of();
        str.map(value -> (value * value));

        int[] exp = {};
        int[] act = str.toArray();

        assertArrayEquals(exp, act);
    }

    @Test
    public void mapOnSomeValues() {
        IntStream str = AsIntStream.of(0, 1, 2, 3, 4, 5, 6);
        str.map(value -> (value * value));

        int[] exp = {0, 1, 4, 9, 16, 25, 36};
        int[] act = str.toArray();

        assertArrayEquals(exp, act);
    }

    @Test
    public void flatMapOnEmptyValues() {
        IntStream str = AsIntStream.of();
        str.flatMap(value -> AsIntStream.of(value + value));

        int[] exp = {};
        int[] act = str.toArray();

        assertArrayEquals(exp, act);
    }

    @Test
    public void flatMapOnSomeValues() {
        IntStream str = AsIntStream.of(0, 1, 2, 3, 4, 5, 6);
        str.flatMap(value -> AsIntStream.of(value + value));

        int[] exp = {0, 2, 4, 6, 8, 10, 12};
        int[] act = str.toArray();

        assertArrayEquals(exp, act);
    }

    @Test
    public void reduseOnEmptyValues() {
        IntStream str = AsIntStream.of();

        int exp = 0;
        int act = str.reduce(0, (a, b) -> a * b);

        assertEquals(exp, act);
    }

    @Test
    public void reduseOnSomeValues() {
        IntStream str = AsIntStream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        int exp = 0;
        int act = str.reduce(0, (a, b) -> a * b);

        assertEquals(exp, act);
    }
}