Feature: Product Management

  Scenario: Store Owner adds a new product
    Given the user is logged in as a "store owner"
    When the user adds a product "Test Product" with price 10.99 and quantity 100
    Then the product should be added successfully

  Scenario: Supplier adds a new product
    Given the user is logged in as a "supplier"
    When the user adds a product "Raw Material" with price 5.99 and quantity 1000
    Then the product should be added successfully

  Scenario: Store Owner updates a product
    Given the user is logged in as a "store owner"
    And the user adds a product "Test Product" with price 10.99 and quantity 100
    When the user updates the product "Test Product" with new price 15.99 and new quantity 50
    Then the product should be updated successfully

  Scenario: Store Owner removes a product
    Given the user is logged in as a "store owner"
    And the user adds a product "Test Product" with price 10.99 and quantity 100
    When the user removes the product "Test Product"
    Then the product should be removed successfully