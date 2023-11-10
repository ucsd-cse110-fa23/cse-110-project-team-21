
import org.junit.jupiter.api.Test;

import RecipeLogic.Recipe;
import RecipeLogic.RecipeManager;
import UI.OpenAIResponsePage.OpenAIResponseController;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;


public class BackendIntegrationTest {

    @Test
    void testBDDScenario() {
        OpenAIResponseController controller = new OpenAIResponseController("breakfast", "eggs, bacon, bread");
        RecipeManager manager = new RecipeManager();
        Recipe mockRecipe = controller.sendRequestMock();

        manager.removeAllRecipe();
        manager.addRecipe(mockRecipe);
        assertEquals("Mock Recipe", manager.getList().get(0).getTitle());

        manager.editRecipe("Mock Recipe", "Mock Description 2");
        assertEquals("Mock Description 2", manager.getList().get(0).getDescription());
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
