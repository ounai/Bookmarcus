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
import database.bookmark.Type;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A DAO implementation for a sqlite database of bookmarks.
 *
 * @author WebCoodi
 */
public class BookmarkDAO implements DatabaseDAO<Bookmark> {

    private final static String DEFAULT_DATABASE_PATH = "sql/db/Bookmarcus.db";

    private String dbPath;

    /**
     * A DAO with a database in the default path.
     */
    public BookmarkDAO() {
        this(DEFAULT_DATABASE_PATH);
    }

    /**
     * A DAO with a database in the given path.
     */
    public BookmarkDAO(String path) {
        this.dbPath = path;
    }

    /**
     * Finds a bookmark with the given id.
     * 
     * @param id the id of the bookmark to find
     * 
     * @return a bookmark with the id given
     */
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
    
    /**
     * Find bookmarks by a given author.
     * 
     * @param author the author to find bookmarks with
     * 
     * @return a list of bookmarks with the given author
     */
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

    /**
     * Find bookmarks with a given type.
     * 
     * See {@link database.bookmark.Type} for the possible types and their names.
     * 
     * @param type the name of the type to find bookmarks by
     * 
     * @return a list of bookmarks with the given type
     */
    @Override
    public List<Bookmark> findByType(String type) {
        ArrayList<Bookmark> bookmarks = new ArrayList<>();
        String sql = "SELECT id, name, description, author, isbn, url, type, read FROM bookmark WHERE type = ?;";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Type.getFromString(type, Type.BOOK).toInt());
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

    /**
     * Search the comments of all bookmarks for a string.
     * 
     * @param comment the string to search for
     * 
     * @return a list of bookmarks whose comments match the given string
     */
    @Override
    public List<Bookmark> searchWithComment(String comment) {
        ArrayList<Bookmark> bookmarks = new ArrayList<>();
        String sql = "SELECT id, name, description, author, isbn, url, type, read FROM bookmark WHERE description LIKE '%" + comment + "%';";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

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

    /**
     * Returns all bookmarks in the database.
     * 
     * @return a list containing every bookmark in the database
     */
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

    /**
     * Returns all unread bookmarks in the database.
     * 
     * @return a list containing all bookmarks with the status of having not been read
     */
    @Override
    public List<Bookmark> getAllUnRead() {
        ArrayList<Bookmark> bookmarks = new ArrayList<>();
        String sql = "SELECT id, name, description, author, isbn, url, type, read FROM bookmark WHERE read='';";

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

    /**
     * Returns all read bookmarks in the database.
     * 
     * @return a list containing all bookmarks with the status of having been read
     */
    @Override
    public List<Bookmark> getAllRead() {
        ArrayList<Bookmark> bookmarks = new ArrayList<>();
        String sql = "SELECT id, name, description, author, isbn, url, type, read FROM bookmark WHERE read !='';";

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
        bookmark.setRead(rs.getString("read"));
        return bookmark;
    }

    /**
     * Deletes a bookmark by its ID.
     * 
     * @param id the ID of the bookmark
     * 
     * @return true if the bookmark was found and deleted
     */
    @Override
    public boolean delete(int id) {
        if (notFound(id)) {
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

    /**
     * Marks a bookmark as read.
     * 
     * @param id the ID of the bookmark
     * 
     * @return true if the bookmark was found and marked as read
     */
    @Override
    public boolean markAsRead(int id) {
        if (notFound(id)) {
            return false;
        }

        String sql = "UPDATE bookmark SET read=? WHERE id=?;";
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String timeStampString = dateTimeFormat.format(timestamp);
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, timeStampString);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        return true;
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

    /**
     * Adds a bookmark to the database.
     * 
     * @param bookmark the bookmark to save into the database
     * 
     * @return true if the bookmark was saved into the database
     */
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
            pstmt.setString(7, bookmark.getRead());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
    
    /**
     * Updates a bookmark with the given ID with the data of the given bookmark.
     * 
     * @param id the ID of the bookmark to update
     * @param bookmark the object whose data to save
     * 
     * @return true if the bookmark was saved
     */
    @Override
    public boolean update(int id, Bookmark bookmark) {
        if (notFound(id)) {
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
            pstmt.setString(7, bookmark.getRead());
            pstmt.setInt(8, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        
        return true;
    }

    private boolean notFound(int id) {
        return find(id) == null;
    }
}
