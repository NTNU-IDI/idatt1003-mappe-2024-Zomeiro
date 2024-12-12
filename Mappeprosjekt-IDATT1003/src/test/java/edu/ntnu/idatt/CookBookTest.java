package edu.ntnu.idatt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idatt.models.CookBook;
import edu.ntnu.idatt.models.FoodStorage;
import edu.ntnu.idatt.models.Grocery;
import edu.ntnu.idatt.models.Recipe;
import edu.ntnu.idatt.models.Unit;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CookBookTest {

  private FoodStorage foodStorage;
  private CookBook cookBook;

  @BeforeEach
  void setUp() {
    foodStorage = new FoodStorage();
    cookBook = new CookBook(foodStorage);
  }

  @Test
  void testAddRecipeSuccessfully() {
    List<Grocery> ingredients = List.of(
        new Grocery("Milk", 1.0, Unit.LITRE, LocalDate.now().plusDays(5), 20.0),
        new Grocery("Flour", 0.5, Unit.KILOGRAM, LocalDate.now().plusDays(30), 10.0)
    );

    cookBook.addRecipe("Pancakes", "Delicious pancakes", "Mix and fry", ingredients, 2);

    Recipe recipe = cookBook.getRecipe("Pancakes");
    assertNotNull(recipe);
    assertEquals("Delicious pancakes", recipe.getDescription());
    assertEquals(2, recipe.getPortions());
    assertEquals(2, recipe.getIngredients().size());
  }

  @Test
  void testAddDuplicateRecipeThrowsException() {
    List<Grocery> ingredients = List.of(
        new Grocery("Milk", 1.0, Unit.LITRE, LocalDate.now().plusDays(5), 20.0)
    );

    cookBook.addRecipe("Pancakes", "Delicious pancakes", "Mix and fry", ingredients, 2);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      cookBook.addRecipe("Pancakes", "Duplicate description", "Different instructions", ingredients,
          4);
    });

    assertEquals("Recipe with this name already exists.", exception.getMessage());
  }

  @Test
  void testCheckRecipeAvailabilityTrue() {
    foodStorage.addGrocery(new Grocery("Milk", 2.0, Unit.LITRE, LocalDate.now().plusDays(5), 20.0));
    foodStorage.addGrocery(
        new Grocery("Flour", 1.0, Unit.KILOGRAM, LocalDate.now().plusDays(30), 10.0));

    List<Grocery> ingredients = List.of(
        new Grocery("Milk", 1.0, Unit.LITRE, LocalDate.now().plusDays(5), 20.0),
        new Grocery("Flour", 0.5, Unit.KILOGRAM, LocalDate.now().plusDays(30), 10.0)
    );

    cookBook.addRecipe("Pancakes", "Delicious pancakes", "Mix and fry", ingredients, 2);

    assertTrue(cookBook.checkRecipeAvailability("Pancakes"));
  }

  @Test
  void testCheckRecipeAvailabilityFalse() {
    foodStorage.addGrocery(new Grocery("Milk", 0.5, Unit.LITRE, LocalDate.now().plusDays(5), 20.0));

    List<Grocery> ingredients = List.of(
        new Grocery("Milk", 1.0, Unit.LITRE, LocalDate.now().plusDays(5), 20.0),
        new Grocery("Flour", 0.5, Unit.KILOGRAM, LocalDate.now().plusDays(30), 10.0)
    );

    cookBook.addRecipe("Pancakes", "Delicious pancakes", "Mix and fry", ingredients, 2);

    assertFalse(cookBook.checkRecipeAvailability("Pancakes"));
  }

  @Test
  void testSuggestRecipes() {
    foodStorage.addGrocery(new Grocery("Milk", 2.0, Unit.LITRE, LocalDate.now().plusDays(5), 20.0));
    foodStorage.addGrocery(
        new Grocery("Flour", 1.0, Unit.KILOGRAM, LocalDate.now().plusDays(30), 10.0));

    List<Grocery> ingredients1 = List.of(
        new Grocery("Milk", 1.0, Unit.LITRE, LocalDate.now().plusDays(5), 20.0),
        new Grocery("Flour", 0.5, Unit.KILOGRAM, LocalDate.now().plusDays(30), 10.0)
    );

    List<Grocery> ingredients2 = List.of(
        new Grocery("Milk", 2.0, Unit.LITRE, LocalDate.now().plusDays(5), 2.0),
        new Grocery("Eggs", 6.0, Unit.KILOGRAM, LocalDate.now().plusDays(10), 0.2)
    );

    cookBook.addRecipe("Pancakes", "Delicious pancakes", "Mix and fry", ingredients1, 2);
    cookBook.addRecipe("Omelette", "Fluffy omelette", "Whisk and fry", ingredients2, 3);

    List<String> suggestedRecipes = cookBook.suggestRecipes();
    assertEquals(1, suggestedRecipes.size());
    assertTrue(suggestedRecipes.contains("Pancakes"));
  }

  @Test
  void testPrepareRecipeSuccessfully() {
    foodStorage.addGrocery(new Grocery("Milk", 2.0, Unit.LITRE, LocalDate.now().plusDays(5), 20.0));
    foodStorage.addGrocery(
        new Grocery("Flour", 1.0, Unit.KILOGRAM, LocalDate.now().plusDays(30), 10.0));

    List<Grocery> ingredients = List.of(
        new Grocery("Milk", 1.0, Unit.LITRE, LocalDate.now().plusDays(5), 20.0),
        new Grocery("Flour", 0.5, Unit.KILOGRAM, LocalDate.now().plusDays(30), 10.0)
    );

    cookBook.addRecipe("Pancakes", "Delicious pancakes", "Mix and fry", ingredients, 2);

    cookBook.prepareRecipe("Pancakes");

    assertEquals(1.0, foodStorage.getTotalAmount("Milk"));
    assertEquals(0.5, foodStorage.getTotalAmount("Flour"));
  }

  @Test
  void testPrepareRecipeInsufficientIngredientsThrowsException() {
    foodStorage.addGrocery(new Grocery("Milk", 0.5, Unit.LITRE, LocalDate.now().plusDays(5), 20.0));

    List<Grocery> ingredients = List.of(
        new Grocery("Milk", 1.0, Unit.LITRE, LocalDate.now().plusDays(5), 20.0),
        new Grocery("Flour", 0.5, Unit.KILOGRAM, LocalDate.now().plusDays(30), 10.0)
    );

    cookBook.addRecipe("Pancakes", "Delicious pancakes", "Mix and fry", ingredients, 2);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      cookBook.prepareRecipe("Pancakes");
    });

    assertEquals("Not enough ingredients to prepare the recipe: Pancakes", exception.getMessage());
  }
}