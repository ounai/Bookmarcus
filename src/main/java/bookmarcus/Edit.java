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

import database.DatabaseDAO;
import database.bookmark.Bookmark;
import io.IO;

/**
 *
 * @author WebCoodi
 */
public class Edit implements Command {

    private final IO io;
    private final DatabaseDAO<Bookmark> bdao;

    public Edit(IO io, DatabaseDAO<Bookmark> bdao) {
        this.io = io;
        this.bdao = bdao;
    }
    
    @Override
    public void run() {
        io.print("Syötä muokattavan vinkin numero: ");
        int idToEdit = Integer.parseInt(io.nextLine()); // virheenhallinta puuttuu!
        Bookmark bookmarkToEdit = bdao.find(idToEdit);

        if (bookmarkToEdit != null) {
            edit(bookmarkToEdit);
        } else {
            io.print("! - Syötä oikea vinkin numero.");
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

        int fieldToEdit;
        String oldValue;

        CHOOSE_FIELD_TO_EDIT:
        while (true) {
            fieldToEdit = Integer.parseInt(io.nextLine()); // virheenhallinta puuttuu!
            oldValue = "";
            
            switch (fieldToEdit) {
                case 1:
                    oldValue = bookmarkToEdit.getName();
                    break CHOOSE_FIELD_TO_EDIT;
                case 2:
                    oldValue = bookmarkToEdit.getDescription();
                    break CHOOSE_FIELD_TO_EDIT;
                case 3:
                    oldValue = bookmarkToEdit.getURL();
                    break CHOOSE_FIELD_TO_EDIT;
                case 4:
                    if (bookmarkToEdit.hasAuthor()) {
                        oldValue = bookmarkToEdit.getAuthor();
                        break CHOOSE_FIELD_TO_EDIT;
                    } else {
                        io.print("! - Syötä oikea kentän numero.");
                        break;
                    }
                case 5:
                    if (bookmarkToEdit.hasISBN()) {
                        oldValue = bookmarkToEdit.getISBN();
                        break CHOOSE_FIELD_TO_EDIT;
                    } else {
                        io.print("! - Syötä oikea kentän numero.");
                        break;
                    }
                default:
                    io.print("! - Syötä oikea kentän numero.");
                    break;
            }
        }
                
        io.print("Vanha arvo: \"" + oldValue + "\"");

        NEW_VALUE:
        while (true) {
            io.print("Anna uusi arvo: ");
            String newValue = io.nextLine();

            switch (fieldToEdit) {
                case 1:
                    bookmarkToEdit.setName(newValue);
                    break NEW_VALUE;
                case 2:
                    bookmarkToEdit.setDescription(newValue);
                    break NEW_VALUE;
                case 3:
                    bookmarkToEdit.setURL(newValue);
                    break NEW_VALUE;
                case 4:
                    bookmarkToEdit.setAuthor(newValue);
                    break NEW_VALUE;
                case 5:
                    if (bookmarkToEdit.setISBN(newValue)) { 
                        break NEW_VALUE;
                    } else {
                        io.print("! - ISBN-tunnus ei kelpaa.");
                        break;
                    }
            }
        }
        
        if (bdao.update(bookmarkToEdit.getID(), bookmarkToEdit)) {
            io.print("Muokkaaminen onnistui!");
        } else {
            io.print("! - Muokkaaminen epäonnistui.");
        }
    }
    
}
