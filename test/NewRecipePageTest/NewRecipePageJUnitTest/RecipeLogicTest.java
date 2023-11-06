package test.NewRecipePageTest.NewRecipePageJUnitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import RecipeLogic.Recipe;

public class RecipeLogicTest {

    private Recipe recipe;

    @BeforeEach
    void setUp() {
        recipe = new Recipe("Chocolate Cake", "Delicious dark chocolate cake");
    }

    @Test
    void testGetTitle_shouldReturnCorrectTitle() {
        assertEquals("Chocolate Cake", recipe.getTitle(), "The title should be 'Chocolate Cake'.");
    }

    @Test
    void testGetDescription_shouldReturnCorrectDescription() {
        assertEquals("Delicious dark chocolate cake", recipe.getDescription(), "The description should match the initial value.");
    }

    @Test
    void testSetDescription_shouldUpdateDescription() {
        String newDescription = "Moist chocolate cake with ganache";
        recipe.setDescription(newDescription);
        assertEquals(newDescription, recipe.getDescription(), "The description should be updated to the new value.");
    }
}