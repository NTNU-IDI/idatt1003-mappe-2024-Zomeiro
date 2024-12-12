//Unit test code provided by OpenAI's ChatGPT and double-checked and debugged by hand

package edu.ntnu.idatt;

import edu.ntnu.idatt.models.Grocery;
import edu.ntnu.idatt.models.Unit;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class GroceryTest {

  String name;
  double amount;
  Unit unit;
  LocalDate expiryDate;
  double unitPrice;

  Grocery testObject;

  @BeforeEach
  void createTestObject() {
    name = "Milk";
    amount = 1.75;
    unit = Unit.LITRE;
    expiryDate = LocalDate.of(2024, 12, 23);
    unitPrice = 18.20;

    testObject = new Grocery(name, amount, unit, expiryDate, unitPrice);
  }

  @Test
  void getName() {
    assertEquals(name, testObject.getName());
  }

  @Test
  void getAmount() {
    assertEquals(amount, testObject.getAmount());
  }

  @Test
  void getUnit() {
    assertEquals(unit, testObject.getUnit());
  }

  @Test
  void getExpiryDate() {
    assertEquals(expiryDate, testObject.getExpiryDate());
  }

  @Test
  void getUnitPrice() {
    assertEquals(unitPrice, testObject.getUnitPrice());
  }

  @Test
  void getTotalPrice() {
    assertEquals(amount * unitPrice, testObject.getTotalPrice());
  }

  @Test
  void setAmount() {
    testObject.setAmount(1);
    assertEquals(1, testObject.getAmount());
  }

  @Test
  void setExpiryDate() {
    testObject.setExpiryDate(LocalDate.of(2024, 12, 31));
    assertEquals(LocalDate.of(2024, 12, 31), testObject.getExpiryDate());
  }

  @Test
  void testToString() {
    String groceryToString =
        name + ":\nAmount: " + amount + "\nUnit: " + unit.toString() + "\nExpiryDate: "
            + expiryDate.toString();
    assertEquals(groceryToString, testObject.toString());
  }
}
