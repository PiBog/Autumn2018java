package ua.pp.sola.totalizer.domain;/*Bohdan Pysarenko © All right reserved.
 *
 */

import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

@Log4j
public class RoundTest {

    List<ArrayList<String>> sampleLists;


    @Test
    @Ignore
    public void testParseLine() {

        String lineOne = "2015;33;2;2015.08.17.;" +
                "0;0 UAH;" +
                "1;6 607 515 UAH;" +
                "29;60 240 UAH;" +
                "318;5 495 UAH;" +
                "2181;1 535 UAH;" +
                "2;1;2;X;2;1;1;2;X;X;1;2;2;+1";

        String lineTwo = "1998;1;-;;" +
                "0;0 UAH;" +
                "0;0 UAH;" +
                "23;116 415 UAH;" +
                "421;6 360 UAH;" +
                "3618;1 233 UAH;" +
                "x;1;x;x;2;x;2;2;1;x;1;x;x;1";

//        sampleLists = Stream.of(lineOne,lineTwo)
//                .map((x)->Round.parseLine(x))
//                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        sampleLists.forEach(item->Assert.assertEquals(28,item.size()));

    }

    @Test
    @Ignore
    public void testOf_getObjectFrom() {


    }
}