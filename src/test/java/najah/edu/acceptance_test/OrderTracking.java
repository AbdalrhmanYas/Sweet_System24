package najah.edu.acceptance_test;

import MySystem.Order;
import MySystem.StoreOwner;
import MySystem.sweetSys;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderTracking {

    private final MySystem.sweetSys sweetSys;



    public OrderTracking() {
        this.sweetSys = new sweetSys();
    }


    @Given("I am logged in with a {string}")
    public void i_am_logged_in_with_a(String role) {
        assertEquals("User is an owner", role,sweetSys.orderSupervisor(role));

    }

    @When("I process an order with {string} and status {string} to {string}")
    public void i_process_an_order_with_and_status_to(String id, String oldState, String newState) {
        sweetSys.updateOrder(id, oldState, newState);

    }

    @Then("the system should reflect the order with {string} as {string}")
    public void the_system_should_reflect_the_order_with_as(String id, String newState) {
        assertEquals("The order's status is updated", newState, sweetSys.checkOrderState(id));
    }

}
