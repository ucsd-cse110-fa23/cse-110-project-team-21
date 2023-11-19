package RecipeLogic;

import java.util.Objects;

public class Recipe {
    // each instance of this class represents a recipe that has been saved
    private String title;
    private String description;
    private String mealType;

    public Recipe(String title, String description) {
        this.title = title;
        this.description = description;
        //this.mealType = mealType;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String newDesc) {
        this.description = newDesc;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getMealType() {
        return this.mealType;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Recipe recipe = (Recipe) obj;
        return Objects.equals(getTitle(), recipe.getTitle());
    }
}
