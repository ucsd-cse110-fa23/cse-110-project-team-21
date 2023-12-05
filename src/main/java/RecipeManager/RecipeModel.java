package RecipeManager;

import java.util.Objects;

/**
 * TODO: Decide if the images are gonna have fixed, uniform dimensions or variable ones which cap at a max and min value
 */
public class RecipeModel {
    private static final String DEFAULT_IMAGE = "resources/previewimgs/notfound.png";
    // each instance of this class represents a recipe that has been saved
    private String title;
    private String description;
    private String mealType;
    private int index;
    private String previewImgPath;
    private String imgURL;

    public RecipeModel(String title, String description) {
        this.title = title;
        this.description = description;
        this.mealType = mealType;
        this.previewImgPath = DEFAULT_IMAGE;
        //this.mealType = mealType;
    }

    public RecipeModel(String title, String description, String previewImgPath) {
        this.title = title;
        this.description = description;
        this.previewImgPath = previewImgPath;
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

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public String getPreviewImgPath(){
        return this.previewImgPath;
    }

    public void setPreviewImage(String previewImgPath){
        this.previewImgPath = previewImgPath;
    }

    public void setImageURL(String url) {
        this.imgURL = url;
    }

    public String getImageURL() {
        return this.imgURL;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RecipeModel recipe = (RecipeModel) obj;
        return Objects.equals(getTitle(), recipe.getTitle());
    }
}
