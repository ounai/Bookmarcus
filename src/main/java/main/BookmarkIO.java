/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel
 */
public class BookmarkIO implements DatabaseIO {
    private String dbPath;
    
    public BookmarkIO() {
        this.dbPath ="sql/db/Bookmarcus.db";
    }
    public BookmarkIO(String path) {
        this.dbPath = path;
    }

    @Override
    public Bookmark find(int id) {
        Bookmark bookmark = null;
        String sql = "SELECT id, name, description FROM bookmark WHERE id = ?";
        try(Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            
            ResultSet rs = pstmt.executeQuery();
            bookmark = new Bookmark(rs.getInt("id"), rs.getString("name"), rs.getString("description"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bookmark;
    }

    @Override
    public ArrayList<Bookmark> getAll() {
        ArrayList<Bookmark> bookmarks = new ArrayList();
        String sql = "SELECT id, name, description FROM bookmark";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                bookmarks.add(new Bookmark(rs.getInt("id"), rs.getString("name"), rs.getString("description")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bookmarks;
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Bookmark bookmark) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:" + this.dbPath;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

}
