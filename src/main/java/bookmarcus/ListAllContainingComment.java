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
 * A command for searching bookmarks' comments for a string.
 *
 * @author WebCoodi
 */
public class ListAllContainingComment implements Command {

    private final IO io;
    private final DatabaseDAO<Bookmark> bdao;

    public ListAllContainingComment(IO io, DatabaseDAO<Bookmark> bdao) {
        this.io = io;
        this.bdao = bdao;
    }
    
    /**
     * Asks the user for a string, then searches all bookmarks for comments containing that string and outputs found bookmarks.
     */
    @Override
    public void run() {
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
    }
    
}
