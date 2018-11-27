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
import database.DatabaseDAO;
import io.IO;
import java.util.ArrayList;

/**
 *
 * @author WebCoodi
 */
public class Bookmarcus {

    // These are used for consistent calling when numbers may change
    public final static String UUSI_COMMAND = "uusi";
    public final static String POISTU_COMMAND = "poistu";

    private final DatabaseDAO<Bookmark> bdao;
    private final IO io;

    public Bookmarcus(DatabaseDAO<Bookmark> bdao, IO io) {
        this.bdao = bdao;
        this.io = io;
    }
    
    public void consoleApp() {
        WHILE:
        while(io.hasNextLine()) {
            io.print("Valitsee komento:");
            io.print("1) Listaa vinkit", "2) Uusi vinkki", "3) Poista vinkki", "4) Listaa lukemattomat vinkit", "5) Listaa luetut vinkit", "6) Merkitse vinkki luetuksi", "7) POISTU");
            io.print("--------------------");

            switch (io.nextLine().toLowerCase()) {
                case "1":
                    ArrayList<Bookmark> bookmarks = bdao.getAll();
                    for (Bookmark bm : bookmarks) {
                        io.print(bm.toString());
                    }
                    break;
                case "2": case UUSI_COMMAND:
                    add();
                    break;
                case "3":
                    io.print("Syötä poistettavan vinkin numero: ");
                    int id = Integer.parseInt(io.nextLine()); // virheenhallinta puuttuu!
                    boolean success = bdao.delete(id);
                    if(success) {
                        io.print("Vinkki on poistettu.");
                    } else {
                        io.print("! - Syötä oikea vinkin numero.");
                    }
                    break;
                case "4":
                    ArrayList<Bookmark> unRead = bdao.getAllUnRead();
                    for (Bookmark bm : unRead) {
                        io.print(bm.toString());
                    }
                    break;
                case "5":
                    ArrayList<Bookmark> read = bdao.getAllRead();
                    for (Bookmark bm : read) {
                        io.print(bm.toString());
                    }
                    break;
                case "6":
                    io.print("Syötä luetuksi merkattavan vinkin numero: ");
                    int readId = Integer.parseInt(io.nextLine()); // virheenhallinta puuttuu!
                    boolean readSuccess = bdao.markAsRead(readId);
                    if(readSuccess) {
                        io.print("Vinkki on merkitty luetuksi.");
                    } else {
                        io.print("! - Syötä oikea vinkin numero.");
                    }
                    break;
                case "7": case POISTU_COMMAND:
                    break WHILE;
                default:
                    io.print("Tuntematon komento");
            }
            io.print("");
        }
    }

    private void add() {
        Bookmark bookmark = new Bookmark();
        io.print("Anna vinkin tyyppi. Vaihtoehdot: artikkeli, blogikirjoitus, kirja");
        String type = io.nextLine();
        switch (type.toLowerCase()) {
            case "artikkeli":
                bookmark.setType(Bookmark.TYPE_ARTICLE);
                io.print("Syötä artikkelin nimi:");
                bookmark.setName(io.nextLine());
                io.print("Syötä artikkelin kirjoittaja:");
                bookmark.setAuthor(io.nextLine());
                io.print("Syötä artikkelin osoite:");
                bookmark.setUrl(io.nextLine());
                break;
            case "blogikirjoitus":
                bookmark.setType(Bookmark.TYPE_BLOGPOST);
                io.print("Syötä blogikirjoituksen otsikko:");
                bookmark.setName(io.nextLine());
                io.print("Syötä blogikirjoituksen kirjoittaja:");
                bookmark.setAuthor(io.nextLine());
                io.print("Syötä blogikirjoituksen osoite:");
                bookmark.setUrl(io.nextLine());
                break;
            case "kirja":
                bookmark.setType(Bookmark.TYPE_BOOK);
                io.print("Syötä kirjan nimi:");
                bookmark.setName(io.nextLine());
                io.print("Syötä kirjailijan nimi:");
                bookmark.setAuthor(io.nextLine());
                io.print("Syötä kirjan ISBN-tunnus:");
                bookmark.setIsbn(io.nextLine());
                break;
            default:
                io.print("! - Tuntematon tyyppi");
                return;
        }
        io.print("Lisää vinkkiä koskevat muistiinpanot:");
        bookmark.setDescription(io.nextLine());
        bdao.add(bookmark);
    }
    
}
