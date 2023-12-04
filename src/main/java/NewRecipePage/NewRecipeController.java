package NewRecipePage;

import java.util.ArrayList;

import Main.Main;
import RecipeManager.RecipeModel;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class NewRecipeController {

    NewRecipeView newRecipeView;
    GPTModel gptModel;
    RecipeModel recipe;

    public NewRecipeController(NewRecipeView newRecipeView) {
        this.newRecipeView = newRecipeView;
    }

    // Binding the controller to the view
    // This function is called when the view is first loaded.
    public void activate(ArrayList<String> recordingResult) {
        addListeners();
        String mealType = recordingResult.get(0);
        String ingredients = recordingResult.get(1);
        this.gptModel = new GPTModel();
        this.gptModel.setPerameters(mealType, ingredients);  
        try {
            recipe = gptModel.sendRequest();
            //recipe.setTitle(mealType+ ": " + recipe.getTitle());
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            showNoServerAlert();
            return ;
        }
        newRecipeView.getHeader().setTitleText(recipe.getTitle());
        this.newRecipeView.getDesc().setDescription(recipe.getDescription());
    }


    public void addListeners() {
        // Save button functionality
        newRecipeView.getFooter().getSaveButton().setOnAction(e -> {
            System.out.println("Save button pressed");
            recipe.setIndex(Main.recipeManager.incrementNextIndex());
            Main.recipeManager.addRecipe(recipe);
            Main.mainView.homecontroller.updateRecipeList();
            Main.sceneManager.ChangeScene(Main.mainView);
        });

        // Dont save button functionality
        newRecipeView.getFooter().getDontSaveButton().setOnAction(e -> {
            System.out.println("Don't Save button pressed");
            Main.sceneManager.ChangeScene(Main.mainView);
        });
    }

    public String generateImage(String title){
        //return Main.dallEModel.performImageRequest(title);
        recipe.setImageURL(Main.dallEModel.performImageRequest(title));
        return "=" + title + ".jpg";
    }

    public void showNoServerAlert (){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Server Down Error");
        alert.setHeaderText(null);
        alert.setContentText("Server could not be connected. Please try again later.");
        alert.showAndWait();
    }
}






