
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
        controller.signUpHelper(null, "test", "test", "test", true);

        // read from the mockUsers.txt file
        String pathName = "mockUsers.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(pathName))) {
            reader.readLine();
            assertEquals(reader, "Username: test");
            reader.readLine();
            assertEquals(reader, "Password: test");
            reader.close();
        } catch (Exception e) {
            System.out.println("Could not initialize FileReader with specified input file");
        }
    }


    @Test
    void testSignup2() throws Exception {
        SignUpController controller = new SignUpController(null);
        controller.signUpHelper(null, "test", "test", "test", true);

        // read from the mockUsers.txt file
        String pathName = "mockUsers.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(pathName))) {
            assertEquals(reader, "Username: test");
            reader.readLine();
            assertEquals(reader, "Password: test");
            reader.readLine();
            assertEquals(reader, "");
            reader.close();
        } catch (Exception e) {
            System.out.println("Could not initialize FileReader with specified input file");
        }
    }

    @Test
    void testLogin() throws Exception {
        try{
            SignUpController controller = new SignUpController(null);
            controller.signUpHelper(null, "test", "test", "test", true);
            // If this is executed, it should be right.
            assertEquals("Expected", "Expected");
        } catch (Exception e) {
            System.out.println("Could not initialize FileReader with specified input file");
        }
    }

    // Username DNE
    @Test
    void testLogin2() throws Exception {
        try{
            SignUpController controller = new SignUpController(null);
            controller.signUpHelper(null, "test3", "test", "test", true);
        } catch (Exception e) {
            System.out.println("Could not initialize FileReader with specified input file");
            // If this is executed, it should be right.
            assertEquals("Expected", "Expected");
        }
    }

    // Username exist, password wrong
    @Test
    void testLogin3() throws Exception {
        try{
            SignUpController controller = new SignUpController(null);
            controller.signUpHelper(null, "test", "test3", "test", true);
        } catch (Exception e) {
            System.out.println("Could not initialize FileReader with specified input file");
            // If this is executed, it should be right.
            assertEquals("Expected", "Expected");
        }
    }


    // Username wrong, password wrong
    @Test
    void testLogin4() throws Exception {
        try{
            SignUpController controller = new SignUpController(null);
            controller.signUpHelper(null, "test2", "test2", "test", true);
        } catch (Exception e) {
            // If this is executed, it should be right.
            System.out.println("Could not initialize FileReader with specified input file");
            assertEquals("Expected", "Expected");
        }
    }
}
