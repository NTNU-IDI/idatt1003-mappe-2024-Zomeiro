package edu.ntnu.idatt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FoodStorageTest {

    private FoodStorage foodStorage;

    @BeforeEach
    void setUp() {
        foodStorage = new FoodStorage();

        // Legg til noen matvarer i foodStorage
        foodStorage.addGrocery(new Grocery("Milk", 1.0, Unit.LITRE, LocalDate.of(2024, 12, 8),12.20));
        foodStorage.addGrocery(new Grocery("Milk", 1.5, Unit.LITRE, LocalDate.of(2024, 12, 10),10.50));
        foodStorage.addGrocery(new Grocery("Cheese", 0.5, Unit.KILOGRAM, LocalDate.of(2024, 12, 6),134.90));
        foodStorage.addGrocery(new Grocery("Cheese", 0.7, Unit.KILOGRAM, LocalDate.of(2024, 12, 7),92.90));
    }

    @Test
    void testAddGrocery() {
        Grocery newGrocery = new Grocery("Butter", 0.5, Unit.KILOGRAM, LocalDate.of(2024, 12, 15),200);
        foodStorage.addGrocery(newGrocery);

        assertDoesNotThrow(() -> foodStorage.displayGroceryByKey("Butter"));
    }

    @Test
    void testRemoveAmount() {
        // Fjerner 1 liter melk
        foodStorage.removeAmount("Milk", 1.0);

        // Sjekker at riktig mengde melk er igjen
        List<Grocery> milkList = foodStorage.expiredGroceries(LocalDate.of(2024, 12, 15));
        assertEquals(1.5, milkList.stream().filter(g -> g.getName().equals("Milk"))
                .findFirst().orElseThrow().getAmount());
    }

    @Test
    void testRemoveAmountExceedingAvailable() {
        // Fjerner mer melk enn det som finnes
        assertDoesNotThrow(() -> foodStorage.removeAmount("Milk", 5.0));
        assertTrue(foodStorage.expiredGroceries(LocalDate.of(2024, 12, 15))
                .stream().noneMatch(g -> g.getName().equals("Milk")));
    }

    @Test
    void testExpiredGroceries() {
        List<Grocery> expired = foodStorage.expiredGroceries(LocalDate.of(2024, 12, 8));

        // Sjekk at kun varer før 2024-12-08 er inkludert
        assertEquals(2, expired.size());
        assertTrue(expired.stream().allMatch(g -> g.getExpiryDate().isBefore(LocalDate.of(2024, 12, 8))));
    }

    @Test
    void testDisplayGroceryByKey() {
        assertDoesNotThrow(() -> foodStorage.displayGroceryByKey("Cheese"));
    }

    @Test
    void testDisplayGroceries() {
        assertDoesNotThrow(() -> foodStorage.displayGroceries());
    }

    @Test
    void testAddNullGrocery() {
        assertThrows(IllegalArgumentException.class, () -> foodStorage.addGrocery(null));
    }

    @Test
    void testDisplayInvalidKey() {
        assertThrows(IllegalArgumentException.class, () -> foodStorage.displayGroceryByKey(null));
        assertThrows(IllegalArgumentException.class, () -> foodStorage.displayGroceryByKey("InvalidKey"));
    }
}
