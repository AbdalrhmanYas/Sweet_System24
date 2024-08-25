package najah.edu.acceptance_test;

import static org.junit.Assert.*;
import MySystem.*;
import io.cucumber.java.en.*;
import java.util.*;
import java.util.logging.Logger;

public class SalesMonitoringSteps {
    private sweetSys sweetSys;
    private String storeOwner;
    private Map<String, Double> salesReport;
    private Map<String, Double> profitReport;
    private String bestSellingProduct;
    private static final Logger logger = Logger.getLogger(InventoryManager.class.getName());

    public SalesMonitoringSteps(sweetSys sweetSys) {
        this.sweetSys = sweetSys;
    }

    @Given("the store owner {string} is logged in")
    public void the_store_owner_is_logged_in(String username) {
        storeOwner = username;
        if (sweetSys.getUserByUsername(username) == null) {
            sweetSys.signUp(username, "password", "owner", "TestCity");
        }
        sweetSys.login(username, "password");
        assertTrue("Store owner should be logged in", sweetSys.isLoggedIn());
    }

    @Given("{string} has the following products:")
    public void has_the_following_products(String username, io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> products = dataTable.asMaps(String.class, String.class);
        StoreOwner owner = (StoreOwner) sweetSys.getUserByUsername(username);
        for (Map<String, String> productData : products) {
            String name = productData.get("Product Name");
            double price = Double.parseDouble(productData.get("Price"));
            int quantity = Integer.parseInt(productData.get("Quantity"));
            int sales = Integer.parseInt(productData.get("Sales"));
            Product product = new Product(owner.getInventory().size() + 1, name, price, quantity, username);
            product.setSalesQuantity(sales);
            product.setCost(price * 0.6); // Assume cost is 60% of the price for this example
            owner.addProduct(product);
        }
    }

    @When("the store owner requests a sales report")
    public void the_store_owner_requests_a_sales_report() {
        salesReport = sweetSys.getSalesReport(storeOwner);
        assertNotNull("Sales report should not be null", salesReport);
    }

    @Then("the sales report should show total revenue of {double}")
    public void the_sales_report_should_show_total_revenue_of(Double expectedRevenue) {
        assertEquals("Total revenue should match", expectedRevenue, salesReport.get("Total Revenue"), 0.01);
        logger.info("Expected revenue: " + expectedRevenue);
        logger.info("Actual revenue: " + salesReport.get("Total Revenue"));
        logger.info("Full sales report: " + salesReport);
    }

    @Then("the sales report should show {string} as the best-selling product")
    public void the_sales_report_should_show_as_the_best_selling_product(String expectedBestSeller) {
        bestSellingProduct = sweetSys.getBestSellingProduct(storeOwner);
        assertEquals("Best-selling product should match", expectedBestSeller, bestSellingProduct);
    }

    @When("the store owner requests a profit report")
    public void the_store_owner_requests_a_profit_report() {
        profitReport = sweetSys.getProfitReport(storeOwner);
        assertNotNull("Profit report should not be null", profitReport);
    }

    @Then("the profit report should show the profit for each product")
    public void the_profit_report_should_show_the_profit_for_each_product() {
        assertFalse("Profit report should not be empty", profitReport.isEmpty());
        for (String productName : profitReport.keySet()) {
            assertTrue("Profit should be non-negative", profitReport.get(productName) >= 0);
        }
    }
}