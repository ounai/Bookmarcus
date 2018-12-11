Feature: As a user I can edit bookmarks

    Scenario: Editing a name
        Given that the command "muokkaa" is entered
        When the number "1" is entered
        And the number "1" is entered
        And new name "uusi" is entered
        Then name is edited
