package edu.ntnu.idatt;

/**
 * Enum representing units of measurement for groceries. Currently supports "gram" and "litre".
 */
public enum Unit {
  /**
   * Unit representing grams.
   */
  KILOGRAM,

  /**
   * Unit representing litres.
   */
  LITRE;

  /**
   * Returns a string representation of the unit. Overrides the default {@code toString()} method to
   * provide a lowercase version of the unit name.
   *
   * @return A string representation of the unit ("gram" or "litre").
   */
  @Override
  public String toString() {
    if (this == KILOGRAM) {
      return "kilogram";
    } else if (this == LITRE) {
      return "litre";
    } else {
      return super.toString();
    }
  }
}