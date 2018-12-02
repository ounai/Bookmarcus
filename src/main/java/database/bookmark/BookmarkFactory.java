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
    
    public static Bookmark newBookmarkByType(int type) {
        for (Type t : Type.values()) {
            if (type == t.toInt()) {
                return newBookmarkByType(t);
            }
        }
        throw new IllegalArgumentException("Type " + type + " not supported.");
    }
    
    public static Bookmark newBookmarkByType(String type) {
        for (Type t : Type.values()) {
            if (type.equalsIgnoreCase(t.toString())) {
                return newBookmarkByType(t);
            }
        }
        throw new IllegalArgumentException("Type '" + type + "' not supported.");
    }
    
    public static Bookmark newBookmarkByType(Type type) {
        Bookmark bm = new Bookmark();
        bm.setType(type.toInt());
        return bm;
    }
    
}
