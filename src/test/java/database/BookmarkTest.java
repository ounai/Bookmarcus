/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

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


public class BookmarkTest {
    
    Bookmark bm;
    
    public BookmarkTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        bm = new Bookmark();
        bm.setId(1);
        bm.setAuthor("Matti");
        bm.setDescription("Selostus");
        bm.setIsbn("12345");
        bm.setName("Paras juttu");
        bm.setRead(Bookmark.READ_FALSE);
        bm.setUrl("localhost:8080");
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void toStringTest() {
        bm.setType(Bookmark.TYPE_BOOK);
        System.out.println(bm);
        assertEquals(true, bm.toString().contains(Integer.toString(bm.getId())));
        assertEquals(true, bm.toString().contains(bm.getAuthor()));
        assertEquals(true, bm.toString().contains(bm.getDescription()));
        assertEquals(false, bm.toString().contains("null"));
    }   
    
    @Test
    public void testIsRead() {
        assertEquals(false, bm.isRead());
        bm.setRead(Bookmark.READ_TRUE);
        assertEquals(true, bm.isRead());
    }
        @Test
    public void testGetRead() {
        assertEquals(0, bm.getRead());
        bm.setRead(Bookmark.READ_TRUE);
        assertEquals(1, bm.getRead());
    }
    





    
}
