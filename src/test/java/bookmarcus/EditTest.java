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
package bookmarcus;

import database.DatabaseDAO;
import database.bookmark.Bookmark;
import database.bookmark.BookmarkFactory;
import database.bookmark.Type;
import io.IO;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.mockito.stubbing.OngoingStubbing;

/**
 *
 * @author WebCoodi
 */
public class EditTest {
    
    Bookmark bookmark;
    DatabaseDAO<Bookmark> mockDAO;
    IO mockIO;
    
    @Before
    public void setUp() {
        bookmark = BookmarkFactory.newBookmarkByType(Type.BOOK);
        mockIO = mock(IO.class);
        mockDAO = (DatabaseDAO<Bookmark>) mock(DatabaseDAO.class);
        when(mockDAO.find(anyInt())).thenReturn(bookmark);
    }
    
    private Edit init(String... inputs) {
        OngoingStubbing feed = when(mockIO.nextLine());
        for (String input : inputs) {
            feed = feed.thenReturn(input);
        }
        feed.thenThrow(new NoSuchElementException());
        return new Edit(mockIO, mockDAO);
    }
    
    @Test
    public void bookmarkAskedFromDAO() {
        Edit edit = init("0");
        verify(mockDAO, times(0)).find(anyInt());
        try {
            edit.run();
        } catch (NoSuchElementException e) {}
        verify(mockDAO, times(1)).find(anyInt());
        verify(mockDAO, times(1)).find(eq(0));
    }
    
}
