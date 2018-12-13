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
import io.IO;

/**
 * Constructs the menus and their actions in the app.
 *
 * @author WebCoodi
 */
public class Bookmarcus {

    // These are used for consistent calling when numbers may change
    public final static String UUSI_COMMAND = "uusi";
    public final static String POISTU_COMMAND = "poistu";
    public final static String MUOKKAA_COMMAND = "muokkaa";

    private final DatabaseDAO<Bookmark> bdao;
    private final IO io;
    private final Menu<Command> menu;
    private boolean exit;

    public Bookmarcus(DatabaseDAO<Bookmark> bdao, IO io) {
        this.bdao = bdao;
        this.io = io;
        this.exit = false;
        this.menu = new Menu((Command) () -> io.print("! - Tuntematon komento"));
        initMenu();
        
    }
    
    private void initMenu() {
        menu.add("listaa", "Listaa vinkit", () -> bdao.getAll().forEach(bm -> io.print(bm.toString())));
        menu.add(UUSI_COMMAND, "Uusi vinkki", new NewBookmark(io, bdao));
        menu.add("poista", "Poista vinkki", new Remove(io, bdao));
        menu.add("lukemattomat", "Listaa lukemattomat vinkit", () -> bdao.getAllUnRead().forEach(bm -> io.print(bm.toString())));
        menu.add("luetut", "Listaa luetut vinkit", () -> bdao.getAllRead().forEach(bm -> io.print(bm.toString())));
        menu.add("lue", "Merkitse vinkki luetuksi/katsotuksi", new SetRead(io, bdao));
        menu.add("tekija", "Etsi vinkkejä tekijän mukaan", new ListAllByAuthor(io, bdao));
        menu.add(MUOKKAA_COMMAND, "Muokkaa vinkkiä", new Edit(io, bdao));
        menu.add("muistiinpano", "Lisää vinkkiin muistiinpano", new AddNote(io, bdao));
        menu.add("tyyppi", "Etsi vinkkejä tyypin mukaan", new ListAllOfType(io, bdao));
        menu.add("kommenttihaku", "Etsi vinkkejä esiintyvällä kommentilla", new ListAllContainingComment(io, bdao));
        menu.add(POISTU_COMMAND, "POISTU", () -> exit = true);
    }
    
    /**
     * Starts running a console app on the IO provided.
     */
    public void consoleApp() {
        while (!exit) {
            io.print("Valitse komento:");
            io.print(menu.toString());
            io.print("--- --- --- --- ---");
            menu.get(io.nextLine()).run();
            io.print("");
        }
    }
    
}
