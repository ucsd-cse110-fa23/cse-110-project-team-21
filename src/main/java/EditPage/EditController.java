package EditPage;
import Main.Main;
import RecipeManager.RecipeModel;

public class EditController {

    private EditView editview;
    private EditModel editmodel;
    private RecipeModel recipe;

    public EditController(EditView view) {
        this.editview = view;
        this.recipe = view.getRecipe();
        this.editmodel = new EditModel(this);
    }

    public void activate() {
        addListeners();
    }

    private void addListeners() {
        // Back button functionality
        editview.getCancelButton().setOnAction(e -> {
            Main.sceneManager.ChangeScene(editview.getDetailView());
        });


        // Save button functionality:
        // 1. Update the recipe in the database using the model
        // 2. Update the DetailView
        editview.getSaveButton().setOnAction(e -> {
            String editedDescription = editview.getEditedText();
            editmodel.updateRecipe(editedDescription);
            editview.getDetailView().controller.update(editedDescription);
            Main.sceneManager.ChangeScene(editview.getDetailView());
        });
    }

    public RecipeModel getRecipe() {
        return recipe;
    }
}
