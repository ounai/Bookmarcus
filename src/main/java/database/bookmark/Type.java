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
 *
 * @author WebCoodi
 */
public enum Type {
    BOOK(1, "kirja"),
    ARTICLE(2, "artikkeli"),
    BLOGPOST(3, "blogikirjoitus"),
    VIDEO(4, "video");
    
    private final int id;
    private final String name;
    
    Type(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public static Type getFromString(String string, Type defaultType) {
        for (Type t : Type.values()) {
            if (string.equalsIgnoreCase(t.toString())) {
                return t;
            }
        }
        return defaultType;
    }

    public static Type getFromInt(int i, Type defaultType) {
        for (Type t : Type.values()) {
            if (i == t.toInt()) {
                return t;
            }
        }
        return defaultType;
    }
    
    public int toInt() {
        return id;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
