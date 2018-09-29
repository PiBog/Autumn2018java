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


import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.util.*;
import java.util.function.Consumer;

/**
 * An implementation of circular buffer
 *
 * @param <T> the type of elements in this list
 * @author Bohdan Pysarenko
 * @version 1.0
 * @since 1.0
 */
@Log4j
public class CircularBuffer<T> {

    @Getter
    private int readPointer;
    @Getter
    private int writePointer;
    private int initialCapacity;
    private Object[] elementData;
    /*set True when read pointer resetted to start and set false when write pointer resetted to start*/
    private boolean isSameCycle;
    private int modCount;

    /**
     * Construct new instance of buffer\
     *
     * @param initialCapacity size of buffer
     */
    public CircularBuffer(int initialCapacity) {

        this.initialCapacity = initialCapacity;
        elementData = new Object[initialCapacity];
        this.readPointer = 0;
        this.writePointer = 0;
        isSameCycle = true;
        this.modCount = 0;
    }

    /**
     * Write element to buffer
     *
     * @param data element that writing to buffer
     * @throws java.nio.BufferOverflowException if buffer is full
     */
    public void put(T data) {
        if (isFull()) {
            throw new BufferOverflowException();
        }
        this.elementData[writePointer] = data;
        writePointer++;
        if (writePointer == this.initialCapacity) {
            writePointer = 0;
            this.isSameCycle = false;
        }
    }

    /**
     * Read next element from buffer
     *
     * @return element from buffer
     * @throws BufferOverflowException if buffer is empty
     */
    @SuppressWarnings("unchecked")
    public T get() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        T result = (T) elementData[readPointer];
        readPointer++;
        if (readPointer == this.initialCapacity) {
            readPointer = 0;
            this.isSameCycle = true;
        }
        this.modCount++;
        return result;
    }

    /**
     * Get actual elements from buffer as Object[] array
     *
     * @return a new Object array with containing the actual elements from the buffer
     */
    public Object[] toObjectArray() {
        Object[] result;
        if (!isEmpty()) {
            if (isSameCycle) {
                result = Arrays.copyOfRange(this.elementData, readPointer, writePointer);

                return result;
            } else {
                result = new Object[this.elementData.length - (readPointer - writePointer)];
                System.arraycopy(this.elementData, this.readPointer, result,
                        0, this.initialCapacity - readPointer);
                System.arraycopy(this.elementData, 0, result,
                        this.initialCapacity - readPointer, this.writePointer);
                return result;
            }
        } else {
            result = new Object[0];
            return result;
        }
    }

    /**
     * Get array T[] of actual elements from buffer
     * @param arr - generic array
     *
     * @return a new array with containing the actual elements from the buffer
     */
    @SuppressWarnings("unchecked")
    public T[] toArray(T[] arr) {
        Object[] objects = toObjectArray();
        int size = objects.length;
        // Make a new array of a's runtime type, but my contents:
        return (T[]) Arrays.copyOf(objects, size, arr.getClass());
    }

    /**
     * Get array T[] of actual elements from buffer
     *
     * @return a new array with containing the actual elements from the buffer
     */

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        Object[] arr = toObjectArray();
        Object[] outData = new Object[arr.length];
        for (int i=0; i<arr.length; i++){
            outData[i] =(T) arr[i];
        }

//    Object[] outData = this.toObjectArray();
    return (T[])outData;
    }

    /**
     * Get list of actual elements from buffer
     *
     * @return a list view of the actual buffer data
     */
    @SuppressWarnings("unchecked")
    public  List<T> asList() {
        ArrayList<T> list = new ArrayList<>();
        for (Object item : toObjectArray()) {
            list.add((T) item);
        }
        return list;
    }

    /**
     * Add all elements from list to buffer if enough space otherwise throw exception
     *
     * @param toAdd list with elements of T class
     * @throws BufferOverflowException if buffer has not enough space for write all data from list
     */
    public void addAll(List<? extends T> toAdd) {
        if (toAdd.size() > calcFreeSpace()) throw new BufferOverflowException();
        log.info(toAdd.size() + ">" + calcFreeSpace());
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
        this.clean();
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

    public int size(){
        return this.initialCapacity-this.calcFreeSpace();
    }

    public int maxSize(){
        return this.initialCapacity;
    }

    /*calculate free space in buffer*/
    private int calcFreeSpace() {
        return (isSameCycle)
                ? initialCapacity - (writePointer - readPointer)
                :  readPointer - writePointer;
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        StringBuilder bufferAsString = new StringBuilder("Buffered elements: ");
        if (isEmpty()) {
            bufferAsString.append("empty");
        } else {

            for (Object item : this.toArray()) {
                bufferAsString.append("[" + item + "] ");
            }
        }

        return bufferAsString.toString();
    }

    /**
     * Clean buffer and return <tt>true</tt>.
     *
     * @return <tt>true</tt> when clean buffer
     */
    public void clean(){
        this.readPointer=this.writePointer;
        this.isSameCycle=true;

    }

    public Iterator<T> iterator() {
        return new Itr();
    }

    /**
     * An optimized version of AbstractList.Itr
     */
    private class Itr implements Iterator<T> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        public boolean hasNext() {
            return cursor != size();
        }

        @SuppressWarnings("unchecked")
        public T next() {
            checkForComodification();
            int i = cursor;
            if (i >= size())
                throw new NoSuchElementException();
            Object[] elementData = CircularBuffer.this.toObjectArray();
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (T) elementData[lastRet = i];
        }

        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer);
            final int size = CircularBuffer.this.size();
            int i = cursor;
            if (i >= size) {
                return;
            }
            final Object[] elementData = CircularBuffer.this.toObjectArray();
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            while (i != size && modCount == expectedModCount) {
                consumer.accept((T) elementData[i++]);
            }
            // update once at end of iteration to reduce heap write traffic
            cursor = i;
            lastRet = i - 1;
            checkForComodification();
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }
}
