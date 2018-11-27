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

import java.util.Scanner;

/**
 *
 * @author WebCoodi
 */
public class ConsoleIO implements IO {
    
    private final Scanner scanner;
    
    public ConsoleIO() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public boolean hasNextLine() {
        return true;
    }

    @Override
    public String nextLine() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    @Override
    public void close() {
        scanner.close();
    }

    @Override
    public void print(String... lines) {
        for (String line : lines) {
            System.out.println(line);
        }
    }
    
}
