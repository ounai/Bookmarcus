/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author daniel
 */
public class BookmarkIOTest {
    BookmarkIO bio;
    
    public BookmarkIOTest() {
    }
    
    
    @Before
    public void setUp() {
        String testdbPath = "test.db";
        String testdb = "jdbc:sqlite:" + testdbPath;
        
        try (Connection conn = DriverManager.getConnection(testdb)) {
            if (conn != null) {
                System.out.println("Test database created");
                String sql = "CREATE TABLE IF NOT EXISTS bookmark (id INTEGER PRIMARY KEY, name TEXT NOT NULL, description TEXT);";
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            } else {
                System.out.println("Failed to create test database");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        bio = new BookmarkIO("test.db");
    }
    
    @After
    public void tearDown() {
        File  f = new File("test.db");
        f.delete();
    }

    /**
     * Test of find method, of class BookmarkIO.
     */
    @Test
    public void testFind() {
        Bookmark bm = new Bookmark("test", "desc");
        bio.add(bm);
        assertEquals(bio.find(1).getName(), "test");
    }
    @Test
    public void testAdd() {
        Bookmark bm = new Bookmark("test", "desc");
        Bookmark bm2 = new Bookmark("test2", "desc2");
        bio.add(bm);
        bio.add(bm2);
        ArrayList<Bookmark> bms = bio.getAll();
        assertEquals(2, bms.size());
        assertEquals(bio.find(1).getName(), "test");
    }

    /**
     * Test of getAll method, of class BookmarkIO.
     */
    @Test
    public void testGetAll() {
        Bookmark bm = new Bookmark("test", "desc");
        Bookmark bm2 = new Bookmark("test2", "desc2");
        Bookmark bm3 = new Bookmark("test3", "desc3");
        bio.add(bm);
        bio.add(bm2);
        bio.add(bm3);
        ArrayList<Bookmark> bms = bio.getAll();
        assertEquals(3, bms.size());
        
    }
    


    
}
