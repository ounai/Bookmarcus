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

/**
 * A list of possible types of bookmark, and helper methods for them.
 *
 * @author WebCoodi
 */
public enum Type {
    /**
     * The type of a book, ID 1, name "kirja".
     */
    BOOK(1, "kirja"),

    /**
     * The type of an article, ID 2, name "artikkeli".
     */
    ARTICLE(2, "artikkeli"),

    /**
     * The type of a blog post, ID 3, name "blogikirjoitus".
     */
    BLOGPOST(3, "blogikirjoitus"),

    /**
     * The type of a video, ID 4, name "video".
     */
    VIDEO(4, "video");
    
    private final int id;
    private final String name;
    
    Type(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    /**
     * Tries to find a type with the given name.
     * 
     * @param string the name of the type to search for
     * 
     * @param defaultType the type to return if no type matches the given string
     * 
     * @return the type, if found, otherwise the given default type
     */
    public static Type getFromString(String string, Type defaultType) {
        for (Type t : Type.values()) {
            if (string.equalsIgnoreCase(t.toString())) {
                return t;
            }
        }

        return defaultType;
    }

    /**
     * Tries to find a type with the given ID.
     * 
     * @param i the ID of the type to search for
     * 
     * @param defaultType the type to return if no type matches the given ID
     * 
     * @return the type, if found, otherwise the given default type
     */
    public static Type getFromInt(int i, Type defaultType) {
        for (Type t : Type.values()) {
            if (i == t.toInt()) {
                return t;
            }
        }

        return defaultType;
    }
    
    /**
     * @return the id of the type
     */
    public int toInt() {
        return id;
    }
    
    /**
     * @return the name of the type
     */
    @Override
    public String toString() {
        return name;
    }
    
}
