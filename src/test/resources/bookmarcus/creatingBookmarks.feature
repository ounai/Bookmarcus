Feature: As a user I can create bookmarks

    Scenario: Adding a book hint
        Given that the command "uusi" is entered
        When  the type "kirja" is entered
        And   name "Sodankäynnin taito", author "Sun Tzu" and ISBN "" are entered
        And   the following notes are entered: "Muinaista kiinalaista viisautta yleissivistykseksi"
        Then  a new bookmark is created

    Scenario:
        Given that the command "uusi" is entered
        When  the type "artikkeli" is entered
        And   name "Klassinen musiikki kuin vitamiineja kasveille", author "Veikko Viheriä" and url "www.biopaperi.fi/a/401/" are entered
        And   the following notes are entered: "Kotona vilkaistavaksi jos saisi viherkasviin vähän lisää virettä"
        Then  a new bookmark is created
        