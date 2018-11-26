/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import java.util.Scanner;

/**
 *
 * @author Jori Lampi
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
