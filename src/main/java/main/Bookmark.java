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
package main;

/**
 * Database entry container for Bookmark table
 * 
 * @author WebCoodi
 */
public class Bookmark {

    private final static int MISSING_ID_PLACEHOLDER = -1;
    private final static String EMPTY_DESCRIPTION = "";

    private String name;
    private int id;
    private String description;

    public Bookmark(String name) {
        this(MISSING_ID_PLACEHOLDER, name, EMPTY_DESCRIPTION);
    }

    public Bookmark(String name, String description) {
        this(MISSING_ID_PLACEHOLDER, name, description);
    }

    public Bookmark(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "" + this.id + " " + this.name + " " + this.description;
    }
}
