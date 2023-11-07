
package test.java.OpenAIResponsePageTest.OpenAIResponsePageJUnitTest;

import org.junit.*;

import main.java.OpenAIResponsePage.OpenAIResponsePageHeader;

public class OpenAIResponsePageHeaderTest {

    @Test
    public void testHeader() {
        OpenAIResponsePageHeader header = new OpenAIResponsePageHeader("My Recipe Title");
        Assert.assertEquals(header.getTitleText().getText(), "My Recipe Title");
    }   
}
