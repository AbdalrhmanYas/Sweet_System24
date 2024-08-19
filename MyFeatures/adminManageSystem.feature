Feature: Admin Management System

Scenario: Admin manages user accounts
  Given the admin is logged in
  When the admin views the list of user accounts
  Then the admin should see a list of all users including store owners and suppliers
  When the admin updates the role of user "john_doe" to "store owner"
  Then the role of user "john_doe" should be "store owner"
  When the admin deletes the account of user "inactive_user"
  Then the user "inactive_user" should no longer exist in the system

Scenario: Admin monitors profits and generates financial reports
  Given the admin is logged in
  When the admin requests a financial report for the last month
  Then the system should generate a report showing total revenue, expenses, and profit

Scenario: Admin identifies best-selling products in each store
  Given the admin is logged in
  When the admin requests the best-selling products report
  Then the system should display a list of top 5 products for each store

Scenario: Admin gathers statistics on registered users by city
  Given the admin is logged in
  When the admin requests user statistics by city
  Then the system should display the number of users in each city

Scenario: Admin manages shared content
  Given the admin is logged in
  When the admin views the list of shared recipes
  Then the admin should see all recipes in the system
  When the admin deletes an inappropriate recipe
  Then the deleted recipe should no longer be visible in the system

Scenario: Admin manages user feedback
  Given the admin is logged in
  When the admin views the list of user feedback
  Then the admin should see all feedback entries
  When the admin marks a feedback entry as resolved
  Then the feedback entry should be marked as resolved in the system