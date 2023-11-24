//package //test.java;


import org.junit.jupiter.api.Test;

import RecipeManager.RecipeManagerModel;
import RecipeManager.RecipeModel;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;


public class RecipeJunitTest {
    private RecipeManagerModel manager;
    private RecipeModel firstRecipe;
    
    @BeforeEach
    void setUp() {
        firstRecipe = new RecipeModel("sandwich", "put stuff between two slices of bread");
        manager = new RecipeManagerModel();
        manager.removeAllRecipe();
    }

    // Testing the Recipe 
    @Test
    void testGetTitle_shouldReturnCorrectTitle() {
        RecipeModel newRecipe = new RecipeModel("Chocolate Cake", "Delicious dark chocolate cake");
        assertEquals("Chocolate Cake", newRecipe.getTitle(), "The title should be 'Chocolate Cake'.");
    }

    @Test
    void testGetDescription_shouldReturnCorrectDescription() {
        RecipeModel newRecipe = new RecipeModel("Chocolate Cake", "Delicious dark chocolate cake");
        assertEquals("Delicious dark chocolate cake", newRecipe.getDescription(), "The description should match the initial value.");
    }

    @Test
    void testSetDescription_shouldUpdateDescription() {
        String newDescription = "Moist chocolate cake with ganache";
        RecipeModel oldRecipe = new RecipeModel("Chocolate Cake", "Delicious dark chocolate cake");
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
        RecipeModel fRecipe = new RecipeModel("macarrao", "DEAD\n DEAD\n DEMONS \r DEDEDEDEDE\r DESTRUCTION");
        manager.addRecipe(fRecipe);
        RecipeModel secondRecipe = new RecipeModel("Sauteed elder thing", "Get 4 \r\r\r eyes Jouurhk \r\n put it in bowl\n\n uuurhy with tomato\n\n\n\r sauce.");
        manager.addRecipe(secondRecipe);
        RecipeModel thirdRecipe = new RecipeModel("CS student healthy meal", "\n\r\r\r");
        manager.addRecipe(thirdRecipe);
        RecipeModel fourthRecipe = new RecipeModel("Food for black holes", "");
        manager.addRecipe(fourthRecipe);

        //Recipe thirdRecipe = new Recipe("Sarcastic's buffet", "healthy salad");
        assertEquals("DEAD\n DEAD\n DEMONS \r DEDEDEDEDE\r DESTRUCTION", manager.getList().get(3).getDescription());
        assertEquals("Get 4 \r\r\r eyes Jouurhk \r\n put it in bowl\n\n uuurhy with tomato\n\n\n\r sauce.", manager.getList().get(2).getDescription());
        assertEquals("\n\r\r\r", manager.getList().get(1).getDescription());
        assertEquals("", manager.getList().get(0).getDescription());

        /**
         * In Title
         */
        RecipeModel fifthRecipe = new RecipeModel("Birds\nneck\r", "dummy");
        manager.addRecipe(fifthRecipe);
        RecipeModel sixthRecipe = new RecipeModel("\n\n\rGotcha", "dummy");
        manager.addRecipe(sixthRecipe);
        RecipeModel seventhRecipe = new RecipeModel("\n\r\r\n", "dummy");
        manager.addRecipe(seventhRecipe);
        RecipeModel eighthRecipe = new RecipeModel("", "dummy");
        manager.addRecipe(eighthRecipe);

        assertEquals("Birds\nneck\r", manager.getList().get(3).getTitle());
        assertEquals("\n\n\rGotcha", manager.getList().get(2).getTitle());
        assertEquals("\n\r\r\n", manager.getList().get(1).getTitle());
        assertEquals("", manager.getList().get(0).getTitle());
    }

    @Test
    void testQuotes() {
        RecipeModel fRecipe = new RecipeModel("dummy", "i'll assume you \"know\" how 'a' recipe work\"");
        manager.addRecipe(fRecipe);
        RecipeModel secondRecipe = new RecipeModel("Sarcastic delight", "\"\"\"\"''''");
        manager.addRecipe(secondRecipe);
        assertEquals("i'll assume you \"know\" how 'a' recipe work\"", manager.getList().get(1).getDescription());
        assertEquals("\"\"\"\"''''", manager.getList().get(0).getDescription());
        
        RecipeModel thirdRecipe = new RecipeModel("\"Pasta\"", "dummy");
        manager.addRecipe(thirdRecipe);
        RecipeModel fourhRecipe = new RecipeModel("\"Lonelyquote", "dummy");
        manager.addRecipe(fourhRecipe);
        RecipeModel fifthRecipe = new RecipeModel("Mamma's Special \"Teaa\" J\" \" '' '", "dummy");
        manager.addRecipe(fifthRecipe);

        assertEquals("\"Pasta\"", manager.getList().get(2).getTitle());
        assertEquals("\"Lonelyquote", manager.getList().get(1).getTitle());
        assertEquals("Mamma's Special \"Teaa\" J\" \" '' '", manager.getList().get(0).getTitle());
    }

    @Test
    void specialCharacters() {
        RecipeModel fRecipe = new RecipeModel(".?!#$&%{}[]<>_-+=/*^;:", ".?!#$&%{}[]<>_-+=/*^;:");
        manager.addRecipe(fRecipe);
        assertEquals(".?!#$&%{}[]<>_-+=/*^;:", manager.getList().get(0).getTitle());
        assertEquals(".?!#$&%{}[]<>_-+=/*^;:", manager.getList().get(0).getDescription());
    }
}
