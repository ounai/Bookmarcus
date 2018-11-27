/*
 * This file is part of Bookmarcus.
 *
 * Bookmarcus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bookmarcus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bookmarcus. If not, see <https://www.gnu.org/licenses/>.
 */
package database;

import database.BookmarkDAO;
import static org.junit.Assert.assertEquals;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author WebCoodi
 */
public class BookmarkDAOTest {

    BookmarkDAO bio;
    String testdbPath = "test.db";

    public BookmarkDAOTest() {
    }

    @Before
    public void setUp() throws SQLException {

        String testdb = "jdbc:sqlite:" + testdbPath;

        try (Connection conn = DriverManager.getConnection(testdb)) {
            if (conn != null) {
                System.out.println("Test database created");
                String sql = "CREATE TABLE bookmark (id INTEGER PRIMARY KEY, name TEXT NOT NULL, description TEXT, author TEXT, isbn VARCHAR, url TEXT,type INTEGER);";
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            } else {
                System.out.println("Failed to create test database");
            }
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }

        bio = new BookmarkDAO(testdbPath);
    }

    @After
    public void tearDown() {
        File f = new File(testdbPath);
        f.delete();
    }

    /**
     * Test of find method, of class BookmarkDAO.
     */
    @Test
    public void testFind() {
        Bookmark bm = new Bookmark("test", "desc");
        bm.setName("test");
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
     * Test of getAll method, of class BookmarkDAO.
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

    @Test
    public void testDeleteById() {
        Bookmark bm = new Bookmark("test", "desc");
        bm.setType(1);
        bm.setAuthor("Markus");
        bm.setIsbn("1234-1234");
        Bookmark bm2 = new Bookmark("test", "desc");
        bm2.setType(1);
        bm2.setAuthor("Markus");
        bm2.setIsbn("1234-1234");
        bio.add(bm);
        bio.add(bm2);
        bio.delete(1);
        assertEquals(1, bio.getAll().size());
    }
    
    @Test
    public void testDeleteIdNotFound() {
        Bookmark bm = new Bookmark();
        bm.setName("Test");
        bm.setType(1);
        bio.add(bm);
        assertEquals(bio.delete(5), false);
    }

    @Test
    public void testConnectionWrongDBAddress() {
        BookmarkDAO b = new BookmarkDAO("wrong");
        assertEquals(b.add(new Bookmark()), false);

    }

}
