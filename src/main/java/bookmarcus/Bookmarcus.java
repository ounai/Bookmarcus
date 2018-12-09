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
import database.bookmark.BookmarkFactory;
import database.bookmark.ISBN;
import static database.bookmark.ISBN.validISBN;
import io.IO;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WebCoodi
 */
public class Bookmarcus {

    private class Field {

        private final String externalName;
        private final String internalName;

        public Field(String externalName, String internalName) {
            this.externalName = externalName;
            this.internalName = internalName;
        }

        public String getExternalName() {
            return externalName;
        }

        public String getInternalName() {
            return internalName;
        }
    }

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
        while (io.hasNextLine()) {
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
                    "0) POISTU");
            io.print("--------------------");

            switch (io.nextLine().toLowerCase()) {
                case "1":
                    List<Bookmark> bookmarks = bdao.getAll();
                    for (Bookmark bm : bookmarks) {
                        io.print(bm.toString());
                    }
                    break;
                case "2":
                case UUSI_COMMAND:
                    add();
                    break;
                case "3":
                    io.print("Syötä poistettavan vinkin numero: ");
                    int id = Integer.parseInt(io.nextLine()); // virheenhallinta puuttuu!
                    boolean success = bdao.delete(id);
                    if (success) {
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
                    io.print("Syötä luetuksi tai katsotuksi merkattavan vinkin numero: ");
                    int readId = Integer.parseInt(io.nextLine()); // virheenhallinta puuttuu!
                    boolean readSuccess = bdao.markAsRead(readId);
                    if (readSuccess) {
                        io.print("Vinkki on merkitty luetuksi/katsotuksi.");
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
                case "9":
                    io.print("Syötä vinkin numero: ");
                    int idToAddNote = Integer.parseInt(io.nextLine()); // virheenhallinta puuttuu!
                    Bookmark bookmarkToAddNote = bdao.find(idToAddNote);

                    if (bookmarkToAddNote != null) {
                        addNote(bookmarkToAddNote);
                    } else {
                        io.print("! - Syötä oikea vinkin numero.");
                    }

                    break;
                case "10":
                    io.print("Syötä haettavan tyypin nimi: ");
                    String type = io.nextLine();
                    List<Bookmark> typeResultBookMarks = bdao.findByType(type);
                    if (!typeResultBookMarks.isEmpty()) {
                        for (Bookmark bm : typeResultBookMarks) {
                            io.print(bm.toString());
                        }
                    } else {
                        io.print("Tyypillä \"" + type + "\" ei löytynyt yhtään vinkkiä.");
                    }
                    break;
                case "0":
                case POISTU_COMMAND:
                    break WHILE;
                default:
                    io.print("Tuntematon komento");
            }
            io.print("");
        }
    }

    private void addNote(Bookmark bookmarkToAddNote) {
        io.print(bookmarkToAddNote.toString());

        io.print("Syötä lisättävä muistiinpano: ");

        String note = io.nextLine();

        bookmarkToAddNote.setDescription(bookmarkToAddNote.getDescription() + ", " + note);

        if (bdao.update(bookmarkToAddNote.getID(), bookmarkToAddNote)) {
            io.print("Muistiinpanon lisääminen onnistui!");
        } else {
            io.print("! - Muistiinpanon lisääminen epäonnistui.");
        }
    }

    private void edit(Bookmark bookmarkToEdit) {
        HashMap<Integer, Field> fields = getEditableFields(bookmarkToEdit);
        io.print(bookmarkToEdit.toString());
        io.print("Valitse muokattava kenttä:");
        fields.entrySet().stream()
                .sorted((entry1, entry2) -> entry1.getKey().compareTo(entry2.getKey()))
                .forEach(e -> io.print(e.getKey() + ") " + e.getValue().getExternalName()));

        int fieldToEdit = getFieldNumber(fields);

        String oldValue = getFieldValue(bookmarkToEdit, fieldToEdit);

        io.print("Vanha arvo: \"" + oldValue + "\"");
        io.print("Anna uusi arvo: ");
        String newValue = "";
        while (true) {
            newValue = io.nextLine();
            if (fields.get(fieldToEdit).externalName.equals("ISBN") && !validISBN(newValue)) {
                io.print("! - ISBN-tunnus ei kelpaa.");
            } else {
                break;
            }
        }
        setFieldValue(bookmarkToEdit, fieldToEdit, newValue);

        if (bdao.update(bookmarkToEdit.getID(), bookmarkToEdit)) {
            io.print("Muokkaaminen onnistui!");
        } else {
            io.print("! - Muokkaaminen epäonnistui.");
        }
    }

    private void add() {
        Bookmark bookmark;
        io.print("Syötä vinkin tyyppi. Vaihtoehdot: artikkeli, blogikirjoitus, kirja, video");
        try {
            bookmark = BookmarkFactory.newBookmarkByType(io.nextLine());
        } catch (IllegalArgumentException e) {
            io.print("! - Tuntematon tyyppi");
            return;
        }
        io.print("Syötä vinkille nimi:");
        bookmark.setName(io.nextLine());
        io.print("Syötä vinkin tekijän nimi:");
        bookmark.setAuthor(io.nextLine());
        if (bookmark.hasURL()) {
            io.print("Syötä vinkin osoite:");
            bookmark.setURL(io.nextLine());
        }
        if (bookmark.hasISBN()) {
            String isbn;

            while (true) {
                io.print("Syötä vinkin ISBN-tunnus:");

                isbn = io.nextLine();

                if (bookmark.setISBN(isbn)) {
                    break;
                } else {
                    io.print("! - ISBN-tunnus ei kelpaa, syötä oikea ISBN-tunnus.");
                }
            }

        }
        io.print("Lisää vinkkiä koskevat muistiinpanot:");
        bookmark.setDescription(io.nextLine());
        bdao.add(bookmark);
    }

    private HashMap<Integer, Field> getEditableFields(Bookmark bookmarkToEdit) {
        HashMap<Integer, Field> fields = new HashMap<>();
        fields.put(1, new Field("nimi", "Name"));
        fields.put(2, new Field("kuvaus", "Description"));
        fields.put(3, new Field("url", "URL"));
        if (bookmarkToEdit.hasAuthor()) {
            fields.put(4, new Field("tekija", "Author"));
            if (bookmarkToEdit.hasISBN()) {
                fields.put(5, new Field("ISBN", "ISBN"));
            }
        }
        return fields;
    }

    private int getFieldNumber(HashMap<Integer, Field> fields) {
        int fieldToEdit = 0;
        while (fieldToEdit == 0) {
            try {
                fieldToEdit = Integer.parseInt(io.nextLine());
                if (!fields.containsKey(fieldToEdit)) {
                    throw new IllegalArgumentException();
                }
            } catch (Exception e) {
                io.print("Syötä oikea kentän numero.");
            }
        }
        return fieldToEdit;
    }

    private String getFieldValue(Bookmark bookmark, int fieldNumber) {
        String internalName = getEditableFields(bookmark).get(fieldNumber).internalName;
        String getterName = "get" + internalName;
        String value = "";
        try {
            Method getter = bookmark.getClass().getMethod(getterName);
            value = (String) getter.invoke(bookmark);
        } catch (Exception ex) {
        }
        return value;
    }

    private void setFieldValue(Bookmark bookmark, int fieldNumber, String newValue) {
        String internalName = getEditableFields(bookmark).get(fieldNumber).internalName;
        String setterName = "set" + internalName;
        try {
            Method setter = bookmark.getClass().getMethod(setterName, String.class);
            setter.invoke(bookmark, newValue);
        } catch (Exception ex) {
        }
    }

}
