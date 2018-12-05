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
    
    ISBN isbn;
    

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
        isbn = new ISBN();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void isbnCheckingWorks() {
        assertTrue(isbn.setISBN(""));

        assertTrue(isbn.setISBN("9519854894"));
        assertTrue(isbn.setISBN("9789519854892"));

        assertTrue(isbn.setISBN("951-98548-9-4"));
        assertTrue(isbn.setISBN("978-951-98548-9-2"));
        assertTrue(isbn.setISBN("007462542X"));

        assertFalse(isbn.setISBN("Moi"));
        assertFalse(isbn.setISBN("951-98548-9-5"));
    }

}
