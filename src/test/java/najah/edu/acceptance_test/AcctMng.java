package najah.edu.acceptance_test;

import MySystem.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;

public class AcctMng {
    private final MySystem.sweetSys sweetSys;
    private boolean checkIfChanged;

    public AcctMng() {
        this.sweetSys = new sweetSys();
    }

    @Given("The store owner {string} with {string} is logged in")
    public void the_store_owner_with_is_logged_in(String username, String oldPassword) {
        sweetSys.login(username, oldPassword);
        assertEquals("User should be owner", "owner", sweetSys.getCurrentUser().getRole());
        assertTrue("Store owner should be logged in", sweetSys.isLoggedIn());
    }

    @When("Store owner enter {string} and {string} and {string}")
    public void store_owner_enter_and_and(String username, String oldPassword, String newPassword) {

        sweetSys.changePass(username, oldPassword, newPassword);
        StoreOwner updatedOwner = (StoreOwner) sweetSys.getUserByUsername(username);
        checkIfChanged = updatedOwner.getPassword().equals(newPassword);
    }

    @Then("Password will change")
    public void password_will_change() {
        assertTrue("Password should be changed", checkIfChanged);
    }
}
