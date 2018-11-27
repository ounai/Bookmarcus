Feature: As a user I can create bookmarks

    Scenario: Adding a book hint
        Given that the command "uusi" is entered
        When  the type "kirja" is entered
        And   name "Sodankäynnin taito", author "Sun Tzu" and ISBN "" are entered
        And   the following notes are entered: "Muinaista kiinalaista viisautta yleissivistykseksi"
        Then  a new bookmark is created

