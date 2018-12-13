/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.bookmark;

/**
 * A representation of a validated ISBN value.
 *
 * @author WebCoodi
 */
public class ISBN {

    private String isbn;

    public ISBN() {
        this.isbn = "";
    }

    public String getIsbn() {
        return this.isbn;
    }

    /**
     * Validates and sets the ISBN value.
     * 
     * @param isbn the ISBN value to validate and set
     * 
     * @return was the given ISBN value valid
     */
    public boolean setISBN(String isbn) {
        if (validISBN(isbn)) {
            this.isbn = isbn;
            return true;
        }

        return false;
    }

    private static boolean validISBN(String isbn) {
        if (isbn.isEmpty()) {
            return true;
        }

        return isbnValidation(isbn);
    }

    private static boolean isbnValidation(String isbn) {
        String regex = "^(97(8|9))?\\-?[0-9]{3}\\-?[0-9]{5}\\-?[0-9]\\-?([0-9]|X)$";
        if (!isbn.matches(regex)) {
            return false;
        }
        String isbnNoDashes = isbn.replaceAll("-", "");
        switch (isbnNoDashes.length()) {
            case 10: {
                return isbnValidationLength10(isbnNoDashes);

            }
            case 13: {
                return isbnValidationLength13(isbnNoDashes);
            }
            default:
                // Not a valid ISBN

                return false;
        }
    }

    private static boolean isbnValidationLength13(String isbnNoDashes) {
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
        return sum % 10 == 0;
    }

    private static boolean isbnValidationLength10(String isbnNoDashes) {
        int sum = 0, multiplier = 10;
        for (char c : isbnNoDashes.toCharArray()) {
            if (c == 'X') {
                sum += multiplier * 10;
            } else {
                sum += multiplier * (c - '0');
            }

            multiplier--;
        }
        return sum % 11 == 0;
    }

}
