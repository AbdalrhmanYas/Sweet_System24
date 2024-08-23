Feature: Dynamic Discount Management

  Scenario: Store owner sets a discount on a product
    Given the user is logged in as a "store owner"
    And the store owner has a product "Chocolate Cake" with price 20.00
    When the store owner sets a discount of 10% on the product for 7 days
    Then the discount should be successfully applied
    And the discounted price of the product should be 18.00

  Scenario: Store owner removes a discount from a product
    Given the user is logged in as a "store owner"
    And the store owner has a product "Vanilla Cupcake" with price 5.00 and a 20% discount
    When the store owner removes the discount from the product
    Then the discount should be successfully removed
    And the price of the product should be back to 5.00

  Scenario: Discount expires automatically
    Given the user is logged in as a "store owner"
    And the store owner has a product "Apple Pie" with price 15.00
    When the store owner sets a discount of 15% on the product for 0 days
    Then the discount should be successfully applied
    But the discounted price of the product should be 15.00


  Scenario: Discount expires immediately for 0-day duration
    Given the user is logged in as a "store owner"
    And the store owner has a product "Apple Pie" with price 15.00
    When the store owner sets a discount of 15% on the product for 0 days
    Then the discount should be successfully applied
    But the discounted price of the product should be 15.00