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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
    private String read;
    
    // Optional fields
    private String author;
    private ISBN isbn;
    private String url;
    
    // Constructors
    
    /**
     * A constructor for a placeholder bookmark, that cannot be saved into the database before being given its mandatory fields.
     */
    protected Bookmark() {
        this(MISSING_ID_PLACEHOLDER, MISSING_NAME);
    }
    
    /**
     * A constuctor for a bookmark that gives it its id and name, making it possible to save it into a database.
     */
    protected Bookmark(int id, String name) {
        this.id = id;
        this.name = name;

        this.description = "";
        this.read = "";
        
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
    
    /**
     * Sets the state of having been read.
     * 
     * When being called with read: true, it also sets the timestamp to the current time.
     * When being called with read: false, it clears any timestamp that had been previously saved.
     */
    public void setRead(boolean read) {
        if(read) {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String timeStampString = dateTimeFormat.format(timestamp);
            this.read = timeStampString;
        } else {
            this.read = "";
        }
    }
    
    public void setRead(String read) {
        this.read = read;
    }

    public String getRead() {
        return this.read;
    }
    
    public boolean isRead() {
        return !read.equals("");
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
        if (hasISBN()) {
            return this.isbn.setISBN(isbn);
        } else {
            throw new UnsupportedOperationException();
        }
    }
    
    public String getISBN() {
        return isbn.getIsbn();
    }
    
    public abstract boolean hasURL();
    
    public void setURL(String url) {
        if (hasURL()) {
            if (url != null) {
                this.url = url;
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }
    
    public String getURL() {
        return url;
    }
    
    /**
     * Returns a formatted text representation of the bookmark, that can be used in graphical interfaces.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------------------------------\n");
        sb.append(id).append(": ").append(getType()).append(" | ");
        sb.append(getName()).append(" | ");
        sb.append((isRead()) ? "luettu: " + getRead().substring(0,getRead().length()-3) : "(lukematta)").append("\n");
        if (hasURL() && !url.isEmpty()) {
            sb.append("  URL:  ").append(getURL()).append('\n');
        }
        if (hasISBN()) {
            sb.append("  ISBN: ").append(getISBN()).append('\n');
        }
        if (!description.isEmpty()) {
            sb.append("   ").append(description).append('\n');
        }
        sb.append("---------------------------------------------------------");
        return sb.toString();
    }

}
