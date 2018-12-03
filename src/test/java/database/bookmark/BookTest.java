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
public class BookTest {
    
    Book book;
    
    @Before
    public void setUp() {
        book = new Book();
    }
    
    @Test
    public void ArticleVerification() {
        assertTrue("Book should have author", book.hasAuthor());
        assertFalse("Book shouldn't have URL", book.hasURL());
        assertTrue("Book should have ISBN", book.hasISBN());
        assertEquals("Book has correct type", Type.BOOK, book.getType());    
    }
}
