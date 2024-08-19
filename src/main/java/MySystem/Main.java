package MySystem;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
                System.out.println("Supplier menu not implemented yet.");
            } else if (currentUser instanceof StoreOwner) {
                System.out.println("Store owner menu not implemented yet.");
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
        System.out.println("------------------------------------");
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
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7: {
                    System.out.println("This feature is not implemented yet.");
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