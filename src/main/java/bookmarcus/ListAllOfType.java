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
import java.util.List;

/**
 * A command for listing all bookmarks of a given type.
 *
 * @author WebCoodi
 */
public class ListAllOfType implements Command {

    private final IO io;
    private final DatabaseDAO<Bookmark> bdao;

    public ListAllOfType(IO io, DatabaseDAO<Bookmark> bdao) {
        this.io = io;
        this.bdao = bdao;
    }
    
    /**
     * Asks the user for the type, then lists all bookmarks that are of that type.
     */
    @Override
    public void run() {
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
    }
    
}
