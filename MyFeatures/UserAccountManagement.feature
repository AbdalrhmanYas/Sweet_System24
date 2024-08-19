Feature: User Account Management

  Scenario: User updates their account details
  
    Given the user is logged in
    When the user updates their details
    Then the user's details should be updated

  Scenario: User retrieves their account information
  
    Given the user is logged in
    When the user retrieves their account details
    Then the user should see their account information