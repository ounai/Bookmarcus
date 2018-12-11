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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bookmarcus. If not, see <https://www.gnu.org/licenses/>.
 */
package database;

import database.bookmark.Bookmark;
import database.BookmarkDAO;
import database.bookmark.BookmarkFactory;
import database.bookmark.Type;
import static org.junit.Assert.assertEquals;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author WebCoodi
 */
public class BookmarkDAOTest {

    BookmarkDAO bio;
    Bookmark bm;
    Bookmark bm2;
    Bookmark bm3;
    String testdbPath = "test.db";

    public BookmarkDAOTest() {
    }

    @Before
    public void setUp() throws SQLException {

        String testdb = "jdbc:sqlite:" + testdbPath;

        try (Connection conn = DriverManager.getConnection(testdb)) {
            if (conn != null) {
                System.out.println("Test database created");
                String sql = "CREATE TABLE bookmark (id INTEGER PRIMARY KEY, name TEXT NOT NULL, description TEXT, author TEXT, isbn VARCHAR, url TEXT,type INTEGER, read INTEGER);";
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            } else {
                System.out.println("Failed to create test database");
            }
        } catch (SQLException e) {
            System.out.println(e);
            throw e;
        }
        setUpLocalVariables();

    }

    private void setUpLocalVariables() {
        bio = new BookmarkDAO(testdbPath);
        bm = BookmarkFactory.newBookmarkByType(Type.BOOK);
        bm.setAuthor("Writer Gurd");
        bm.setName("How NOT to test code");
        bm.setDescription("This is a nice book for me");
        bm.setISBN("1234-6789");
        bm2 = BookmarkFactory.newBookmarkByType(Type.BLOGPOST);
        bm2.setAuthor("Markus the blogger");
        bm2.setDescription("This was a funny blogpost");
        bm2.setName("How NOT to write too long methods");
        bm2.setURL("localhost:8080");
        bm3 = BookmarkFactory.newBookmarkByType(Type.ARTICLE);
        bm3.setName("Git gud: how to test");
        bm3.setAuthor("Professor X");
        bm3.setDescription("I learned a lot from this");

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
        bio.add(bm);
        assertEquals(bio.find(1).getName(), bm.getName());
    }
    
    /**
     * Test of findByAuthor method, of class BookmarkDAO
     */
    @Test
    public void testFindByAuthor() {
        assertEquals(0, bio.findByAuthor("Writer Gurd").size());
        
        bio.add(bm);
        
        assertEquals(1, bio.findByAuthor("Writer Gurd").size());
    }
    
    /**
     * Test of update method, of class BookmarkDAO
     */
    @Test
    public void testUpdate() {
        bio.add(bm);
        
        String newName = "New Name Best Name",
                newDesc = "The description changed all of a sudden!",
                newAuthor = "This Guy",
                newISBN = "9519854894";
        
        bm.setName(newName);
        bm.setDescription(newDesc);
        bm.setAuthor(newAuthor);
        bm.setISBN(newISBN);
        
        bio.update(1, bm);
        
        assertEquals(bio.find(1).getName(), newName);
        assertEquals(bio.find(1).getDescription(), newDesc);
        assertEquals(bio.find(1).getAuthor(), newAuthor);
        assertEquals(bio.find(1).getISBN(), newISBN);
    }

    @Test
    public void testAdd() {
        bio.add(bm);
        bio.add(bm2);
        List<Bookmark> bms = bio.getAll();
        assertEquals(2, bms.size());
        assertEquals(bio.find(1).getName(), bm.getName());
    }

    /**
     * Test of getAll method, of class BookmarkDAO.
     */
    @Test
    public void testGetAll() {

        bio.add(bm);
        bio.add(bm2);
        bio.add(bm3);
        List<Bookmark> bms = bio.getAll();
        assertEquals(3, bms.size());

    }

    @Test
    public void testDeleteById() {

        bio.add(bm);
        bio.add(bm2);
        bio.delete(1);
        assertEquals(1, bio.getAll().size());
    }

    @Test
    public void testDeleteIdNotFound() {
        bio.add(bm);
        assertEquals(bio.delete(5), false);
    }

    @Test
    public void testConnectionWrongDBAddress() {
        BookmarkDAO b = new BookmarkDAO("wrong");
        assertEquals(b.add(bm), false);

    }
    
    @Test
    public void testFindNullIfNotExists() {
        assertEquals(null,bio.find(1));
    }
    
    @Test
    public void markAsRead() {
        bio.add(bm);
        assertEquals(1, bio.getAllUnRead().size());
        bio.markAsRead(1);
        assertEquals(1, bio.getAllRead().size());
        assertEquals(0, bio.getAllUnRead().size());
        
    }
    @Test
    public void markAsReadFalseIfNotFound() {
        assertEquals(false, bio.markAsRead(1));
    }
    
    @Test
    public void noAddingIfNotName() {
        Bookmark bookmark = BookmarkFactory.newBookmarkByType(Type.BOOK);
        assertEquals(false, bio.add(bookmark));
    }
    
    @Test
    public void updateFalseIfWrongId() {
        assertEquals(false, bio.update(4, bm));
    }
    
    @Test
    public void findByType() {
        bio.add(bm);
        bio.add(bm2);
        bio.add(bm3);
        assertEquals(1, bio.findByType("kirja").size());
    }

}
