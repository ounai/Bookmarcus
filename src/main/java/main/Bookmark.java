/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author daniel
 */
public class Bookmark {

    private String name;
    private int id;
    private String description;

    public Bookmark(String name) {
        this.name = name;
    }

    public Bookmark(String name, String description) {
        this.name = name;
        this.description = description;
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
        return "" + this.id + " " +this.name + " " + this.description;
    }
}
