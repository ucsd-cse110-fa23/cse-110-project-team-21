import org.junit.jupiter.api.Test;

import DetailPage.MockDallEModel;
import HomePage.HomeController;
import LoginPage.MockLoginController;
import NewRecipePage.GPTModel;
import NewRecipePage.MockGPTModel;
import RecipeManager.MockRecipeManagerModel;
import RecipeManager.RecipeModel;
import SharePage.MockShareView;
import SignUpPage.MockSignUpController;
import SignUpPage.SignUpController;
import WhisperPage.WhisperModel;
import server.UserModel;
import Main.Main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


public class EndtoEndTwoTest {
    ArrayList<UserModel> mockUserDatabase;
    ArrayList<RecipeModel> mockRecipeDatabase;
    ArrayList<String> mockLocalFileDirectory = new ArrayList<String>();
    @Test
    void EndtoEndScenario() throws Exception{
        /**
         * Start the app. And on the first screen, if the server is functioning,
         *  and you have never registered an account before, you should see a login 
         * page where you can input your username and password. There is another 
         * button below saying “create a new account”.
         */
        MockLoginController loginController = new MockLoginController(true);
        assertTrue(loginController.isServer);
        /**
         * Click the Create new account button, and the app will direct you to a new account
         *  creating page with a username input box and password input boxes as well as 
         * a checkbox for auto-login. Input your defined username and password, check the 
         * autologin box, and click the “create” button. If the username isn’t already taken,
         * you should see a confirmation message saying that your account is successfully created. 
         */
        mockUserDatabase = new ArrayList<UserModel>();
        mockLocalFileDirectory = new ArrayList<String>();
        mockRecipeDatabase = new ArrayList<RecipeModel>();
        MockSignUpController controller;
        UserModel user = new UserModel("dummy", "123456");
        UserModel attemptedUser = new UserModel("dummy", "newpassword");
        try {
            controller = new MockSignUpController(mockUserDatabase);
            String result =  controller.signUpHelper(null, user.getUsername(), user.getPassword(), "123456", true);
            assertEquals("Sign up successful", result);
            String result2 = controller.signUpHelper(null, attemptedUser.getUsername(), attemptedUser.getPassword(), "newpassword", true);
            assertEquals("Username already taken", result2);
        } catch (Exception e) {
            assertEquals("Couldn't initialize SignUpController and mock account creation", "");
        }
        mockLocalFileDirectory.add("password.txt");
        /**
         * Confirm the message. The app will then direct you to the mainpage where you should 
         * see an empty rectangular space, an “New Recipe” button, a “Sort” button, a “Filter” 
         * button, a “Logout” button, and the title of the application.
         */
        /**
         * Click on the “New Recipe” Button. The app should transition to another page 
         * containing a “Recording” button and a text prompt for inputting meal type information 
         * needed for generating a recipe.
         */
        /**
         * Click on the “Record” button to start recording, and there should show up a line of 
         * red text alerting you that your microphone is currently recording. Say “Lunch” and then 
         * click the “Stop” button again. The red text disappears and the app should then 
         * transition to another page containing a “Record” button and a text prompt for inputting 
         * ingredient list information needed for generating a recipe
         */
        String mealType = "Lunch";

        /**
         * Click on the “Record” button to start recording. There should show up a line of red 
         * text alerting you that your microphone is currently recording. Say “Tomatoes, Noodles, 
         * Carrots, Cheese, Garlic” and then click the “Stop” button. The red text then disappears 
         * and the app should then transition to a new recipe result page displaying the newly 
         * generated recipe as well as “Save”, “Don’t Save”, and “Regenerate” buttons. The recipe should 
         * have the ingredient list and meal type tag as specified, have a reasonable title, step-by-step 
         * directions, and a picture depicting the possible appearance of this dish.
         */
        String ingredients = "Tomatoes, Noodles, Carrots, Cheese, Garlic";
        MockGPTModel gptModel = new MockGPTModel();
        gptModel.setPerameters(mealType, ingredients);
        RecipeModel generatedRecipe = gptModel.sendRequest(0);

        MockDallEModel dallE = new MockDallEModel();
 
        String previewImgFileName = dallE.performImageRequest(generatedRecipe.getTitle().replace(" ", "%20"), mockLocalFileDirectory);
        /* The recipe should have the ingredient list and meal type tag as specified, have a reasonable title, step-by-step 
         * directions, and a picture depicting the possible appearance of this dish.
         */
        assertEquals("Lunch", generatedRecipe.getMealType());
        assertEquals("Title with ingredients Tomatoes, Noodles, Carrots, Cheese, Garlic", generatedRecipe.getTitle());
        assertEquals("Description with ingredients Tomatoes, Noodles, Carrots, Cheese, Garlic", generatedRecipe.getDescription());
        assertEquals("=Title%20with%20ingredients%20Tomatoes,%20Noodles,%20Carrots,%20Cheese,%20Garlic.jpg", previewImgFileName);
        //mockLocalFileDirectory.add(previewImgFileName);
        /**
         * Click on the “Regenerate” button. The app should be refreshed and display another newly 
         * generated recipe  transition to the list screen and the title of the newly-saved recipe may 
         * have a different title, final ingredient list (but your ingredient list input was the same), 
         * step-by-step directions, but the same meal type tag.
         */
        generatedRecipe = gptModel.sendRequest(1);
        previewImgFileName = dallE.performImageRequest(generatedRecipe.getTitle().replace(" ", "%20"), mockLocalFileDirectory);
        assertEquals("Lunch", generatedRecipe.getMealType());
        assertEquals("Regenerated title with ingredients Tomatoes, Noodles, Carrots, Cheese, Garlic", generatedRecipe.getTitle());
        assertEquals("Regenerated description with ingredients Tomatoes, Noodles, Carrots, Cheese, Garlic", generatedRecipe.getDescription());
        assertEquals("=Regenerated%20title%20with%20ingredients%20Tomatoes,%20Noodles,%20Carrots,%20Cheese,%20Garlic.jpg", previewImgFileName);
        /**
         * Click the “Save” button, the app should transition to the main screen and the title of the 
         * newly-saved recipe should appear at the top of the list, and the meal type tag is to the 
         * left of that title.
         */
        generatedRecipe.setIndex(1);
        mockRecipeDatabase.add(generatedRecipe);
        mockLocalFileDirectory.add(previewImgFileName); 
        /**
         * Repeat the process d-h for 6 times with different meal types and ingredients to expand 
         * our recipe list. 2 of them should be given a voice input of “Breakfast”, 2 of them should be 
         * given a voice input of “Lunch”, and  2 of them should be given a voice input of “Dinner”. 
         */
        mealType = "Breakfast";
        ingredients = "Butter, Pancakes, Bacons, Cheese";
        gptModel.setPerameters(mealType, ingredients);
        RecipeModel generatedRecipe2 = gptModel.sendRequest(0);
        String previewImgFileName2 = dallE.performImageRequest(generatedRecipe2.getTitle().replace(" ", "%20"), mockLocalFileDirectory); 
        assertEquals("Breakfast", generatedRecipe2.getMealType());
        assertEquals("Title with ingredients Butter, Pancakes, Bacons, Cheese", generatedRecipe2.getTitle());
        assertEquals("Description with ingredients Butter, Pancakes, Bacons, Cheese", generatedRecipe2.getDescription());
        assertEquals("=Title%20with%20ingredients%20Butter,%20Pancakes,%20Bacons,%20Cheese.jpg", previewImgFileName2);
        generatedRecipe2.setIndex(2);
        mockRecipeDatabase.add(generatedRecipe2);
        mockLocalFileDirectory.add(previewImgFileName2);

        mealType = "Breakfast";
        ingredients = "Honey, Oat, Blueberries";
        gptModel.setPerameters(mealType, ingredients);
        RecipeModel generatedRecipe3 = gptModel.sendRequest(0);
        String previewImgFileName3 = dallE.performImageRequest(generatedRecipe3.getTitle().replace(" ", "%20"), mockLocalFileDirectory); 
        assertEquals("Breakfast", generatedRecipe3.getMealType());
        assertEquals("Title with ingredients Honey, Oat, Blueberries", generatedRecipe3.getTitle());
        assertEquals("Description with ingredients Honey, Oat, Blueberries", generatedRecipe3.getDescription());
        assertEquals("=Title%20with%20ingredients%20Honey,%20Oat,%20Blueberries.jpg", previewImgFileName3);
        generatedRecipe3.setIndex(3);
        mockRecipeDatabase.add(generatedRecipe3);
        mockLocalFileDirectory.add(previewImgFileName3);

        mealType = "Lunch";
        ingredients = "Beans, Rice, Chicken";
        gptModel.setPerameters(mealType, ingredients);
        RecipeModel generatedRecipe4 = gptModel.sendRequest(0);
        String previewImgFileName4 = dallE.performImageRequest(generatedRecipe4.getTitle().replace(" ", "%20"), mockLocalFileDirectory); 
        assertEquals("Lunch", generatedRecipe4.getMealType());
        assertEquals("Title with ingredients Beans, Rice, Chicken", generatedRecipe4.getTitle());
        assertEquals("Description with ingredients Beans, Rice, Chicken", generatedRecipe4.getDescription());
        assertEquals("=Title%20with%20ingredients%20Beans,%20Rice,%20Chicken.jpg", previewImgFileName4);
        generatedRecipe4.setIndex(4);
        mockRecipeDatabase.add(generatedRecipe4);
        mockLocalFileDirectory.add(previewImgFileName4);

        mealType = "Dinner";
        ingredients = "Steak, Pasta, Pepper, Salt, Paprica";
        gptModel.setPerameters(mealType, ingredients);
        RecipeModel generatedRecipe5 = gptModel.sendRequest(0);
        String previewImgFileName5 = dallE.performImageRequest(generatedRecipe5.getTitle().replace(" ", "%20"), mockLocalFileDirectory); 
        assertEquals("Dinner", generatedRecipe5.getMealType());
        assertEquals("Title with ingredients Steak, Pasta, Pepper, Salt, Paprica", generatedRecipe5.getTitle());
        assertEquals("Description with ingredients Steak, Pasta, Pepper, Salt, Paprica", generatedRecipe5.getDescription());
        assertEquals("=Title%20with%20ingredients%20Steak,%20Pasta,%20Pepper,%20Salt,%20Paprica.jpg", previewImgFileName5);
        generatedRecipe5.setIndex(5);
        mockRecipeDatabase.add(generatedRecipe5);
        mockLocalFileDirectory.add(previewImgFileName5);

        mealType = "Dinner";
        ingredients = "Garlic, Lettuce";
        gptModel.setPerameters(mealType, ingredients);
        RecipeModel generatedRecipe6 = gptModel.sendRequest(0);
        String previewImgFileName6 = dallE.performImageRequest(generatedRecipe6.getTitle().replace(" ", "%20"), mockLocalFileDirectory); 
        assertEquals("Dinner", generatedRecipe6.getMealType());
        assertEquals("Title with ingredients Garlic, Lettuce", generatedRecipe6.getTitle());
        assertEquals("Description with ingredients Garlic, Lettuce", generatedRecipe6.getDescription());
        assertEquals("=Title%20with%20ingredients%20Garlic,%20Lettuce.jpg", previewImgFileName6);
        generatedRecipe6.setIndex(6);
        mockRecipeDatabase.add(generatedRecipe6);
        mockLocalFileDirectory.add(previewImgFileName6);
        /**
         * At the home page where those 6 different recipes are displayed, click the “Sort” 
         * dropdown menu and there should display four different options to sort: “Alphabetical Order”, 
         * “Reversed Alphabetical Order”, “Chronological Order” and “Reversed Chronological Order”. 
         */
        /**
         * Click the  “Alphabetical Order”, and the main page should have all six of the recipes 
         * sorted by their title’s alphabetical order.
         */
        HomeController homeController = new HomeController(null);
        List<RecipeModel> tempList = mockRecipeDatabase;
        List<RecipeModel> alphabeticallySorted = homeController.mockSortedList(tempList, "A-Z");
        List<RecipeModel> updatedList = new ArrayList<RecipeModel>();
        for(RecipeModel r : alphabeticallySorted){ updatedList.add(r);}
        assertEquals("Regenerated title with ingredients Tomatoes, Noodles, Carrots, Cheese, Garlic", updatedList.get(0).getTitle());
        assertEquals("Title with ingredients Beans, Rice, Chicken", updatedList.get(1).getTitle());
        assertEquals("Title with ingredients Butter, Pancakes, Bacons, Cheese", updatedList.get(2).getTitle());
        assertEquals("Title with ingredients Garlic, Lettuce", updatedList.get(3).getTitle());
        assertEquals("Title with ingredients Honey, Oat, Blueberries", updatedList.get(4).getTitle());
        assertEquals("Title with ingredients Steak, Pasta, Pepper, Salt, Paprica", updatedList.get(5).getTitle());
        /**
         * At the home page where those 6 different recipes are displayed, click the “Filter” dropdown 
         * menu and there should display four different options to sort: “Breakfast”, “Lunch”, “Dinner” 
         * and “Display All”.
         */
        /**
         * Click the “Lunch”, and the main page should only display the 2 dishes that have the tag type 
         * “Lunch” (still sorted by their title’s alphabetical order).
         */
        List<RecipeModel> lunchList = homeController.mockFilteredList(alphabeticallySorted, "Lunch");
        updatedList.clear();
        for(RecipeModel r : lunchList){ updatedList.add(r);}
        assertEquals(lunchList.size(), 2);
        assertEquals("Regenerated title with ingredients Tomatoes, Noodles, Carrots, Cheese, Garlic", updatedList.get(0).getTitle());
        assertEquals("Title with ingredients Beans, Rice, Chicken", updatedList.get(1).getTitle());

        /**
         * Decides to share the second lunch recipe. Click on its title, and the user will be taken to the RecipeDetail
         * page. Click the Share button and the user will be taken to the share page, containing an unique, shareable
         * URL that would display the recipe as a webpage
         */
        MockShareView mockShareView = new MockShareView(updatedList.get(1), user.getUsername());
        assertEquals(Main.HOSTNAME_URL + "/recipe/?=%20" + "dummy" + "&" + "Title%20with%20ingredients%20Beans,%20Rice,%20Chicken",
        mockShareView.getUrl());
        /**
         * Return to recipe list.
         */
        /**
         * Click the “Filter” dropdown menu again and choose “Display All”, then the main page should 
         * display all 6 recipes (still sorted by their title’s alphabetical order).
         */
        List<RecipeModel> revertFilter = homeController.mockFilteredList(alphabeticallySorted, "AllFilter");
        updatedList.clear();
        for(RecipeModel r : revertFilter){ updatedList.add(r);}
        assertEquals(updatedList.size(), alphabeticallySorted.size());
        /**
         * Click “Logout'' at the bottom of the mainpage, and the app automatically closes itself.
         */
        /**
         * Restart the app, and since you have previously chosen to click “auto-login”, this time you 
         * should be automatically logged in and the app is directly displaying the main page with six 
         * recipes you just created, sorted in the order from the latest creation to the oldest creation.
         */
        if(mockLocalFileDirectory.contains("password.txt")){
            assertEquals(0,0);
        } 
        else{
            assertEquals(1, 0);
        }
        /**
         * Click “Logout'' at the bottom of the mainpage, and the app automatically closes itself.
         */
    }
}
