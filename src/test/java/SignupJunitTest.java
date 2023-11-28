
import org.junit.jupiter.api.Test;


import SignUpPage.SignUpController;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;



public class SignupJunitTest {

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
            reader.readLine();
            reader.readLine();
            reader.readLine();
            assertEquals(reader, "Username: test");
            reader.readLine();
            assertEquals(reader, "Password: test");
            reader.close();
        } catch (Exception e) {
            System.out.println("Could not initialize FileReader with specified input file");
        }
    }
}
