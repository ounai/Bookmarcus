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
public class SetRead implements Command {

    private final IO io;
    private final DatabaseDAO<Bookmark> bdao;

    public SetRead(IO io, DatabaseDAO<Bookmark> bdao) {
        this.io = io;
        this.bdao = bdao;
    }
    
    @Override
    public void run() {
        io.print("Syötä luetuksi tai katsotuksi merkattavan vinkin numero: ");
        int readId = Integer.parseInt(io.nextLine()); // virheenhallinta puuttuu!
        boolean readSuccess = bdao.markAsRead(readId);
        if(readSuccess) {
            io.print("Vinkki on merkitty luetuksi/katsotuksi.");
        } else {
            io.print("! - Syötä oikea vinkin numero.");
        }
    }
    
}
