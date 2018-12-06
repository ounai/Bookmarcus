
package bookmarcus;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import database.bookmark.Bookmark;
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

    @When("^the id \"([^\"]*)\" is entered$")
    public void the_id_is_entered(String id) throws Throwable {
        inputFeed = inputFeed.thenReturn(id);
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
        inputFeed = inputFeed.thenReturn(Bookmarcus.POISTU_COMMAND).thenThrow(new NoSuchElementException("Input exhausted too soon"));
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

    @Then("^^bookmarks are listed$")
    public void bookmarks_are_listed() throws Throwable {
        inputFeed = inputFeed.thenReturn(Bookmarcus.POISTU_COMMAND).thenThrow(new NoSuchElementException("Input exhausted too soon"));
        when(mockIO.hasNextLine()).thenReturn(true);
        Bookmarcus bookmarcus = new Bookmarcus(mockDAO, mockIO);
        try {
            bookmarcus.consoleApp();
        } catch (NoSuchElementException e) {
            fail(e.getMessage());
        }
        verify(mockDAO, times(1)).getAll();
    }

    @Then("^unread bookmarks are listed$")
    public void unread_bookmarks_are_listed() throws Throwable {
        inputFeed = inputFeed.thenReturn(Bookmarcus.POISTU_COMMAND).thenThrow(new NoSuchElementException("Input exhausted too soon"));
        when(mockIO.hasNextLine()).thenReturn(true);
        Bookmarcus bookmarcus = new Bookmarcus(mockDAO, mockIO);
        try {
            bookmarcus.consoleApp();
        } catch (NoSuchElementException e) {
            fail(e.getMessage());
        }
        verify(mockDAO, times(1)).getAllUnRead();
    }

    @Then("^read bookmarks are listed$")
    public void read_bookmarks_are_listed() throws Throwable {
        inputFeed = inputFeed.thenReturn(Bookmarcus.POISTU_COMMAND).thenThrow(new NoSuchElementException("Input exhausted too soon"));
        when(mockIO.hasNextLine()).thenReturn(true);
        Bookmarcus bookmarcus = new Bookmarcus(mockDAO, mockIO);
        try {
            bookmarcus.consoleApp();
        } catch (NoSuchElementException e) {
            fail(e.getMessage());
        }
        verify(mockDAO, times(1)).getAllRead();
    }

    @Then("^a bookmark is deleted$")
    public void a_bookmark_is_deleted() throws Throwable {
        inputFeed = inputFeed.thenReturn(Bookmarcus.POISTU_COMMAND).thenThrow(new NoSuchElementException("Input exhausted too soon"));
        when(mockIO.hasNextLine()).thenReturn(true);
        Bookmarcus bookmarcus = new Bookmarcus(mockDAO, mockIO);
        try {
            bookmarcus.consoleApp();
        } catch (NoSuchElementException e) {
            fail(e.getMessage());
        }
        verify(mockDAO, times(1)).delete(anyInt());
    }

    @Then("^bookmark is marked as read$")
    public void bookmark_is_marked_as_read() throws Throwable {
        inputFeed = inputFeed.thenReturn(Bookmarcus.POISTU_COMMAND).thenThrow(new NoSuchElementException("Input exhausted too soon"));
        when(mockIO.hasNextLine()).thenReturn(true);
        Bookmarcus bookmarcus = new Bookmarcus(mockDAO, mockIO);
        try {
            bookmarcus.consoleApp();
        } catch (NoSuchElementException e) {
            fail(e.getMessage());
        }
        verify(mockDAO, times(1)).markAsRead(anyInt());
    }

    @Then("^bookmarks with author are listed$")
    public void bookmarks_with_author_are_listed() throws Throwable {
        inputFeed = inputFeed.thenReturn(Bookmarcus.POISTU_COMMAND).thenThrow(new NoSuchElementException("Input exhausted too soon"));
        when(mockIO.hasNextLine()).thenReturn(true);
        Bookmarcus bookmarcus = new Bookmarcus(mockDAO, mockIO);
        try {
            bookmarcus.consoleApp();
        } catch (NoSuchElementException e) {
            fail(e.getMessage());
        }
        verify(mockDAO, times(1)).findByAuthor(anyString());
    }

    @Then("^bookmarks with type are listed$")
    public void bookmarks_with_type_are_listed() throws Throwable {
        inputFeed = inputFeed.thenReturn(Bookmarcus.POISTU_COMMAND).thenThrow(new NoSuchElementException("Input exhausted too soon"));
        when(mockIO.hasNextLine()).thenReturn(true);
        Bookmarcus bookmarcus = new Bookmarcus(mockDAO, mockIO);
        try {
            bookmarcus.consoleApp();
        } catch (NoSuchElementException e) {
            fail(e.getMessage());
        }
        verify(mockDAO, times(1)).findByType(anyString());
    }

}