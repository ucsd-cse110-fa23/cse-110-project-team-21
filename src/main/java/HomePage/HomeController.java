package HomePage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import DetailPage.DetailView;
import Main.Main;
import RecipeManager.RecipeModel;
import WhisperPage.WhisperView;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;

public class HomeController {

    HomeView mainPageView;
    HomeModel mainPageModel = new HomeModel(this);

    public HomeController(HomeView mainPageView) {
        this.mainPageView = mainPageView;
    }

    // Binding the controller to the view
    // This function is called when the view is first loaded
    public void activate(){
        updateRecipeList();
        addListeners();
    }


    // This function updates the recipe list in the home page
    // It is called when a new recipe is added, edited, or deleted
    public void updateRecipeList(){
        this.mainPageView.recipeList.getChildren().clear();
        try{
            List<RecipeModel> newRecipeList = mainPageModel.loadFromDatabase();
            for(RecipeModel r: newRecipeList){
                MainPageRecipeCard toAdd = new MainPageRecipeCard(r.getTitle(), r.getMealType());
                this.mainPageView.recipeList.getChildren().add(toAdd);
            }
            addListeners();
        } catch (Exception e) {
            e.printStackTrace();
            showNoServerAlert();
            return ;
        }
    }

    // This function adds listeners to all the title buttons in the recipe list
    // When a title button is clicked, the DetailView for that recipe is loaded
    public void addListeners(){
        for (int i = 0; i < this.mainPageView.recipeList.getChildren().size(); i++) {
            MainPageRecipeCard card = (MainPageRecipeCard) this.mainPageView.recipeList.getChildren().get(i);
            card.getDetailButton().setOnAction(e -> {
                DetailView details = new DetailView(mainPageModel.getRecipe(card.getTitle()));
                Main.sceneManager.ChangeScene(details);
            });
        }

        this.mainPageView.getAddButton().setOnAction(e -> {
            WhisperView whisperPageView = new WhisperView();
            Main.sceneManager.ChangeScene(whisperPageView);
        });

        this.mainPageView.getHeader().getSortDropDown().setOnAction(e -> {
            ComboBox<String> sortDropDown = this.mainPageView.getHeader().getSortDropDown();
            String selectedOption = sortDropDown.getValue();
            System.out.println("Selected Option: " + selectedOption);

            List<RecipeModel> sortedList = loadCurrentList();
            List<RecipeModel> dateList = loadCurrentList();

            for (int i = 0; i < sortedList.size(); i++) {
                System.out.println(sortedList.get(i).getTitle());
            }

            for (int i = 0; i < dateList.size(); i++) {
                System.out.println(dateList.get(i).getTitle());
            }

            Comparator<RecipeModel> titleComparator = Comparator.comparing(RecipeModel::getTitle);
            Comparator<RecipeModel> dateComparator = Comparator.comparing(RecipeModel::getIndex);

            dateList = sortedList.stream().collect(Collectors.toList());

            Collections.sort(sortedList, titleComparator);
            Collections.sort(dateList, dateComparator);

            switch (selectedOption) {
                case "A-Z":
                    showSortedRecipe(sortedList);
                    break;

                case "Z-A":
                    Collections.reverse(sortedList);
                    showSortedRecipe(sortedList);
                    break;
                
                case "Old-New":
                    showSortedRecipe(dateList);
                    break;
                
                case "New-Old":
                    Collections.reverse(dateList);
                    showSortedRecipe(dateList);
                    break;
            }
        });

        this.mainPageView.getHeader().getFilterDropDown().setOnAction(e -> {
            ComboBox<String> filterDropDown = this.mainPageView.getHeader().getFilterDropDown();
            String selectedOption = filterDropDown.getValue();
            System.out.println("Selected Option: " + selectedOption);
            List<RecipeModel> filteredList = mainPageModel.loadFromDatabase();
            mainPageView.getHeader().getSortDropDown().setValue("Sort");
            if (selectedOption.equals("AllFilter")) {
                showSortedRecipe(filteredList);
                return;
            };

            filteredList = filteredList.stream()
                .filter(recipe -> selectedOption.equals(recipe.getMealType()))
                .collect(Collectors.toList());


            showSortedRecipe(filteredList);
        });

    }

    public void showNoServerAlert (){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Server Down Error");
        alert.setHeaderText(null);
        alert.setContentText("Server could not be connected. Please try again later.");
        alert.showAndWait();
    }

    public void showSortedRecipe(List<RecipeModel> newRecipeList){
        this.mainPageView.recipeList.getChildren().clear();

        for(RecipeModel r: newRecipeList){
            MainPageRecipeCard toAdd = new MainPageRecipeCard(
                r.getTitle(), r.getMealType());
            this.mainPageView.recipeList.getChildren().add(toAdd);
        }
        addListeners();
    }

    public List<RecipeModel> loadCurrentList() {
        List<RecipeModel> newRecipeList = new ArrayList<RecipeModel>();
        for (int i = 0; i < this.mainPageView.recipeList.getChildren().size(); i++) {
            MainPageRecipeCard card = (MainPageRecipeCard) this.mainPageView.recipeList.getChildren().get(i);
            newRecipeList.add(mainPageModel.getRecipe(card.getTitle()));

        }
        return newRecipeList;
    }
}