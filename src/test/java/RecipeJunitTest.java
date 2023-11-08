//package //test.java;


import org.junit.jupiter.api.Test;

import RecipeLogic.Recipe;
import RecipeLogic.RecipeManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;


public class RecipeJunitTest {
    private RecipeManager manager;
    private Recipe firstRecipe;
    
    @BeforeEach
    void setUp() {
        firstRecipe = new Recipe("sandwich", "put stuff between two slices of bread");
        manager = new RecipeManager();
        manager.removeAllRecipe();
    }

    // Testing the Recipe 
    @Test
    void testGetTitle_shouldReturnCorrectTitle() {
        Recipe newRecipe = new Recipe("Chocolate Cake", "Delicious dark chocolate cake");
        assertEquals("Chocolate Cake", newRecipe.getTitle(), "The title should be 'Chocolate Cake'.");
    }

    @Test
    void testGetDescription_shouldReturnCorrectDescription() {
        Recipe newRecipe = new Recipe("Chocolate Cake", "Delicious dark chocolate cake");
        assertEquals("Delicious dark chocolate cake", newRecipe.getDescription(), "The description should match the initial value.");
    }

    @Test
    void testSetDescription_shouldUpdateDescription() {
        String newDescription = "Moist chocolate cake with ganache";
        Recipe oldRecipe = new Recipe("Chocolate Cake", "Delicious dark chocolate cake");
        oldRecipe.setDescription(newDescription);
        assertEquals(newDescription, oldRecipe.getDescription(), "The description should be updated to the new value.");
    }


    // Testing Recipe Manager
    @Test
    void testAdd() {
        manager.addRecipe(firstRecipe);
        assertEquals("sandwich", manager.getList().get(0).getTitle());
        assertEquals(1, manager.getList().size());
        manager.removeRecipe(firstRecipe);
        assertEquals(0, manager.getList().size());
    }


    @Test 
    void testRemove() {
        manager.addRecipe(firstRecipe);
        manager.addRecipe(firstRecipe);
        manager.removeRecipe(firstRecipe);
        assertEquals(1, manager.getList().size());
        manager.removeRecipe(firstRecipe);
        assertEquals(0, manager.getList().size());
    }

    @Test
    void testGetList() {
        manager.addRecipe(firstRecipe);
        manager.addRecipe(firstRecipe);
        assertEquals(2, manager.getList().size());
        assertEquals("sandwich", manager.getList().get(0).getTitle());
        assertEquals("sandwich", manager.getList().get(1).getTitle());
        manager.removeRecipe(firstRecipe);
        assertEquals(1, manager.getList().size());
        manager.removeRecipe(firstRecipe);
        assertEquals(0, manager.getList().size());
    }
}
