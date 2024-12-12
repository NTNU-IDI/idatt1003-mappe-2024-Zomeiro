package edu.ntnu.idatt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


/**
 * Represents a storage system for groceries, allowing for adding, removing, displaying, and
 * managing groceries.
 */
public class FoodStorage {

  private final HashMap<String, ArrayList<Grocery>> groceries;

  /**
   * Initializes an empty FoodStorage.
   */
  public FoodStorage() {
    groceries = new HashMap<>();
  }

  /**
   * Adds a grocery item to the storage. No restrictions on expiry date.
   *
   * @param grocery the grocery item to add
   * @throws IllegalArgumentException if the grocery is null
   */
  public void addGrocery(Grocery grocery) {
    if (grocery == null) {
      throw new IllegalArgumentException("grocery cannot be null");
    }
    groceries.putIfAbsent(grocery.getName(), new ArrayList<>());
    groceries.get(grocery.getName()).add(grocery);
  }

  /**
   * Removes a specified amount of a grocery item, starting with the oldest.
   *
   * @param groceryName   the name of the grocery to remove
   * @param amountRemoved the amount to remove
   */
  public void removeAmount(String groceryName, double amountRemoved) {
    /* tolerance set to 1g/1ml in order to circumvent floating point inaccuracies
    when trying to remove the entire amount */
    final double tolerance = 0.001;
    if (groceryName == null || groceryName.trim().isEmpty()) {
      throw new IllegalArgumentException("Grocery name cannot be null or empty");
    }
    if (!groceries.containsKey(groceryName)) {
      throw new IllegalArgumentException("No grocery found with the name: " + groceryName);
    }
    if (getTotalAmount(groceryName) < amountRemoved) {
      throw new IllegalArgumentException("Amount to remove cannot be larger than the total amount");
    }

    groceries.get(groceryName).sort(Comparator.comparing(Grocery::getExpiryDate));
    while (amountRemoved > tolerance) {
      if (groceries.get(groceryName).getFirst().getAmount() > amountRemoved) {
        groceries.get(groceryName).getFirst()
            .setAmount(groceries.get(groceryName).getFirst().getAmount() - amountRemoved);
        amountRemoved = 0;
      } else {
        amountRemoved -= groceries.get(groceryName).getFirst().getAmount();
        groceries.get(groceryName).removeFirst();
      }
    }

  }

  /**
   * Calculates the total amount of a grocery in FoodStorage given its key.
   *
   * @param groceryName the name of the grocery
   * @return the total amount of the grocery, or 0.0 if the grocery is not found
   * @throws IllegalArgumentException if the grocery name is null or empty
   */
  public double getTotalAmount(String groceryName) {
    if (groceryName == null || groceryName.trim().isEmpty()) {
      throw new IllegalArgumentException("Grocery name cannot be null or empty");
    }

    if (!groceries.containsKey(groceryName)) {
      return 0.0; // No such grocery found
    }

    // Sum up the amounts of all groceries with the given name
    return groceries.get(groceryName).stream()
        .mapToDouble(Grocery::getAmount)
        .sum();
  }

  /**
   * Returns a list of all expired groceries before the given date.
   *
   * @param date the cutoff date
   * @return a list of expired groceries
   */
  public List<Grocery> expiredGroceries(LocalDate date) {
    return groceries.values().stream()
        .flatMap(List::stream)
        .filter(g -> g.getExpiryDate().isBefore(date))
        .toList();
  }

  /**
   * Removes all expired groceries from the storage.
   */
  //Method written by ChatGPT
  public void removeCurrentlyExpiredGroceries() {
    for (String key : groceries.keySet()) {
      groceries.get(key).removeIf(g -> g.getExpiryDate().isBefore(LocalDate.now()));
    }
    groceries.entrySet().removeIf(entry -> entry.getValue().isEmpty());
  }

  /**
   * Displays all groceries with the specified name.
   *
   * @param groceryName the name of the grocery to display
   * @throws IllegalArgumentException if the grocery name is null
   */
  public String displayGroceryByKey(String groceryName) {
    if (groceryName == null) {
      throw new IllegalArgumentException("Grocery name cannot be null");
    }
    if (!groceries.containsKey(groceryName)) {
      throw new IllegalArgumentException("No grocery found with the name: " + groceryName);
    }
    StringBuilder groceryString = new StringBuilder();
    for (Grocery g : groceries.get(groceryName)) {
      groceryString.append(g.toString()).append("\n");
    }
    return groceryString.toString();
  }

  /**
   * Displays all groceries in the storage, sorted by name.
   */
  public void displayGroceries() {
    if (groceries.isEmpty()) {
      System.out.println("No groceries found");
    } else {
      groceries.keySet().stream()
          .sorted()
          .forEach(key -> {
            System.out.println(key + ":");
            groceries.get(key).forEach(System.out::println);
          });
    }
  }
}