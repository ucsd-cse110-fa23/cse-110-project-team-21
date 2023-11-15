import org.junit.jupiter.api.Test;

import Controller.GPTController;
import Controller.WhisperController;
import RecipeLogic.Recipe;
import RecipeLogic.RecipeManager;
import UI.MainPage.Main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;



public class EndtoEndTest {
    RecipeManager manager;
    WhisperController whisper;
    GPTController controller;

    @Test
    void EndtoEndScenario() {
        //when app starts
        manager = new RecipeManager();
        manager.removeAllRecipe();

        whisper = new WhisperController();


        //adding a new recipe
        //whisper api mocking B/L/D and ingredients
        String mealType = whisper.getMockResult().get(0);
        assertEquals("lunch", mealType);
        String ingredients = whisper.getMockResult().get(1);
        assertEquals("turkey, tomato, bread, cheese, mayonaise", ingredients);
        controller = new GPTController();
        controller.setPerameters(mealType, ingredients);

        //chat gpt mock response
        Recipe recipe = controller.sendRequestMock();
        assertEquals("Mock Recipe", recipe.getTitle());
        assertEquals("Ingredients: \n" + "Mock Ingredients", recipe.getDescription());

        //save function for recipe manger
        manager.addRecipe(recipe);
        assertEquals(1, manager.getList().size());
        assertEquals(recipe, manager.getList().get(0));

        //check csv if it is there, testing if it is visibale from main


        //creating duplicate recipes
        Recipe recipe2 = controller.sendRequestMock();
        manager.addRecipe(recipe2);
        Recipe recipe3 = controller.sendRequestMock();
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
