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

    public static int getTypeId(String type) {
         switch(type) {
             case "kirja":
                 return 1;
             case "artikkeli":
                 return 2;
             case "blogikirjoitus":
                 return 3;
             case "video":
                 return 4;
             default:
                 return 0;
         }
    }
    
    public int toInt() {
        return id;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
