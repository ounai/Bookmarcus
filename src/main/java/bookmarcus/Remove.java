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
 * A command for removing a bookmark.
 *
 * @author WebCoodi
 */
public class Remove implements Command {

    private final IO io;
    private final DatabaseDAO<Bookmark> bdao;

    public Remove(IO io, DatabaseDAO<Bookmark> bdao) {
        this.io = io;
        this.bdao = bdao;
    }
    
    /**
     * Asks the user to input the ID of the bookmark, then attempts to delete a bookmark with that ID.
     */
    @Override
    public void run() {
        io.print("Syötä poistettavan vinkin numero: ");

        int id;

        try {
            id = Integer.parseInt(io.nextLine());
        } catch(NumberFormatException e) {
            io.print("! - Syötä oikea vinkin numero.");

            return;
        }

        boolean success = bdao.delete(id);

        if(success) {
            io.print("Vinkki on poistettu.");
        } else {
            io.print("! - Syötä oikea vinkin numero.");
        }
    }
    
}
