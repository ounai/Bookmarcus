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
 * Database entry container for Bookmark table
 *
 * @author WebCoodi
 */
public abstract class Bookmark {

    private final static int MISSING_ID_PLACEHOLDER = -1;
    private final static String MISSING_NAME = "n/a";

    // Default fields
    private int id;
    private String name;
    private String description;
    private boolean read;
    
    // Optional fields
    private String author;
    private ISBN isbn;
    private String url;
    
    // Constructors
    
    protected Bookmark() {
        this(MISSING_ID_PLACEHOLDER, MISSING_NAME);
    }
    
    protected Bookmark(int id, String name) {
        this.id = id;
        this.name = name;
        this.description = "";
        this.read = false;
        
        this.author = "";
        this.isbn = new ISBN();
        this.url = "";
    }
    
    
    // Setters and getters
    
    public abstract Type getType();

    public void setName(String name) {
        this.name = (name.isEmpty()) ? MISSING_NAME : name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setRead(boolean read) {
        this.read = read;
    }
    
    public void setRead(int read) {
        this.read = (read != 0);
    }
    
    public int getRead() {
        return (read) ? 1 : 0;
    }
    
    public boolean isRead() {
        return read;
    }
    
    public void setID(int id) {
        this.id = id;
    }
    
    public int getID() {
        return id;
    }
    
    
    // Default functionality for optional fields
    
    public abstract boolean hasAuthor();
    
    public void setAuthor(String author) {
        if (hasAuthor()) {
            this.author = author;
        } else {
            throw new UnsupportedOperationException();
        }
    }
    
    public String getAuthor() {
        return author;
    }
    
    public abstract boolean hasISBN();
    
    public boolean setISBN(String isbn) {
        if (!hasISBN()) {
            throw new UnsupportedOperationException();
        }
        return this.isbn.setISBN(isbn);
    }
    

    
    public String getISBN() {
        return isbn.getIsbn();
    }
    
    public abstract boolean hasURL();
    
    public void setURL(String url) {
        if (hasURL()) {
            this.url = url;
        } else {
            throw new UnsupportedOperationException();
        }
    }
    
    public String getURL() {
        return url;
    }
    
    @Override
    public String toString() {
        return id + " " + getType() + " " + getName() + " " + ((read) ? "(luettu)" : "(lukematta)");
    }

}
