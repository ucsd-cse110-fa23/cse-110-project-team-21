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
    ArrayList<String> recordingResult;

    public NewRecipeController(NewRecipeView newRecipeView) {
        this.newRecipeView = newRecipeView;
    }

    // Binding the controller to the view
    // This function is called when the view is first loaded.
    public void activate(ArrayList<String> recordingResult) {
        this.recordingResult = recordingResult;
        addListeners();
        String mealType = recordingResult.get(0);
        String ingredients = recordingResult.get(1);
        this.gptModel = new GPTModel();
        this.gptModel.setPerameters(mealType, ingredients);  
        try {
            recipe = gptModel.sendRequest();
            recipe.setTitle(mealType+ ": " + recipe.getTitle());
            recipe.setMealType(mealType);
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
            Main.recipeManager.addRecipe(recipe);
            Main.mainView.homecontroller.updateRecipeList();
            Main.sceneManager.ChangeScene(Main.mainView);
        });

        // Dont save button functionality
        newRecipeView.getFooter().getDontSaveButton().setOnAction(e -> {
            System.out.println("Don't Save button pressed");
            Main.sceneManager.ChangeScene(Main.mainView);
        });

        newRecipeView.getFooter().getRefreshButton().setOnAction(e -> {
            System.out.println("Refresh button pressed");
            try {
                recipe = gptModel.sendRequest();
                recipe.setTitle(recordingResult.get(0)+ ": " + recipe.getTitle());
            } catch (Exception e1) {
                System.out.println(e1);
                e1.printStackTrace();
                showNoServerAlert();
                return;
            }
            newRecipeView.getHeader().setTitleText(recipe.getTitle());
            this.newRecipeView.getDesc().setDescription(recipe.getDescription());
        });
    }

    public void showNoServerAlert (){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Server Down Error");
        alert.setHeaderText(null);
        alert.setContentText("Server could not be connected. Please try again later.");
        alert.showAndWait();
    }
}






