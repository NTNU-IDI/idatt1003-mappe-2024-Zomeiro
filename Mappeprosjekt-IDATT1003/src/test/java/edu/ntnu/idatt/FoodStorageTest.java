//Unit test code provided by OpenAI's ChatGPT and double-checked and debugged by hand

package edu.ntnu.idatt;

import edu.ntnu.idatt.models.FoodStorage;
import edu.ntnu.idatt.models.Grocery;
import edu.ntnu.idatt.models.Unit;
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

      Grocery apple = new Grocery("Apple", 1.0, Unit.KILOGRAM, LocalDate.now().plusDays(5), 20.0);
    Grocery banana = new Grocery("Banana", 1.5, Unit.KILOGRAM, LocalDate.now().plusDays(2), 15.0);

        foodStorage.addGrocery(apple);
        foodStorage.addGrocery(banana);
    }

    @Test
    void testAddGrocery() {
        Grocery orange = new Grocery("Orange", 1.0, Unit.KILOGRAM, LocalDate.now().plusDays(7), 25.0);
        foodStorage.addGrocery(orange);

        assertEquals(1.0, foodStorage.getTotalAmount("Orange"));
    }

    @Test
    void testRemoveAmount() {
        foodStorage.removeAmount("Banana", 0.5);
        assertEquals(1.0, foodStorage.getTotalAmount("Banana"));

        foodStorage.removeAmount("Banana", 1.0);
        assertEquals(0.0, foodStorage.getTotalAmount("Banana"));
    }

    @Test
    void testGetTotalAmount() {
        assertEquals(1.0, foodStorage.getTotalAmount("Apple"));
        assertEquals(1.5, foodStorage.getTotalAmount("Banana"));
        assertEquals(0.0, foodStorage.getTotalAmount("Nonexistent"));
    }

    @Test
    void testExpiredGroceries() {
        Grocery expiredMilk = new Grocery("Milk", 1.0, Unit.LITRE, LocalDate.now().minusDays(1), 10.0);
        foodStorage.addGrocery(expiredMilk);

        List<Grocery> expiredGroceries = foodStorage.expiredGroceries(LocalDate.now());
        assertEquals(1, expiredGroceries.size());
        assertEquals("Milk", expiredGroceries.getFirst().getName());
    }

    @Test
    void testRemoveCurrentlyExpiredGroceries() {
        Grocery expiredMilk = new Grocery("Milk", 1.0, Unit.LITRE, LocalDate.now().minusDays(1), 10.0);
        foodStorage.addGrocery(expiredMilk);

        foodStorage.removeCurrentlyExpiredGroceries();

        assertEquals(0.0, foodStorage.getTotalAmount("Milk"));
        assertEquals(1.0, foodStorage.getTotalAmount("Apple"));
    }

    @Test
    void testDisplayGroceries() {
        assertDoesNotThrow(() -> foodStorage.displayGroceries());
    }
}
