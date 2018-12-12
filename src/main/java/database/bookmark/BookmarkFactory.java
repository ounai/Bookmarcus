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
package database.bookmark;

/**
 *
 * @author WebCoodi
 */
public class BookmarkFactory {
    
    public static Bookmark newBookmarkByType(int i) {
        Type type = Type.getFromInt(i, null);
        if (type != null) {
            return newBookmarkByType(type);
        }
        throw new IllegalArgumentException("Type " + i + " not supported.");
    }
    
    public static Bookmark newBookmarkByType(String string) {
        Type type = Type.getFromString(string, null);
        if (type != null) {
            return newBookmarkByType(type);
        }
        throw new IllegalArgumentException("Type '" + string + "' not supported.");
    }
    
    public static Bookmark newBookmarkByType(Type type) {
        if (type == null) {
            throw new IllegalArgumentException("Null is not allowed");
        }
        switch (type) {
            case ARTICLE:  return new Article();
            case BLOGPOST: return new Blogpost();
            case BOOK:     return new Book();
            case VIDEO:    return new Video();
            default:       throw new RuntimeException(); // Should never be reached
        }
    }
    
}
