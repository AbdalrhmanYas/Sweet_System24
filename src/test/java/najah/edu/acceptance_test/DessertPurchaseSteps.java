package najah.edu.acceptance_test;
import static org.junit.Assert.*;
import MySystem.*;
import io.cucumber.java.en.*;

public class DessertPurchaseSteps {
    private sweetSys sweetSys;
    private User currentUser;
    private StoreOwner storeOwner;
    private Product selectedDessert;
    private boolean purchaseAttemptSuccessful;

    public DessertPurchaseSteps(sweetSys sweetSys) {
        this.sweetSys = sweetSys;
    }

    @Given("there is a store owner {string} with a dessert {string} priced at {double}")
    public void there_is_a_store_owner_with_a_dessert_priced_at(String ownerName, String dessertName, Double price) {
        storeOwner = new StoreOwner(ownerName, "password", "TestCity");
        sweetSys.addUser(storeOwner);
        Product dessert = new Product(1, dessertName, price, 10, ownerName);
        storeOwner.addProduct(dessert);
    }

    @When("the user selects {string} from {string}")
    public void the_user_selects_from(String dessertName, String ownerName) {
        currentUser = sweetSys.getCurrentUser();
        storeOwner = (StoreOwner) sweetSys.getUserByUsername(ownerName);
        selectedDessert = storeOwner.getProductByName(dessertName);
        // Check if the item is out of stock
        if (selectedDessert != null && selectedDessert.getQuantity() <= 0) {
            purchaseAttemptSuccessful = false;
            sweetSys.setMessage("Item is out of stock");
        } else {
            purchaseAttemptSuccessful = true;
        }
    }

    @When("the user confirms the purchase")
    public void the_user_confirms_the_purchase() {
        if (purchaseAttemptSuccessful) {
            // Add funds to the user's account to ensure they can make the purchase
            sweetSys.addFundsToUser(currentUser.getUsername(), selectedDessert.getPrice() + 10); // Add a little extra
            sweetSys.purchaseProduct(currentUser.getUsername(), storeOwner.getUsername(), selectedDessert.getName());
        }
    }

    @Then("the purchase should be successful")
    public void the_purchase_should_be_successful() {
        assertTrue("Purchase should be successful", sweetSys.isLastOperationSuccessful());
    }

    @Then("the user's order history should include {string}")
    public void the_user_s_order_history_should_include(String dessertName) {
        assertTrue("Order history should include the purchased dessert",
                sweetSys.getUserOrderHistory(currentUser.getUsername()).contains(dessertName));
    }

    @Given("there is a store owner {string} with an out-of-stock dessert {string}")
    public void there_is_a_store_owner_with_an_out_of_stock_dessert(String ownerName, String dessertName) {
        storeOwner = new StoreOwner(ownerName, "password", "TestCity");
        sweetSys.addUser(storeOwner);
        Product dessert = new Product(2, dessertName, 12.99, 0, ownerName);
        storeOwner.addProduct(dessert);
    }

    @Then("the user should be informed that the item is out of stock")
    public void the_user_should_be_informed_that_the_item_is_out_of_stock() {
        assertFalse("Purchase should not be successful", purchaseAttemptSuccessful);
        assertEquals("Item is out of stock", sweetSys.getMessage());
    }
}