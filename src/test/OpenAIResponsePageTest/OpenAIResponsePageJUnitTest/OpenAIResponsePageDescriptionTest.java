

package test.OpenAIResponsePageTest.OpenAIResponsePageJUnitTest;

import org.junit.*;


import OpenAIResponsePage.OpenAIResponsePageDescription;
import RecipeLogic.Recipe;

public class OpenAIResponsePageDescriptionTest {

    @Test
    public void testDescription() {
        Recipe recipe = new Recipe("hot dog", "bread, hot dog, ketchup, mustard, relish");
        OpenAIResponsePageDescription description = new OpenAIResponsePageDescription(recipe);
        Assert.assertEquals(description.getDescription().getText(), "bread, hot dog, ketchup, mustard, relish");
    }   

}
