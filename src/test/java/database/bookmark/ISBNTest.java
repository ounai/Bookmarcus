/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.bookmark;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author daniel
 */
public class ISBNTest {
    

    public ISBNTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void isbnCheckingWorks() {
        assertTrue(ISBN.validISBN(""));

        assertTrue(ISBN.validISBN("9519854894"));
        assertTrue(ISBN.validISBN("9789519854892"));

        assertTrue(ISBN.validISBN("951-98548-9-4"));
        assertTrue(ISBN.validISBN("978-951-98548-9-2"));
        assertTrue(ISBN.validISBN("007462542X"));

        assertFalse(ISBN.validISBN("Moi"));
        assertFalse(ISBN.validISBN("951-98548-9-5"));
    }

}
