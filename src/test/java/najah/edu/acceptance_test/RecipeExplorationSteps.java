package najah.edu.acceptance_test;

import static org.junit.Assert.*;
import MySystem.*;
import io.cucumber.java.en.*;
import java.util.List;
import java.util.logging.Logger;

public class RecipeExplorationSteps {
    private sweetSys myApp;
    private List<Recipe> searchResults;
    private List<Recipe> browseResults;
    private static final Logger logger = Logger.getLogger(InventoryManager.class.getName());

    public RecipeExplorationSteps(sweetSys sweetSys) {
        this.myApp = sweetSys;
        
        
        logger.info("RecipeExplorationSteps initialized with sweetSys: " + sweetSys); // i want to track the last failure
    }

    // Remove the duplicate step definition because this testcase file can use the testing of this step from userLoginLogupSteps testcase file , while we had programmed this step  (  @Given("the user is logged in") in this calss --->userLoginLogupSteps
    // @Given("the user is logged in") method is removed and we will move immedietly to the next step

    @When("the user searches for recipes containing {string}")
    public void the_user_searches_for_recipes_containing(String keyword) {
        logger.info("Searching for recipes containing '" + keyword + "' using sweetSys: " + myApp); // while toString() for sweetSys is not overridden so it will put  sweetSys@7a8fa663 in the place of myApp
        searchResults = myApp.searchRecipes(keyword);
        logger.info("Search results for '" + keyword + "': " + searchResults.size());
        for (Recipe recipe : searchResults) {
            logger.info("- " + recipe.getName());
        }
    }
    

    @Then("the search results should contain recipes with {string}")
    public void the_search_results_should_contain_recipes_with(String keyword) {
        for (Recipe recipe : searchResults) {
            assertTrue("Recipe should contain the keyword",
                recipe.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                recipe.getIngredients().toLowerCase().contains(keyword.toLowerCase()));
        }
    }


    
    @Then("the number of search results should be greater than {int}")
    public void the_number_of_search_results_should_be_greater_than(Integer minResults) {
        logger.info("Asserting that search results (" + searchResults.size() + ") are greater than or equal to " + minResults);
        assertTrue("Number of search results should be greater than or equal to " + minResults + 
                   " but was " + searchResults.size() + 
                   ". Search results: " + searchResults, 
                   searchResults.size() >= minResults);
    }
    
    
    @When("the user browses recipes in the {string} category")
    public void the_user_browses_recipes_in_the_category(String category) {
        browseResults = myApp.browseRecipes(category);
    }

    @Then("all recipes in the results should be in the {string} category")
    public void all_recipes_in_the_results_should_be_in_the_category(String category) {
        for (Recipe recipe : browseResults) {
            assertEquals("Recipe category should match", category, recipe.getCategory());
        }
    }

    @Then("the number of browse results should be greater than {int}")
    public void the_number_of_browse_results_should_be_greater_than(Integer minResults) {
        assertTrue("Number of browse results should be greater than " + minResults, 
            browseResults.size() > minResults);
    }

    @Then("the number of search results should be {int}")
    public void the_number_of_search_results_should_be(Integer expectedResults) {
        assertEquals("Number of search results should match", expectedResults.intValue(), searchResults.size());
    }

    @When("the user browses all recipes")
    public void the_user_browses_all_recipes() {
        browseResults = myApp.browseRecipes(null);
    }

    @Then("the number of browse results should equal the total number of recipes")
    public void the_number_of_browse_results_should_equal_the_total_number_of_recipes() {
        assertEquals("Number of browse results should match total recipes", 
            myApp.getAllRecipes().size(), browseResults.size());
    }
    
    
    
    
    
    
    
    
    @When("the user filters recipes for {string} diet")
    public void the_user_filters_recipes_for_diet(String dietaryRestriction) {
        searchResults = myApp.filterRecipesByDiet(dietaryRestriction);
    }

    @Then("all recipes in the results should be {string}")
    public void all_recipes_in_the_results_should_be(String dietaryRestriction) {
        for (Recipe recipe : searchResults) {
            assertTrue("Recipe should meet dietary restriction",
                    recipe.getDietaryRestrictions().contains(dietaryRestriction));
        }
    }

    @When("the user filters recipes to exclude {string}")
    public void the_user_filters_recipes_to_exclude(String allergen) {
        searchResults = myApp.filterRecipesByAllergy(allergen);
    }

    @Then("none of the recipes in the results should contain {string}")
    public void none_of_the_recipes_in_the_results_should_contain(String allergen) {
        for (Recipe recipe : searchResults) {
            assertFalse("Recipe should not contain allergen",
                    recipe.getIngredients().toLowerCase().contains(allergen.toLowerCase()));
        }
    }

    @When("the user searches for {string} with a {string} dietary filter")
    public void the_user_searches_for_with_a_dietary_filter(String keyword, String dietaryRestriction) {
        searchResults = myApp.searchRecipesWithDietaryFilter(keyword, dietaryRestriction);
    }

    @Then("the search results should contain {string} {string} recipes")
    public void the_search_results_should_contain_recipes(String dietaryRestriction, String keyword) {
        for (Recipe recipe : searchResults) {
            assertTrue("Recipe should meet dietary restriction",
                    recipe.getDietaryRestrictions().contains(dietaryRestriction));
            assertTrue("Recipe should contain keyword",
                    recipe.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                    recipe.getIngredients().toLowerCase().contains(keyword.toLowerCase()));
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}