Feature: As a user I can view bookmarks

    Scenario: Listing all bookmarks
        Given that the command "1" is entered
        Then  bookmarks are listed

    Scenario: Listing all unread bookmarks
        Given that the command "4" is entered
        Then  unread bookmarks are listed

    Scenario: Listing all read bookmarks
        Given that the command "5" is entered
        Then  read bookmarks are listed

    Scenario: Listing bookmarks by author
        Given that the command "7" is entered
        When  the id "Matti" is entered
        Then  bookmarks with author are listed

    Scenario: Listing bookmarks by type
        Given that the command "10" is entered
        When  the type "artikkeli" is entered
        Then  bookmarks with type are listed