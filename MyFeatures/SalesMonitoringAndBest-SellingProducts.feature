Feature: Sales Monitoring and Best-Selling Products

Scenario: Store owner monitors sales, profits, and identifies best-selling products
  Given the store owner "sweetshop" is logged in
  And "sweetshop" has the following products:
    | Product Name    | Price | Quantity | Sales |
    | Chocolate Cake  | 20.00 | 10       | 5     |
    | Vanilla Cupcake | 5.00  | 20       | 15    |
    | Apple Pie       | 15.00 | 8        | 3     |
  When the store owner requests a sales report
  Then the sales report should show total revenue of 220.00
  And the sales report should show "Vanilla Cupcake" as the best-selling product
  When the store owner requests a profit report
  Then the profit report should show the profit for each product