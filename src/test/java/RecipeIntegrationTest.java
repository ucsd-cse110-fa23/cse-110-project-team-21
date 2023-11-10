//package test.java;


import org.junit.jupiter.api.Test;
import RecipeLogic.Recipe;
import RecipeLogic.RecipeManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;




public class RecipeIntegrationTest {

    @Test
    void testBDDScenario() {

        RecipeManager manager = new RecipeManager();
        manager.removeAllRecipe();
        Recipe firstRecipe = new Recipe("sandwich", "put stuff between two slices of bread");
        manager.addRecipe(firstRecipe);
        Recipe secondRecipe = new Recipe("chocolate cake", "delicious dark chocolate cake");
        manager.addRecipe(secondRecipe);
        Recipe thirdRecipe = new Recipe("veggie salad", "healthy salad");
        manager.addRecipe(thirdRecipe);
        assertEquals("veggie salad", manager.getList().get(0).getTitle());
        assertEquals("chocolate cake", manager.getList().get(1).getTitle());
        assertEquals("sandwich", manager.getList().get(2).getTitle());
        assertEquals(3, manager.getList().size());

        manager.removeRecipe(firstRecipe);
        assertEquals("veggie salad", manager.getList().get(0).getTitle());
        assertEquals("chocolate cake", manager.getList().get(1).getTitle());
        assertEquals(2, manager.getList().size());


        // Some nonsense input
        Recipe fourthRecipe = new Recipe("Happy Meal", "Go McDonald's");
        manager.addRecipe(fourthRecipe);
        assertEquals("Happy Meal", manager.getList().get(0).getTitle());
        assertEquals("veggie salad", manager.getList().get(1).getTitle());
        assertEquals("chocolate cake", manager.getList().get(2).getTitle());
    
        manager.removeAllRecipe();
        assertEquals(0, manager.getList().size());
    }
}