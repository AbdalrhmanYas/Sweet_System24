package najah.edu.acceptance_test;

import static org.junit.Assert.*;

import java.util.List;

import MySystem.*;
import io.cucumber.java.en.*;

public class CommunicationFeedbackSteps {
	 private sweetSys sweetSys;
	    private String currentUser;
	    private String storeOwner;
	    private String productName;
	    private String recipeName;

    public CommunicationFeedbackSteps(sweetSys sweetSys) {
        this.sweetSys = sweetSys;
    }

    @Given("the user {string} is logged in")
    public void the_user_is_logged_in(String username) {
        currentUser = username;
        if (sweetSys.getUserByUsername(username) == null) {
            sweetSys.signUp(username, "password", "regular", "TestCity");
        }
        sweetSys.login(username, "password");
        assertTrue("User should be logged in", sweetSys.isLoggedIn());
    }

    @Given("there is a store owner {string} with a product {string}")
    public void there_is_a_store_owner_with_a_product(String ownerUsername, String productName) {
        storeOwner = ownerUsername;
        this.productName = productName;
        if (sweetSys.getUserByUsername(ownerUsername) == null) {
            sweetSys.signUp(ownerUsername, "password", "owner", "TestCity");
        }
        User owner = sweetSys.getUserByUsername(ownerUsername);
        assertTrue("User should be a store owner", owner instanceof StoreOwner);
        StoreOwner storeowner = (StoreOwner) owner;
        Product product = new Product(1, productName, 15.99, 10, ownerUsername);
        storeowner.addProduct(product);
    }

    @When("the user sends a message {string} to {string}")
    public void the_user_sends_a_message_to(String messageContent, String recipient) {
        boolean result = sweetSys.sendMessage(currentUser, recipient, messageContent);
        assertTrue("Message should be sent successfully", result);
    }

    @Then("the message should be successfully sent")
    public void the_message_should_be_successfully_sent() {
        List<Message> messages = sweetSys.getMessages(storeOwner);
        assertFalse("Store owner should have received a message", messages.isEmpty());
        assertEquals("Message content should match", "Is the Chocolate Cake gluten-free?", messages.get(0).getContent());
    }

    @When("the user purchases {string} from {string}")
    public void the_user_purchases_from(String product, String seller) {
        sweetSys.addFundsToUser(currentUser, 100.0); // Ensure user has enough funds
        sweetSys.purchaseProduct(currentUser, seller, product);
        assertTrue("Purchase should be successful", sweetSys.isLastOperationSuccessful());
    }

    @When("the user provides feedback {string} for the purchased product")
    public void the_user_provides_feedback_for_the_purchased_product(String feedbackContent) {
        boolean result = sweetSys.provideFeedback(currentUser, productName, feedbackContent, false);
        assertTrue("Feedback should be provided successfully", result);
    }
    
    @When("the user provides feedback {string} for the recipe {string}")
    public void the_user_provides_feedback_for_the_recipe(String feedbackContent, String recipeName) {
        this.recipeName = recipeName;
        boolean result = sweetSys.provideFeedback(currentUser, recipeName, feedbackContent, true);
        assertTrue("Feedback should be provided successfully for the recipe", result);
    }

    @Then("the feedback should be successfully recorded")
    public void the_feedback_should_be_successfully_recorded() {
        assertTrue("Last operation should be successful", sweetSys.isLastOperationSuccessful());
        
        String itemName = (productName != null) ? productName : recipeName;
        List<Feedback> feedbackList = sweetSys.getItemFeedback(itemName);
        assertFalse("Item should have received feedback", feedbackList.isEmpty());
        
        Feedback lastFeedback = feedbackList.get(feedbackList.size() - 1);
        assertEquals("Feedback author should match", currentUser, lastFeedback.getAuthor());
    }
    
    
    
    @Given("there is a shared recipe {string}")
    public void there_is_a_shared_recipe(String recipeName) {
        Recipe recipe = new Recipe(sweetSys.getAllRecipes().size() + 1, recipeName, "ingredients", "instructions", "author", "category");
        sweetSys.addRecipe(recipe);
    }

}