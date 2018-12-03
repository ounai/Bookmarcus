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
public class VideoTest {
    
    Video video;
    
    @Before
    public void setUp() {
        video = new Video();
    }
    
    @Test
    public void ArticleVerification() {
        assertTrue("Video should have author", video.hasAuthor());
        assertTrue("Video should have URL", video.hasURL());
        assertFalse("Video shouldn't have ISBN", video.hasISBN());
        assertEquals("Video has correct type", Type.VIDEO, video.getType());    
    }
}
