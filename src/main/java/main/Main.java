
package main;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        BookmarkIO bio = new BookmarkIO();

        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.println("Valitsee komento: ");
            System.out.println("1) Listaa vinkit");
            System.out.println("2) Lisää vinkki");
            System.out.println("3) poista vinkki");
            System.out.println("4) POISTU");
            System.out.println("--------------------");
            System.out.print("> ");

            int command = Integer.parseInt(in.nextLine());
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
                String name = in.nextLine();
                System.out.println("Syötä vinkin sisältö: ");
                String description = in.nextLine();
                Bookmark newBookark = new Bookmark(name, description);
                bio.add(newBookark);
            } else if(command == 3) {
                System.out.print("Syötä poistettavan vinkin numero: ");
                int id = Integer.parseInt(in.nextLine());
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
