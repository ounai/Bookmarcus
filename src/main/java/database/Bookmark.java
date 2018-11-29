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

    public enum contentType {
        BOOK,
        ARTICLE,
        BLOGPOST,
        VIDEO
    }

    public final static int READ_TRUE = 1;
    public final static int READ_FALSE = 0;

    private String name;
    private int id;
    private String description;
    private String url;
    private contentType type; // 1 = Book, 2 = Article, 3 = Blogpost
    private boolean read;

    // Book, Article, Blogpost type variables
    private String author;

    //Book type variables
    private String isbn;

    public boolean hasAuthor() {
        return type == contentType.BOOK || type == contentType.ARTICLE || type == contentType.BLOGPOST || type == contentType.VIDEO;
    }

    public boolean hasISBN() {
        return contentType.BOOK.equals(type);
    }

    public int getType() {
        return type.ordinal();
    }

    public void setType(int type) {
        this.type = contentType.values()[type];
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

    public void setId(int id) {
        this.id = id;
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
        this.read = (read == READ_TRUE);
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
        switch (type) {
            case BOOK:
                return bookToString();
            case ARTICLE:
                return articleToString();
            case BLOGPOST:
                return blogpostToString();
            case VIDEO:
                return videoToString();
            default:
                return "Bookmark type not defined";
        }
    }

    //Private functions
    private String bookToString() {
        return this.id + " BOOK " + this.name + " " + this.author + " - " + this.isbn + " " + this.description
                + " "+(this.isRead() ? "(luettu)" : "(lukematon)");
    }

    private String articleToString() {
        return this.id + " ARTICLE " + this.name + " " + this.author + " " + this.description + " " + this.url
                + " " + (this.isRead() ? "(luettu)" : "(lukematon)");
    }

    private String blogpostToString() {
        return this.id + " BLOGPOST " + this.name + " " + this.author + " " + this.description + " " + this.url
                + " "+(this.isRead() ? "(luettu)" : "(lukematon)");
    }

    private String videoToString() {
        return this.id + " VIDEO " + this.name + " " + this.author + " " + this.description + " " + this.url
                + " " + (this.isRead() ? "(katsottu)" : "(katsomaton)");
    }
}
