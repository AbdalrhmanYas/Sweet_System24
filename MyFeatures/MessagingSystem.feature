Feature: Communication via Messaging System

  Scenario Outline: Use messaging system to communicate with other users and suppliers
    Given I am logged in as a "<role>"
    When I use the messaging system to communicate with "<recipient>"
    Then I should see the message sent to "<recipient>"

    Examples:
      | role  | recipient |
      | owner | supplier  |
      | owner | admin     |
