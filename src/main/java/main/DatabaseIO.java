/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;

/**
 *
 * @author daniel
 */
public interface DatabaseIO {
    public Bookmark find(int id);
    public ArrayList<Bookmark> getAll();
    public boolean delete(int id);
    public boolean delete(Bookmark bookmark);
}
