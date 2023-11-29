package Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import RecipeManager.RecipeModel;
import javafx.event.ActionEvent;

public class DBController {

    // a significant amount of the code in this class is taken or inspired from Lab 5

    // all methods in this class that handle DB functions call model.performRequest.
    // model.performRequest takes 4 args: a http request method name, a UserModel user, a RecipeModel recipe, a Integer misc

    // right now the code is written as close as possible to the MVP way in lab 5, but if we need to, we can make these methods take args directly

    //private DBView view;
    private DBModel model;

    private UserModel fakeUser;
    private RecipeModel fakeRecipe;
    private RecipeModel fakeRecipe2;
    private RecipeModel editedRecipe;

    public DBController(/*DBView view,*/ DBModel model) {
        //this.view = view;
        this.model = model;

        fakeUser = new UserModel("Alex", "1234");
        fakeRecipe = new RecipeModel("Sliced Apple", "Slice up an apple");
        fakeRecipe.setMealType("Lunch");
        fakeRecipe.setIndex(0);

        editedRecipe = new RecipeModel("Sliced Apple", "Slice up 2 apples");
        editedRecipe.setMealType("Lunch");
        editedRecipe.setIndex(0);

        fakeRecipe2 = new RecipeModel("Bread on a Plate", "Put some bread ... on da plate");
        fakeRecipe2.setMealType("Lunch");
        fakeRecipe2.setIndex(1);

        /*this.view.setPostButtonAction(this::handlePostButton);
        this.view.setGetButtonAction(this::handleGetButton);
        this.view.setPutButtonAction(this::handlePutButton);
        this.view.setDeleteButtonAction(this::handleDeleteButton); */
    }


    public void handlePostButton(ActionEvent event, int misc/*testing , int num */) throws Exception { // add
        // add one recipe: needs user info, recipe info, and misc=nextIndex
        // add new user: needs user info, misc=-1
        String response;
        /*if (num==1) {*/response = model.performRequest("POST", fakeUser, fakeRecipe, misc);/* }
        else {response = model.performRequest("POST", fakeUser, fakeRecipe2, misc);} */
        System.out.println("Response: " + response);
        //view.showAlert("Response", response);
    }

    public String handleGetButton(ActionEvent event, int misc) throws Exception { // get
        // get a user (check if the user name is taken for account creation): needs user info, misc=1
        // get a user (verify their password matches for login): needs user info, misc=2
        // get all of a user's recipes: needs user info, misc=3
        String response = model.performRequest("GET", fakeUser, null, misc);
        return response;
        //view.showAlert("Response", response);
    }

    public void handlePutButton(ActionEvent event) throws Exception{ // edit
        // edit: needs user info, recipe info, misc=0
        String response = model.performRequest("PUT", fakeUser, editedRecipe, 0);
        //view.showAlert("Response", response);
    }

    public void handleDeleteButton(ActionEvent event) throws Exception{ // delete
        // delete: needs user info and recipe info

        String response = model.performRequest("DELETE", fakeUser, fakeRecipe, 0);
        //view.showAlert("Response", response);
    }

    public ArrayList<RecipeModel> parseDBRecipes(String raw) {
        ArrayList<RecipeModel> recipes = new ArrayList<>();
        String[] rawRecipes = raw.split("&");
        for (String rawRecipe : rawRecipes) {
            String[] recipeInfo = rawRecipe.split("_");
            RecipeModel tmp = new RecipeModel(recipeInfo[0], recipeInfo[1]);
            tmp.setMealType(recipeInfo[2]);
            tmp.setIndex(Integer.parseInt(recipeInfo[3]));
            recipes.add(tmp);
        }
        return recipes;
    }

    public void setUser (UserModel user) {
        this.fakeUser = user;
    }
    
}
