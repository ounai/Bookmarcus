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
package database;

import database.bookmark.Bookmark;
import database.bookmark.BookmarkFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
        String sql = "SELECT id, name, description, author, isbn, url, type, read FROM bookmark WHERE id = ?;";
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                bookmark = collectNextBookmark(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return bookmark;
    }
    
    @Override
    public List<Bookmark> findByAuthor(String author) {
        ArrayList<Bookmark> bookmarks = new ArrayList<>();
        String sql = "SELECT id, name, description, author, isbn, url, type, read FROM bookmark WHERE author = ?;";
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, author);
            ResultSet rs = pstmt.executeQuery();
            
            // loop through the result set
            while (rs.next()) {
                bookmarks.add(collectNextBookmark(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return bookmarks;
    }

    @Override
    public List<Bookmark> getAll() {
        ArrayList<Bookmark> bookmarks = new ArrayList<>();
        String sql = "SELECT id, name, description, author, isbn, url, type, read FROM bookmark;";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                bookmarks.add(collectNextBookmark(rs));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bookmarks;
    }

    @Override
    public List<Bookmark> getAllUnRead() {
        ArrayList<Bookmark> bookmarks = new ArrayList<>();
        String sql = "SELECT id, name, description, author, isbn, url, type, read FROM bookmark WHERE read=0;";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                bookmarks.add(collectNextBookmark(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bookmarks;
    }

    @Override
    public List<Bookmark> getAllRead() {
        ArrayList<Bookmark> bookmarks = new ArrayList<>();
        String sql = "SELECT id, name, description, author, isbn, url, type, read FROM bookmark WHERE read=1;";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                bookmarks.add(collectNextBookmark(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bookmarks;
    }
    
    private Bookmark collectNextBookmark(ResultSet rs) throws SQLException {
        Bookmark bookmark = BookmarkFactory.newBookmarkByType(rs.getInt("type"));
        bookmark.setID(rs.getInt("id"));
        bookmark.setName(rs.getString("name"));
        bookmark.setDescription(rs.getString("description"));
        if (bookmark.hasAuthor()) {
            bookmark.setAuthor(rs.getString("author"));
        }
        if (bookmark.hasISBN()) {
            bookmark.setISBN(rs.getString("isbn"));
        }
        if (bookmark.hasURL()) {
            bookmark.setURL(rs.getString("url"));
        }
        bookmark.setRead(rs.getInt("read"));
        return bookmark;
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
        if (bookmark.getName() == null || bookmark.getName().equals("n/a")) {
            return false;
        }

        String sql = "INSERT INTO bookmark (name, description, author, isbn, url, type, read) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookmark.getName());
            pstmt.setString(2, bookmark.getDescription());
            pstmt.setString(3, bookmark.getAuthor());
            pstmt.setString(4, bookmark.getISBN());
            pstmt.setString(5, bookmark.getURL());
            pstmt.setInt(6, bookmark.getType().toInt());
            pstmt.setInt(7, bookmark.getRead());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
    
    @Override
    public boolean update(int id, Bookmark bookmark) {
        if (find(id) == null) {
            return false;
        }
        
        String sql = "UPDATE Bookmark SET name = ?, description = ?, author = ?, isbn = ?, url = ?, type = ?, read = ? WHERE id = ?;";
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, bookmark.getName());
            pstmt.setString(2, bookmark.getDescription());
            pstmt.setString(3, bookmark.getAuthor());
            pstmt.setString(4, bookmark.getISBN());
            pstmt.setString(5, bookmark.getURL());
            pstmt.setInt(6, bookmark.getType().toInt());
            pstmt.setInt(7, bookmark.getRead());
            pstmt.setInt(8, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        
        return true;
    }

}
