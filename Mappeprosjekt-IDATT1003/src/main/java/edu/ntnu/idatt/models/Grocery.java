package edu.ntnu.idatt.models;

import java.time.LocalDate;

//All javadoc commentary provided by OpenAI's ChatGPT and double-checked by hand

/**
 * Represents a grocery item stored in a food inventory or fridge. This class encapsulates details
 * about the grocery, including its name, amount, unit of measurement, expiry date, and unit price.
 *
 * <p>Provides functionality to calculate the total price of a grocery item and ensures valid data
 * through input validation.</p>
 */
public class Grocery {

  private final String name;
  private double amount;
  private final Unit unit;
  private LocalDate expiryDate;
  private final double unitPrice;

  /**
   * Constructs a new Grocery item with the specified attributes. Validates input data to ensure the
   * grocery item is valid.
   *
   * @param name       the name of the grocery item, must not be null or empty.
   * @param amount     the quantity of the item, must be greater than 0.
   * @param unit       the unit of measurement for the item (e.g., "litre" or "kilogram"), must be
   *                   valid.
   * @param expiryDate the expiry date of the item, must not be in the past.
   * @param unitPrice  the price per unit of the item. Negative or zero prices are allowed for
   *                   special campaigns.
   * @throws IllegalArgumentException if any of the inputs are invalid.
   */
  public Grocery(String name, double amount, Unit unit, LocalDate expiryDate, double unitPrice) {
    // Data validation
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Name is null or empty");
    }
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than 0");
    }
    if (unit == null) {
      throw new IllegalArgumentException("Unit cannot be null");
    }
    if (expiryDate == null) {
      throw new IllegalArgumentException("Expiry date cannot be null");
    }
    /* No validation of unitPrice due to possible campaigns where food can be
    received for free or companies being paid specifically to use and promote
    the wares, resulting in a negative price. */

    this.name = name;
    this.amount = amount;
    this.unit = unit;
    this.expiryDate = expiryDate;
    this.unitPrice = unitPrice;
  }

  /**
   * Gets the name of the grocery item.
   *
   * @return the name of the item.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the quantity of the grocery item.
   *
   * @return the amount of the item.
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Gets the unit of measurement for the grocery item.
   *
   * @return the unit of the item (e.g., "litre" or "gram").
   */
  public Unit getUnit() {
    return unit;
  }

  /**
   * Gets the expiry date of the grocery item.
   *
   * @return the expiry date.
   */
  public LocalDate getExpiryDate() {
    return expiryDate;
  }

  /**
   * Gets the price per unit of the grocery item.
   *
   * @return the price per unit.
   */
  public double getUnitPrice() {
    return unitPrice;
  }

  /**
   * Calculates the total price of the grocery item based on its quantity and unit price.
   *
   * @return the total price.
   */
  public double getTotalPrice() {
    return amount * unitPrice;
  }

  /**
   * Updates the quantity of the grocery item.
   *
   * @param amount the new quantity, must be greater than 0.
   * @throws IllegalArgumentException if the amount is invalid.
   */
  public void setAmount(double amount) {
    this.amount = amount;
    if (amount < 0) {
      throw new IllegalArgumentException("Amount must be greater than 0");
    }
  }

  /**
   * Updates the expiry date of the grocery item.
   *
   * @param expiryDate the new expiry date, must not be in the past.
   * @throws IllegalArgumentException if the expiry date is invalid.
   */
  public void setExpiryDate(LocalDate expiryDate) {
    if (expiryDate == null) {
      throw new IllegalArgumentException("Expiry date cannot be null");
    } else if (expiryDate.isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("Expiry date cannot be in the past");
    }
    this.expiryDate = expiryDate;
  }

  /**
   * Returns a string representation of the grocery item.
   *
   * @return a string containing the name, amount, unit, and expiry date of the item.
   */
  @Override
  public String toString() {
    return getName() + ":\nAmount: " + getAmount() + "\nUnit: " + getUnit().toString()
        + "\nExpiryDate: "
        + getExpiryDate().toString();
  }
}