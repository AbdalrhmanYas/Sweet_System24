Feature: Order Processing and Tracking

  Scenario Outline: Process and track orders
    Given I am logged in with a "<role>"
    When I process an order with "<id>" and status "<status>" to "<newStatus>"
    Then the system should reflect the order with "<id>" as "<newStatus>"

    Examples:
      | id  | role     | status    | newStatus   |
      | 000 | owner    | Shipped   | Delivered   |
      | 001 | supplier | Delivered | Collected   |
      | 002 | owner    | Cancelled | In Progress |
