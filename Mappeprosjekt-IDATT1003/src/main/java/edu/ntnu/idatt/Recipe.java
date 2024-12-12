package edu.ntnu.idatt;

import java.util.List;

public class Recipe {

  private final String description;
  private final String instructions;
  private final List<Grocery> ingredients;
  private final double portions;

  public Recipe(String description, String instructions, List<Grocery> ingredients,
      double portions) {
    this.description = description;
    this.instructions = instructions;
    this.ingredients = ingredients;
    this.portions = portions;
  }

  public String getDescription() {
    return description;
  }

  public String getInstructions() {
    return instructions;
  }

  public List<Grocery> getIngredients() {
    return ingredients;
  }

  public double getPortions() {
    return portions;
  }

  void setPortions(double newPortions) {
    double portionRatio = newPortions / portions;
    for (Grocery ingredient : getIngredients()) {
      ingredient.setAmount(ingredient.getAmount() * portionRatio);
    }
  }
}
