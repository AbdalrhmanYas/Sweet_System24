package najah.edu.acceptance_test;

import static org.junit.Assert.*;
import MySystem.*;
import io.cucumber.java.en.*;

import java.util.Map;
import java.util.logging.Logger;

public class userLoginLogupSteps {
    
    private sweetSys myApp;
    private String username;   // THIS AND OTHER ATTRIPUTES FOR SIGN UP TESTING 
    private String password;
    private String confirmedPassword;
    private String city;
    private String role;
    private static final Logger logger = Logger.getLogger(InventoryManager.class.getName());
    
    private User currentUser;  
    private Map<String, String> userDetails;  
    
    
    
    // Dependency injection through constructor
    
   	// عشان اعمل تيست على السيستم اللي بدي اتستو بلزمني اوبجكت من هاظ التيست فعملت حقن لاوبجكت السيستم اللي بدي اتستو

       public userLoginLogupSteps(sweetSys obj) {
           this.myApp = obj;
       }

    
    //     VALID LOGIN , ALL STEPS ARE COMMON 
     //------------------------------------------------------------------------------------------  
       @Given("user is not in the sweet system")
       public void user_is_not_in_the_sweet_system() {
           myApp.logout();
           assertFalse("User should be logged out", myApp.isLoggedIn());
       }

       @When("user enters username {string} and password {string}")
       public void user_enters_username_and_password(String username, String password) {
           logger.info("Attempting to login with username: " + username + " and password: " + password);
           myApp.login(username, password);
       }

       @Then("user is now in the system")   
       public void user_is_now_in_the_system() {
           assertTrue("User should be logged in", myApp.isLoggedIn());
       }
    
    @Then("welcome message will be appeared")
    public void welcome_message_will_be_appeared() {
        String message = myApp.getMessage();
        assertTrue("Welcome message should be displayed", 
                   message.startsWith("Welcome to the Sweet Management System"));
    }
    
  //------------------------------------------------------------------------------------------  
    
    
    
    
    //   INVALID LOGIN SENARIO , THERE IS 2 STEPS NOT COMMON WITH PREVIOUS
  //------------------------------------------------------------------------------------------  
    @Then("user is now out of the system")    // هاي ستيب مش مشاركة مع اللي قبل موجودة في سيناريو ال انفاليد لوج ان
    public void user_is_now_out_of_the_system() {
        assertFalse("User should be logged out", myApp.isLoggedIn());
    }
    
    @Then("failed login msg will be appeared")
    public void failed_login_msg_will_be_appeared() {
        assertEquals("Login failed. Please check your username and password.", 
                     myApp.getMessage());
    }
   //------------------------------------------------------------------------------------------  
  
    
    //   VALID SIGN UP , ALL STEPS ARE COMON 
  //------------------------------------------------------------------------------------------ 
    @Given("the user is on the sign up page")
    public void the_user_is_on_the_sign_up_page() {
        myApp.startSignUp();
        assertTrue("Sign up process should be initiated", myApp.signUpFlag());
    }
    
    @When("the user enters a valid username {string}")
    public void the_user_enters_a_valid_username(String username) {
        this.username = username;
    }
    
    @When("the user enters a valid password {string}")
    public void the_user_enters_a_valid_password(String password) {
        this.password = password;
    }

    @When("the user confirms the password {string}")
    public void the_user_confirms_the_password(String confirmPassword) {
        this.confirmedPassword = confirmPassword;
    }
    
    @When("the user selects the role {string}")
    public void the_user_selects_the_role(String role) {
        this.role = role;
    }
    
    @When("the user enters the city {string}")
    public void the_user_enters_the_city(String city) {
        this.city = city;
    }

    @When("the user submits the sign up form")
    public void the_user_submits_the_sign_up_form() {
        if (password.equals(confirmedPassword)) {
            myApp.signUp(username, password, role, city);
        } else {
            myApp.setMessage("Password confirmation does not match");
            throw new AssertionError("Password confirmation does not match");
        }
    }
    @Then("the user should be registered successfully")
    public void the_user_should_be_registered_successfully() {
        assertTrue("User should be registered successfully", myApp.isLastOperationSuccessful());
        assertTrue("User should be logged in after registration", myApp.isLoggedIn());
    }
    
    @Then("a welcome message should be displayed")
    public void a_welcome_message_should_be_displayed() {
        String message = myApp.getMessage();
        assertTrue("Welcome message should be displayed", 
                   message.startsWith("Welcome! You have successfully signed up"));
    }
    
    @Then("the user should see an error message {string}")
    public void the_user_should_see_an_error_message(String errorMessage) {
        assertEquals(errorMessage, myApp.getMessage());  // the error message is Username already exists and this accour when the username is already exist
    }
    
    @Then("the user's role should be {string}")
    public void the_users_role_should_be(String expectedRole) {
        User currentuser = myApp.getCurrentUser();
        assertNotNull("Current user should not be null", currentuser);
        String actualRole = currentuser.getRole();
        if (expectedRole.equals("owner") && actualRole.equals("store owner")) {
            actualRole = "owner";
        }
        assertEquals("User's role should match", expectedRole, actualRole);
    }
    
    
   //---------------------------------------------------------------------------------- 

    
    
    
    
    
    //هذول خاصات في ملف الفيتشر لما اليوزر يتحكم في معلوماته وبدي ابرمجهن هون لانو الو علاقة في الموضوع 
    
    //---------------------------------------------------------------------------------- 
    @Given("the user is logged in")
    public void the_user_is_logged_in() {
        // First, ensure the test user exists
        if (myApp.getUserByUsername("testuser") == null) {
            myApp.signUp("testuser", "password123", "regular", "TestCity");
        }
        
        // Now attempt to log in
        myApp.login("testuser", "password123");
        
        // Check if login was successful
        assertTrue("User should be logged in", myApp.isLoggedIn());
        
        currentUser = myApp.getCurrentUser();
        assertNotNull("Current user should not be null", currentUser);
    }

    @When("the user updates their details")
    public void the_user_updates_their_details() {
        boolean result = myApp.updateUserDetails(currentUser.getUsername(), "newPassword123", "NewCity", "newemail@example.com", "1234567890");
        assertTrue("User details should be updated successfully", result);
    }

    @Then("the user's details should be updated")
    public void the_users_details_should_be_updated() {
        userDetails = myApp.getUserDetails(currentUser.getUsername());
        assertNotNull("User details should not be null", userDetails);
        assertEquals("City should be updated", "NewCity", userDetails.get("city"));
        assertEquals("Email should be updated", "newemail@example.com", userDetails.get("email"));
        assertEquals("Phone number should be updated", "1234567890", userDetails.get("phoneNumber"));
    }

    @When("the user retrieves their account details")
    public void the_user_retrieves_their_account_details() {
        userDetails = myApp.getUserDetails(currentUser.getUsername());
        assertNotNull("User details should not be null", userDetails);
    }

    @Then("the user should see their account information")
    public void the_user_should_see_their_account_information() {
        assertEquals("Username should match", currentUser.getUsername(), userDetails.get("username"));
        assertEquals("Role should match", currentUser.getRole(), userDetails.get("role"));
        assertNotNull("City should not be null", userDetails.get("city"));
        assertNotNull("Email should not be null", userDetails.get("email"));
        assertNotNull("Phone number should not be null", userDetails.get("phoneNumber"));
    }
    
    
    
    
    
    
    
    
    
}