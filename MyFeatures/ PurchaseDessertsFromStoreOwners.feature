Feature: Purchase Desserts from Store Owners

Scenario: User successfully purchases a dessert
  Given the user is logged in
  And there is a store owner "sweetshop" with a dessert "Chocolate Cake" priced at 15.99
  When the user selects "Chocolate Cake" from "sweetshop"
  And the user confirms the purchase
  Then the purchase should be successful
  And the user's order history should include "Chocolate Cake"

Scenario: User attempts to purchase an out-of-stock dessert
  Given the user is logged in
  And there is a store owner "candystore" with an out-of-stock dessert "Strawberry Tart"
  When the user selects "Strawberry Tart" from "candystore"
  Then the user should be informed that the item is out of stock