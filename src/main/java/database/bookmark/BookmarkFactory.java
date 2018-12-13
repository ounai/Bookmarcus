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
 * Contains factory methods for creating a bookmark with given parameters.
 *
 * @author WebCoodi
 */
public class BookmarkFactory {
    
    /**
     * Generates a bookmark from an integer representation of a type.
     * 
     * For possible types and their id values see {@link database.bookmark.Type}
     * 
     * @param i the integer representation of the type of bookmark to create
     * 
     * @return the created bookmark
     */
    public static Bookmark newBookmarkByType(int i) {
        Type type = Type.getFromInt(i, null);

        if (type != null) {
            return newBookmarkByType(type);
        }

        throw new IllegalArgumentException("Type " + i + " not supported.");
    }
    
    /**
     * Generates a bookmark from a string representation of a type.
     * 
     * For possible types and their names, see {@link database.bookmark.Type}
     * 
     * @param string the string representation of the type of bookmark to create
     * 
     * @return the created bookmark
     */
    public static Bookmark newBookmarkByType(String string) {
        Type type = Type.getFromString(string, null);

        if (type != null) {
            return newBookmarkByType(type);
        }

        throw new IllegalArgumentException("Type '" + string + "' not supported.");
    }
    
    /**
     * Generates a bookmark with a given type.
     * 
     * @param type the type of the bookmark
     * 
     * @return the created bookmark
     */
    public static Bookmark newBookmarkByType(Type type) {
        if (type == null) {
            throw new IllegalArgumentException("null is not allowed");
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
