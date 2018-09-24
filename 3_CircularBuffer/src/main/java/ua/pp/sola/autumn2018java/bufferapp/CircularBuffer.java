/* Copyright © 2018 Bohdan Pysarenko. E-mail: <Pisarenko.B.O@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ua.pp.sola.autumn2018java.bufferapp;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * An implementation of circular buffer
 *
 * @param <T> the type of elements in this list
 * @author Bohdan Pysarenko
 * @version 1.0
 * @since 1.0
 */
public class CircularBuffer<T> {

    private int initialCapacity;
    private Object[] data;
    private int readPointer;
    private int writePointer;
    /*set True when read pointer resetted to start and set false when write pointer resetted to start*/
    private boolean isSameCycle;

    /**
     * Construct new instance of buffer\
     *
     * @param initialCapacity size of buffer
     */
    public CircularBuffer(int initialCapacity) {

        this.initialCapacity = initialCapacity;
        data = new Object[initialCapacity];
        this.readPointer = 0;
        this.writePointer = 0;
        isSameCycle = true;

    }

    /**
     * Write element to buffer
     *
     * @param data element that writing to buffer
     * @throws RuntimeException if buffer is full
     */
    public void put(T data) {
        if (isFull()) {
            throw new RuntimeException("Buffer is full");
        }
        this.data[this.writePointer] = data;
        updPointer(writePointer);
    }

    /**
     * Read next element from buffer
     *
     * @return element from buffer
     * @throws RuntimeException if buffer is empty
     */
    @SuppressWarnings("unchecked")
    public T get() {
        if (isEmpty()) {
            throw new RuntimeException("Buffer is empty");
        }
        T result = (T) data[readPointer];
        updPointer(readPointer);
        return result;
    }

    /**
     * Get actual elements from buffer as Object[] array
     *
     * @return a new Object array with containing the actual elements from the buffer
     */
    public Object[] toObjectArray() {
        if (isSameCycle) {
            return Arrays.copyOfRange(this.data, readPointer, writePointer);
        } else {
            Object[] newArray = new Object[this.data.length - (readPointer - writePointer)];
            System.arraycopy(this.data, this.readPointer, newArray,
                    0, this.initialCapacity - readPointer);
            System.arraycopy(this.data, 0, newArray,
                    this.writePointer, this.writePointer);
            return newArray;
        }
    }

    /**
     * Get array T[] of actual elements from buffer
     *
     * @return a new array with containing the actual elements from the buffer
     */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        return (T[]) toObjectArray();
    }

    /**
     * Get list of actual elements from buffer
     *
     * @return a list view of the actual buffer data
     */
    public List<T> asList() {
        return Arrays.asList(toArray());
    }

    /**
     * Add all elements from list to buffer if enough space otherwise throw exception
     *
     * @param toAdd list with elements of T class
     * @throws RuntimeException if buffer has not enough space for write all data from list
     */
    public void addAll(List<? extends T> toAdd) {
        if (toAdd.size() > calcFreeSpace()) throw new RuntimeException("Not enough space");
        for (T item : toAdd) {
            put(item);
        }
    }

    /**
     * Sorts this buffer according to the order induced by the specified
     * {@link Comparator}.
     *
     * @param comparator the comparator to determine the order of the array.  A
     *                   {@code null} value indicates that the elements'
     *                   {@linkplain Comparable natural ordering} should be used.
     */
    @SuppressWarnings("unchecked")
    void sort(Comparator<? super T> comparator) {
        Object[] a = toObjectArray();
        Arrays.sort(a, (Comparator) comparator);
        this.writePointer = this.readPointer;
        for (Object item : a) {
            put((T) item);
        }
    }

    /**
     * Returns <tt>true</tt> if this list contains no elements.
     *
     * @return <tt>true</tt> if this list contains no elements
     */
    public boolean isEmpty() {
        return (this.readPointer == this.writePointer) && this.isSameCycle;
    }

    /*check if buffer full*/
    private boolean isFull() {
        return (this.readPointer == this.writePointer) && !this.isSameCycle;
    }

    /*update pointer*/
    private void updPointer(int pointer) {
        pointer++;
        if (pointer == this.initialCapacity) {
            pointer = 0;
            this.isSameCycle = !this.isSameCycle;
        }
    }

    /*calculate free space in buffer*/
    private int calcFreeSpace() {
        return (isSameCycle)
                ? initialCapacity - (writePointer - readPointer)
                : writePointer - readPointer;
    }


}
