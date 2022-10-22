package pages;

import static com.codeborne.selenide.Selenide.$;

import static driver.EmulatorHelper.closeKeyBoard;
import static driver.EmulatorHelper.sendKeysAndFind;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import io.appium.java_client.MobileBy;
import io.qameta.allure.Step;

/**
 * Страница с поиском
 */
public class SearchPage extends BasePage {
    private SelenideElement searchField = $(MobileBy.id("searchTv"));
    private SelenideElement searchFieldExpanded = $(MobileBy.id("search_src_text"));
    private SelenideElement tempItem;


    @Step("Поиск товара {item}")
    public SearchPage search(String item) {
        searchField.should(Condition.visible).click();
        sendKeysAndFind(searchFieldExpanded, item);
        closeKeyBoard();
        return this;
    }

    @Step("Получаем название первого найденного товара")
    public String getFirstFoundedFullItemName() {
        tempItem = $(MobileBy.xpath("(//android.widget.TextView[@content-desc=\"tile-name\"])[1]"));
        return tempItem.getText();
    }

    @Step("Открываем первый найденный товар")
    public ItemPage openFirstFoundItemByTextContains() {
        tempItem.click();
        return new ItemPage();
    }


}
