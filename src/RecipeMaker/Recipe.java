package RecipeMaker;

public class Recipe {
    private String title;
    private String description;

    Recipe(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String newDesc) {
        this.description = newDesc;
    }
}
