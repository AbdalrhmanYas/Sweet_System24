package MySystem;
import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import java.time.LocalDate;

public class Main {
    public static void mainMenu() {
        System.out.println("\n===== MAIN MENU =====");
        System.out.println("1-) Login to the system");
        System.out.println("2-) Sign up for a new account"); // abood don't forget to enhance it as you imagine
        System.out.println("3-) Log out of the system");
        System.out.println("4-) Exit");
        System.out.println("Please enter your choice:");
    }

    public static void adminMenu() {
        System.out.println("\n===== Admin Dashboard =====");
        System.out.println("1-) Manage accounts");
        System.out.println("2-) Monitor profits");
        System.out.println("3-) Generate financial reports");
        System.out.println("4-) Identify best-selling products in each store");
        System.out.println("5-) Display statistics on registered users by City");
        System.out.println("6-) Manage the content shared on the system");
        System.out.println("7-) Manage user feedback");
        System.out.println("8-) Log out from the system");
        System.out.println("Please enter your choice:");
    }

    public static void userMenu() {
        System.out.println("\n===== User Dashboard =====");
        System.out.println("1-) Manage personal account");
        System.out.println("2-) Browse and search for dessert recipes");
        System.out.println("3-) Purchase desserts directly from store owners");
        System.out.println("4-) Provide feedback on purchased products and shared recipes");
        System.out.println("5-) Log out from the system");
        System.out.println("Please enter your choice:");
    }






    public static void loginProcess(Scanner sc, sweetSys s) {
        System.out.print("Enter your username: ");
        String username = sc.nextLine();
        System.out.print("Enter your password: ");
        String password = sc.nextLine();

        s.login(username, password);

        if (s.isLoggedIn()) {
            User currentUser = s.getCurrentUser();
            System.out.println(s.getMessage());
            if (currentUser instanceof Admin) {
                handleAdminMenu(sc, s);
            } else if (currentUser instanceof Supplier) {
                handleSupplierMenu(sc, s, currentUser);
            } else if (currentUser instanceof StoreOwner) {
                handleStoreOwnerMenu(sc, s, currentUser);
            } else {
                handleUserMenu(sc, s, currentUser);
            }
        } else {
            System.out.println(s.getMessage());
        }
    }



    public static void signUp(Scanner sc, sweetSys s) {
        System.out.println("\n===== SIGN UP =====");
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        System.out.print("Enter city: ");
        String city = sc.nextLine();

        System.out.println("Select role:");
        System.out.println("1-) Admin");
        System.out.println("2-) Supplier");
        System.out.println("3-) Owner");
        System.out.println("4-) Regular User");
        System.out.print("Enter your choice: ");

        String role = "regular";
        try {
            int roleChoice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (roleChoice) {
                case 1:
                    role = "admin";
                    break;
                case 2:
                    role = "supplier";
                    break;
                case 3:
                    role = "owner";
                    break;
                case 4:
                    role = "regular";
                    break;
                default:
                    System.out.println("Invalid role choice. Defaulting to regular user.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Defaulting to regular user.");
            sc.nextLine(); // Clear the invalid input
        }

        s.signUp(username, password, role, city);
        System.out.println(s.getMessage());
    }





    public static void manageAccountsMenu(Scanner sc, sweetSys s) {
        int choice;
        do {
            System.out.println("\n===== Manage Accounts Menu =====");
            System.out.println("1-) Get suppliers and owners");
            System.out.println("2-) Update user role");
            System.out.println("3-) Remove user");
            System.out.println("4-) Back to Admin Menu");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1: {
                    displayUsersLists(s);
                    break;
                }
                case 2: {
                    updateUserRole(sc, s);
                    break;
                }
                case 3: {
                    deleteUser(sc, s);
                    break;
                }
                case 4: {
                    System.out.println("Returning to Admin Menu...");
                    break;
                }
                default: {
                    System.out.println("Invalid choice");
                    break;
                }
            }
        } while (choice != 4);
    }

    public static void displayUsersLists(sweetSys s) {
        List<User> ownerList = s.getAllOwners();
        List<User> supplierList = s.getAllSuppliers();
        System.out.println("\n===== Users in the System =====");
        //System.out.println("------------------------------------");
        System.out.println(repeat("-", 36));
        System.out.println("Store Owners:");
        for (User u : ownerList) {
            displayUserDetails(s, u.getUsername());
        }
        System.out.println("------------------------------------");
        System.out.println("Suppliers:");
        for (User u : supplierList) {
            displayUserDetails(s, u.getUsername());
        }
        System.out.println("------------------------------------");
    }

    public static void displayUserDetails(sweetSys s, String username) {
        Map<String, String> details = s.getUserDetails(username);
        System.out.println("Username: " + details.get("username"));
        System.out.println("Role: " + details.get("role"));
        System.out.println("City: " + details.get("city"));
        System.out.println("Email: " + details.get("email"));
        System.out.println("Phone Number: " + details.get("phoneNumber"));
        System.out.println();
    }

    public static void updateUserRole(Scanner sc, sweetSys s) {
        System.out.print("Enter the User's name you want to change its role in the system: ");
        String userName = sc.nextLine();
        System.out.print("Enter the new role for " + userName + ": ");
        String newRole = sc.nextLine();
        s.updateUserRole(userName, newRole);
        System.out.println(s.getMessage());
    }

    public static void deleteUser(Scanner sc, sweetSys s) {
        System.out.print("Enter the User's name you want to delete from the system: ");
        String userName = sc.nextLine();
        s.deleteUser(userName);
        System.out.println(s.getMessage());
    }

