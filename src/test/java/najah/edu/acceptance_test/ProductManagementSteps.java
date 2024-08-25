package najah.edu.acceptance_test;

import static org.junit.Assert.*;
import MySystem.*;
import io.cucumber.java.en.*;

import java.util.logging.Logger;

public class ProductManagementSteps {
    private sweetSys sweetSys;
    private boolean operationResult;
    
    private String lastAddedProductName;
    private static final Logger logger = Logger.getLogger(InventoryManager.class.getName());

    public ProductManagementSteps(sweetSys sweetSys) {
        this.sweetSys = sweetSys;
    }

    @Given("the user is logged in as a {string}")
    public void the_user_is_logged_in_as_a(String role) {
        String username = role.equals("store owner") ? "testowner" : "testsupplier";
        String password = "password";
        sweetSys.signUp(username, password, role, "TestCity");
        sweetSys.login(username, password);
        assertTrue("User should be logged in", sweetSys.isLoggedIn());
        logger.info("Logged in user: " + sweetSys.getCurrentUser().getClass().getSimpleName() + " - " + username);
    }

    @When("the user adds a product {string} with price {double} and quantity {int}")
    public void the_user_adds_a_product_with_price_and_quantity(String name, Double price, Integer quantity) {
        User currentUser = sweetSys.getCurrentUser();
        logger.info("Current user before adding product: " + currentUser.getClass().getSimpleName() + " - " + currentUser.getUsername());
        Product newProduct = new Product(1, name, price, quantity, currentUser.getUsername());
        operationResult = sweetSys.addProduct(currentUser.getUsername(), newProduct);
        lastAddedProductName = name;
        logger.info("Add product result: " + operationResult);
        if (currentUser instanceof InventoryManager) {
            InventoryManager manager = (InventoryManager) currentUser;
            logger.info("Inventory after adding product: " + manager.getInventory());
        }
    }

    @Then("the product should be added successfully")
    public void the_product_should_be_added_successfully() {
        assertTrue("Product should be added successfully. Operation result: " + operationResult, operationResult);
        User currentUser = sweetSys.getCurrentUser();
        assertTrue("Current user should be an InventoryManager", currentUser instanceof InventoryManager);
        InventoryManager manager = (InventoryManager) currentUser;
        boolean productFound = manager.isProductInInventory(lastAddedProductName);
        assertTrue("Product should be in inventory. Inventory: " + manager.getInventory() + ", Product found: " + productFound, productFound);
    }
    
    
    @When("the user updates the product {string} with new price {double} and new quantity {int}")
    public void the_user_updates_the_product_with_new_price_and_new_quantity(String name, Double newPrice, Integer newQuantity) {
        operationResult = sweetSys.updateProduct(sweetSys.getCurrentUser().getUsername(), name, newPrice, newQuantity);
    }

    @Then("the product should be updated successfully")
    public void the_product_should_be_updated_successfully() {
        assertTrue("Product should be updated successfully", operationResult);
        Product updatedProduct = sweetSys.getProductByName("Test Product");
        assertNotNull("Updated product should exist", updatedProduct);
        assertEquals("Price should be updated", 15.99, updatedProduct.getPrice(), 0.01);
        assertEquals("Quantity should be updated", 50, updatedProduct.getQuantity());
    }

    @When("the user removes the product {string}")
    public void the_user_removes_the_product(String name) {
        operationResult = sweetSys.removeProduct(sweetSys.getCurrentUser().getUsername(), name);
    }

    @Then("the product should be removed successfully")
    public void the_product_should_be_removed_successfully() {
        assertTrue("Product should be removed successfully", operationResult);
        assertFalse("Product should not be in inventory", sweetSys.isProductInInventory("Test Product"));
    }
}
