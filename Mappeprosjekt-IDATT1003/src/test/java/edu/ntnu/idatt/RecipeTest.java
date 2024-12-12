package edu.ntnu.idatt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class RecipeTest {

  private Recipe recipe;

  @BeforeEach
  void setUp() {
    // Create sample ingredients
    List<Grocery> ingredients = new ArrayList<>();
    ingredients.add(new Grocery("Flour", 500, Unit.KILOGRAM, LocalDate.of(2025, 1, 1), 20));
    ingredients.add(new Grocery("Milk", 1, Unit.LITRE, LocalDate.of(2024, 12, 31), 15));
    ingredients.add(new Grocery("Eggs", 6, Unit.KILOGRAM, LocalDate.of(2024, 12, 20), 30));

    // Initialize a recipe
    recipe = new Recipe("Pancakes", "Mix all ingredients and fry.", ingredients, 4);
  }

  @Test
  void testGetDescription() {
    assertEquals("Pancakes", recipe.getDescription());
  }

  @Test
  void testGetInstructions() {
    assertEquals("Mix all ingredients and fry.", recipe.getInstructions());
  }

  @Test
  void testGetIngredients() {
    List<Grocery> ingredients = recipe.getIngredients();
    assertEquals(3, ingredients.size());
    assertEquals("Flour", ingredients.get(0).getName());
    assertEquals("Milk", ingredients.get(1).getName());
    assertEquals("Eggs", ingredients.get(2).getName());
  }

  @Test
  void testGetPortions() {
    assertEquals(4, recipe.getPortions());
  }

  @Test
  void testSetPortionsValid() {
    recipe.setPortions(2);

    // Verify new portions
    assertEquals(2, recipe.getPortions());

    // Verify ingredient adjustments
    List<Grocery> ingredients = recipe.getIngredients();
    assertEquals(250, ingredients.get(0).getAmount()); // Flour
    assertEquals(0.5, ingredients.get(1).getAmount()); // Milk
    assertEquals(3, ingredients.get(2).getAmount()); // Eggs
  }

  @Test
  void testSetPortionsInvalid() {
    assertThrows(IllegalArgumentException.class, () -> recipe.setPortions(0));
    assertThrows(IllegalArgumentException.class, () -> recipe.setPortions(-2));
  }

  @Test
  void testConstructorInvalidPortions() {
    List<Grocery> ingredients = new ArrayList<>();
    ingredients.add(new Grocery("Flour", 500, Unit.KILOGRAM, LocalDate.of(2025, 1, 1), 20));
    assertThrows(IllegalArgumentException.class, () -> new Recipe("Cake", "Bake.", ingredients, 0));
  }
}
