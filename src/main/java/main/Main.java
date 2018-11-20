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
package main;

import database.Bookmark;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import database.BookmarkDAO;
import io.IO;
import io.ScannerIO;

public class Main {

    public static void main(String[] args) {
        BookmarkDAO bio = new BookmarkDAO();

        IO io = new ScannerIO();
        while(true) {
            System.out.println("Valitsee komento: ");
            System.out.println("1) Listaa vinkit");
            System.out.println("2) Lisää vinkki");
            System.out.println("3) poista vinkki");
            System.out.println("4) POISTU");
            System.out.println("--------------------");
            System.out.print("> ");

            int command = Integer.parseInt(io.nextLine());
            if(command == 1) {
                ArrayList<Bookmark> bookmarks = bio.getAll();
                for (Bookmark bm : bookmarks) {
                    System.out.println("");
                    System.out.println("\u001B[31m" + bm.getId() + "\u001B[0m"
                            + "  " + "\u001B[32m" + bm.getName() + "\u001B[0m"
                            + "   " + bm.getDescription());
                }
            } else if(command == 2) {
                System.out.print("Anna vinkille nimi: ");
                String name = io.nextLine();
                System.out.println("Syötä vinkin sisältö: ");
                String description = io.nextLine();
                Bookmark newBookark = new Bookmark(name, description);
                bio.add(newBookark);
            } else if(command == 3) {
                System.out.print("Syötä poistettavan vinkin numero: ");
                int id = Integer.parseInt(io.nextLine());
                boolean success = bio.delete(id);
                if(success) {
                    System.out.println("Vinkki on poistettu.");
                } else {
                    System.out.println("! - Syötä oikea vinkin numero.");
                }
            } else if(command == 4) {
                break;
            } else {
                System.out.println("Tuntematon komento");
            }
            System.out.println("");
        }
        io.close();
    }

    public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:sql/db/Bookmarcus.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
class SelectApp {
 
    /**
     * Connect to the test.db database
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:sql/db/Bookmarcus.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
 
    
    /**
     * select all rows in the bookmark table
     */
    public void selectAll(){
        String sql = "SELECT id, name FROM bookmark";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" + 
                                   rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
