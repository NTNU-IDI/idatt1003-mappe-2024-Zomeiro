package edu.ntnu.idatt.userinterface;

import edu.ntnu.idatt.models.CookBook;
import edu.ntnu.idatt.models.FoodStorage;
import edu.ntnu.idatt.models.Grocery;
import edu.ntnu.idatt.models.Recipe;
import edu.ntnu.idatt.models.Unit;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the user interface for managing FoodStorage and CookBook systems. Provides options to
 * add, view, remove groceries, display recipes, and check availability.
 */
public class UserInterface {

  private FoodStorage foodStorage;
  private CookBook cookBook;
  private Scanner scanner;

  /**
   * Initializes the UserInterface, FoodStorage, and CookBook objects with sample data.
   */
  public void init() {
    foodStorage = new FoodStorage();
    cookBook = new CookBook(foodStorage);
    scanner = new Scanner(System.in);

    // Example groceries
    foodStorage.addGrocery(new Grocery("Milk", 2, Unit.LITRE, LocalDate.now().plusDays(5), 1.50));
    foodStorage.addGrocery(
        new Grocery("Flour", 1, Unit.KILOGRAM, LocalDate.now().plusMonths(3), 2.00));
    foodStorage.addGrocery(
        new Grocery("Eggs", 0.5, Unit.KILOGRAM, LocalDate.now().plusDays(10), 0.10));

    // Example recipe: Pancakes
    ArrayList<Grocery> pancakeIngredients = new ArrayList<>();
    pancakeIngredients.add(new Grocery("Milk", 1, Unit.LITRE, LocalDate.now().plusDays(5), 1.50));
    pancakeIngredients.add(
        new Grocery("Flour", 0.5, Unit.KILOGRAM, LocalDate.now().plusMonths(3), 1.00));
    pancakeIngredients.add(
        new Grocery("Eggs", 0.1, Unit.KILOGRAM, LocalDate.now().plusDays(10), 0.10));

    cookBook.addRecipe("Pancakes", "Delicious pancakes", "Mix ingredients and fry on a pan.",
        pancakeIngredients, 4);
  }

  /**
   * Runs the main interactive menu for managing food storage and recipes.
   */
  public void start() {
    boolean running = true;

    while (running) {
      System.out.println("\n--- Food Storage & CookBook Menu ---");
      System.out.println("1. Add a grocery");
      System.out.println("2. Display all groceries");
      System.out.println("3. Display grocery by name");
      System.out.println("4. Remove a specific amount of grocery");
      System.out.println("5. Remove expired groceries");
      System.out.println("6. Suggest available recipes");
      System.out.println("7. Display a recipe by name");
      System.out.println("8. Prepare a recipe");
      System.out.println("10. Exit");
      System.out.print("Choose an option: ");

      try {
        int choice = Integer.parseInt(scanner.nextLine()); // Sikre riktig input-type

        switch (choice) {
          case 1:
            addGrocery();
            break;
          case 2:
            foodStorage.displayGroceries();
            break;
          case 3:
            displayGroceryByName();
            break;
          case 4:
            removeGroceryAmount();
            break;
          case 5:
            foodStorage.removeCurrentlyExpiredGroceries();
            System.out.println("Expired groceries removed.");
            break;
          case 6:
            suggestRecipes();
            break;
          case 7:
            displayRecipeByName();
            break;
          case 8:
            prepareRecipe();
            break;
          case 9:
            valueExpired();
            break;
          case 10:
            System.out.println("Exiting... Goodbye!");
            running = false;
            break;
          default:
            System.out.println("Invalid choice. Please try again.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number between 1 and 9.");
      } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
      } catch (Exception e) {
        System.out.println("An unexpected error occurred: " + e.getMessage());
      }
    }
  }


  /**
   * Prompts the user to add a grocery to FoodStorage.
   */
  private void addGrocery() {
    System.out.print("Enter grocery name: ");
    final String name = scanner.nextLine();

    System.out.print("Enter amount: ");
    final double amount = scanner.nextDouble();

    System.out.print("Enter unit (KILOGRAM, LITRE, PIECE): ");
    final Unit unit = Unit.valueOf(scanner.next().toUpperCase());
    scanner.nextLine(); // Consume newline

    System.out.print("Enter expiration date (YYYY-MM-DD): ");
    LocalDate date = LocalDate.parse(scanner.nextLine());

    System.out.print("Enter price per unit: ");
    double price = scanner.nextDouble();
    scanner.nextLine(); // Consume newline

    foodStorage.addGrocery(new Grocery(name, amount, unit, date, price));
    System.out.println("Grocery added successfully!");
  }

  /**
   * Displays a grocery by its name.
   */
  private void displayGroceryByName() {
    System.out.print("Enter grocery name: ");
    String name = scanner.nextLine();
    String grocery = foodStorage.displayGroceryByKey(name);
    System.out.println(grocery != null ? grocery : "Grocery not found.");
  }

  /**
   * Prints total value of expired food items.
   */
  private void valueExpired() {
    System.out.print("Enter date: ");
    LocalDate date = LocalDate.parse(scanner.nextLine());
    double value = foodStorage.valueOfExpiredGroceries(date);
    System.out.println("Total value of expired items is: " + value);
  }

  /**
   * Prompts user to remove a specific amount of a grocery.
   */
  private void removeGroceryAmount() {
    System.out.print("Enter grocery name: ");
    String name = scanner.nextLine();

    System.out.print("Enter amount to remove: ");
    double amount = scanner.nextDouble();
    scanner.nextLine(); // Consume newline

    foodStorage.removeAmount(name, amount);
    System.out.println("Amount removed successfully!");
  }

  /**
   * Suggests recipes that can be prepared with available groceries.
   */
  private void suggestRecipes() {
    List<String> recipes = cookBook.suggestRecipes();
    System.out.println("Available recipes:");
    if (recipes.isEmpty()) {
      System.out.println("No recipes can be prepared with current groceries.");
    } else {
      recipes.forEach(System.out::println);
    }
  }

  /**
   * Displays a recipe by its name.
   */
  private void displayRecipeByName() {
    System.out.print("Enter recipe name: ");
    String name = scanner.nextLine();
    Recipe recipe = cookBook.getRecipe(name);
    if (recipe != null) {
      System.out.println(recipe);
    } else {
      System.out.println("Recipe not found.");
    }
  }

  /**
   * Prepares a recipe and removes the required ingredients from FoodStorage.
   */
  private void prepareRecipe() {
    System.out.print("Enter recipe name to prepare: ");
    String name = scanner.nextLine();
    cookBook.prepareRecipe(name);
    System.out.println("Recipe prepared successfully!");
  }

}