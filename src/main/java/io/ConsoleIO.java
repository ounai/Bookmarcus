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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bookmarcus. If not, see <https://www.gnu.org/licenses/>.
 */
package io;

import java.util.Arrays;
import java.util.Scanner;

/**
 * An implementation of user input and output in a terminal.
 *
 * @author WebCoodi
 */
public class ConsoleIO implements IO {
    
    private final Scanner scanner;
    
    public ConsoleIO() {
        this.scanner = new Scanner(System.in);
    }
    
    public ConsoleIO(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public boolean hasNextLine() {
        return true;
    }

    /**
     * Reads and returns a line of input from the scanner object.
     * 
     * @return a string containing the line of scanner input
     */
    @Override
    public String nextLine() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    /**
     * Closes the connection to the scanner.
     */
    @Override
    public void close() {
        scanner.close();
    }

    /**
     * Prints the given lines to standard output.
     */
    @Override
    public void print(String... lines) {
        Arrays.stream(lines).forEach(System.out::println);
    }
    
}
