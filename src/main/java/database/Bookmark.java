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
package database;

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
    private String url;
    private int type; // 1 = Book, 2 = Article, 3 = Blogpost

    // Book, Article, Blog type variables
    private String author;

    //Book type variables
    private String isbn;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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
        switch (this.type) {
            case 1:
                return bookToString();
            case 2:
                return articleToString();
            case 3:
                return blogpostToString();
            default:
                return "Unknown bookmark";
        }
    }
    
    //Private functions
    
    private String bookToString() {
        String s = this.id + " BOOK ";
        s += this.name;
        s += " ";
        s += this.author;
        s += " - ";
        s += this.isbn;
        s += " ";
        s += this.description;
        return s;
    }
    
    private String articleToString() {
        return this.name +" "+this.author+" " + this.description +" "+this.url;
    }
    
    private String blogpostToString() {
        return this.name + " " + this.author + " " + this.description + " " + this.url;
    }
}
