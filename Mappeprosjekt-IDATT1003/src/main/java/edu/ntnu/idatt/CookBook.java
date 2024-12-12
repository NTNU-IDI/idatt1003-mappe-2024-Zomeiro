package edu.ntnu.idatt;

import java.util.HashMap;
import java.util.List;

public class CookBook {

  FoodStorage foodStorage;
  private HashMap<String, Recipe> recipes;

  public CookBook(FoodStorage foodStorage) {
    this.foodStorage = foodStorage;
  }

  void createRecipe(String name, String description, String instructions, List<Grocery> groceryList,
      double portions) {
    recipes.put(name, new Recipe(description, instructions, groceryList, portions));
  }


}
