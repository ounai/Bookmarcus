
package main;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.assertEquals;

public class Stepdefs {

    Dummy dummy;
    String result;

    @Given("^A dummy object is created$")
    public void a_dummy_object_is_created() throws Throwable {
        dummy = new Dummy();
    }

    @When("^Its method is called$")
    public void its_method_is_called() throws Throwable {
        result = dummy.helloWorld();
    }

    @Then("^\"([^\"]*)\" is given$")
    public void is_given(String helloWorld) throws Throwable {
        assertEquals(helloWorld, result);
    }

}