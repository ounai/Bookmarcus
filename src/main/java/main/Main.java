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
package main;

import bookmarcus.Bookmarcus;
import database.BookmarkDAO;
import io.IO;
import io.ConsoleIO;

/**
 * The main class of the app, used for initializing the main app ({@link bookmarcus.Bookmarcus}).
 * 
 * @author WebCoodi
 */
public class Main {

    /**
     * The main class of the app, generates the required dependencies of the main app then creates it and injects them.
     */
    public static void main(String[] args) {
        IO io = new ConsoleIO();
        new Bookmarcus(new BookmarkDAO(), io).consoleApp();
        io.close();
    }

}
