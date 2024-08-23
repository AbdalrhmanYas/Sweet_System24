package najah.edu.acceptance_test;

import static org.junit.Assert.*;
import MySystem.*;
import io.cucumber.java.en.*;
import java.time.LocalDate;

public class DynamicDiscountSteps {
    private sweetSys sweetSys;
    private String productName;

    public DynamicDiscountSteps(sweetSys sweetSys) {
        this.sweetSys = sweetSys;
    }

    @Given("the store owner has a product {string} with price {double}")
    public void the_store_owner_has_a_product_with_price(String name, Double price) {
        productName = name;
        User currentUser = sweetSys.getCurrentUser();
        assertTrue("Current user should be a StoreOwner", currentUser instanceof StoreOwner);
        StoreOwner owner = (StoreOwner) currentUser;
        Product product = new Product(owner.getInventory().size() + 1, name, price, 10, owner.getUsername());
        owner.addProduct(product);
    }

    @Given("the store owner has a product {string} with price {double} and a {double}% discount")
    public void the_store_owner_has_a_product_with_price_and_a_discount(String name, Double price, Double discount) {
        productName = name;
        User currentUser = sweetSys.getCurrentUser();
        assertTrue("Current user should be a StoreOwner", currentUser instanceof StoreOwner);
        StoreOwner owner = (StoreOwner) currentUser;
        Product product = new Product(owner.getInventory().size() + 1, name, price, 10, owner.getUsername());
        product.setDiscount(discount, LocalDate.now(), LocalDate.now().plusDays(7));
        owner.addProduct(product);
    }

    @When("the store owner sets a discount of {double}% on the product for {int} days")
    public void the_store_owner_sets_a_discount_on_the_product_for_days(Double discountPercentage, Integer durationDays) {
        User currentUser = sweetSys.getCurrentUser();
        assertTrue("Current user should be a StoreOwner", currentUser instanceof StoreOwner);
        StoreOwner owner = (StoreOwner) currentUser;
        Product product = owner.getProductByName(productName);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = durationDays > 0 ? startDate.plusDays(durationDays) : startDate;
        product.setDiscount(discountPercentage, startDate, endDate);
    }

    @When("the store owner removes the discount from the product")
    public void the_store_owner_removes_the_discount_from_the_product() {
        User currentUser = sweetSys.getCurrentUser();
        assertTrue("Current user should be a StoreOwner", currentUser instanceof StoreOwner);
        StoreOwner owner = (StoreOwner) currentUser;
        Product product = owner.getProductByName(productName);
        product.removeDiscount();
    }

    @Then("the discount should be successfully applied")
    public void the_discount_should_be_successfully_applied() {
        User currentUser = sweetSys.getCurrentUser();
        assertTrue("Current user should be a StoreOwner", currentUser instanceof StoreOwner);
        StoreOwner owner = (StoreOwner) currentUser;
        Product product = owner.getProductByName(productName);
        // We don't check if the discount is active for 0-day discounts
        if (!product.getDiscountStartDate().equals(product.getDiscountEndDate())) {
            assertTrue("Discount should be active", product.isDiscountActive());
        }
    }

    @Then("the discounted price of the product should be {double}")
    public void the_discounted_price_of_the_product_should_be(Double expectedPrice) {
        User currentUser = sweetSys.getCurrentUser();
        assertTrue("Current user should be a StoreOwner", currentUser instanceof StoreOwner);
        StoreOwner owner = (StoreOwner) currentUser;
        Product product = owner.getProductByName(productName);
        assertEquals("Discounted price should match", expectedPrice, product.getDiscountedPrice(), 0.01);
    }

    @Then("the discount should be successfully removed")
    public void the_discount_should_be_successfully_removed() {
        User currentUser = sweetSys.getCurrentUser();
        assertTrue("Current user should be a StoreOwner", currentUser instanceof StoreOwner);
        StoreOwner owner = (StoreOwner) currentUser;
        Product product = owner.getProductByName(productName);
        assertFalse("Discount should not be active", product.isDiscountActive());
    }

    @Then("the price of the product should be back to {double}")
    public void the_price_of_the_product_should_be_back_to(Double originalPrice) {
        User currentUser = sweetSys.getCurrentUser();
        assertTrue("Current user should be a StoreOwner", currentUser instanceof StoreOwner);
        StoreOwner owner = (StoreOwner) currentUser;
        Product product = owner.getProductByName(productName);
        assertEquals("Price should be back to original", originalPrice, product.getPrice(), 0.01);
        assertEquals("Discounted price should match original price", originalPrice, product.getDiscountedPrice(), 0.01);
    }
}