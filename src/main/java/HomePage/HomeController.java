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
                MainPageRecipeCard toAdd = new MainPageRecipeCard(r.getTitle());
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

            List<RecipeModel> sortedList = mainPageModel.loadFromDatabase();
            List<RecipeModel> dateList = new ArrayList<RecipeModel>();
            Comparator<RecipeModel> titleComparator = Comparator.comparing(RecipeModel::getTitle);
            Comparator<RecipeModel> dateComparator = Comparator.comparing(RecipeModel::getIndex);

            for (int i = 0; i < sortedList.size(); i++) {
                String[] parts = sortedList.get(i).getTitle().split(": ");
                sortedList.get(i).setTitle(parts[1]);
                sortedList.get(i).setMealType(parts[0]);
            }
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
                    Collections.reverse(dateList);
                    showSortedRecipe(dateList);
                    break;
                
                case "New-Old":
                    showSortedRecipe(dateList);
                    break;
            }
        });

        this.mainPageView.getHeader().getFilterDropDown().setOnAction(e -> {
            ComboBox<String> filterDropDown = this.mainPageView.getHeader().getFilterDropDown();
            String selectedOption = filterDropDown.getValue();
            System.out.println("Selected Option: " + selectedOption);
            List<RecipeModel> filteredList = mainPageModel.loadFromDatabase();
        
            for (int i = 0; i < filteredList.size(); i++) {
                String[] parts = filteredList.get(i).getTitle().split(": ");
                System.out.println("parts: ");
                System.out.println(parts[0]);                
                System.out.println(parts[1]);
                filteredList.get(i).setTitle(parts[1]);
                filteredList.get(i).setMealType(parts[0]);
            }

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
                r.getMealType() + ": " + r.getTitle());
            this.mainPageView.recipeList.getChildren().add(toAdd);
        }
        addListeners();
    }
}