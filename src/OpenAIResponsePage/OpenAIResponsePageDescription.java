package OpenAIResponsePage;

import RecipeLogic.Recipe;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class OpenAIResponsePageDescription extends FlowPane {
    Text description;

    public OpenAIResponsePageDescription(Recipe recipe) {
        description = new Text(recipe.getDescription());

        //this.setPrefWrapLength(400);
        description.setWrappingWidth(400);
        // this.setPrefWidth(200);

        this.getChildren().add(description);
    }

    public Text getDescription() {
        return description;
    }
}