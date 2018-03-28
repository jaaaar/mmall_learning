package com.mmall.test;

import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.*;
import java.math.BigDecimal;
import java.util.Map;

/**
 * create by YuWen
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class BigDecimalTest {



    Map<Integer, String> map = Maps.newHashMap();
    @Test
    public void string() {
        String s = "course.png.png";
        System.out.println(s.substring(s.lastIndexOf(".") + 1));
    }

    @Test
    public void test1() {
        System.out.println(0.05 + 0.01);
        System.out.println(1.0 - 0.42);
        System.out.println(4.015 * 100);
        System.out.println(123.3 / 100);
    }

    @Test
    public void test2() {
        BigDecimal b1 = new BigDecimal(0.05);
        BigDecimal b2 = new BigDecimal(0.01);
        System.out.println(b1.add(b2));
    }

    @Test
    public void test3() {
        BigDecimal b1 = new BigDecimal("0.05");
        BigDecimal b2 = new BigDecimal("0.01");
        System.out.println(b1.add(b2));
    }

    @Test
    public void io() {
        File file = new File("mmall.txt");
        File file2 = new File("d:/text.txt");
        InputStreamReader isr = null;
        OutputStreamWriter osw = null;

        try {
            isr = new InputStreamReader(new FileInputStream(file), "gbk");
            osw = new OutputStreamWriter(new FileOutputStream(file2), "gbk");

            while(isr.read() != -1) {
                osw.write(isr.read());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                isr.close();
                osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void tow() {
        System.out.println(1 == 1.0);
    }
}
