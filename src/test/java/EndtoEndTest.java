import org.junit.jupiter.api.Test;

import Controller.Whisper;
import RecipeLogic.Recipe;
import RecipeLogic.RecipeManager;
import UI.MainPage.Main;
import UI.OpenAIResponsePage.OpenAIResponseController;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;



public class EndtoEndTest {
    RecipeManager manager;
    Whisper whisper;
    OpenAIResponseController controller;

    @Test
    void EndtoEndScenario() {
        //when app starts
        manager = new RecipeManager();
        manager.removeAllRecipe();

        whisper = new Whisper();


        //adding a new recipe
        //whisper api mocking B/L/D and ingredients
        String mealType = whisper.getMockResult().get(0);
        assertEquals("lunch", mealType);
        String ingredients = whisper.getMockResult().get(1);
        assertEquals("turkey, tomato, bread, cheese, mayonaise", ingredients);
        controller = new OpenAIResponseController(mealType, ingredients);

        //chat gpt mock response
        Recipe recipe = controller.sendRequestMock();
        assertEquals("Mock Recipe", recipe.getTitle());
        assertEquals("Ingredients: \n" + "Mock Ingredients", recipe.getDescription());

        //save function for recipe manger
        manager.addRecipe(recipe);
        assertEquals(1, manager.getList().size());

        //check csv if it is there, testing if it is visibale from main

        //creating duplicate recipes


        //testing viewing a recipe (if that is possible) and mock editting the recipe, then saving
        recipe.setDescription("this is a new description");
        try {
            manager.updateRecipesToDatabase();
        } catch (IOException e1) {
            System.out.println("Could not update recipe");
        }
        assertEquals("this is a new description", recipe.getDescription());
        assertEquals(1, manager.getList().size());

        //test deleting a recipe from recipeManager and seeing it change in the csv



        manager.removeAllRecipe();
    }
    
}
