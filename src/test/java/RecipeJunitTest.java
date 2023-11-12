//package //test.java;


import org.junit.jupiter.api.Test;

import RecipeLogic.Recipe;
import RecipeLogic.RecipeManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;


public class RecipeJunitTest {
    private RecipeManager manager;
    private Recipe firstRecipe;
    
    @BeforeEach
    void setUp() {
        firstRecipe = new Recipe("sandwich", "put stuff between two slices of bread");
        manager = new RecipeManager();
        manager.removeAllRecipe();
    }

    // Testing the Recipe 
    @Test
    void testGetTitle_shouldReturnCorrectTitle() {
        Recipe newRecipe = new Recipe("Chocolate Cake", "Delicious dark chocolate cake");
        assertEquals("Chocolate Cake", newRecipe.getTitle(), "The title should be 'Chocolate Cake'.");
    }

    @Test
    void testGetDescription_shouldReturnCorrectDescription() {
        Recipe newRecipe = new Recipe("Chocolate Cake", "Delicious dark chocolate cake");
        assertEquals("Delicious dark chocolate cake", newRecipe.getDescription(), "The description should match the initial value.");
    }

    @Test
    void testSetDescription_shouldUpdateDescription() {
        String newDescription = "Moist chocolate cake with ganache";
        Recipe oldRecipe = new Recipe("Chocolate Cake", "Delicious dark chocolate cake");
        oldRecipe.setDescription(newDescription);
        assertEquals(newDescription, oldRecipe.getDescription(), "The description should be updated to the new value.");
    }


    // Testing Recipe Manager
    @Test
    void testAdd() {
        manager.addRecipe(firstRecipe);
        assertEquals("sandwich", manager.getList().get(0).getTitle());
        assertEquals(1, manager.getList().size());
        manager.removeRecipe(firstRecipe);
        assertEquals(0, manager.getList().size());
    }


    @Test 
    void testRemove() {
        manager.addRecipe(firstRecipe);
        manager.addRecipe(firstRecipe);
        manager.removeRecipe(firstRecipe);
        assertEquals(1, manager.getList().size());
        manager.removeRecipe(firstRecipe);
        assertEquals(1, manager.getList().size());
    }

    @Test
    void testGetList() {
        manager.addRecipe(firstRecipe);
        manager.addRecipe(firstRecipe);
        assertEquals(2, manager.getList().size());
        assertEquals("sandwich 1", manager.getList().get(0).getTitle());
        assertEquals("sandwich", manager.getList().get(1).getTitle());
        manager.removeRecipe(firstRecipe);
        assertEquals(1, manager.getList().size());
        manager.removeRecipe(firstRecipe);
        assertEquals(1, manager.getList().size());
    }
    
    @Test
    void testLineBreaks() {
        /**
         * In Description
         */
        Recipe fRecipe = new Recipe("macarrao", "DEAD\n DEAD\n DEMONS \r DEDEDEDEDE\r DESTRUCTION");
        manager.addRecipe(fRecipe);
        Recipe secondRecipe = new Recipe("Sauteed elder thing", "Get 4 \r\r\r eyes Jouurhk \r\n put it in bowl\n\n uuurhy with tomato\n\n\n\r sauce.");
        manager.addRecipe(secondRecipe);
        Recipe thirdRecipe = new Recipe("CS student healthy meal", "\n\r\r\r");
        manager.addRecipe(thirdRecipe);
        Recipe fourthRecipe = new Recipe("Food for black holes", "");
        manager.addRecipe(fourthRecipe);

        //Recipe thirdRecipe = new Recipe("Sarcastic's buffet", "healthy salad");
        assertEquals("DEAD\n DEAD\n DEMONS \r DEDEDEDEDE\r DESTRUCTION", manager.getList().get(3).getDescription());
        assertEquals("Get 4 \r\r\r eyes Jouurhk \r\n put it in bowl\n\n uuurhy with tomato\n\n\n\r sauce.", manager.getList().get(2).getDescription());
        assertEquals("\n\r\r\r", manager.getList().get(1).getDescription());
        assertEquals("", manager.getList().get(0).getDescription());

        /**
         * In Title
         */
        Recipe fifthRecipe = new Recipe("Birds\nneck\r", "dummy");
        manager.addRecipe(fifthRecipe);
        Recipe sixthRecipe = new Recipe("\n\n\rGotcha", "dummy");
        manager.addRecipe(sixthRecipe);
        Recipe seventhRecipe = new Recipe("\n\r\r\n", "dummy");
        manager.addRecipe(seventhRecipe);
        Recipe eighthRecipe = new Recipe("", "dummy");
        manager.addRecipe(eighthRecipe);

        assertEquals("Birds\nneck\r", manager.getList().get(3).getTitle());
        assertEquals("\n\n\rGotcha", manager.getList().get(2).getTitle());
        assertEquals("\n\r\r\n", manager.getList().get(1).getTitle());
        assertEquals("", manager.getList().get(0).getTitle());
    }

    @Test
    void testQuotes() {
        Recipe fRecipe = new Recipe("dummy", "i'll assume you \"know\" how 'a' recipe work\"");
        manager.addRecipe(fRecipe);
        Recipe secondRecipe = new Recipe("Sarcastic delight", "\"\"\"\"''''");
        manager.addRecipe(secondRecipe);
        assertEquals("i'll assume you \"know\" how 'a' recipe work\"", manager.getList().get(1).getDescription());
        assertEquals("\"\"\"\"''''", manager.getList().get(0).getDescription());
        
        Recipe thirdRecipe = new Recipe("\"Pasta\"", "dummy");
        manager.addRecipe(thirdRecipe);
        Recipe fourhRecipe = new Recipe("\"Lonelyquote", "dummy");
        manager.addRecipe(fourhRecipe);
        Recipe fifthRecipe = new Recipe("Mamma's Special \"Teaa\" J\" \" '' '", "dummy");
        manager.addRecipe(fifthRecipe);

        assertEquals("\"Pasta\"", manager.getList().get(2).getTitle());
        assertEquals("\"Lonelyquote", manager.getList().get(1).getTitle());
        assertEquals("Mamma's Special \"Teaa\" J\" \" '' '", manager.getList().get(0).getTitle());
    }

    @Test
    void specialCharacters() {
        Recipe fRecipe = new Recipe(".?!#$&%{}[]<>_-+=/*^;:", ".?!#$&%{}[]<>_-+=/*^;:");
        manager.addRecipe(fRecipe);
        assertEquals(".?!#$&%{}[]<>_-+=/*^;:", manager.getList().get(0).getTitle());
        assertEquals(".?!#$&%{}[]<>_-+=/*^;:", manager.getList().get(0).getDescription());
    }
}
