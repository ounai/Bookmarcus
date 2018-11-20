package main;

import main.Dummy;
import org.junit.Test;
import static org.junit.Assert.*;

public class testDummy {

    @Test
    public void testHelloWorld() {
        Dummy d = new Dummy();
        assertEquals(d.helloWorld(), "Hello World");
    }
}