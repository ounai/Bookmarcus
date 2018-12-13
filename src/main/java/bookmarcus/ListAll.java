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
 * A command for listing all bookmarks.
 * 
 * @author WebCoodi
 */
public class ListAll implements Command {

    private final IO io;
    private final DatabaseDAO<Bookmark> bdao;

    public ListAll(IO io, DatabaseDAO<Bookmark> bdao) {
        this.io = io;
        this.bdao = bdao;
    }
    
    /**
     * Runs through all bookmarks found in the database and lists them.
     */
    @Override
    public void run() {
        List<Bookmark> bookmarks = bdao.getAll();
        for (Bookmark bm : bookmarks) {
            io.print(bm.toString());
        }
    }
    
}
