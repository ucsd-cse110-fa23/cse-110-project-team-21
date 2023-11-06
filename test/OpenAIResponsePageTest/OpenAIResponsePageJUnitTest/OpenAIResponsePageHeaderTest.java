
package test.OpenAIResponsePageTest.OpenAIResponsePageJUnitTest;

import org.junit.*;

import OpenAIResponsePage.OpenAIResponsePageHeader;

public class OpenAIResponsePageHeaderTest {

    @Test
    public void testHeader() {
        OpenAIResponsePageHeader header = new OpenAIResponsePageHeader("My Recipe Title");
        Assert.assertEquals(header.getTitleText().getText(), "My Recipe Title");
    }   
}
