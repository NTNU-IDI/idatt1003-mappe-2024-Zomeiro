package edu.ntnu.idatt.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a cookbook that integrates with a FoodStorage system to manage recipes and
 * ingredients.
 */
public class CookBook {

  private final FoodStorage foodStorage;
  private final HashMap<String, Recipe> recipes;

  /**
   * Constructs a new CookBook instance.
   *
   * @param foodStorage the FoodStorage system to manage groceries
   * @throws NullPointerException if foodStorage is null
   */
  public CookBook(FoodStorage foodStorage) {
    if (foodStorage == null) {
      throw new NullPointerException("foodStorage is null");
    }
    this.foodStorage = foodStorage;
    this.recipes = new HashMap<>();
  }

  /**
   * Adds a recipe to the cookbook.
   *
   * @param name         the name of the recipe
   * @param description  a description of the recipe
   * @param instructions the cooking instructions
   * @param groceryList  the list of ingredients for the recipe
   * @param portions     the number of portions the recipe serves
   */
  public void addRecipe(String name, String description, String instructions,
      List<Grocery> groceryList,
      double portions) {
    if (recipes.containsKey(name)) {
      throw new IllegalArgumentException("Recipe with this name already exists.");
    }
    recipes.put(name, new Recipe(description, instructions, groceryList, portions));
  }

  /**
   * Checks if all the ingredients for a given recipe are available in sufficient quantities in the
   * food storage.
   *
   * @param recipeName the name of the recipe to check
   * @return true if all ingredients are available, false otherwise
   * @throws IllegalArgumentException if the recipe does not exist
   */
  public boolean checkRecipeAvailability(String recipeName) {
    Recipe recipe = recipes.get(recipeName);
    if (recipe == null) {
      throw new IllegalArgumentException("Recipe not found: " + recipeName);
    }

    for (Grocery ingredient : recipe.getIngredients()) {
      double requiredAmount = ingredient.getAmount();
      double availableAmount = foodStorage.getTotalAmount(ingredient.getName());
      if (availableAmount < requiredAmount) {
        return false;
      }
    }
    return true;
  }

  /**
   * Suggests recipes from the cookbook that can be made with the ingredients available in the food
   * storage.
   *
   * @return a list of recipe names that can be made
   */
  public List<String> suggestRecipes() {
    List<String> availableRecipes = new ArrayList<>();

    for (String recipeName : recipes.keySet()) {
      if (checkRecipeAvailability(recipeName)) {
        availableRecipes.add(recipeName);
      }
    }

    return availableRecipes;
  }

  /**
   * Prepares a recipe by removing the required ingredients from the food storage.
   *
   * @param recipeName the name of the recipe to prepare
   * @throws IllegalArgumentException if the recipe does not exist or ingredients are insufficient
   */
  public void prepareRecipe(String recipeName) {
    if (!checkRecipeAvailability(recipeName)) {
      throw new IllegalArgumentException(
          "Not enough ingredients to prepare the recipe: " + recipeName);
    }

    Recipe recipe = recipes.get(recipeName);
    for (Grocery ingredient : recipe.getIngredients()) {
      foodStorage.removeAmount(ingredient.getName(), ingredient.getAmount());
    }
  }

  /**
   * Retrieves a recipe from the cookbook by its name.
   *
   * @param recipeName the name of the recipe to retrieve
   * @return the Recipe object
   * @throws IllegalArgumentException if the recipe does not exist
   */
  public Recipe getRecipe(String recipeName) {
    Recipe recipe = recipes.get(recipeName);
    if (recipe == null) {
      throw new IllegalArgumentException("Recipe not found: " + recipeName);
    }
    return recipe;
  }
}
