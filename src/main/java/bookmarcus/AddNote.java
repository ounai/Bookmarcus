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

import bookmarcus.Command;
import database.DatabaseDAO;
import database.bookmark.Bookmark;
import io.IO;

/**
 * A command for adding a note to a bookmark.
 *
 * @author WebCoodi
 */
public class AddNote implements Command {

    private final IO io;
    private final DatabaseDAO<Bookmark> bdao;

    public AddNote(IO io, DatabaseDAO<Bookmark> bdao) {
        this.io = io;
        this.bdao = bdao;
    }
    
    /**
     * Asks the user for the ID of a bookmark, then starts the process of adding a note to it.
     */
    @Override
    public void run() {
        io.print("Syötä vinkin numero: ");

        int idToAddNote;

        try {
            idToAddNote = Integer.parseInt(io.nextLine());
        } catch(NumberFormatException e) {
            io.print("! - Syötä oikea vinkin numero.");

            return;
        }

        Bookmark bookmarkToAddNote = bdao.find(idToAddNote);

        if (bookmarkToAddNote != null) {
            addNote(bookmarkToAddNote);
        } else {
            io.print("! - Syötä oikea vinkin numero.");
        }
    }
    
    private void addNote(Bookmark bookmarkToAddNote) {
        io.print(bookmarkToAddNote.toString());
        
        io.print("Syötä lisättävä muistiinpano: ");
        
        String note = io.nextLine();
        
        bookmarkToAddNote.setDescription(bookmarkToAddNote.getDescription() + ", " + note);
        
        if (bdao.update(bookmarkToAddNote.getID(), bookmarkToAddNote)) {
            io.print("Muitiinpanon lisääminen onnistui!");
        } else {
            io.print("! - Muistiinpanon lisääminen epäonnistui.");
        }
    }
    
}
