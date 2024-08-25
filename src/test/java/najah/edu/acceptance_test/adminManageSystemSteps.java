package najah.edu.acceptance_test;

import static org.junit.Assert.*;
import MySystem.*;
import io.cucumber.java.en.*;
import java.util.*;
import java.util.logging.Logger;

public class adminManageSystemSteps {

    private sweetSys sweetSys;

    private List<User> userList;
    private Map<String, List<Product>> bestSellingProducts;
    private Map<String, Integer> userStatistics;
    private List<Recipe> recipes;
    private List<Feedback> feedbackList;


    public adminManageSystemSteps() {
        this.sweetSys = new sweetSys(); // dependenct injection before each test
    }

    @Given("the admin is logged in")
    public void the_admin_is_logged_in() {
        Admin admin = new Admin("admin", "adminpass" , "nablus");
        sweetSys.addUser(admin); // Ensure admin is in the system
        sweetSys.login(admin.getUsername(), admin.getPassword());
        assertTrue("Admin should be logged in", sweetSys.isLoggedIn());
        assertEquals("User should be admin", "admin", sweetSys.getCurrentUser().getRole());
    }
    
    @When("the admin views the list of user accounts")
    public void the_admin_views_the_list_of_user_accounts() {
        userList = sweetSys.getAllUsers();
        assertNotNull("User list should not be null", userList);
    }

    @Then("the admin should see a list of all users including store owners and suppliers")
    public void the_admin_should_see_a_list_of_all_users() {
        sweetSys.addUser(new User("storeOwner", "password", "store owner", "Nablus"));
        sweetSys.addUser(new User("supplier", "password", "supplier", "Jenin"));
        userList = sweetSys.getAllUsers();
        assertTrue("User list should contain store owners",
                userList.stream().anyMatch(user -> user.getRole().equals("store owner")));
        assertTrue("User list should contain suppliers",
                userList.stream().anyMatch(user -> user.getRole().equals("supplier")));
    }

    @When("the admin updates the role of user {string} to {string}")
    public void the_admin_updates_the_role_of_user(String username, String newRole) {
        sweetSys.addUser(new User(username, "password", "oldRole","nablus")); // Add user to ensure existence
        sweetSys.updateUserRole(username, newRole);
    }

    @Then("the role of user {string} should be {string}")
    public void the_role_of_user_should_be(String username, String expectedRole) {
        User user = sweetSys.getUserByUsername(username);
        assertNotNull("User should exist", user);
        assertEquals("User role should be updated", expectedRole, user.getRole());
    }

    @When("the admin deletes the account of user {string}")
    public void the_admin_deletes_the_account_of_user(String username) {
        sweetSys.addUser(new User(username, "password", "role","nablus")); // Ensure user exists
        sweetSys.deleteUser(username);
    }

    @Then("the user {string} should no longer exist in the system")
    public void the_user_should_no_longer_exist_in_the_system(String username) {
        assertNull("Deleted user should not exist", sweetSys.getUserByUsername(username));
    }

    @When("the admin requests a financial report for the last month")
    public void the_admin_requests_a_financial_report_for_the_last_month() {
        sweetSys.generateFinancialReport(); // Assume this generates and stores the report
    }

    @Then("the system should generate a report showing total revenue, expenses, and profit")
    public void the_system_should_generate_a_financial_report() {
        String report = sweetSys.getLastGeneratedReport();
        assertNotNull("Financial report should not be null", report);
        assertTrue("Report should contain revenue", report.contains("Revenue:"));
        assertTrue("Report should contain expenses", report.contains("Expenses:"));
        assertTrue("Report should contain profit", report.contains("Profit:"));
    }

    @When("the admin requests the best-selling products report")
    public void the_admin_requests_the_best_selling_products_report() {
        bestSellingProducts = sweetSys.getBestSellingProductss();
        assertNotNull("Best selling products report should not be null", bestSellingProducts);
    }

