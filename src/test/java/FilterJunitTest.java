
import org.junit.jupiter.api.Test;

import HomePage.HomeController;
import RecipeManager.RecipeModel;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;
import java.util.List;
    

public class FilterJunitTest {

    @Test
    void testFilterBreakfast() throws Exception {
        // test sort by title
        List<RecipeModel> recipes = new ArrayList<RecipeModel>();

        RecipeModel recipe1 = new RecipeModel("aname1", "description1");
        recipe1.setIndex(1);
        recipe1.setMealType("Breakfast");

        RecipeModel recipe2 = new RecipeModel("bname2", "description2");
        recipe2.setIndex(2);
        recipe2.setMealType("Lunch");

        RecipeModel recipe3 = new RecipeModel("cname3", "description3");
        recipe3.setIndex(3);
        recipe3.setMealType("Dinner");

        recipes.add(recipe1);
        recipes.add(recipe2);
        recipes.add(recipe3);

        HomeController homeController = new HomeController(null);
        List<RecipeModel> expected = homeController.mockFilteredList(recipes, "Breakfast");
        assertEquals(expected.get(0).getTitle(), "aname1");
        assertEquals(expected.size(), 1);
    }


    @Test
    void testFilterLunch() throws Exception {
        // test sort by title
        List<RecipeModel> recipes = new ArrayList<RecipeModel>();

        RecipeModel recipe1 = new RecipeModel("aname1", "description1");
        recipe1.setIndex(1);
        recipe1.setMealType("Breakfast");

        RecipeModel recipe2 = new RecipeModel("bname2", "description2");
        recipe2.setIndex(2);
        recipe2.setMealType("Lunch");

        RecipeModel recipe3 = new RecipeModel("cname3", "description3");
        recipe3.setIndex(3);
        recipe3.setMealType("Dinner");

        recipes.add(recipe1);
        recipes.add(recipe2);
        recipes.add(recipe3);

        HomeController homeController = new HomeController(null);
        List<RecipeModel> expected = homeController.mockFilteredList(recipes, "Lunch");
        assertEquals(expected.get(0).getTitle(), "bname2");
        assertEquals(expected.size(), 1);
    }


    @Test
    void testFilterDinner() throws Exception {
        // test sort by title
        List<RecipeModel> recipes = new ArrayList<RecipeModel>();

        RecipeModel recipe1 = new RecipeModel("aname1", "description1");
        recipe1.setIndex(1);
        recipe1.setMealType("Breakfast");

        RecipeModel recipe2 = new RecipeModel("bname2", "description2");
        recipe2.setIndex(2);
        recipe2.setMealType("Lunch");

        RecipeModel recipe3 = new RecipeModel("cname3", "description3");
        recipe3.setIndex(3);
        recipe3.setMealType("Dinner");

        recipes.add(recipe1);
        recipes.add(recipe2);
        recipes.add(recipe3);

        HomeController homeController = new HomeController(null);
        List<RecipeModel> expected = homeController.mockFilteredList(recipes, "Dinner");
        assertEquals(expected.get(0).getTitle(), "cname3");
        assertEquals(expected.size(), 1);
    }
}
