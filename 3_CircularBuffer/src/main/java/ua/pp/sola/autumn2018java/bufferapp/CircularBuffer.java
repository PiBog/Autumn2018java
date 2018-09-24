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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * An implementation of circular buffer
 *
 * @param
 * @author Bohdan Pysarenko
 * @version 1.0
 * @since 1.0
 */
public class CircularBuffer<T> {

    private int size;
    private int initialCapacity;
    private Object[] data;
    private int readPointer;st
    private int writePointer;
    /*set True when read pointer resetted to start and set false when write pointer resetted to start*/
    private boolean isSameCycle;

    public CircularBuffer(int initialCapacity) {

        this.initialCapacity = initialCapacity;
        data = new Object[initialCapacity];
        this.readPointer = 0;
        this.writePointer = 0;
        isSameCycle=true;

    }

    public void put(T data){
        if (isFull()){
            throw new RuntimeException("Buffer is full");
        }
        this.data[this.writePointer]=data;
        updPointer(writePointer);
    }

    @SuppressWarnings("unchecked")
    public T get(){
        if(isEmpty()){
            throw new RuntimeException("Buffer is empty");
        }
        T result = (T) data[readPointer];
        updPointer(readPointer);
        return result;
    }

    public Object[] toObjectArray(){
        if (isSameCycle){
            return Arrays.copyOfRange(this.data,readPointer,writePointer);
        }
        else{
            Object[] newArray = new Object[this.data.length-(readPointer-writePointer)];
            System.arraycopy(this.data,this.readPointer,newArray,
                    0,this.initialCapacity-readPointer);
            System.arraycopy(this.data, 0, newArray,
                    this.writePointer,this.writePointer);
            return newArray;
        }
    }

    @SuppressWarnings("unchecked")
    public T[] toArray(){
        return (T[])toObjectArray();
    }

    public List<T> asList(){
        return Arrays.asList(toArray());
    }

    public void addAll(List<? extends T> toAdd){
        if (toAdd.size()>calcFreeSpace()) throw new RuntimeException("Not enough space");
        for (T item : toAdd){
            put(item);
        }
    }

    @SuppressWarnings("unchecked")
    void sort(Comparator<? super T> comparator){
        Object[] a = toObjectArray();
        Arrays.sort(a, (Comparator) comparator);
        this.writePointer = this.readPointer;
        for (Object item : a) {
            put((T)item);
        }
    }



    public boolean isEmpty() {
        return (this.readPointer == this.writePointer)&&this.isSameCycle;
    }

    private boolean isFull() {
        return (this.readPointer == this.writePointer)&&!this.isSameCycle;
    }

    private void updPointer(int pointer){
        pointer++;
        if (pointer==this.initialCapacity){
            pointer=0;
            this.isSameCycle=!this.isSameCycle;
        }
    }

    private int calcFreeSpace(){
        return (isSameCycle)
                ? initialCapacity-(writePointer-readPointer)
                : writePointer-readPointer;
    }

    List list;


}
