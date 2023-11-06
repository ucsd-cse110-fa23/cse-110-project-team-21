package test.OpenAIResponsePageTest;


import org.junit.*;
import OpenAIResponsePage.*;

public class OpenAIResponseIntegrationTest {
    
    @Test
    public void testIntegration (){
        OpenAIResponseController controller = new OpenAIResponseController("breakfast", "egg, milk, cheese, break, jam, bacon, eggs");
        Assert.assertEquals(controller.getIngredients(), "egg, milk, cheese, break, jam, bacon, eggs");
        Assert.assertEquals(controller.getMealType(), "breakfast");
        Assert.assertEquals(controller.getRecipe(), null);
        // OpenAIResponsePageFooter footer = new OpenAIResponsePageFooter();
        // Assert.assertEquals(footer.getSaveButton().getText(), "Save");
        // Assert.assertEquals(footer.getDontSaveButton().getText(), "Don't Save");
        OpenAIResponsePageHeader header = new OpenAIResponsePageHeader("My Recipe Title");
        Assert.assertEquals(header.getTitleText().getText(), "My Recipe Title");
        // OpenAIResponseScene scene = new OpenAIResponseScene();
        // Assert.assertEquals(scene.getRecipe(), null);
    }
}
