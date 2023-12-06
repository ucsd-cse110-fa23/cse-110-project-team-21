import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.jupiter.api.Test;

import RecipeManager.RecipeModel;
import SharePage.MockShareView;
import Main.Main;

public class URLJunitTest {
    @Test
    void commaHandling() throws Exception {
        String username = "cat,me";
        RecipeModel displayedRecipe1 = new RecipeModel("Fish, Fish,Salmon , and Tuna", "");
        MockShareView mockShareView = new MockShareView(displayedRecipe1, username);
        assertEquals(Main.HOSTNAME_URL +"/recipe/?=%20cat,me&Fish,%20Fish,Salmon%20,%20and%20Tuna", mockShareView.getUrl());
    }
    void spaceHandling() throws Exception {
        String username = "zebra_enjoyer";
        RecipeModel displayedRecipe1 = new RecipeModel("Zebra Meat Quinnoa Salad", "");
        MockShareView mockShareView = new MockShareView(displayedRecipe1, username);
        assertEquals("http://localhost:8100/recipe/?=%20zebra_enjoyer&Zebra%20Meat%20Quinnoa%20Salad", mockShareView.getUrl());
    }
    void operatorsHandling() throws Exception {
        String username = "=+me=-";
        RecipeModel displayedRecipe1 = new RecipeModel("Arithmetic Salad with Onions", "");
        MockShareView mockShareView = new MockShareView(displayedRecipe1, username);
        assertEquals("http://localhost:8100/recipe/?%20=+me=-%20zebra_enjoyer&Arithmetic%20Salad%20with%20Onions", mockShareView.getUrl());
    }
}
