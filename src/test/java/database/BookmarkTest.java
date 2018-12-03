/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import database.bookmark.Bookmark;
import database.bookmark.BookmarkFactory;
import database.bookmark.Type;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author WebCoodi
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
        bm = BookmarkFactory.newBookmarkByType(Type.BOOK);
        bm.setAuthor("Matti");
        bm.setDescription("Selostus");
        bm.setISBN("12345");
        bm.setName("Paras juttu");
        bm.setRead(true);
        bm.setURL("localhost:8080");
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void toStringTest() {
        System.out.println(bm);
        assertEquals(true, bm.toString().contains(Integer.toString(bm.getID())));
        assertEquals(true, bm.toString().contains(bm.getAuthor()));
        assertEquals(true, bm.toString().contains(bm.getDescription()));
        assertEquals(false, bm.toString().contains("null"));
    }   
    
    @Test
    public void testIsRead() {
        assertEquals(false, bm.isRead());
        bm.setRead(true);
        assertEquals(true, bm.isRead());
    }
    
    @Test
    public void testGetRead() {
        assertEquals(0, bm.getRead());
        bm.setRead(true);
        assertEquals(1, bm.getRead());
    }
    
}
