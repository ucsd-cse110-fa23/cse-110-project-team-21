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
        },
        {
            "type": "java",
            "name": "Server",
            "request": "launch",
            "mainClass": "server.Server",
            "vmArgs": "--module-path '<path to your local javafx lib folder>' --add-modules javafx.controls,javafx.fxml"
        }
    ```
4. Run Server.java
5. Run Main.java
6. Enjoy PantryPal!

This is the link to our GitHub: [https://github.com/ucsd-cse110-fa23/cse-110-project-team-21/tree/MS2-Demo]
