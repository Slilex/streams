package ua.procamp.streams.arrays;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

 /**
 * This class is developed by Ivanov Alexey (mrSlilex@gmail.com) on 22.03.2019.
 */

public class InterimArray<T> implements Iterable<T> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final int CUT_RATE = 4;
    private T[] data;
    private int capacity;

    @SuppressWarnings("unchecked")
    public InterimArray() {
        this.capacity = 0;
        this.data = (T[]) new Object[DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    public InterimArray(final T... values) {
        this.capacity = 0;
        this.data = (T[]) new Object[DEFAULT_CAPACITY];
        this.add(values);
    }

    @SuppressWarnings("unchecked")
    public InterimArray(final int initialCapacity) {
        validationCapacity(initialCapacity);

        this.capacity = initialCapacity;
        this.data = (T[]) new Object[initialCapacity];
    }

    @SuppressWarnings("unchecked")
    InterimArray(final int initialCapacity, final T initialValue) {
        validationCapacity(initialCapacity);

        this.capacity = initialCapacity;
        this.data = (T[]) new Object[initialCapacity];
        this.fill(initialValue);
    }

    private void validationCapacity(final int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException(
                    "Illegal Capacity: " + initialCapacity);
        }
    }
    /**
     *Adds a new item to the list. Upon reaching the size of the inner
     *of the array its increase in (data.length * 3) / 2 + 1) times
     */
    public boolean add(T value) {
        if (capacity == data.length - 1) {
            resize((data.length * 3) / 2 + 1);
        }
        this.data[capacity++] = value;
        return true;
    }

    @SafeVarargs
    public final void add(final T... values) {
        for (T value : values) {
            this.add(value);
        }
    }

    public final void add(final InterimArray<T> values) {
        for (int i = 0; i < values.size(); i++) {
            this.add(values.get(i));
        }
    }

    private void resize(final int newLength) {
        data = copyArray(newLength);
    }

    @SuppressWarnings("unchecked")
    private T[] copyArray(final int newLength) {
        T[] newArray = (T[]) new Object[newLength];
        System.arraycopy(data, 0, newArray, 0, capacity);
        return newArray;
    }

    public final T[] toArray() {
        return copyArray(this.capacity);
    }

    /**
     *Removes a list item by index. All items to the right of being deleted.
     *move one step to the left. If after removing an item
     *elements in CUT_RATE times smaller than the size of the internal array,
     *then the internal array is halved, to save occupied
     *places.
     */
    public void remove(final int index) {
        if (index >= capacity) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + capacity);
        }
        for (int i = index; i < capacity; i++) {
            data[i] = data[i + 1];
        }
        data[capacity] = null;
        capacity--;
        if (data.length > DEFAULT_CAPACITY
                && capacity < data.length / CUT_RATE) {
            resize(data.length / 2);
        }

    }

    public final void set(final int index, final T value) {
        if (index > capacity || index < 0) {
            throw new IllegalArgumentException("Illegal Position");
        }
        this.data[index] = value;
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

    public int size() {
        return capacity;
    }

    public T get(int index) {
        return data[index];
    }

    public boolean isEmpty() {
        return capacity == 0;
    }

    public Iterator<T> iterator() {
        return new InterimArrayIterator<T>(this);
    }

    @SuppressWarnings("unchecked")
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
        } else {
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

        InterimArrayIterator(final InterimArray<T> interimArray) {
            this.values = interimArray;
        }

        public boolean hasNext() {
            return this.current < this.values.size();
        }

        public T next() {
            if (this.hasNext()) {
                return this.values.get(current++);
            } else {
                throw new IndexOutOfBoundsException(
                        "There is not exist next element");
            }
        }

        public void remove(final int index) {
             this.values.remove(index);
        }

    }
}
