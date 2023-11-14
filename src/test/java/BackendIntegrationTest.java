
import org.junit.jupiter.api.Test;

import Controller.GPTController;
import RecipeLogic.Recipe;
import RecipeLogic.RecipeManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;


public class BackendIntegrationTest {

    @Test
    void testBDDScenario() {
        GPTController controller = new GPTController("breakfast", "eggs, bacon, bread");
        RecipeManager manager = new RecipeManager();
        Recipe mockRecipe = controller.sendRequestMock();

        manager.removeAllRecipe();
        manager.addRecipe(mockRecipe);
        assertEquals("Mock Recipe", manager.getList().get(0).getTitle());

        manager.getRecipe(mockRecipe.getTitle()).setDescription("Mock Description 2");
        try {
            manager.updateRecipesToDatabase();
        } catch (IOException e1) {
            System.out.println("Could not update recipe");
        }
        assertEquals("Mock Description 2", manager.getList().get(0).getDescription());
        assertEquals(1, manager.getList().size());
        manager.addRecipe(mockRecipe);
        assertEquals("Mock Recipe 1", manager.getList().get(0).getTitle());
        assertEquals("Mock Recipe", manager.getList().get(1).getTitle());


        mockRecipe = controller.sendRequestMock();
        manager.addRecipe(mockRecipe);
        assertEquals("Mock Recipe 2", manager.getList().get(0).getTitle());
        assertEquals("Mock Recipe 1", manager.getList().get(1).getTitle());
        assertEquals("Mock Recipe", manager.getList().get(2).getTitle());

        mockRecipe = controller.sendRequestMock();
        manager.removeRecipe(mockRecipe);
        assertEquals("Mock Recipe 2", manager.getList().get(0).getTitle());
        assertEquals("Mock Recipe 1", manager.getList().get(1).getTitle());

        mockRecipe = controller.sendRequestMock();
        mockRecipe.setTitle("Mock Recipe 1");
        manager.removeRecipe(mockRecipe);
        assertEquals(1, manager.getList().size());
        mockRecipe.setTitle("Mock Recipe 2");
        manager.removeRecipe(mockRecipe);
        assertEquals(0, manager.getList().size());
    }
}
