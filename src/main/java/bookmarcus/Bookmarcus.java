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
package bookmarcus;

import database.Bookmark;
import database.BookmarkDAO;
import io.IO;
import java.util.ArrayList;

/**
 *
 * @author WebCoodi
 */
public class Bookmarcus {
    private final BookmarkDAO bdao;
    
    public Bookmarcus() {
        this.bdao = new BookmarkDAO();
    }
    
    public void consoleApp(IO io) {
        while(true) {
            io.print("Valitsee komento:");
            io.print("1) Listaa vinkit", "2) Lisää vinkki", "3) poista vinkki", "4) POISTU");
            io.print("--------------------");

            int command = Integer.parseInt(io.nextLine());
            if(command == 1) {
                ArrayList<Bookmark> bookmarks = bdao.getAll();
                for (Bookmark bm : bookmarks) {
                    io.print(bm.toString());
                }
            } else if(command == 2) {
                io.print("Anna vinkille nimi: ");
                String name = io.nextLine();
                io.print("Syötä vinkin sisältö: ");
                String description = io.nextLine();
                Bookmark newBookark = new Bookmark(name, description);
                bdao.add(newBookark);
            } else if(command == 3) {
                io.print("Syötä poistettavan vinkin numero: ");
                int id = Integer.parseInt(io.nextLine());
                boolean success = bdao.delete(id);
                if(success) {
                    io.print("Vinkki on poistettu.");
                } else {
                    io.print("! - Syötä oikea vinkin numero.");
                }
            } else if(command == 4) {
                break;
            } else {
                io.print("Tuntematon komento");
            }
            io.print("");
        }
    }
    
}
