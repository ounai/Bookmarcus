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
public class ScannerIO implements IO {
    
    private final static Scanner SYSTEM_IN = new Scanner(System.in);
    
    private final Scanner scanner;

    public ScannerIO() {
        this(SYSTEM_IN);
    }
    
    public ScannerIO(Scanner scanner) {
        this.scanner = scanner;
    }
    
    @Override
    public void addLines(String... lines) {
        throw new UnsupportedOperationException("ScannerIO doesn't support this method.");
    }

    @Override
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }

    @Override
    public void close() {
        scanner.close();
    }
    
}
