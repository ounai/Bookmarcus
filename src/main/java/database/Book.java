/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author daniel
 */
public class Book extends Bookmark {
    
    private String author;
    private String isbn;
    
    public Book(String name) {
        super(name);
    }
    public Book(String name, String author) {
        super(name);
        this.author = author;
    }
    public Book (String name, String author, String isbn) {
        super(name);
        this.author = author;
        this.isbn = isbn;
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
    
}
