
import org.junit.jupiter.api.Test;

import LoginPage.LoginController;
import SignUpPage.SignUpController;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;




public class LoginJunitTest {
    
    @Test
    void testSignup() throws Exception {
        SignUpController controller = new SignUpController(null);
        controller.signUpHelper(null, "testusername", "testpassword", "testpassword", true);

        // read from the mockUsers.txt file
        String pathName = "mockUsers.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(pathName))) {
            String temp = reader.readLine();
            assertEquals(temp, "testusername");
            temp = reader.readLine();
            assertEquals(temp, "testpassword");
            reader.close();
        } catch (Exception e) {
            System.out.println("Could not initialize FileReader with specified input file");
        }
    }

    @Test
    void testSignup2() throws Exception {
        SignUpController controller = new SignUpController(null);
        controller.signUpHelper(null, "testusername2", "testpassword2", "testpassword2", true);

        // read from the mockUsers.txt file
        String pathName = "mockUsers.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(pathName))) {
            String temp = reader.readLine();
            assertEquals(temp, "testusername2");
            temp = reader.readLine();
            assertEquals(temp, "testpassword2");
            temp = reader.readLine();
            assertEquals(temp, null);
            reader.close();
        } catch (Exception e) {
            System.out.println("Could not initialize FileReader with specified input file");
        }
    }

    // Username exists, password correct
    @Test
    void testLogin() throws Exception {
        try{
            LoginController loginController = new LoginController(null);
            loginController.login(null, "testusername", "testpassword",false, true );
            assertEquals("Expected", "Expected");
        } catch (Exception e) {
            System.out.println("Could not initialize FileReader with specified input file");
            assertEquals("Expected", "NonExpected");
        }
    }

    // Username DNE
    @Test
    void testLogin2() throws Exception {
       try{
            LoginController loginController = new LoginController(null);
            loginController.login(null, "testwrong", "test",false, true );
        } catch (Exception e) {
            assertEquals("Expected", "Expected");
        }
    }

    // Username exist, password wrong
    @Test
    void testLogin3() throws Exception {
       try{
            LoginController loginController = new LoginController(null);
            loginController.login(null, "test", "testwrong",false, true );
        } catch (Exception e) {
            assertEquals("Expected", "Expected");
        }
    }


    // Username wrong, password wrong
    @Test
    void testLogin4() throws Exception {
       try{
            LoginController loginController = new LoginController(null);
            loginController.login(null, "testwrong", "testwrong",false, true );
        } catch (Exception e) {
            assertEquals("Expected", "Expected");
        }
    }
}
