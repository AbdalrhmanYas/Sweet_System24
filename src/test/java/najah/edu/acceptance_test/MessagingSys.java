package najah.edu.acceptance_test;

import MySystem.User;
import MySystem.sweetSys;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MessagingSys {
    private final MySystem.sweetSys sweetSys;



    public MessagingSys() {
        this.sweetSys = new sweetSys();
    }


    @Given("I am logged in as a {string}")
    public void i_am_logged_in_as_a(String role) {
        assertEquals("User is an owner", "owner",role);

    }

    @When("I use the messaging system to communicate with {string}")
    public void i_use_the_messaging_system_to_communicate_with(String recipient) {
        assertEquals("Recipient is a supplier", recipient, sweetSys.checkRecipient(recipient));

    }

    @Then("I should see the message sent to {string}")
    public void i_should_see_the_message_sent_to(String recipient) {
        User u1= new User();
        u1.setRole(recipient);
        assertTrue("The message was sent to the recipient",sweetSys.receivedCheck(u1));
    }

}
