Feature: Recipe Exploration and Search

Scenario: User searches for recipes by keyword

  Given the user is logged in
  When the user searches for recipes containing "chocolate"
  Then the search results should contain recipes with "chocolate"
  And the number of search results should be greater than 0

Scenario: User browses recipes by category
  Given the user is logged in
  When the user browses recipes in the "Cakes" category
  Then all recipes in the results should be in the "Cakes" category
  And the number of browse results should be greater than 0   

Scenario: User searches for recipes with no results
  Given the user is logged in
  When the user searches for recipes containing "nonexistent recipe"
  Then the number of search results should be 0

Scenario: User browses all recipes
  Given the user is logged in
  When the user browses all recipes
  Then the number of browse results should equal the total number of recipes

Scenario: User filters recipes by dietary restriction
  Given the user is logged in
  When the user filters recipes for "gluten-free" diet
  Then all recipes in the results should be "gluten-free"

Scenario: User filters recipes by allergy
  Given the user is logged in
  When the user filters recipes to exclude "nuts"
  Then none of the recipes in the results should contain "nuts"

Scenario: User combines search and dietary filter
  Given the user is logged in
  When the user searches for "cake" with a "vegan" dietary filter
  Then the search results should contain "vegan" "cake" recipes
  And the number of search results should be greater than 0