package DetailPage;

import java.util.Optional;

import EditPage.EditView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import Main.Main;

public class DetailController {

    // NOTE: Detail Page does not have a model. This is because there is no saving/loading logic for the detail page.

    private DetailView detailview;
    
    public DetailController(DetailView view) {
        this.detailview = view;
    }

    public void activate() {
        addListeners();
    }

    public void addListeners() {
        // Back button functionality
        detailview.getBackButton().setOnAction(e -> {
            Main.mainView.homecontroller.updateRecipeList();
            Main.sceneManager.ChangeScene(Main.mainView);
        });

        // Delete button functionality
        detailview.getDeleteButton().setOnAction(e -> {
            // create an alert that allows user to confirm deletion
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Delete Recipe Confirmation");
            alert.setHeaderText("Delete Recipe Confirmation");
            alert.setContentText("Are you sure to permanently delete this recipe?");

            ButtonType buttonConfirm = new ButtonType("Confirm");
            ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonConfirm, buttonCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonConfirm){
                try{
                    Main.recipeManager.removeRecipe(detailview.getRecipe());
                    Main.mainView.homecontroller.updateRecipeList();
                    Main.sceneManager.ChangeScene(Main.mainView);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showNoServerAlert();
                }
            } 
        });

        // Edit button functionality
        detailview.getEditButton().setOnAction(e -> {
            Main.sceneManager.ChangeScene(new EditView(this.detailview));
        });
    }

    public String generateImage(String title){
        return Main.dallEModel.performImageRequest(title);
    }

    // This function is called when the recipe is edited in the EditView
    public void update(String editedDescription) {
        this.detailview.updateDescription(editedDescription);
    }


    public void showNoServerAlert (){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Server Down Error");
        alert.setHeaderText(null);
        alert.setContentText("Server could not be connected. Please try again later.");
        alert.showAndWait();
    }
}
