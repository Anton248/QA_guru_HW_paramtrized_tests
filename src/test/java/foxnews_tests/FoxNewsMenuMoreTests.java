package foxnews_tests;

import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FoxNewsMenuMoreTests extends TestBase{

    //Проверка того, что элемент главного меню 'More' сайта Foxnews.com работает верно на подстраницах второго уровня:
    //если кликнуть на 'More', то раскрывшийся элемент должен содержать 12 видимых заголовков.
    //Url подстраницы является параметром
    //Тест кейс описан в файле resources/testcases/elementMoreWorksByQuantityOfHeads.txt
    @ValueSource(strings = {
            "/us",
            "/world",
            "/politics",
            "/health",
            "/tech",
            "/shows",
            "/entertainment"
    })
    @Tags({
            @Tag("CRITICAL"),
            @Tag("WEB"),
    })
    @ParameterizedTest(name = "Если кликнуть позицию меню more на странице {0}, то раскроется элемент, содержащий 12 видимых заголовков h6")
    void elementMoreWorksByQuantityOfHeads(String urlOfPage){
        int quantityOfHeaders  = 12;
        open(urlOfPage);
        $(".menu-more").$(byText("More")).click();
        ElementsCollection collectionOfHeaders = $("div.expandable-nav").$$("h6").filter(visible);
        assertEquals(quantityOfHeaders, collectionOfHeaders.size());
    }

    //Аналогично предыдущему тесту, но теперь проверяем не количество заголовков, а наличие названий заголовков
    //Проверка того, что элемент главного меню 'More' работает верно на подстраницах второго уровня: если кликнуть
    //на 'More', то раскрывшийся элемент должен содержать определнные заголовки.
    // Url страницы является параметром, названия заголовков являются параметром.
    static Stream<Arguments> elementMoreWorksByNamesOfHeads() {
        List listOfExpectedHeads = List.of("U.S.", "World", "Politics", "Entertainment", "Business", "Lifestyle",
                "Science", "Tech", "Health", "TV", "About", "Other");
        return Stream.of(
                Arguments.of(
                        "/us", listOfExpectedHeads
                ),
                Arguments.of(
                        "/world", listOfExpectedHeads
                ),
                Arguments.of(
                        "/politics", listOfExpectedHeads
                ),
                Arguments.of(
                        "/health", listOfExpectedHeads
                ),
                Arguments.of(
                        "/tech", listOfExpectedHeads
                ),
                Arguments.of(
                        "/entertainment", listOfExpectedHeads
                )
        );
    }
    @MethodSource()
    @Tags({
            @Tag("CRITICAL"),
            @Tag("WEB"),
    })
    @ParameterizedTest(name = "Если кликнуть позицию меню more на странице {0}, то раскроется элемент, содержащий список заголовков {1}")
    void elementMoreWorksByNamesOfHeads(String urlOfPage, List<String> expectedHeads){
        open(urlOfPage);
        $(".menu-more").$(byText("More")).click();
        ElementsCollection collectionOfHeaders = $("div.expandable-nav").$$("h6").filter(visible).
                shouldHave(texts(expectedHeads));
    }


    //Тестирование всех ссылок секции меню U.S., которое появляется после раскрытия элемента главного меню More.
    //Ссылки тестируются путем проверки наличия нужного заголовка в открывшейся странице после клика на ссылку.
    //Элементы секции и заголовки являются параметрами.
    //Тест кейс описан в файле resources/testcases/allLinksOfUSMenuWorks.txt
    @CsvFileSource(resources = "/moreMenuData.csv", numLinesToSkip = 2)
    @Tags({
            @Tag("CRITICAL"),
            @Tag("WEB"),
    })
    @ParameterizedTest(name = "Если кликнуть позицию {0} в секции меню More/U.S, то откроется страница, содержащая заголовок {1}")
    void allLinksOfUSMenuWorks(String menuItem, String expectedHead) {
        open("/");
        $(".menu-more").$(byText("More")).click();
        $("div.expandable-nav").$$("h4.nav-title").findBy(exactText("U.S.")).$(byLinkText("U.S.")).
                closest("h4").sibling(0).$(byLinkText(menuItem)).click();
        $(byTagAndText("h1", expectedHead)).shouldBe(visible);
    }

}
