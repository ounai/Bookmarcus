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

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author WebCoodi
 */
public class BookmarkFactoryTest {
    
    BookmarkFactory bf;
    
    @Before
    public void setUp() {
        bf = new BookmarkFactory();
    }

    @Test
    public void illegalArgumentValidation() {
        try {
            BookmarkFactory.newBookmarkByType("invalid");
            fail();
        } catch (IllegalArgumentException e) {}
        
        try {
            BookmarkFactory.newBookmarkByType(-1);
            fail();
        } catch (IllegalArgumentException e) {}
        
        try {
            BookmarkFactory.newBookmarkByType((Type) null);
            fail();
        } catch (IllegalArgumentException e) {}
    }
    
    @Test
    public void factoryReturnCorrectTypeOfBookmark() {
        assertTrue(BookmarkFactory.newBookmarkByType(Type.ARTICLE) instanceof Article);
        assertTrue(BookmarkFactory.newBookmarkByType(Type.ARTICLE.toInt()) instanceof Article);
        assertTrue(BookmarkFactory.newBookmarkByType(Type.ARTICLE.toString()) instanceof Article);
        
        assertTrue(BookmarkFactory.newBookmarkByType(Type.BLOGPOST) instanceof Blogpost);
        assertTrue(BookmarkFactory.newBookmarkByType(Type.BLOGPOST.toInt()) instanceof Blogpost);
        assertTrue(BookmarkFactory.newBookmarkByType(Type.BLOGPOST.toString()) instanceof Blogpost);
        
        assertTrue(BookmarkFactory.newBookmarkByType(Type.BOOK) instanceof Book);
        assertTrue(BookmarkFactory.newBookmarkByType(Type.BOOK.toInt()) instanceof Book);
        assertTrue(BookmarkFactory.newBookmarkByType(Type.BOOK.toString()) instanceof Book);
        
        assertTrue(BookmarkFactory.newBookmarkByType(Type.VIDEO) instanceof Video);
        assertTrue(BookmarkFactory.newBookmarkByType(Type.VIDEO.toInt()) instanceof Video);
        assertTrue(BookmarkFactory.newBookmarkByType(Type.VIDEO.toString()) instanceof Video);
    }
}