    @Then("the system should display a list of top {int} products for each store")
    public void the_system_should_display_a_list_of_top_products_for_each_store(Integer topN) {
        for (List<Product> products : bestSellingProducts.values()) {
            assertTrue("Each store should have top " + topN + " products", products.size() <= topN);
        }
    }

    @When("the admin requests user statistics by city")
    public void the_admin_requests_user_statistics_by_city() {
        // Ensure we have users in both Nablus and Jenin
        if (sweetSys.getUserByUsername("admin1") == null) {
            sweetSys.addUser(new User("admin1", "adminpass1", "admin", "Nablus"));
        }
        if (sweetSys.getUserByUsername("owner1") == null) {
            sweetSys.addUser(new User("owner1", "ownerpass1", "owner", "Jenin"));
        }
        
        userStatistics = sweetSys.getUserStatisticsByCity();
        assertNotNull("User statistics should not be null", userStatistics);
    }

    @Then("the system should display the number of users in each city")
    public void the_system_should_display_the_number_of_users_in_each_city() {
        assertFalse("User statistics should not be empty", userStatistics.isEmpty());
        assertTrue("User statistics should include Nablus", userStatistics.containsKey("Nablus"));
        assertTrue("User statistics should include Jenin", userStatistics.containsKey("Jenin"));
        assertTrue("Nablus should have at least one user", userStatistics.get("Nablus") >= 1);
        assertTrue("Jenin should have at least one user", userStatistics.get("Jenin") >= 1);
    }
    

    @When("the admin views the list of shared recipes")
    public void the_admin_views_the_list_of_shared_recipes() {
        recipes = sweetSys.getAllRecipes();
        assertNotNull("Recipe list should not be null", recipes);
        assertFalse("Recipe list should not be empty", recipes.isEmpty());
    }

    @Then("the admin should see all recipes in the system")
    public void the_admin_should_see_all_recipes_in_the_system() {
        assertFalse("Recipe list should not be empty", recipes.isEmpty());
    }

    @When("the admin deletes an inappropriate recipe")
    public void the_admin_deletes_an_inappropriate_recipe() {
        if (!recipes.isEmpty()) {
            Recipe recipeToDelete = recipes.get(0);
            sweetSys.deleteRecipe(recipeToDelete.getId());
        }
    }

    @Then("the deleted recipe should no longer be visible in the system")
    public void the_deleted_recipe_should_no_longer_be_visible_in_the_system() {
        List<Recipe> updatedRecipes = sweetSys.getAllRecipes();
        assertTrue("Recipe list should be smaller after deletion", updatedRecipes.size() < recipes.size());
    }

    @When("the admin views the list of user feedback")
    public void the_admin_views_the_list_of_user_feedback() {
        feedbackList = sweetSys.getAllFeedback();
        assertNotNull("Feedback list should not be null", feedbackList);
        assertFalse("Feedback list should not be empty", feedbackList.isEmpty());
    }

    @Then("the admin should see all feedback entries")
    public void the_admin_should_see_all_feedback_entries() {
        assertFalse("Feedback list should not be empty", feedbackList.isEmpty());
    }

    @When("the admin marks a feedback entry as resolved")
    public void the_admin_marks_a_feedback_entry_as_resolved() {
        if (!feedbackList.isEmpty()) {
            Feedback feedbackToResolve = feedbackList.get(0);
            sweetSys.resolveFeedback(feedbackToResolve.getId());
        }
    }

    @Then("the feedback entry should be marked as resolved in the system")
    public void the_feedback_entry_should_be_marked_as_resolved_in_the_system() {
        if (!feedbackList.isEmpty()) {
            Feedback resolvedFeedback = sweetSys.getFeedbackById(feedbackList.get(0).getId());
            assertTrue("Feedback should be marked as resolved", resolvedFeedback.isResolved());
        }
    }
}
