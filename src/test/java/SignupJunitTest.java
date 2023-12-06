
import org.junit.jupiter.api.Test;


import SignUpPage.SignUpController;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;



public class SignupJunitTest {

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
}
