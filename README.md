# Group 21 PantryPal Application Guide

1. Download entire project folder from GitHub.
2. Open the project folder in an environment (such as an IDE or terminal) where you can create files and run a .java file.
3. Create a launch.json file that launches the file Main.java using the following configuration:
    ```
        {
            "type": "java",
            "name": "Main",
            "request": "launch",
            "mainClass": "UI.MainPage.Main",
            "vmArgs": "--module-path '<path to your local javafx lib folder>' --add-modules javafx.controls,javafx.fxml"
        }
    ```
4. Run Main.java
5. Enjoy PantryPal! 
