Feature: User Sign In and Sign Up

  Scenario: Valid Admin Login
    Given user is not in the sweet system
    When user enters username "admin1" and password "adminpass1"
    Then user is now in the system
    And welcome message will be appeared
    And the user's role should be "admin"

  Scenario: Valid Supplier Login
    Given user is not in the sweet system
    When user enters username "supplier1" and password "supplierpass1"
    Then user is now in the system
    And welcome message will be appeared
    And the user's role should be "supplier"

  Scenario: Valid Owner Login
    Given user is not in the sweet system
    When user enters username "owner1" and password "ownerpass1"
    Then user is now in the system
    And welcome message will be appeared
    And the user's role should be "owner"

  Scenario: Valid Regular User Login
    Given user is not in the sweet system
    When user enters username "user1" and password "userpass1"
    Then user is now in the system
    And welcome message will be appeared
    And the user's role should be "regular"

  Scenario: Invalid Login
    Given user is not in the sweet system
    When user enters username "wronguser" and password "wrongpass"
    Then user is now out of the system
    And failed login msg will be appeared
    
    
#--------------------------------------------------------------------------------------------


  Scenario: Successful Admin Sign Up
    Given the user is on the sign up page
    When the user enters a valid username "newadmin"
    And the user enters a valid password "adminpass123"
    And the user confirms the password "adminpass123"
    And the user selects the role "admin"
    And the user submits the sign up form
    Then the user should be registered successfully
    And a welcome message should be displayed
    And the user's role should be "admin"

  Scenario: Successful Supplier Sign Up
    Given the user is on the sign up page
    When the user enters a valid username "newsupplier"
    And the user enters a valid password "supplierpass123"
    And the user confirms the password "supplierpass123"
    And the user selects the role "supplier"
    And the user submits the sign up form
    Then the user should be registered successfully
    And a welcome message should be displayed
    And the user's role should be "supplier"

  Scenario: Successful Owner Sign Up
    Given the user is on the sign up page
    When the user enters a valid username "newowner"
    And the user enters a valid password "ownerpass123"
    And the user confirms the password "ownerpass123"
    And the user selects the role "owner"
    And the user submits the sign up form
    Then the user should be registered successfully
    And a welcome message should be displayed
    And the user's role should be "owner"

  Scenario: Successful Regular User Sign Up
    Given the user is on the sign up page
    When the user enters a valid username "newuser"
    And the user enters a valid password "userpass123"
    And the user confirms the password "userpass123"
    And the user selects the role "regular"
    And the user submits the sign up form
    Then the user should be registered successfully
    And a welcome message should be displayed
    And the user's role should be "regular"

  Scenario: Sign Up with Existing Username
    Given the user is on the sign up page
    When the user enters a valid username "admin1"
    And the user enters a valid password "newpass456"
    And the user confirms the password "newpass456"
    And the user selects the role "admin"
    And the user submits the sign up form
    Then the user should see an error message "Username already exists"