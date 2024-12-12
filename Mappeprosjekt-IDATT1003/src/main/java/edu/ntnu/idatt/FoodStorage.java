package edu.ntnu.idatt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
    groceries.putIfAbsent(grocery.getName(), new ArrayList<Grocery>());
    groceries.get(grocery.getName()).add(grocery);
  }

  /**
   * Removes a specified amount of a grocery item, starting with the oldest.
   *
   * @param Key           the name of the grocery to remove
   * @param amountRemoved the amount to remove
   */
  public void removeAmount(String Key, double amountRemoved) {

    if (groceries.isEmpty()) {
      System.out.println("No groceries found");
    } else if (!groceries.containsKey(Key)) {
      System.out.println("No grocery with that key found");
    } else {
      groceries.get(Key).sort(Comparator.comparing(Grocery::getExpiryDate));
      while (amountRemoved != 0) {
        if (groceries.get(Key).getFirst().getAmount() > amountRemoved) {
          groceries.get(Key).getFirst()
              .setAmount(groceries.get(Key).getFirst().getAmount() - amountRemoved);
          amountRemoved = 0;
        } else {
          amountRemoved -= groceries.get(Key).getFirst().getAmount();
          groceries.get(Key).removeFirst();
        }
      }
    }
  }

  /**
   * Returns a list of all expired groceries before the given date.
   *
   * @param date the cutoff date
   * @return a list of expired groceries
   */
  public List<Grocery> expiredGroceries(LocalDate date) {
    List<Grocery> expiredGroceriesList = new ArrayList<>();
    for (List<Grocery> groceryList : groceries.values()) {
      expiredGroceriesList.addAll(
          groceryList.stream().filter(g -> g.getExpiryDate().isBefore(date)).toList());
    }
    return expiredGroceriesList;
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
  public void displayGroceryByKey(String groceryName) {
    if (groceryName == null) {
      throw new IllegalArgumentException("grocery name cannot be null");
    }
    if (groceries.containsKey(groceryName)) {
      for (Grocery g : groceries.get(groceryName)) {
        System.out.println(g.toString());
      }
    } else {
      System.out.println("No grocery with name " + groceryName + " found");
    }
  }

  /**
   * Displays all groceries in the storage, sorted by name.
   */
  public void displayGroceries() {
    if (groceries.isEmpty()) {
      System.out.println("No groceries found");
    } else {
      ArrayList<String> sortedKeys = new ArrayList<>(groceries.keySet());
      Collections.sort(sortedKeys);

      for (String key : sortedKeys) {
        System.out.println(key + ": " + groceries.get(key));
        sortedKeys.forEach(k -> groceries.get(k).forEach(System.out::println));
      }
    }
  }
}