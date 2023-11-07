package test.java.OpenAIResponsePageTest.OpenAIResponsePageJUnitTest;

import org.junit.*;

import main.java.OpenAIResponsePage.OpenAIResponseController;


public class OpenAIResponseControllerTest {

    @Test
    public void testController() {
        OpenAIResponseController controller = new OpenAIResponseController("breakfast", "egg, milk, cheese, break, jam, bacon, eggs");
        Assert.assertEquals(controller.getIngredients(), "egg, milk, cheese, break, jam, bacon, eggs");
        Assert.assertEquals(controller.getMealType(), "breakfast");
        Assert.assertEquals(controller.getRecipe(), null);
    }   


}
