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
import java.util.List;

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
            io.print("Valitse komento:");
            io.print("1) Listaa vinkit",
                    "2) Uusi vinkki",
                    "3) Poista vinkki",
                    "4) Listaa lukemattomat vinkit",
                    "5) Listaa luetut vinkit",
                    "6) Merkitse vinkki luetuksi",
                    "7) Etsi vinkkejä tekijän mukaan",
                    "8) Muokkaa vinkkiä",
                    "9) POISTU");
            io.print("--------------------");

            switch (io.nextLine().toLowerCase()) {
                case "1":
                    List<Bookmark> bookmarks = bdao.getAll();
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
                    io.print("Syötä luetuksi merkattavan vinkin numero: ");
                    int readId = Integer.parseInt(io.nextLine()); // virheenhallinta puuttuu!
                    boolean readSuccess = bdao.markAsRead(readId);
                    if(readSuccess) {
                        io.print("Vinkki on merkitty luetuksi.");
                    } else {
                        io.print("! - Syötä oikea vinkin numero.");
                    }
                    break;
                case "7":
                    io.print("Syötä haettavan tekijän nimi: ");
                    String author = io.nextLine();
                    List<Bookmark> resultBookmarks = bdao.findByAuthor(author);
                    if (!resultBookmarks.isEmpty()) {
                        for (Bookmark bm : resultBookmarks) {
                            io.print(bm.toString());
                        }
                    } else {
                        io.print("Tekijällä \"" + author + "\" ei löytynyt yhtään vinkkiä.");
                    }
                    break;
                case "8":
                    io.print("Syötä muokattavan vinkin numero: ");
                    int idToEdit = Integer.parseInt(io.nextLine()); // virheenhallinta puuttuu!
                    Bookmark bookmarkToEdit = bdao.find(idToEdit);
                    
                    if (bookmarkToEdit != null) {
                        edit(bookmarkToEdit);
                    } else {
                        io.print("! - Syötä oikea vinkin numero.");
                    }
                    
                    break;
                case "9": case POISTU_COMMAND:
                    break WHILE;
                default:
                    io.print("Tuntematon komento");
            }
            io.print("");
        }
    }
    
    private void edit(Bookmark bookmarkToEdit) {
        io.print(bookmarkToEdit.toString());
        
        io.print("Valitse muokattava kenttä:");

        io.print("1) nimi");
        io.print("2) kuvaus");
        io.print("3) url");
        if (bookmarkToEdit.hasAuthor()) {
            io.print("4) tekijä");

            // This is nested here because if the bookmark has an ISBN, it also has an author
            if (bookmarkToEdit.hasISBN()) {
                io.print("5) ISBN");
            }
        }

        int fieldToEdit = Integer.parseInt(io.nextLine()); // virheenhallinta puuttuu!
        
        io.print("Anna uusi arvo: ");
        String newValue = io.nextLine();

        switch (fieldToEdit) {
            case 1:
                bookmarkToEdit.setName(newValue);
                break;
            case 2:
                bookmarkToEdit.setDescription(newValue);
                break;
            case 3:
                bookmarkToEdit.setUrl(newValue);
                break;
            case 4:
                if (bookmarkToEdit.hasAuthor()) {
                    bookmarkToEdit.setAuthor(newValue);
                    break;
                } else {
                    io.print("! - Syötä oikea kentän numero.");
                    edit(bookmarkToEdit); // Maybe want to change this to a while loop
                    return;
                }
            case 5:
                if (bookmarkToEdit.hasISBN()) {
                    bookmarkToEdit.setIsbn(newValue);
                    break;
                } else {
                    io.print("! - Syötä oikea kentän numero.");
                    edit(bookmarkToEdit); // Maybe want to change this to a while loop
                    return;
                }
            default:
                io.print("! - Syötä oikea kentän numero.");
                edit(bookmarkToEdit); // Maybe want to change this to a while loop
                return;
        }
        
        if (bdao.update(bookmarkToEdit.getId(), bookmarkToEdit)) {
            io.print("Muokkaaminen onnistui!");
        } else {
            io.print("! - Muokkaaminen epäonnistui.");
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
