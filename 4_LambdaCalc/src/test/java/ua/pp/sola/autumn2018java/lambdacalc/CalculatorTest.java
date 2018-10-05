package ua.pp.sola.autumn2018java.lambdacalc;/*Bohdan Pysarenko © All right reserved.
 *
 */

import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

@Log4j
public class CalculatorTest {


    ByteArrayOutputStream baos;

    @Before
    public void setUp() {
        baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos, true);
        System.setOut(ps);
    }

    @Test
    public void testMain() {
        String[] list = {"12*5","108/3","22+37","46-50","3*8-6/3"};
        String[] result = {"60","36","59","-4","22"};
        for (int i =0; i <list.length; i++) {
            System.setIn(new ByteArrayInputStream(list[i].getBytes()));
            try {
                Calculator.main(new String[0]);
                String out = baos.toString("UTF8");

                Assert.assertEquals(result[i], out.trim());
                baos.reset();
            } catch (IOException e) {
                log.error(e.getStackTrace());
            }

        }

    }


}