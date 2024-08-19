Feature: Communication and Feedback

Scenario: User sends a message to a store owner and provides feedback on a product
  Given the user "customer1" is logged in
  And there is a store owner "sweetshop" with a product "Chocolate Cake"
  When the user sends a message "Is the Chocolate Cake gluten-free?" to "sweetshop"
  Then the message should be successfully sent
  When the user purchases "Chocolate Cake" from "sweetshop"
  And the user provides feedback "Delicious cake!" for the purchased product
  Then the feedback should be successfully recorded

Scenario: User provides feedback on a shared recipe
  Given the user "customer1" is logged in
  And there is a shared recipe "Vegan Brownies"
  When the user provides feedback "Great recipe, very easy to follow!" for the recipe "Vegan Brownies"
  Then the feedback should be successfully recorded