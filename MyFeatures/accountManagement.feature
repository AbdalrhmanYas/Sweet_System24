Feature: Store owner and suppliers management

  Scenario Outline: Store owner changes his password
    Given The store owner "<username>" with "<oldpass>" is logged in
    When Store owner enter "<username>" and "<oldpass>" and "<newpass>"
    Then Password will change

    Examples:
      | username     | oldpass     | newpass     |
      | storeowner_x | storeowner2 | storeowner3 |