/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.bookmark;

/**
 *
 * @author daniel
 */
public class ISBN {

    private String isbn;
    
    public ISBN() {
        this.isbn = "";
    }
    
    public String getIsbn() {
        return this.isbn;
    }

    public boolean setISBN(String isbn) {
        if (validISBN(isbn)) {
            this.isbn = isbn;
            return true;
        }
        return false;
    }

    public static boolean validISBN(String isbn) {
        String regex = "^(97(8|9))?\\-?[0-9]{3}\\-?[0-9]{5}\\-?[0-9]\\-?([0-9]|X)$";

        if (isbn.isEmpty()) {
            return true;
        }

        if (!isbn.matches(regex)) {
            return false;
        }

        String isbnNoDashes = isbn.replaceAll("-", "");

        if (isbnNoDashes.length() == 10) {
            // ISBN-10

            int sum = 0, multiplier = 10;

            for (char c : isbnNoDashes.toCharArray()) {
                if (c == 'X') {
                    sum += multiplier * 10;
                } else {
                    sum += multiplier * (c - '0');
                }

                multiplier--;
            }

            if (sum % 11 != 0) {
                return false;
            }
        } else if (isbnNoDashes.length() == 13) {
            // ISBN-13

            int sum = 0, multiplier = 1;

            for (char c : isbnNoDashes.toCharArray()) {
                if (c == 'X') {
                    // ISBN-13 cannot contain an X
                    return false;
                }

                sum += multiplier * (c - '0');

                if (multiplier == 3) {
                    multiplier = 1;
                } else {
                    multiplier = 3;
                }
            }

            if (sum % 10 != 0) {
                return false;
            }
        } else {
            // Not a valid ISBN

            return false;
        }

        return true;
    }

}
