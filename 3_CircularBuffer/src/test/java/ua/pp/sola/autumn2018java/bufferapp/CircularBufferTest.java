package ua.pp.sola.autumn2018java.bufferapp;/*Bohdan Pysarenko © All right reserved.
 *
 */

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CircularBufferTest {

    CircularBuffer<Integer> buffer;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        this.buffer = new CircularBuffer<>(5);

    }

    @Test
    public void testPut() {

        buffer.put(1);
        assertEquals(1, buffer.size());
    }

    @Test
    public void testPutTooMuchData_expectingException() {


        thrown.expect(BufferOverflowException.class);

        for (int i=0;i<buffer.size()+1;i++){
            buffer.put(i);
        }
    }

    @Test
    public void testGet() {
        buffer.put(22);
        buffer.put(33);
        int readed = buffer.get();
        assertEquals(22, readed);
    }

    @Test
    public void testGetFromEmpty_expectingException() {

        thrown.expect(BufferUnderflowException.class);

        buffer.get();
    }

    @Test
    public void testToObjectArray() {
        buffer.put(1);
        buffer.put(2);
        assertEquals("[1, 2]", Arrays.toString(buffer.toArray()));
    }

    @Test
    public void testToArray() {
        buffer.put(1);
        buffer.put(2);
        Integer[] arr = buffer.toArray();
        assertEquals("[1, 2]", Arrays.toString(buffer.toArray()));
    }

    @Test
    public void testToArrayWithInst() {
        buffer.put(1);
        buffer.put(2);
        Integer[] arr = buffer.toArray(new Integer[0]);
        assertEquals("[1, 2]", Arrays.toString(buffer.toArray()));
    }

    @Test
    public void testAsList() {
        buffer.put(1);
        buffer.get();
        buffer.put(1);
        buffer.get();

        buffer.put(4);
        buffer.put(3);
        buffer.put(5);

        List<Integer> arr = buffer.asList();
        assertEquals("[4, 3, 5]", arr.toString());
    }

    @Test
    public void testAddAll() {
        buffer.put(4);
        buffer.put(3);
        buffer.put(5);
        List<Integer> list = new ArrayList<>();
        list.add(9);
        list.add(10);
        buffer.addAll(list);
        Integer[] arr = buffer.toArray();
        assertEquals("[4, 3, 5, 9, 10]", Arrays.toString(arr));

    }

    @Test
    public void testAddAllException() {
        buffer = new CircularBuffer<>(3);
        int listSize = buffer.maxSize()+1;
        List<Integer> list = new ArrayList<>(listSize);

        for (int i=0; i<listSize; i++){
            list.add(i);
        }

        thrown.expect(BufferOverflowException.class);

        buffer.addAll(list);
    }

    @Test
    public void testSort() {
        List<Integer> list = Arrays.asList(3,1,8,6,9);
        buffer.addAll(list);
        buffer.sort(Integer::compareTo);
        Integer[] arr = buffer.toArray();
        assertEquals("[1, 3, 6, 8, 9]", Arrays.toString(arr));

    }

    @Test
    public void testIsEmpty() {

        assertTrue(buffer.isEmpty());
    }

    @Test
    public void testClean() {
        buffer.put(1);
        buffer.clean();
        assertTrue(buffer.isEmpty());
    }
}