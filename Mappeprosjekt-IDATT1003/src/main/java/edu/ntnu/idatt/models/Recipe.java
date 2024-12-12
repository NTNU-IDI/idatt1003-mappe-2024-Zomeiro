package edu.ntnu.idatt.models;

import java.util.List;

/**
 * Represents a recipe with a description, instructions, ingredients, and portions.
 */
public class Recipe {

  private final String description;
  private final String instructions;
  private final List<Grocery> ingredients;
  private double portions;

  /**
   * Constructs a Recipe object.
   *
   * @param description the description of the recipe, such as its name or type
   * @param instructions the instructions for preparing the recipe
   * @param ingredients the list of ingredients needed for the recipe
   * @param portions the number of portions the recipe serves (must be greater than 0)
   * @throws IllegalArgumentException if portions is less than or equal to 0
   */
  public Recipe(String description, String instructions, List<Grocery> ingredients,
      double portions) {
    if (portions <= 0) {
      throw new IllegalArgumentException("Portions must be greater than 0.");
    }
    this.description = description;
    this.instructions = instructions;
    this.ingredients = ingredients;
    this.portions = portions;
  }

  /**
   * Returns the description of the recipe.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns the instructions for preparing the recipe.
   *
   * @return the instructions
   */
  public String getInstructions() {
    return instructions;
  }

  /**
   * Returns the list of ingredients for the recipe.
   *
   * @return the list of ingredients
   */
  public List<Grocery> getIngredients() {
    return ingredients;
  }

  /**
   * Returns the number of portions the recipe serves.
   *
   * @return the number of portions
   */
  public double getPortions() {
    return portions;
  }

  /**
   * Sets a new number of portions for the recipe and adjusts ingredient amounts accordingly.
   *
   * @param newPortions the new number of portions (must be greater than 0)
   * @throws IllegalArgumentException if newPortions is less than or equal to 0
   */
  public void setPortions(double newPortions) {
    if (newPortions <= 0) {
      throw new IllegalArgumentException("New portions must be greater than 0.");
    }
    double portionRatio = newPortions / portions;
    for (Grocery ingredient : getIngredients()) {
      ingredient.setAmount(ingredient.getAmount() * portionRatio);
    }
    portions = newPortions;
  }
}
