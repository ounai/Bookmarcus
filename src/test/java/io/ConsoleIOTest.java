/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author daniel
 */
public class ConsoleIOTest {

    ConsoleIO cio;

    public ConsoleIOTest() {
    }

    @Before
    public void setUp() {
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        cio = new ConsoleIO(scanner);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of hasNextLine method, of class ConsoleIO.
     */
    @Test
    public void testHasNextLine() {
        assertTrue(cio.hasNextLine());
    }

    /**
     * Test of nextLine method, of class ConsoleIO.
     */
    @Test
    public void testNextLine() {
        assertEquals("1", cio.nextLine());
    }

    /**
     * Test of close method, of class ConsoleIO.
     */
    @Test
    public void testClose() {
        cio.close();
        try {
            cio.nextLine();
        } catch (Exception e) {
            return;
        }
        fail("Input stream not closed");
    }

    /**
     * Test of print method, of class ConsoleIO.
     */
    @Test
    public void testPrint() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        cio.print("hello");
        assertEquals("hello\n", outContent.toString()); //Method print uses println
        System.setOut(originalOut);
        
    }

}
