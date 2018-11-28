Feature: As a user I can delete bookmarks

    Scenario: Deleting a bookmark
        Given that the command "3" is entered
        When  the id "5" is entered
        Then  a bookmark is deleted
