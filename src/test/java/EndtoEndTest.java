import org.junit.jupiter.api.Test;
import RecipeLogic.Recipe;
import RecipeLogic.RecipeManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;



public class EndtoEndTest {

    @Test
    void EndtoEndScenario() {
        //when app starts
        RecipeManager manager = new RecipeManager();
        manager.removeAllRecipe();

        //adding a new recipe
        //whisper api mocking B/L/D

        //whisper mocking ingredients

        //chat gpt mock response


        //create recipe object from gpt resposne, and save function for recipe manger

        //check csv if it is there, testing if it is visibale from main

        //testing viewing a recipe (if that is possible) and mock editting the recipe, then saving

        //test deleting a recipe from recipeManager and seeing it change in the csv




    }
    
}