    public static void manageUserAccount(Scanner sc, sweetSys s, User user) {
        int choice;
        do {
            System.out.println("\n===== Manage Personal Account =====");
            System.out.println("1-) View account details");
            System.out.println("2-) Update account details");
            System.out.println("3-) Back to User Dashboard");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1: {
                    displayUserDetails(s, user.getUsername());
                    break;
                }
                case 2: {
                    updateUserDetails(sc, s, user);
                    break;
                }
                case 3: {
                    System.out.println("Returning to User Dashboard...");
                    break;
                }
                default: {
                    System.out.println("Invalid choice");
                    break;
                }
            }
        } while (choice != 3);
    }

    public static void updateUserDetails(Scanner sc, sweetSys s, User user) {
        System.out.println("Enter new details (leave blank to keep current value):");

        System.out.print("New password: ");
        String newPassword = sc.nextLine();

        System.out.print("New city: ");
        String newCity = sc.nextLine();

        System.out.print("New email: ");
        String newEmail = sc.nextLine();

        System.out.print("New phone number: ");
        String newPhoneNumber = sc.nextLine();

        boolean updated = s.updateUserDetails(user.getUsername(), newPassword, newCity, newEmail, newPhoneNumber);
        if (updated) {
            System.out.println("Account details updated successfully.");
        } else {
            System.out.println("Failed to update account details.");
        }
    }






    public static void browseAndSearchRecipes(Scanner sc, sweetSys s) {
        int choice;
        do {
            System.out.println("\n===== Browse and Search Recipes =====");
            System.out.println("1-) Search recipes by keyword");
            System.out.println("2-) Browse recipes by category");
            System.out.println("3-) Browse all recipes");
            System.out.println("4-) Filter recipes by dietary restriction");
            System.out.println("5-) Filter recipes by allergy");
            System.out.println("6-) Search with dietary filter");
            System.out.println("7-) Back to User Dashboard");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    searchRecipesByKeyword(sc, s);
                    break;
                case 2:
                    browseRecipesByCategory(sc, s);
                    break;
                case 3:
                    browseAllRecipes(s);
                    break;
                case 4:
                    filterRecipesByDiet(sc, s);
                    break;
                case 5:
                    filterRecipesByAllergy(sc, s);
                    break;
                case 6:
                    searchRecipesWithDietaryFilter(sc, s);
                    break;
                case 7:
                    System.out.println("Returning to User Dashboard...");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 7);
    }



    public static void searchRecipesByKeyword(Scanner sc, sweetSys s) {
        System.out.print("Enter keyword to search for: ");
        String keyword = sc.nextLine();
        List<Recipe> results = s.searchRecipes(keyword);
        displayRecipeResults(results);
    }

    public static void browseRecipesByCategory(Scanner sc, sweetSys s) {
        System.out.print("Enter category to browse (or leave blank for all categories): ");
        String category = sc.nextLine();
        List<Recipe> results = s.browseRecipes(category);
        displayRecipeResults(results);
    }

    public static void browseAllRecipes(sweetSys s) {
        List<Recipe> results = s.getAllRecipes();
        displayRecipeResults(results);
    }



    public static void filterRecipesByDiet(Scanner sc, sweetSys s) {
        System.out.print("Enter dietary restriction (e.g., gluten-free, vegan): ");
        String diet = sc.nextLine();
        List<Recipe> results = s.filterRecipesByDiet(diet);
        displayRecipeResults(results);
    }

    public static void filterRecipesByAllergy(Scanner sc, sweetSys s) {
        System.out.print("Enter allergy to exclude (e.g., nuts, dairy): ");
        String allergen = sc.nextLine();
        List<Recipe> results = s.filterRecipesByAllergy(allergen);
        displayRecipeResults(results);
    }



    public static void searchRecipesWithDietaryFilter(Scanner sc, sweetSys s) {
        System.out.print("Enter keyword to search for: ");
        String keyword = sc.nextLine();
        System.out.print("Enter dietary restriction: ");
        String diet = sc.nextLine();
        List<Recipe> results = s.searchRecipesWithDietaryFilter(keyword, diet);
        displayRecipeResults(results);
    }

    public static void displayRecipeResults(List<Recipe> recipes) {
        if (recipes.isEmpty()) {
            System.out.println("No recipes found.");
        } else {
            System.out.println("\nFound " + recipes.size() + " recipes:");
            for (Recipe recipe : recipes) {
                System.out.println("- " + recipe.getName() + " (Category: " + recipe.getCategory() + ")");
            }
        }
    }



    public static void purchaseDesserts(Scanner sc, sweetSys s, User user) {
        System.out.println("\n===== Purchase Desserts =====");
        System.out.print("Enter store owner username: ");
        String storeOwnerUsername = sc.nextLine();
        User storeOwner = s.getUserByUsername(storeOwnerUsername);

        if (storeOwner == null || !(storeOwner instanceof StoreOwner)) {
            System.out.println("Invalid store owner.");
            return;
        }

        System.out.print("Enter dessert name: ");
        String dessertName = sc.nextLine();

        Product dessert = ((StoreOwner) storeOwner).getProductByName(dessertName);
        if (dessert == null) {
            System.out.println("Dessert not found.");
            return;
        }

        if (dessert.getQuantity() <= 0) {
            System.out.println("Sorry, " + dessertName + " is out of stock.");
            return;
        }

        System.out.println("Dessert: " + dessertName);
        System.out.println("Price: $" + dessert.getPrice());
        System.out.print("Confirm purchase (yes/no): ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            s.purchaseProduct(user.getUsername(), storeOwnerUsername, dessertName);
            if (s.isLastOperationSuccessful()) {
                System.out.println("Purchase successful!");
                displayOrderHistory(s, user);
            } else {
                System.out.println("Purchase failed: " + s.getMessage());
            }
        } else {
            System.out.println("Purchase cancelled.");
        }
    }


    public static void displayOrderHistory(sweetSys s, User user) {
        List<String> orderHistory = s.getUserOrderHistory(user.getUsername());
        System.out.println("\nYour Order History:");
        if (orderHistory.isEmpty()) {
            System.out.println("No orders yet.");
        } else {
            for (String order : orderHistory) {
                System.out.println("- " + order);
            }
        }
    }




    // every thing related to admin
    //---------------------------------------------------------------------------------------




    public static void monitorProfits(sweetSys s) {
        System.out.println("\n===== Monitor Profits =====");
        Map<String, Double> profitBreakdown = s.calculateProfits();

        // Display overall profit summary
        System.out.printf("Total Revenue: $%.2f%n", profitBreakdown.get("Total Revenue"));
        System.out.printf("Total Costs: $%.2f%n", profitBreakdown.get("Total Costs"));
        System.out.printf("Total Profit: $%.2f%n", profitBreakdown.get("Total Profit"));
        System.out.printf("Profit Margin: %.2f%%%n", profitBreakdown.get("Profit Margin (%)"));

        System.out.println("\nProfit Breakdown by Store:");
        for (Map.Entry<String, Double> entry : profitBreakdown.entrySet()) {
            if (!entry.getKey().startsWith("Total") && !entry.getKey().equals("Profit Margin (%)")) {
                System.out.printf("%s: $%.2f%n", entry.getKey(), entry.getValue());
            }
        }

        // Offer additional options
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nAdditional Options:");
            System.out.println("1-) View profit trend");
            System.out.println("2-) Analyze top-performing products");
            System.out.println("3-) Return to Admin Dashboard");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewProfitTrend(s);
                    break;
                case 2:
                    analyzeTopProducts(s);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewProfitTrend(sweetSys s) {
        System.out.println("\n===== Profit Trend =====");
        // This is a placeholder. In a real system, you'd fetch historical data.
        System.out.println("Profit trend analysis is not available in this version.");
        // Future enhancement: Implement actual trend analysis using historical data
    }

    private static void analyzeTopProducts(sweetSys s) {
        System.out.println("\n===== Top-Performing Products =====");
        Map<String, List<Product>> bestSellers = s.getBestSellingProductss();
        for (Map.Entry<String, List<Product>> entry : bestSellers.entrySet()) {
            System.out.println("\nStore: " + entry.getKey());
            List<Product> products = entry.getValue();
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                System.out.printf("%d. %s - Sold: %d, Revenue: $%.2f%n",
                        i+1, product.getName(), product.getSalesQuantity(), product.getRevenue());
            }
        }
    }





    public static void generateFinancialReports(sweetSys s) {
        System.out.println("\n===== Generate Financial Reports =====");
        String report = s.generateFinancialReport();
        System.out.println(report);

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1-) Save report to file");
            System.out.println("2-) Email report");
            System.out.println("3-) Return to Admin Dashboard");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    saveReportToFile(report);
                    break;
                case 2:
                    emailReport(report);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void saveReportToFile(String report) {
        System.out.println("Saving report to file...");
        // Placeholder for file saving functionality
        System.out.println("Report saved successfully. (Note: This is a placeholder message)");
    }

    private static void emailReport(String report) {
        System.out.println("Preparing to email report...");
        // Placeholder for email functionality
        System.out.println("Report emailed successfully. (Note: This is a placeholder message)");
    }





    // identify best saling products :

    public static void identifyBestSellingProducts(sweetSys s) {
        System.out.println("\n===== Identify Best-Selling Products in Each Store =====");

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of top products to display for each store: ");
        int topN = sc.nextInt();
        sc.nextLine(); // Consume newline

        Map<String, List<Map<String, Object>>> bestSellingProducts = s.identifyBestSellingProducts(topN);

        if (bestSellingProducts.isEmpty()) {
            System.out.println("No sales data available.");
            return;
        }

        for (Map.Entry<String, List<Map<String, Object>>> entry : bestSellingProducts.entrySet()) {
            System.out.println("\nStore: " + entry.getKey());
            List<Map<String, Object>> products = entry.getValue();

            if (products.isEmpty()) {
                System.out.println("  No sales data available for this store.");
            } else {
                System.out.printf("  %-20s %-15s %-15s %-15s%n", "Product", "Sales Quantity", "Revenue", "Profit Margin");
                //System.out.println("  " + "-".repeat(70));
                System.out.println("  " + repeat("-", 70));


                for (Map<String, Object> product : products) {
                    System.out.printf("  %-20s %-15d $%-14.2f %.2f%%%n",
                            product.get("name"),
                            (int) product.get("salesQuantity"),
                            (double) product.get("revenue"),
                            (double) product.get("profitMargin"));
                }
            }
        }

        // Additional analysis options
        while (true) {
            System.out.println("\nAdditional Options:");
            System.out.println("1-) Compare best-selling products across stores");
            System.out.println("2-) Analyze product performance trends");
            System.out.println("3-) Return to Admin Dashboard");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    compareBestSellingProducts(bestSellingProducts);
                    break;
                case 2:
                    analyzeProductPerformanceTrends(s);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void compareBestSellingProducts(Map<String, List<Map<String, Object>>> bestSellingProducts) {
        System.out.println("\n===== Comparison of Best-Selling Products Across Stores =====");
        Map<String, Double> productTotalSales = new HashMap<>();

        for (List<Map<String, Object>> storeProducts : bestSellingProducts.values()) {
            for (Map<String, Object> product : storeProducts) {
                String name = (String) product.get("name");
                double sales = (double) product.get("revenue");
                productTotalSales.put(name, productTotalSales.getOrDefault(name, 0.0) + sales);
            }
        }

        List<Map.Entry<String, Double>> sortedProducts = new ArrayList<>(productTotalSales.entrySet());
        sortedProducts.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        System.out.printf("%-20s %-15s%n", "Product", "Total Sales");
        //System.out.println("-".repeat(36));
        System.out.println(repeat("-", 36));

        for (Map.Entry<String, Double> entry : sortedProducts) {
            System.out.printf("%-20s $%-14.2f%n", entry.getKey(), entry.getValue());
        }
    }

    private static void analyzeProductPerformanceTrends(sweetSys s) {
        System.out.println("\n===== Product Performance Trends =====");
        // This is a placeholder for future implementation
        System.out.println("Product performance trend analysis is not available in this version.");
        // Future enhancement: Implement actual trend analysis using historical data
    }



    // statistics for user by city
    //----------------------------------
    public static void displayUserStatisticsByCity(sweetSys s) {
        System.out.println("\n===== Statistics on Registered Users by City =====");

        Map<String, Integer> cityStats = s.getUserStatisticsByCity();

        if (cityStats.isEmpty()) {
            System.out.println("No user data available.");
            return;
        }

        System.out.printf("%-20s %-10s%n", "City", "Users");
       // System.out.println("-".repeat(31));
        System.out.println(repeat("-", 31));


        int totalUsers = 0;
        for (Map.Entry<String, Integer> entry : cityStats.entrySet()) {
            System.out.printf("%-20s %-10d%n", entry.getKey(), entry.getValue());
            totalUsers += entry.getValue();
        }

        //System.out.println("-".repeat(31));
        System.out.println(repeat("-", 31));

        System.out.printf("%-20s %-10d%n", "Total", totalUsers);

        // Additional analysis options
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nAdditional Options:");
            System.out.println("1-) View city with most users");
            System.out.println("2-) View city with least users");
            System.out.println("3-) View percentage distribution");
            System.out.println("4-) Return to Admin Dashboard");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewCityWithMostUsers(cityStats);
                    break;
                case 2:
                    viewCityWithLeastUsers(cityStats);
                    break;
                case 3:
                    viewPercentageDistribution(cityStats, totalUsers);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewCityWithMostUsers(Map<String, Integer> cityStats) {
        Map.Entry<String, Integer> maxEntry = Collections.max(cityStats.entrySet(), Map.Entry.comparingByValue());
        System.out.printf("City with most users: %s (%d users)%n", maxEntry.getKey(), maxEntry.getValue());
    }

    private static void viewCityWithLeastUsers(Map<String, Integer> cityStats) {
        Map.Entry<String, Integer> minEntry = Collections.min(cityStats.entrySet(), Map.Entry.comparingByValue());
        System.out.printf("City with least users: %s (%d users)%n", minEntry.getKey(), minEntry.getValue());
    }

    private static void viewPercentageDistribution(Map<String, Integer> cityStats, int totalUsers) {
        System.out.println("\nPercentage Distribution of Users by City");
        System.out.printf("%-20s %-10s %-10s%n", "City", "Users", "Percentage");
       // System.out.println("-".repeat(42));
        System.out.println(repeat("-", 42));


        for (Map.Entry<String, Integer> entry : cityStats.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / totalUsers;
            System.out.printf("%-20s %-10d %.2f%%%n", entry.getKey(), entry.getValue(), percentage);
        }
    }

    //--------------------------------------------------------------------------------------------------------




    // recipes
    //-----------------

    public static void manageSharedContent(sweetSys s) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== Manage Shared Content =====");
            System.out.println("1-) View all recipes");
            System.out.println("2-) Delete a recipe");
            System.out.println("3-) Return to Admin Dashboard");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAllRecipes(s);
                    break;
                case 2:
                    deleteRecipe(s, sc);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewAllRecipes(sweetSys s) {
        List<Recipe> allRecipes = s.getAllRecipes();
        if (allRecipes.isEmpty()) {
            System.out.println("No recipes found in the system.");
        } else {
            System.out.println("\nAll Recipes in the System:");
            System.out.printf("%-5s %-30s %-20s %-20s%n", "ID", "Name", "Category", "Author");
            //System.out.println("-".repeat(80));
            System.out.println(repeat("-", 80));

            for (Recipe recipe : allRecipes) {
                System.out.printf("%-5d %-30s %-20s %-20s%n",
                        recipe.getId(), recipe.getName(), recipe.getCategory(), recipe.getAuthor());
            }
        }
    }

    private static void deleteRecipe(sweetSys s, Scanner sc) {
        System.out.print("Enter the ID of the recipe you want to delete: ");
        int recipeId = sc.nextInt();
        sc.nextLine(); // Consume newline

        Recipe recipe = s.getRecipeById(recipeId);
        if (recipe == null) {
            System.out.println("Recipe not found.");
            return;
        }

        System.out.println("Recipe details:");
        System.out.printf("ID: %d%nName: %s%nCategory: %s%nAuthor: %s%n",
                recipe.getId(), recipe.getName(), recipe.getCategory(), recipe.getAuthor());

        System.out.print("Are you sure you want to delete this recipe? (yes/no): ");
        String confirmation = sc.nextLine().toLowerCase();

        if (confirmation.equals("yes")) {
            boolean deleted = s.deleteRecipe(recipeId);
            if (deleted) {
                System.out.println("Recipe deleted successfully.");
            } else {
                System.out.println("Failed to delete the recipe.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }


    //-------------------------------------------------------------------------------------------------------------




    // feedback fun's

    //----------------------------------------------------------------------------------

    public static void provideFeedback(Scanner sc, sweetSys s, User user) {
        System.out.println("\n===== Provide Feedback =====");
        System.out.println("1-) Provide feedback on purchased product");
        System.out.println("2-) Provide feedback on shared recipe");
        System.out.println("3-) Back to User Dashboard");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                providePurchasedProductFeedback(sc, s, user);
                break;
            case 2:
                provideSharedRecipeFeedback(sc, s, user);
                break;
            case 3:
                System.out.println("Returning to User Dashboard...");
                break;
            default:
                System.out.println("Invalid choice");
        }
    }


    public static void providePurchasedProductFeedback(Scanner sc, sweetSys s, User user) {
        List<String> orderHistory = s.getUserOrderHistory(user.getUsername());
        if (orderHistory.isEmpty()) {
            System.out.println("You haven't purchased any products yet.");
            return;
        }

        System.out.println("Your purchased products:");
        for (int i = 0; i < orderHistory.size(); i++) {
            System.out.println((i + 1) + ". " + orderHistory.get(i));
        }

        System.out.print("Enter the number of the product you want to provide feedback for: ");
        int productChoice = sc.nextInt();
        sc.nextLine(); // Consume newline

        if (productChoice < 1 || productChoice > orderHistory.size()) {
            System.out.println("Invalid product number.");
            return;
        }

        String productName = orderHistory.get(productChoice - 1);
        System.out.print("Enter your feedback for " + productName + ": ");
        String feedbackContent = sc.nextLine();

        boolean success = s.provideFeedback(user.getUsername(), productName, feedbackContent, false);
        if (success) {
            System.out.println("Feedback submitted successfully.");
        } else {
            System.out.println("Failed to submit feedback: " + s.getMessage());
        }
    }



    public static void provideSharedRecipeFeedback(Scanner sc, sweetSys s, User user) {
        List<Recipe> allRecipes = s.getAllRecipes();
        if (allRecipes.isEmpty()) {
            System.out.println("There are no shared recipes available.");
            return;
        }

        System.out.println("Shared recipes:");
        for (int i = 0; i < allRecipes.size(); i++) {
            System.out.println((i + 1) + ". " + allRecipes.get(i).getName());
        }

        System.out.print("Enter the number of the recipe you want to provide feedback for: ");
        int recipeChoice = sc.nextInt();
        sc.nextLine(); // Consume newline

        if (recipeChoice < 1 || recipeChoice > allRecipes.size()) {
            System.out.println("Invalid recipe number.");
            return;
        }

        Recipe selectedRecipe = allRecipes.get(recipeChoice - 1);
        System.out.print("Enter your feedback for " + selectedRecipe.getName() + ": ");
        String feedbackContent = sc.nextLine();

        boolean success = s.provideFeedback(user.getUsername(), selectedRecipe.getName(), feedbackContent, true);
        if (success) {
            System.out.println("Feedback submitted successfully.");
        } else {
            System.out.println("Failed to submit feedback: " + s.getMessage());
        }
    }




    // feed back
    //---------------------

    public static void manageUserFeedback(sweetSys s) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== Manage User Feedback =====");
            System.out.println("1-) View all feedback");
            System.out.println("2-) Mark feedback as resolved");
            System.out.println("3-) Return to Admin Dashboard");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAllFeedback(s);
                    break;
                case 2:
                    resolveFeedback(s, sc);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewAllFeedback(sweetSys s) {
        List<Feedback> allFeedback = s.getAllFeedback();
        if (allFeedback.isEmpty()) {
            System.out.println("No feedback found in the system.");
        } else {
            System.out.println("\nAll Feedback in the System:");
            System.out.printf("%-5s %-20s %-50s %-10s %-10s%n", "ID", "Author", "Content", "Resolved", "Date");
            //System.out.println("-".repeat(100));
            System.out.println(repeat("-", 100));

            for (Feedback feedback : allFeedback) {
                System.out.printf("%-5d %-20s %-50s %-10s %-10s%n",
                        feedback.getId(),
                        feedback.getAuthor(),
                        feedback.getContent().substring(0, Math.min(feedback.getContent().length(), 47)) + "...",
                        feedback.isResolved() ? "Yes" : "No",
                        feedback.getDate().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        }
    }

    private static void resolveFeedback(sweetSys s, Scanner sc) {
        System.out.print("Enter the ID of the feedback you want to mark as resolved: ");
        int feedbackId = sc.nextInt();
        sc.nextLine(); // Consume newline

        Feedback feedback = s.getFeedbackById(feedbackId);
        if (feedback == null) {
            System.out.println("Feedback not found.");
            return;
        }

        System.out.println("Feedback details:");
        System.out.printf("ID: %d%nAuthor: %s%nContent: %s%nResolved: %s%nDate: %s%n",
                feedback.getId(), feedback.getAuthor(), feedback.getContent(),
                feedback.isResolved() ? "Yes" : "No",
                feedback.getDate().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        if (feedback.isResolved()) {
            System.out.println("This feedback is already marked as resolved.");
            return;
        }

        System.out.print("Are you sure you want to mark this feedback as resolved? (yes/no): ");
        String confirmation = sc.nextLine().toLowerCase();

        if (confirmation.equals("yes")) {
            s.resolveFeedback(feedbackId);
            if (s.isLastOperationSuccessful()) {
                System.out.println("Feedback marked as resolved successfully.");
            } else {
                System.out.println("Failed to mark feedback as resolved.");
            }
        } else {
            System.out.println("Operation cancelled.");
        }
    }




//USER
    public static void handleUserMenu(Scanner sc, sweetSys s, User user) {
        int userChoice;
        do {
            userMenu();
            userChoice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (userChoice) {
                case 1: {
                    manageUserAccount(sc, s, user);
                    break;
                }
                case 2: {
                    browseAndSearchRecipes(sc, s);
                    break;
                }
                case 3: {
                    purchaseDesserts(sc, s, user);
                    break;
                }
                case 4: {
                    provideFeedback(sc, s, user);
                    break;
                }
                case 5: {
                    System.out.println("Logging out from the user dashboard...");
                    break;
                }
                default: {
                    System.out.println("Invalid choice");
                    break;
                }
            }
        } while (userChoice != 5);
    }

// ADMIN
    public static void handleAdminMenu(Scanner sc, sweetSys s) {
        int adminChoice;
        do {
            adminMenu();
            adminChoice = sc.nextInt();
            sc.nextLine(); // Consume newline
            switch (adminChoice) {
                case 1: {
                    manageAccountsMenu(sc, s);
                    break;
                }
                case 2: {
                    monitorProfits(s);
                    break;
                }
                case 3: {
                    generateFinancialReports(s);
                    break;
                }
                case 4: {
                    identifyBestSellingProducts(s);
                    break;
                }
                case 5: {
                    displayUserStatisticsByCity(s);
                    break;
                }
                case 6: {
                    manageSharedContent(s);
                    break;
                }
                case 7: {
                    manageUserFeedback(s);
                    break;
                }
                case 8: {
                    System.out.println("Logging out from the admin dashboard...");
                    break;
                }
                default: {
                    System.out.println("Invalid choice");
                    break;
                }
            }
        } while (adminChoice != 8);
    }

// STORE OWNER FUNCTIONS AND HANDLING
//--------------------------------------------------------------------------------------------------------
public static void manageProducts(Scanner sc, sweetSys s, StoreOwner owner) {
    int choice;
    do {
        System.out.println("\n===== Manage Products =====");
        System.out.println("1. Add a new product");
        System.out.println("2. Update an existing product");
        System.out.println("3. Remove a product");
        System.out.println("4. View all products");
        System.out.println("5. Return to Store Owner Dashboard");
        System.out.print("Enter your choice: ");

        choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                addProduct(sc, s, owner);
                break;
            case 2:
                updateProduct(sc, s, owner);
                break;
            case 3:
                removeProduct(sc, s, owner);
                break;
            case 4:
                viewAllProducts(s, owner);
                break;
            case 5:
                System.out.println("Returning to Store Owner Dashboard...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    } while (choice != 5);
}

    public static void addProduct(Scanner sc, sweetSys s, StoreOwner owner) {
        System.out.print("Enter product name: ");
        String name = sc.nextLine();
        System.out.print("Enter product price: ");
        double price = sc.nextDouble();
        System.out.print("Enter product quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine(); // Consume newline

        Product newProduct = new Product(owner.getInventory().size() + 1, name, price, quantity, owner.getUsername());
        boolean added = s.addProduct(owner.getUsername(), newProduct);

        if (added) {
            System.out.println("Product added successfully.");
        } else {
            System.out.println("Failed to add product. It may already exist in the inventory.");
        }
    }

    public static void updateProduct(Scanner sc, sweetSys s, StoreOwner owner) {
        System.out.print("Enter the name of the product to update: ");
        String name = sc.nextLine();
        System.out.print("Enter new price (or -1 to keep current): ");
        double price = sc.nextDouble();
        System.out.print("Enter new quantity (or -1 to keep current): ");
        int quantity = sc.nextInt();
        sc.nextLine(); // Consume newline

        Product product = owner.getProductByName(name);
        if (product != null) {
            if (price != -1) product.setPrice(price);
            if (quantity != -1) product.setQuantity(quantity);
            boolean updated = s.updateProduct(owner.getUsername(), name, product.getPrice(), product.getQuantity());
            if (updated) {
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("Failed to update product.");
            }
        } else {
            System.out.println("Product not found in inventory.");
        }
    }

    public static void removeProduct(Scanner sc, sweetSys s, StoreOwner owner) {
        System.out.print("Enter the name of the product to remove: ");
        String name = sc.nextLine();

        boolean removed = s.removeProduct(owner.getUsername(), name);
        if (removed) {
            System.out.println("Product removed successfully.");
        } else {
            System.out.println("Failed to remove product. It may not exist in the inventory.");
        }
    }

    public static void viewAllProducts(sweetSys s, StoreOwner owner) {
        List<Product> inventory = owner.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("\nYour Inventory:");
            System.out.printf("%-5s %-20s %-10s %-10s%n", "ID", "Name", "Price", "Quantity");
            System.out.println(repeat("-", 50));
            for (Product product : inventory) {
                System.out.printf("%-5d %-20s $%-9.2f %-10d%n",
                        product.getId(), product.getName(), product.getPrice(), product.getQuantity());
            }
        }
    }



    //functions for sails and profits
    public static void monitorSalesAndProfits(Scanner sc, sweetSys s, StoreOwner owner) {
        int choice;
        do {
            System.out.println("\n===== Monitor Sales and Profits =====");
            System.out.println("1. View Sales Report");
            System.out.println("2. View Profit Report");
            System.out.println("3. Return to Store Owner Dashboard");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewSalesReport(s, owner);
                    break;
                case 2:
                    viewProfitReport(s, owner);
                    break;
                case 3:
                    System.out.println("Returning to Store Owner Dashboard...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    public static void viewSalesReport(sweetSys s, StoreOwner owner) {
        Map<String, Double> salesReport = s.getSalesReport(owner.getUsername());
        if (salesReport != null) {
            System.out.println("\n===== Sales Report =====");
            System.out.printf("%-20s %-10s%n", "Product", "Revenue");
            System.out.println(repeat("-", 32));
            double totalRevenue = 0;
            for (Map.Entry<String, Double> entry : salesReport.entrySet()) {
                if (!entry.getKey().equals("Total Revenue")) {
                    System.out.printf("%-20s $%-9.2f%n", entry.getKey(), entry.getValue());
                    totalRevenue += entry.getValue();
                }
            }
            System.out.println(repeat("-", 32));
            System.out.printf("%-20s $%-9.2f%n", "Total Revenue", totalRevenue);
        } else {
            System.out.println("Failed to generate sales report.");
        }
    }

    public static void viewProfitReport(sweetSys s, StoreOwner owner) {
        Map<String, Double> profitReport = s.getProfitReport(owner.getUsername());
        if (profitReport != null) {
            System.out.println("\n===== Profit Report =====");
            System.out.printf("%-20s %-10s%n", "Product", "Profit");
            System.out.println(repeat("-", 32));
            double totalProfit = 0;
            for (Map.Entry<String, Double> entry : profitReport.entrySet()) {
                System.out.printf("%-20s $%-9.2f%n", entry.getKey(), entry.getValue());
                totalProfit += entry.getValue();
            }
            System.out.println(repeat("-", 32));
            System.out.printf("%-20s $%-9.2f%n", "Total Profit", totalProfit);
        } else {
            System.out.println("Failed to generate profit report.");
        }
    }

    public static void identifyBestSellingProducts(Scanner sc, sweetSys s, StoreOwner owner) {
        String bestSeller = s.getBestSellingProduct(owner.getUsername());
        if (bestSeller != null) {
            System.out.println("\n===== Best-Selling Product =====");
            System.out.println("The best-selling product is: " + bestSeller);
        } else {
            System.out.println("Failed to identify the best-selling product.");
        }
    }


    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // FUNCTIONS FOR DISCOUNTING

    public static void manageDiscounts(Scanner sc, sweetSys s, StoreOwner owner) {
        int choice;
        do {
            System.out.println("\n===== Manage Discounts =====");
            System.out.println("1. Set a discount on a product");
            System.out.println("2. Remove a discount from a product");
            System.out.println("3. View all discounts");
            System.out.println("4. Return to Store Owner Dashboard");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    setDiscount(sc, s, owner);
                    break;
                case 2:
                    removeDiscount(sc, s, owner);
                    break;
                case 3:
                    viewAllDiscounts(s, owner);
                    break;
                case 4:
                    System.out.println("Returning to Store Owner Dashboard...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    public static void setDiscount(Scanner sc, sweetSys s, StoreOwner owner) {
        System.out.print("Enter the name of the product to discount: ");
        String productName = sc.nextLine();
        System.out.print("Enter the discount percentage (0-100): ");
        double discountPercentage = sc.nextDouble();
        System.out.print("Enter the number of days for the discount (0 for immediate expiration): ");
        int discountDays = sc.nextInt();
        sc.nextLine(); // Consume newline

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(discountDays);

        boolean success = s.setProductDiscount(owner.getUsername(), productName, discountPercentage, startDate, endDate);
        if (success) {
            System.out.println("Discount applied successfully.");
            Product product = owner.getProductByName(productName);
            if (product != null) {
                System.out.printf("New discounted price: $%.2f%n", product.getDiscountedPrice());
            }
        } else {
            System.out.println("Failed to apply discount. Please check the product name and try again.");
        }
    }

    public static void removeDiscount(Scanner sc, sweetSys s, StoreOwner owner) {
        System.out.print("Enter the name of the product to remove the discount: ");
        String productName = sc.nextLine();

        boolean success = s.removeProductDiscount(owner.getUsername(), productName);
        if (success) {
            System.out.println("Discount removed successfully.");
            Product product = owner.getProductByName(productName);
            if (product != null) {
                System.out.printf("Current price: $%.2f%n", product.getPrice());
            }
        } else {
            System.out.println("Failed to remove discount. Please check the product name and try again.");
        }
    }

    public static void viewAllDiscounts(sweetSys s, StoreOwner owner) {
        List<Product> inventory = owner.getInventory();
        System.out.println("\n===== Current Discounts =====");
        System.out.printf("%-20s %-10s %-15s %-15s %-15s%n", "Product", "Price", "Discount", "Start Date", "End Date");
        System.out.println(repeat("-", 80));
        for (Product product : inventory) {
            if (product.isDiscountActive()) {
                System.out.printf("%-20s $%-9.2f %-15.2f%% %-15s %-15s%n",
                        product.getName(),
                        product.getPrice(),
                        product.getDiscountPercentage(),
                        product.getDiscountStartDate(),
                        product.getDiscountEndDate());
            }
        }
    }

    // functions for messagging
    //-----------------------------------------------------------------------

    public static void messagingSystem(Scanner sc, sweetSys s, User user) {
        int choice;
        do {
            System.out.println("\n===== Messaging System =====");
            System.out.println("1. Send a message");
            System.out.println("2. View received messages");
            System.out.println("3. Return to Dashboard");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    sendMessage(sc, s, user);
                    break;
                case 2:
                    viewMessages(s, user);
                    break;
                case 3:
                    System.out.println("Returning to Dashboard...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    public static void sendMessage(Scanner sc, sweetSys s, User sender) {
        System.out.println("\n===== Send a Message =====");
        System.out.print("Enter recipient's username: ");
        String recipientUsername = sc.nextLine();

        User recipient = s.getUserByUsername(recipientUsername);
        if (recipient == null) {
            System.out.println("Recipient not found. Please check the username and try again.");
            return;
        }

        if (!(recipient instanceof Supplier || recipient instanceof Admin || recipient instanceof StoreOwner)) {
            System.out.println("You can only send messages to suppliers, admins, or other store owners.");
            return;
        }

        System.out.print("Enter your message: ");
        String messageContent = sc.nextLine();

        boolean sent = s.sendMessage(sender.getUsername(), recipientUsername, messageContent);
        if (sent) {
            System.out.println("Message sent successfully.");
        } else {
            System.out.println("Failed to send message. Please try again later.");
        }
    }

    public static void viewMessages(sweetSys s, User user) {
        System.out.println("\n===== Received Messages =====");
        List<Message> messages = s.getMessages(user.getUsername());

        if (messages.isEmpty()) {
            System.out.println("You have no messages.");
        } else {
            for (int i = 0; i < messages.size(); i++) {
                Message message = messages.get(i);
                System.out.printf("%d. From: %s | Date: %s%n", i + 1, message.getSender(), message.getTimestamp());
                System.out.println("   " + message.getContent());
                System.out.println();
            }
        }
    }


    // for account management
    public static void manageStoreOwnerAccount(Scanner sc, sweetSys s, StoreOwner owner) {
        int choice;
        do {
            System.out.println("\n===== Manage Account =====");
            System.out.println("1. Change password");
            System.out.println("2. Return to Store Owner Dashboard");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    changeStoreOwnerPassword(sc, s, owner);
                    break;
                case 2:
                    System.out.println("Returning to Store Owner Dashboard...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 2);
    }

    public static void changeStoreOwnerPassword(Scanner sc, sweetSys s, StoreOwner owner) {
        System.out.print("Enter your current password: ");
        String oldPassword = sc.nextLine();
        System.out.print("Enter your new password: ");
        String newPassword = sc.nextLine();

        s.changePass(owner.getUsername(), oldPassword, newPassword);
        if (s.isLastOperationSuccessful()) {
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Failed to change password. Please make sure your current password is correct.");
        }
    }

//--------------------
    // for managing orders

    public static void manageOrders(Scanner sc, sweetSys s, StoreOwner owner) {
        int choice;
        do {
            System.out.println("\n===== Order Management =====");
            System.out.println("1. View All Orders");
            System.out.println("2. Update Order Status");
            System.out.println("3. Return to Store Owner Dashboard");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAllOrders(s, owner);
                    break;
                case 2:
                    updateOrderStatus(sc, s, owner);
                    break;
                case 3:
                    System.out.println("Returning to Store Owner Dashboard...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    public static void viewAllOrders(sweetSys s, StoreOwner owner) {
        List<Order> orders = s.getOrdersByOwner(owner.getUsername());
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            System.out.println("\nAll Orders:");
            System.out.printf("%-5s %-15s %-15s %-15s%n", "ID", "Recipient", "Status", "Address");
            System.out.println(repeat("-", 55));
            for (Order order : orders) {
                System.out.printf("%-5s %-15s %-15s %-15s%n",
                        order.getId(), order.getRecipient(), order.getStatus(), order.getAddress());
            }
        }
    }

    public static void updateOrderStatus(Scanner sc, sweetSys s, StoreOwner owner) {
        System.out.print("Enter the order ID: ");
        String orderId = sc.nextLine();
        Order order = s.getOrderById(orderId);

        if (order == null) {
            System.out.println("Order not found.");
            return;
        }

        System.out.println("Current status: " + order.getStatus());
        System.out.print("Enter the new status: ");
        String newStatus = sc.nextLine();

        s.updateOrder(orderId, order.getStatus(), newStatus);

        if (s.checkOrderState(orderId).equals(newStatus)) {
            System.out.println("Order status updated successfully.");
        } else {
            System.out.println("Failed to update order status.");
        }
    }








    // STORE OWNER HANDLING

    public static void handleStoreOwnerMenu(Scanner sc, sweetSys s, User user) {
        int choice;
        do {
            System.out.println("\n===== Store Owner Dashboard =====");
            System.out.println("1. Manage Products");
            System.out.println("2. Monitor Sales and Profits");
            System.out.println("3. Identify Best-Selling Products");
            System.out.println("4. Manage Discounts");
            System.out.println("5. Messaging System");
            System.out.println("6. Manage Account");
            System.out.println("7. Order Management");
            System.out.println("8. Log out");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    manageProducts(sc, s, (StoreOwner) user);
                    break;
                case 2:
                    monitorSalesAndProfits(sc, s, (StoreOwner) user);
                    break;
                case 3:
                    identifyBestSellingProducts(sc, s, (StoreOwner) user);
                    break;
                case 4:
                    manageDiscounts(sc, s, (StoreOwner) user);
                    break;
                case 5:
                    messagingSystem(sc, s, user);
                    break;
                case 6:
                    manageStoreOwnerAccount(sc, s, (StoreOwner) user);
                    break;
                case 7:
                    manageOrders(sc, s, (StoreOwner) user);
                    break;
                case 8:
                    System.out.println("Logging out from the Store Owner dashboard...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);
    }


    //------------------------------------------------------------------------------------

    //functions for supplier

    public static void manageSupplierProducts(Scanner sc, sweetSys s, Supplier supplier) {
        int choice;
        do {
            System.out.println("\n===== Manage Supplier Products =====");
            System.out.println("1. Add a new product");
            System.out.println("2. Update an existing product");
            System.out.println("3. Remove a product");
            System.out.println("4. View all products");
            System.out.println("5. Return to Supplier Dashboard");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addSupplierProduct(sc, s, supplier);
                    break;
                case 2:
                    updateSupplierProduct(sc, s, supplier);
                    break;
                case 3:
                    removeSupplierProduct(sc, s, supplier);
                    break;
                case 4:
                    viewSupplierProducts(s, supplier);
                    break;
                case 5:
                    System.out.println("Returning to Supplier Dashboard...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void addSupplierProduct(Scanner sc, sweetSys s, Supplier supplier) {
        System.out.print("Enter product name: ");
        String name = sc.nextLine();
        System.out.print("Enter product price: ");
        double price = sc.nextDouble();
        System.out.print("Enter product quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine(); // Consume newline

        Product newProduct = new Product(supplier.getInventory().size() + 1, name, price, quantity, supplier.getUsername());
        boolean added = s.addProduct(supplier.getUsername(), newProduct);

        if (added) {
            System.out.println("Product added successfully.");
        } else {
            System.out.println("Failed to add product. It may already exist in the inventory.");
        }
    }

    private static void updateSupplierProduct(Scanner sc, sweetSys s, Supplier supplier) {
        System.out.print("Enter the name of the product to update: ");
        String name = sc.nextLine();
        System.out.print("Enter new price (or -1 to keep current): ");
        double price = sc.nextDouble();
        System.out.print("Enter new quantity (or -1 to keep current): ");
        int quantity = sc.nextInt();
        sc.nextLine(); // Consume newline

        Product product = supplier.getProductByName(name);
        if (product != null) {
            if (price != -1) product.setPrice(price);
            if (quantity != -1) product.setQuantity(quantity);
            boolean updated = s.updateProduct(supplier.getUsername(), name, product.getPrice(), product.getQuantity());
            if (updated) {
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("Failed to update product.");
            }
        } else {
            System.out.println("Product not found in inventory.");
        }
    }

    private static void removeSupplierProduct(Scanner sc, sweetSys s, Supplier supplier) {
        System.out.print("Enter the name of the product to remove: ");
        String name = sc.nextLine();

        boolean removed = s.removeProduct(supplier.getUsername(), name);
        if (removed) {
            System.out.println("Product removed successfully.");
        } else {
            System.out.println("Failed to remove product. It may not exist in the inventory.");
        }
    }

    private static void viewSupplierProducts(sweetSys s, Supplier supplier) {
        List<Product> inventory = supplier.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("\nYour Inventory:");
            System.out.printf("%-5s %-20s %-10s %-10s%n", "ID", "Name", "Price", "Quantity");
            System.out.println(repeat("-", 50));
            for (Product product : inventory) {
                System.out.printf("%-5d %-20s $%-9.2f %-10d%n",
                        product.getId(), product.getName(), product.getPrice(), product.getQuantity());
            }
        }
    }

    public static void manageSupplierAccount(Scanner sc, sweetSys s, Supplier supplier) {
        int choice;
        do {
            System.out.println("\n===== Manage Account =====");
            System.out.println("1. Change password");
            System.out.println("2. Return to Supplier Dashboard");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    changeSupplierPassword(sc, s, supplier);
                    break;
                case 2:
                    System.out.println("Returning to Supplier Dashboard...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 2);
    }

    public static void changeSupplierPassword(Scanner sc, sweetSys s, Supplier supplier) {
        System.out.print("Enter your current password: ");
        String oldPassword = sc.nextLine();
        System.out.print("Enter your new password: ");
        String newPassword = sc.nextLine();

        s.changePass(supplier.getUsername(), oldPassword, newPassword);
        if (s.isLastOperationSuccessful()) {
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Failed to change password. Please make sure your current password is correct.");
        }
    }

    //Supplier handling

    public static void handleSupplierMenu(Scanner sc, sweetSys s, User user) {
        int choice;
        do {
            System.out.println("\n===== Supplier Dashboard =====");
            System.out.println("1. Manage Products");
            System.out.println("2. Messaging System");
            System.out.println("3. Manage Account");
            System.out.println("4. Log out");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    manageSupplierProducts(sc, s, (Supplier) user);
                    break;
                case 2:
                    messagingSystem(sc, s, user);
                    break;
                case 3:
                    manageSupplierAccount(sc, s, (Supplier) user);
                    break;
                case 4:
                    System.out.println("Logging out from the Supplier dashboard...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }






    //----------------------------------------------------------

    //for repeat beacuse java doesn't support it ,

    public static String repeat(String str, int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(str);
        }
        return builder.toString();
    }
//-------------------------------------------------------

    //main
    //--------------------------------------------------
    public static void main(String args[]) {
        sweetSys s = new sweetSys();
        int choice;
        Scanner sc = new Scanner(System.in);

        while (true) {
            mainMenu();
            try {
                choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1: {
                        loginProcess(sc, s);
                        break;
                    }
                    case 2: {
                        signUp(sc, s);
                        break;
                    }
                    case 3: {
                        s.logout();
                        System.out.println(s.getMessage());
                        break;
                    }
                    case 4: {
                        System.out.println("Exiting the system. Goodbye!");
                        sc.close();
                        System.exit(0);
                    }
                    default:
                        System.out.println("Invalid choice. Please select a number between 1 and 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // Clear the invalid input
            }
        }
    }

}