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

    public final static int TYPE_BOOK = 1;
    public final static int TYPE_ARTICLE = 2;
    public final static int TYPE_BLOGPOST = 3;

    private String name;
    private int id;
    private String description;
    private String url;
    private int type; // 1 = Book, 2 = Article, 3 = Blogpost
    private boolean read;

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

    public Bookmark() {
        this.id = MISSING_ID_PLACEHOLDER;
        this.read = false;
    }

    public Bookmark(String name) {
        this(MISSING_ID_PLACEHOLDER, name, EMPTY_DESCRIPTION);
        this.read = false;
    }

    public Bookmark(String name, String description) {
        this(MISSING_ID_PLACEHOLDER, name, description);
        this.read = false;
    }

    public Bookmark(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.read = false;
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

    public boolean isRead() {
        return this.read;
    }

    public void setRead(int read) {
        if(read == 1) {
            this.read = true;
        } else {
            this.read = false;
        }
    }

    public int getRead() {
        if(this.read) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        switch (this.type) {
            case TYPE_BOOK:
                return bookToString();
            case TYPE_ARTICLE:
                return articleToString();
            case TYPE_BLOGPOST:
                return blogpostToString();
            default:
                return "Bookmark type not defined";
        }
    }

    //Private functions
    private String bookToString() {
        return this.id + " BOOK " + this.name + " " + this.author + " - " + this.isbn + " " + this.description
                + (this.isRead() ? "(luettu)" : "(lukematon)");
    }

    private String articleToString() {
        return this.id + " ARTICLE " + this.name + " " + this.author + " " + this.description + " " + this.url
                + (this.isRead() ? "(luettu)" : "(lukematon)");
    }

    private String blogpostToString() {
        return this.id + " BLOGPOST " + this.name + " " + this.author + " " + this.description + " " + this.url
                + (this.isRead() ? "(luettu)" : "(lukematon)");
    }
}
