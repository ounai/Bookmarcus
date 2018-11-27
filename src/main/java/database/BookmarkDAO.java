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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author WebCoodi
 */
public class BookmarkDAO implements DatabaseDAO<Bookmark> {

    private final static String DEFAULT_DATABASE_PATH = "sql/db/Bookmarcus.db";

    private String dbPath;

    public BookmarkDAO() {
        this(DEFAULT_DATABASE_PATH);
    }

    public BookmarkDAO(String path) {
        this.dbPath = path; 
    }

    @Override
    public Bookmark find(int id) {
        Bookmark bookmark = null;
        String sql = "SELECT id, name, description, author, isbn, url, type FROM bookmark WHERE id = ?;";
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            bookmark = new Bookmark(rs.getInt("id"), rs.getString("name"), rs.getString("description"));
            bookmark.setAuthor(rs.getString("author"));
            bookmark.setIsbn(rs.getString("isbn"));
            bookmark.setUrl(rs.getString("url"));
            bookmark.setType(rs.getInt("type"));
            bookmark.setRead(rs.getInt("read"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("error on tässä");
        }
        return bookmark;
    }

    @Override
    public ArrayList<Bookmark> getAll() {
        ArrayList<Bookmark> bookmarks = new ArrayList<>();
        String sql = "SELECT id, name, description, author, isbn, url, type, read FROM bookmark;";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                Bookmark bm = new Bookmark(rs.getInt("id"), rs.getString("name"), rs.getString("description"));
                bm.setAuthor(rs.getString("author"));
                bm.setIsbn(rs.getString("isbn"));
                bm.setUrl(rs.getString("url"));
                bm.setType(rs.getInt("type"));
                bm.setRead(rs.getInt("read"));
                bookmarks.add(bm);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bookmarks;
    }

    @Override
    public ArrayList<Bookmark> getAllUnRead() {
        ArrayList<Bookmark> bookmarks = new ArrayList<>();
        String sql = "SELECT id, name, description, author, isbn, url, type, read FROM bookmark WHERE read=0;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                Bookmark bm = new Bookmark(rs.getInt("id"), rs.getString("name"), rs.getString("description"));
                bm.setAuthor(rs.getString("author"));
                bm.setIsbn(rs.getString("isbn"));
                bm.setUrl(rs.getString("url"));
                bm.setType(rs.getInt("type"));
                bm.setRead(rs.getInt("read"));
                bookmarks.add(bm);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bookmarks;
    }

    @Override
    public ArrayList<Bookmark> getAllRead() {
        ArrayList<Bookmark> bookmarks = new ArrayList<>();
        String sql = "SELECT id, name, description, author, isbn, url, type, read FROM bookmark WHERE read=1;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                Bookmark bm = new Bookmark(rs.getInt("id"), rs.getString("name"), rs.getString("description"));
                bm.setAuthor(rs.getString("author"));
                bm.setIsbn(rs.getString("isbn"));
                bm.setUrl(rs.getString("url"));
                bm.setType(rs.getInt("type"));
                bm.setRead(rs.getInt("read"));
                bookmarks.add(bm);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bookmarks;
    }

    @Override
    public boolean delete(int id) {
        if (find(id) == null) {
            return false;
        }

        String sql = "DELETE FROM bookmark WHERE id = ?;";
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean markAsRead(int id) {
        if (find(id) == null) {
            return false;
        }

        String sql = "UPDATE bookmark SET read=1 WHERE id=?;";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Bookmark bookmark) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Connection connect() throws SQLException {
        // SQLite connection string
        String url = "jdbc:sqlite:" + this.dbPath;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return conn;
    }

    @Override
    public boolean add(Bookmark bookmark) {
        if (bookmark.getName() == null) {
            return false;
        }

        String sql = "INSERT INTO bookmark (name, description, author, isbn, url, type, read) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookmark.getName());
            pstmt.setString(2, bookmark.getDescription());
            pstmt.setString(3, bookmark.getAuthor());
            pstmt.setString(4, bookmark.getIsbn());
            pstmt.setString(5, bookmark.getUrl());
            pstmt.setInt(6, bookmark.getType());
            pstmt.setInt(7, bookmark.getRead());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

}
