import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import DetailPage.MockDallEModel;
import RecipeManager.RecipeModel;

public class DallEJunitTest {
    @Test
    void testRequestingImageUnexistentTitle(){

        MockDallEModel dallE = new MockDallEModel();
        ArrayList<String> mockDirectory = new ArrayList<String>();
        mockDirectory.add("=Bacon,%20Syrup,%20and%20Pancake%20Stack.jpg");
        mockDirectory.add("=Bacon%20Pancake%20Roll-Ups.jpg");
        mockDirectory.add("=Fish%20and%20Rice%20Bowl%20.jpg");
        mockDirectory.add("=Coconut%20Fish%20Lunch.jpg");
        mockDirectory.add("=Tomato,%20Onion,%20and%20Bread%20Casserole.jpg");
        
        RecipeModel recipe = new RecipeModel("Tomato, Onion, and Bread Foie gras", ""); 
        String previewImgFileName = dallE.performImageRequest(recipe.getTitle().replace(" ", "%20"), mockDirectory);
        //assertTrue(previewImgFileName.equals("=Tomato,%20Onion,%20and%20Bread%20Foie%20gras.jpg"));
        assertEquals(previewImgFileName,"=Tomato,%20Onion,%20and%20Bread%20Foie%20gras.jpg");
        mockDirectory.add("=Tomato,%20Onion,%20and%20Bread%20Foie%20gras.jpg");

        RecipeModel recipe2 = new RecipeModel("Bacon,Syrup, and Pancakes", "");
        String previewImgFileName2 = dallE.performImageRequest(recipe2.getTitle().replace(" ", "%20"), mockDirectory);
        assertTrue(previewImgFileName2.equals("=Bacon,Syrup,%20and%20Pancakes.jpg"));
        mockDirectory.add("=Bacon,Syrup,%20and%20Pancakes.jpg");
        
        RecipeModel recipe3 = new RecipeModel("Coconut,Fish,Lunch", "");
        String previewImgFileName3 = dallE.performImageRequest(recipe3.getTitle().replace(" ", "%20"), mockDirectory);
        assertTrue(previewImgFileName3.equals("=Coconut,Fish,Lunch.jpg"));
        mockDirectory.add("=Coconut,Fish,Lunch.jpg"); 
    }
    @Test
    void testRequestingImageExistTitle(){
        MockDallEModel dallE = new MockDallEModel();
        ArrayList<String> mockDirectory = new ArrayList<String>();
        mockDirectory.add("=Bacon,%20Syrup,%20and%20Pancake%20Stack.jpg");
        mockDirectory.add("=Bacon%20Pancake%20Roll-Ups.jpg");
        mockDirectory.add("=Fish%20and%20Rice%20Bowl%20.jpg");
        mockDirectory.add("=Coconut%20Fish%20Lunch.jpg");
        mockDirectory.add("=Tomato,%20Onion,%20and%20Bread%20Casserole.jpg");
        
        RecipeModel recipe0 = new RecipeModel("Bacon, Syrup, and Pancake Stack", ""); 
        String previewImgFileName0 = dallE.performImageRequest(recipe0.getTitle().replace(" ", "%20"), mockDirectory);
        assertEquals(previewImgFileName0,"=Bacon,%20Syrup,%20and%20Pancake%20Stack.jpg [already existed]");
        
        RecipeModel recipe1 = new RecipeModel("Tomato, Onion, and Bread Casserole", ""); 
        String previewImgFileName1 = dallE.performImageRequest(recipe1.getTitle().replace(" ", "%20"), mockDirectory);
        assertEquals(previewImgFileName1,"=Tomato,%20Onion,%20and%20Bread%20Casserole.jpg [already existed]");
        
        RecipeModel recipe2 = new RecipeModel("Tomato, Onion, and Bread Foie gras", "");
        String previewImgFileName2 = dallE.performImageRequest(recipe2.getTitle().replace(" ", "%20"), mockDirectory);
        assertEquals(previewImgFileName2,"=Tomato,%20Onion,%20and%20Bread%20Foie%20gras.jpg");
        mockDirectory.add("=Tomato,%20Onion,%20and%20Bread%20Foie%20gras.jpg");
        
        RecipeModel recipe3 = new RecipeModel("Tomato, Onion, and Bread Foie gras", "");
        String previewImgFileName3 = dallE.performImageRequest(recipe3.getTitle().replace(" ", "%20"), mockDirectory);
        assertEquals(previewImgFileName3,"=Tomato,%20Onion,%20and%20Bread%20Foie%20gras.jpg [already existed]");

    }
}
