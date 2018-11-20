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
public interface DatabaseIO<T> {
    public Bookmark find(int id);
    public ArrayList<Bookmark> getAll();
    public boolean delete(int id);
    public boolean delete(T t);
    public boolean add(T t);
}
