import org.junit.jupiter.api.Test;

import NewRecipePage.GPTModel;
import RecipeManager.MockRecipeManagerModel;
import RecipeManager.RecipeModel;
import WhisperPage.WhisperModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;




public class EndtoEndTest {
    MockRecipeManagerModel manager;
    WhisperModel whisper;
    GPTModel model;

    @Test
    void EndtoEndScenario() {
        //when app starts
        manager = new MockRecipeManagerModel();
        manager.removeAllRecipe();
        whisper = new WhisperModel();


        //adding a new recipe
        //whisper api mocking B/L/D and ingredients
        String mealType = whisper.getMockResult().get(0);
        assertEquals("lunch", mealType);
        String ingredients = whisper.getMockResult().get(1);
        assertEquals("turkey, tomato, bread, cheese, mayonaise", ingredients);
        model = new GPTModel();
        model.setPerameters(mealType, ingredients);

        //chat gpt mock response
        RecipeModel recipe = model.sendRequestMock();
        assertEquals("Mock Recipe", recipe.getTitle());
        assertEquals("Ingredients: \n" + "Mock Ingredients", recipe.getDescription());

        //save function for recipe manger
        manager.addRecipe(recipe);
        assertEquals(1, manager.getList().size());
        assertEquals(recipe, manager.getList().get(0));

        //check csv if it is there, testing if it is visibale from main


        //creating duplicate recipes
        RecipeModel recipe2 = model.sendRequestMock();
        manager.addRecipe(recipe2);
        RecipeModel recipe3 = model.sendRequestMock();
        manager.addRecipe(recipe3);
        assertEquals("Mock Recipe 2", manager.getList().get(0).getTitle());
        assertEquals("Mock Recipe 1", manager.getList().get(1).getTitle());
        assertEquals("Mock Recipe", manager.getList().get(2).getTitle());

        //mock editting the recipe
        manager.getRecipe(recipe.getTitle()).setDescription("this is a new description");
        try {
            manager.updateRecipesToDatabase();
        } catch (IOException e1) {
            System.out.println("Could not update recipe");
        }
   
        assertEquals("this is a new description", manager.getList().get(2).getDescription());
        assertEquals(3, manager.getList().size());

        //test deleting a recipe from recipeManager
        manager.removeRecipe(recipe2);
        assertEquals(2, manager.getList().size());
        assertEquals("Mock Recipe 2", manager.getList().get(0).getTitle());
        assertEquals("Mock Recipe", manager.getList().get(1).getTitle());

        manager.removeRecipe(recipe3);
        assertEquals(1, manager.getList().size());
        assertEquals("Mock Recipe", manager.getList().get(0).getTitle());

        manager.removeRecipe(recipe);
        assertEquals(0, manager.getList().size());
    }
    
}
