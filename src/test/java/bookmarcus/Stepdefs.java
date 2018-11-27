
package bookmarcus;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import database.Bookmark;
import database.DatabaseDAO;
import io.IO;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.NoSuchElementException;

import org.mockito.stubbing.OngoingStubbing;

@SuppressWarnings("unchecked")
public class Stepdefs {

    DatabaseDAO<Bookmark> mockDAO = (DatabaseDAO<Bookmark>) mock(DatabaseDAO.class);
    IO mockIO = mock(IO.class);
    OngoingStubbing<String> inputFeed = when(mockIO.nextLine());

    @Given("^that the command \"([^\"]*)\" is entered$")
    public void that_the_command_is_entered(String command) throws Throwable {
        inputFeed = inputFeed.thenReturn(command);
    }

    @When("^the type \"([^\"]*)\" is entered$")
    public void the_type_is_entered(String type) throws Throwable {
        inputFeed = inputFeed.thenReturn(type);
    }

    @When("^name \"([^\"]*)\", author \"([^\"]*)\" and ISBN \"([^\"]*)\" are entered$")
    public void name_author_and_ISBN_are_entered(String name, String author, String isbn) throws Throwable {
        inputFeed = inputFeed.thenReturn(name, author, isbn);
    }

    @When("^name \"([^\"]*)\", author \"([^\"]*)\" and url \"([^\"]*)\" are entered$")
    public void name_author_and_url_are_entered(String name, String author, String url) throws Throwable {
        inputFeed = inputFeed.thenReturn(name, author, url);
    }

    @When("^the following notes are entered: \"([^\"]*)\"$")
    public void the_following_notes_are_entered(String notes) throws Throwable {
        inputFeed = inputFeed.thenReturn(notes);
    }

    @Then("^a new bookmark is created$")
    public void a_new_bookmark_is_created() throws Throwable {
        inputFeed = inputFeed.thenReturn("4").thenThrow(new NoSuchElementException("Input exhausted too soon"));
        when(mockIO.hasNextLine()).thenReturn(true);
        Bookmarcus bookmarcus = new Bookmarcus(mockDAO, mockIO);
        verify(mockDAO, times(0)).add(any(Bookmark.class));
        try {
            bookmarcus.consoleApp();
        } catch (NoSuchElementException e) {
            fail(e.getMessage());
        }
        verify(mockDAO, times(1)).add(any(Bookmark.class));
    }

}