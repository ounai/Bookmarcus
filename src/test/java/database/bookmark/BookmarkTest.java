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
package database.bookmark;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author WebCoodi
 */


public class BookmarkTest {
    
    Bookmark bookmark;
    Bookmark book;
    Bookmark article;
    
    @Before
    public void setUp() {
        book = BookmarkFactory.newBookmarkByType(Type.BOOK);
        article = BookmarkFactory.newBookmarkByType(Type.ARTICLE);
        bookmark = new Bookmark() {
            @Override
            public Type getType() {
                return null;
            }

            @Override
            public boolean hasAuthor() {
                return false;
            }

            @Override
            public boolean hasISBN() {
                return false;
            }

            @Override
            public boolean hasURL() {
                return false;
            }
        };
    }
    
    @Test
    public void parameterValidation() {
        // ID
        bookmark.setID(9000);
        assertEquals(9000, bookmark.getID());
        
        // Name
        String noName = "n/a";
        assertEquals(noName, bookmark.getName());
        
        String newName = "kirjanmerkki";
        bookmark.setName(newName);
        assertEquals("Name should be set", newName, bookmark.getName());
        
        bookmark.setName("");
        assertEquals(noName, book.getName());
        
    }
    
    @Test
    public void missingFieldHandling() {
        try {
            bookmark.setAuthor("Matti");
            fail();
        } catch (UnsupportedOperationException e) {}
        
        try {
            bookmark.setISBN("");
            fail();
        } catch (UnsupportedOperationException e) {}
        
        try {
            bookmark.setURL("localhost");
            fail();
        } catch (UnsupportedOperationException e) {}
    }

    @Test
    public void isbnCheckingWorks() {
        assertTrue(Bookmark.validISBN("9519854894"));
        assertTrue(Bookmark.validISBN("9789519854892"));

        assertTrue(Bookmark.validISBN("951-98548-9-4"));
        assertTrue(Bookmark.validISBN("978-951-98548-9-2"));
        assertTrue(Bookmark.validISBN("007462542X"));

        assertFalse(Bookmark.validISBN("Moi"));
        assertFalse(Bookmark.validISBN("951-98548-9-5"));
    }
    
}
