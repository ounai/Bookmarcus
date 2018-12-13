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
import database.bookmark.BookmarkFactory;
import io.IO;

/**
 * A command for creating a new bookmark.
 *
 * @author WebCoodi
 */
public class NewBookmark implements Command {

    private final IO io;
    private final DatabaseDAO<Bookmark> bdao;

    public NewBookmark(IO io, DatabaseDAO<Bookmark> bdao) {
        this.io = io;
        this.bdao = bdao;
    }
    
    /**
     * Guides the user through the process of creating a new bookmarks.
     * 
     * First asks the user of the new bookmark's type, then its name, following other information about the bookmark.
     */
    @Override
    public void run() {
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
    
}
