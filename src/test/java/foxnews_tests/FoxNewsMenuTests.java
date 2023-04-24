package foxnews_tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class FoxNewsMenuTests extends TestBase{

    //Тестирование всех элементов секции меню U.S. (после раскрытия элемента главного меню More)
    //путем проверки наличия нужного заголовка в открывшейся странице
    @CsvFileSource(resources = "/moreMenuData.csv", numLinesToSkip = 2)
    @Tags({
            @Tag("CRITICAL"),
            @Tag("WEB"),
    })
    @ParameterizedTest(name = "Если кликнуть позицию {0} в меню more/U.S, то откроется страница, содержащая заголовок {1}")
    void allItemsOfUSMenuWorks(String menuItem, String head) {
        open("https://www.foxnews.com/");
        $(".menu-more").$(byText("More")).click();
        $(".section-nav").$(byLinkText("U.S.")).closest("h4").sibling(0).$(byLinkText(menuItem)).click();
        $(byTagAndText("h1", head)).shouldBe(visible);
    }
}
