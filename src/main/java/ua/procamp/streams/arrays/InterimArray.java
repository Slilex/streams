package ua.procamp.streams.arrays;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

import static java.lang.System.*;

/**
 * This class is developed by Ivanov Alexey (mrSlilex@gmail.com) on 22.03.2019
 */

public class InterimArray<T> implements Iterable<T> {


    private static final int DEFAULT_CAPACITY = 3;
    private T[] data;
    private int capacity;

    @SuppressWarnings("unchecked")
    public InterimArray() {
        this.capacity = 0;
        this.data = (T[]) new Object[DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    public  InterimArray(T ... values) {
        this.capacity = 0;
        this.data = (T[]) new Object[DEFAULT_CAPACITY];
        this.add(values);
    }

    @SuppressWarnings("unchecked")
    public InterimArray(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException(
                    "Illegal Capacity: " + initialCapacity);
        }

        this.capacity = initialCapacity;
        this.data = (T[]) new Object[initialCapacity];
    }

    @SuppressWarnings("unchecked")
    public InterimArray(int initialCapacity, T initialValue) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException(
                    "Illegal Capacity: " + initialCapacity);
        }

        this.capacity = initialCapacity;
        this.data = (T[]) new Object[initialCapacity];
        this.fill(initialValue);
    }

    public int size() {
        return capacity;
    }

    public T get(int position) {
        return data[position];
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] ans = (T[]) new Object[this.capacity];

        System.arraycopy(data, 0, ans, 0, capacity);

        return ans;
    }

    public boolean set(int position, T value) {
        if (position > capacity || position < 0) {
            throw new IllegalArgumentException("Illegal Position");
        }
        this.data[position] = value;
        return true;
    }

    public boolean isEmpty() {
        return capacity == 0;
    }

    public void fill(T value) {
        if (this.capacity == 0 || this.data == null) {
            throw new IllegalArgumentException(
                    "Illegal Capacity or Data isn't initialised");
        }
        for (int i = 0; i < this.capacity; i++) {
            this.data[i] = value;
        }
    }

    @SuppressWarnings("unchecked")
    private void validateCapacity(int minCapacity) {
        int old = this.data.length;
        if (minCapacity > old) {

            Object[] oldData = data;
            int newCapacity = (old + old + old)/2 + 1;

            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }

            this.data = (T[]) new Object[newCapacity];
            arraycopy(oldData, 0, data, 0, capacity);
        }
    }

    public boolean add(T value) {
        validateCapacity(this.capacity + 1);
        this.data[capacity++] = value;
        return true;
    }

    @SafeVarargs
    public final boolean add(T... values) {
        for (T value : values) this.add(value);
        return true;
    }

    public boolean add(InterimArray<T> values) {
        for (int i = 0; i < values.size(); i++) {
            this.add(values.get(i));
        }
        return true;
    }

    public T remove(int position) {
        if (position >= capacity) {
            throw new IndexOutOfBoundsException(
                    "Index: " + position + ", Size: " + capacity);
        }
        T oldValue = this.data[position];

        int numMoved = this.capacity - position - 1;
        if (numMoved > 0) {
            arraycopy(this.data, position + 1,
                    this.data, position, numMoved);
        }
        this.data[--this.capacity] = null;

        return oldValue;
    }

    public Iterator<T> iterator() {
        return new InterimArrayIterator<T>(this);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        InterimArray<T> another = (InterimArray<T>) obj;
        Iterator<T> ait = another.iterator();

        if (this.capacity != another.size()) {
            return false;
        }
        else {
            for (int i = 0; i < this.capacity; i++) {
                if (this.data[i] != ait.next()) {
                    return false;
                }
            }
            return true;
        }

    }

    @Override
    public int hashCode() {
        int result = Objects.hash(capacity);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    private class InterimArrayIterator<T> implements Iterator<T> {
        private int current = 0;
        private InterimArray<T> values;

        public InterimArrayIterator(InterimArray<T> interimArray) {
            this.values = interimArray;
        }

        public boolean hasNext() {
            return this.current < this.values.size();
        }

        public T next() {
            if (this.hasNext()) {
                return this.values.get(current++);
            }
            else {
                throw new IndexOutOfBoundsException(
                        "There is not exist next element");
            }
        }

        public T remove(int pos) {
            return this.values.remove(pos);
        }

    }
}