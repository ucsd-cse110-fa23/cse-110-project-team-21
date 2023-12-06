
import org.junit.jupiter.api.Test;

import NewRecipePage.GPTModel;
import NewRecipePage.NewRecipeController;
import RecipeManager.RecipeModel;
import SignUpPage.SignUpController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.BufferedReader;
import java.io.FileReader;


public class RefreshJunitTest {

    @Test
    public void testRefresh() throws Exception {
        NewRecipeController newRecipeController = new NewRecipeController(null);
        GPTModel gptModel = new GPTModel(); 
        newRecipeController.gptModel = gptModel;
        RecipeModel currRecipe = newRecipeController.regenerateRecipeMock();
        RecipeModel expectedRecipe = new RecipeModel("Mock Recipe", "Ingredients: \n" + "Mock Ingredients");
        assertEquals(currRecipe.getTitle(), expectedRecipe.getTitle());
        assertEquals(currRecipe.getDescription(), expectedRecipe.getDescription());
    }


    @Test
    public void testRefresh2() throws Exception {
        NewRecipeController newRecipeController = new NewRecipeController(null);
        GPTModel gptModel = new GPTModel(); 
        newRecipeController.gptModel = gptModel;
        RecipeModel currRecipe = newRecipeController.regenerateRecipeMock();
        RecipeModel nextRecipe = newRecipeController.regenerateRecipeMockUnique();
        assertNotEquals(currRecipe.getTitle(), nextRecipe.getTitle());
        assertNotEquals(currRecipe.getDescription(), nextRecipe.getDescription());
    }

    @Test
    public void testRefresh3() throws Exception {
        NewRecipeController newRecipeController = new NewRecipeController(null);
        GPTModel gptModel = new GPTModel(); 
        newRecipeController.gptModel = gptModel;
        RecipeModel currRecipe = newRecipeController.regenerateRecipeMock();
        RecipeModel nextRecipe = newRecipeController.regenerateRecipeMockUnique();
        assertNotEquals(currRecipe.getTitle(), nextRecipe.getTitle());
        assertNotEquals(currRecipe.getDescription(), nextRecipe.getDescription());

        // Refresh again, should not be the same
        currRecipe = newRecipeController.regenerateRecipeMock();
        nextRecipe = newRecipeController.regenerateRecipeMockUnique();
        assertNotEquals(currRecipe.getTitle(), nextRecipe.getTitle());
        assertNotEquals(currRecipe.getDescription(), nextRecipe.getDescription());
    }
}
