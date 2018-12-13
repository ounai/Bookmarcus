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
 * A command for listing all bookmarks with a given author.
 *
 * @author WebCoodi
 */
public class ListAllByAuthor implements Command {

    private final IO io;
    private final DatabaseDAO<Bookmark> bdao;

    public ListAllByAuthor(IO io, DatabaseDAO<Bookmark> bdao) {
        this.io = io;
        this.bdao = bdao;
    }
    
    /**
     * Asks for an author, then lists all bookmarks with them as the author.
     */
    @Override
    public void run() {
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
    }
    
}
