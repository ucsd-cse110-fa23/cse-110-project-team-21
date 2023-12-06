import org.junit.jupiter.api.Test;

import LoginPage.MockLoginController;
import NewRecipePage.GPTModel;
import RecipeManager.MockRecipeManagerModel;
import WhisperPage.WhisperModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EndtoEndOneTest {
    MockRecipeManagerModel manager;
    WhisperModel whisper;
    GPTModel model;

    @Test
    void EndtoEndScenario() {
        /**
         * Start the app. You should be on the login screen. Enter in the 
         * username and password of your account and click “Login”. 
         * If the server is down, you will end up on a page with an error message 
         * telling you that the server is not functioning.
         */
        
        MockLoginController loginController = new MockLoginController(false);
        //MockLoginController loginController = new MockLoginController(false);
        String result = loginController.result;
        assertEquals("Alert: Server could not be connected. Please try again later.", result);
        /**
         * Exit the app.
         */
    }
}
