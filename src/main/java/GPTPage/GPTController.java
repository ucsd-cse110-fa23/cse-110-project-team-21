package GPTPage;

import java.util.ArrayList;

import Main.Main;
import RecipeManager.RecipeModel;

public class GPTController {

    GPTView gptView;
    GPTModel gptModel;
    RecipeModel recipe;

    public GPTController(GPTView gptView) {
        this.gptView = gptView;
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
            recipe.setTitle(mealType+ ": " + recipe.getTitle());
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return ;
        }
        gptView.getHeader().setTitleText(recipe.getTitle());
        this.gptView.getDesc().setDescription(recipe.getDescription());
    }


    public void addListeners() {
        // Save button functionality
        gptView.getFooter().getSaveButton().setOnAction(e -> {
            System.out.println("Save button pressed");
            Main.recipeManager.addRecipe(recipe);
            Main.mainView.homecontroller.updateRecipeList();
            Main.sceneManager.ChangeScene(Main.mainView);
        });

        // Dont save button functionality
        gptView.getFooter().getDontSaveButton().setOnAction(e -> {
            System.out.println("Don't Save button pressed");
            Main.sceneManager.ChangeScene(Main.mainView);
        });
    }
}






