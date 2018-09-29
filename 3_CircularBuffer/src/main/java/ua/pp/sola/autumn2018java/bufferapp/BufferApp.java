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

import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Class for checking of work of implementation of CircleBuffer. Contains entry point.
 *
 * @author Bohdan Pysarenko
 * @version 1.0
 * @since 1.0
 */
@Log4j
public class BufferApp {

    public static void main(String[] args) {

        CircularBuffer<Integer> buffer = new CircularBuffer<>(10);

        log.info("1. Try to read from empty buffer");
        try {
            buffer.get();
        } catch (Exception e) {
            log.info("Error message: " + e.getMessage());

        }
        log.info("Check isEmpty method: " + buffer.isEmpty());
        log.info(buffer.toString());
        log.info("wr:" + buffer.getWritePointer() + "/rd:" + buffer.getReadPointer());

        log.info("-------------");
        log.info("2. Add 7 elements");
        StringBuilder wr = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            wr.append(buffer.getWritePointer() + ",");
            buffer.put(i);
        }
        log.info("wr pointer:" + wr);
        log.info("wr:" + buffer.getWritePointer() + "/rd:" + buffer.getReadPointer());

        log.info("-------------");
        log.info("3. Read 7 elements");
        StringBuilder string = new StringBuilder();
        StringBuilder rd = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            rd.append(buffer.getWritePointer() + ",");
            string.append(buffer.get() + ", ");
        }
        log.info(string);
        log.info("rd pointer:" + rd);
        log.info("wr:" + buffer.getWritePointer() + "/rd:" + buffer.getReadPointer());

        log.info("-------------");
        log.info("4. Input 5 element and get array of data");
        StringBuilder inputData = new StringBuilder("input:");
        for (int i = 0; i < 6; i++) {
            buffer.put(i);
            inputData.append("[" + i + "], ");
        }
        log.info(inputData);
        log.info(buffer.toString());
        log.info("wr:" + buffer.getWritePointer() + "/rd:" + buffer.getReadPointer());



        log.info("-------------");
        log.info("5. Try write 11 elements and overload buffer");
        log.info("Before -> " + buffer.toString());
        try {
            for (int i = 0; i < 11; i++) {
                buffer.put(i);
            }
        } catch (Exception e) {
            log.info("Error message: " + e.getMessage());
        }
        log.info(buffer.toString());
        log.info("wr:" + buffer.getWritePointer() + "/rd:" + buffer.getReadPointer());

        log.info("-------------");
        log.info("6. Get list");
        int i = 0;
        while (i < 4) {
            buffer.get();
            i++;
        }
        List<Integer> newList = buffer.asList();
        StringBuilder listAsString = new StringBuilder("list: ");
        for (int item : newList) {
            listAsString.append(item + ",");
        }
        log.info("Get list: " + listAsString + ", with size: " + newList.size());
        log.info(buffer.toString());
        log.info("wr:" + buffer.getWritePointer() + "/rd:" + buffer.getReadPointer());

        log.info("-------------");
        log.info("7. Add list");
        i = 0;
        List<Integer> list = new ArrayList<>();
        while (i < 10) {
            list.add(10);
            i++;
        }
        try {
            log.info("try add too big collection");
            buffer.addAll(list);
        } catch (Exception e) {
            log.info("Error message: " + e.getMessage());
        }
        try {
            log.info("try add 3 first elements from list to buffer");
            buffer.addAll(list.subList(0, 3));
        } catch (Exception e) {
            log.info("Error message: " + e.getMessage());
        }
        log.info(buffer.toString());
        log.info("wr:" + buffer.getWritePointer() + "/rd:" + buffer.getReadPointer());

        log.info("-------------");
        log.info("8. Sort buffer");
        buffer.sort(Comparator.comparingInt(o -> o));
        log.info(buffer.toString() + "(ascent)");

        buffer.sort((o1, o2) -> o2 - o1);
        log.info(buffer.toString() + "(descent)");

        log.info("wr:" + buffer.getWritePointer() + "/rd:" + buffer.getReadPointer());
    }
}
