//package test.java;


import org.junit.jupiter.api.Test;

import RecipeManager.RecipeManagerModel;
import RecipeManager.RecipeModel;

import static org.junit.jupiter.api.Assertions.assertEquals;




public class RecipeIntegrationTest {

    @Test
    void testBDDScenario() {

        RecipeManagerModel manager = new RecipeManagerModel();
        manager.removeAllRecipe();
        RecipeModel firstRecipe = new RecipeModel("sandwich", "put stuff between two slices of bread");
        manager.addRecipe(firstRecipe);
        RecipeModel secondRecipe = new RecipeModel("chocolate cake", "delicious dark chocolate cake");
        manager.addRecipe(secondRecipe);
        RecipeModel thirdRecipe = new RecipeModel("veggie salad", "healthy salad");
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
        RecipeModel fourthRecipe = new RecipeModel("Happy Meal", "Go McDonald's");
        manager.addRecipe(fourthRecipe);
        assertEquals("Happy Meal", manager.getList().get(0).getTitle());
        assertEquals("veggie salad", manager.getList().get(1).getTitle());
        assertEquals("chocolate cake", manager.getList().get(2).getTitle());
    
        manager.removeAllRecipe();
        assertEquals(0, manager.getList().size());
    }
}