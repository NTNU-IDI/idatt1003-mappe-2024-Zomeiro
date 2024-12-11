package edu.ntnu.idatt;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class CookBook {
    private HashMap<String,Recipe> recipes;
    FoodStorage foodStorage;
    public CookBook(FoodStorage foodStorage) {
        this.foodStorage = foodStorage;
    }

    void createRecipe(String name, String description, String instructions, List<Grocery> groceryList, double portions) {
        recipes.put(name, new Recipe(description, instructions, groceryList, portions));
    }

    public Boolean checkRecipeAvailability(String recipeName) {

    }
}
