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
package bookmarcus;

import database.bookmark.Bookmark;
import database.DatabaseDAO;
import io.IO;
import java.util.List;

/**
 *
 * @author WebCoodi
 */
public class Bookmarcus {

    // These are used for consistent calling when numbers may change
    public final static String UUSI_COMMAND = "uusi";
    public final static String POISTU_COMMAND = "poistu";
    public final static String MUOKKAA_COMMAND = "muokkaa";

    private final DatabaseDAO<Bookmark> bdao;
    private final IO io;

    public Bookmarcus(DatabaseDAO<Bookmark> bdao, IO io) {
        this.bdao = bdao;
        this.io = io;
    }
    
    public void consoleApp() {
        WHILE:
        while(io.hasNextLine()) {
            io.print("Valitse komento:");
            io.print("1) Listaa vinkit",
                    "2) Uusi vinkki",
                    "3) Poista vinkki",
                    "4) Listaa lukemattomat vinkit",
                    "5) Listaa luetut vinkit",
                    "6) Merkitse vinkki luetuksi tai katsotuksi",
                    "7) Etsi vinkkejä tekijän mukaan",
                    "8) Muokkaa vinkkiä",
                    "9) Lisää vinkkiin muistiinpano",
                    "10) Etsi vinkkejä tyypin mukaan",
                    "11) Etsi vinkkejä joilla on vastaava kommentti",
                    "0) POISTU");
            io.print("--------------------");

            switch (io.nextLine().toLowerCase()) {
                case "1":
                    List<Bookmark> bookmarks = bdao.getAll();
                    for (Bookmark bm : bookmarks) {
                        io.print(bm.toString());
                    }
                    break;
                case "2": case UUSI_COMMAND:
                    new NewBookmark(io, bdao).run();
                    break;
                case "3":
                    new Remove(io, bdao).run();
                    break;
                case "4":
                    List<Bookmark> unRead = bdao.getAllUnRead();
                    for (Bookmark bm : unRead) {
                        io.print(bm.toString());
                    }
                    break;
                case "5":
                    List<Bookmark> read = bdao.getAllRead();
                    for (Bookmark bm : read) {
                        io.print(bm.toString());
                    }
                    break;
                case "6":
                    new SetRead(io, bdao).run();
                    break;
                case "7":
                    new ListAllByAuthor(io, bdao).run();
                    break;
                case "8": case MUOKKAA_COMMAND:
                    new Edit(io, bdao).run();
                    break;
                case "9":
                    new AddNote(io, bdao).run();
                    break;
                case "10":
                    new ListAllOfType(io, bdao).run();
                    break;
                case "11":
                    io.print("Syötä haettava kommentti: ");
                    String comment = io.nextLine();
                    List<Bookmark> matchingBookmarks = bdao.searchWithComment(comment);
                    if (!matchingBookmarks.isEmpty()) {
                        for (Bookmark bm : matchingBookmarks) {
                            io.print(bm.toString());
                        }
                    } else {
                        io.print("Yhdelläkään vinkillä ei ollut kommenttia: '" + comment + "'");
                    }
                    break;
                case "0": case POISTU_COMMAND:
                    break WHILE;
                default:
                    io.print("Tuntematon komento");
            }
            io.print("");
        }
    }
    
}
