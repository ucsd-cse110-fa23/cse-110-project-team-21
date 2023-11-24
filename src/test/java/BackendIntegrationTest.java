
import org.junit.jupiter.api.Test;

import GPTPage.GPTModel;
import RecipeManager.RecipeManagerModel;
import RecipeManager.RecipeModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;



public class BackendIntegrationTest {

    @Test
    void testBDDScenario() {
        GPTModel model = new GPTModel();
        RecipeManagerModel manager = new RecipeManagerModel();
        RecipeModel mockRecipe = model.sendRequestMock();

        model.setPerameters("breakfast", "eggs, bacon, bread");
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


        mockRecipe = model.sendRequestMock();
        manager.addRecipe(mockRecipe);
        assertEquals("Mock Recipe 2", manager.getList().get(0).getTitle());
        assertEquals("Mock Recipe 1", manager.getList().get(1).getTitle());
        assertEquals("Mock Recipe", manager.getList().get(2).getTitle());

        mockRecipe = model.sendRequestMock();
        manager.removeRecipe(mockRecipe);
        assertEquals("Mock Recipe 2", manager.getList().get(0).getTitle());
        assertEquals("Mock Recipe 1", manager.getList().get(1).getTitle());

        mockRecipe = model.sendRequestMock();
        mockRecipe.setTitle("Mock Recipe 1");
        manager.removeRecipe(mockRecipe);
        assertEquals(1, manager.getList().size());
        mockRecipe.setTitle("Mock Recipe 2");
        manager.removeRecipe(mockRecipe);
        assertEquals(0, manager.getList().size());
    }
}
