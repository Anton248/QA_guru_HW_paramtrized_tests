package foxnews_tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

// Preparing browser for tests
public class TestBase {

    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://www.foxnews.com";  //site to test
        Configuration.browserSize = "1920x1080";
        //Configuration.holdBrowserOpen = true; // show browser to test the tests themselves

    }
}
