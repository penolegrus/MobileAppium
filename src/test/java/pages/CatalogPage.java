package pages;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import io.appium.java_client.MobileBy;
import io.qameta.allure.Step;

/**
 * Страница Каталог
 */
public class CatalogPage extends SearchPage {

    private SelenideElement catalogMenuIcon = $(MobileBy.id("menu_catalog"));

    @Step("Проверяем, что кнопка меню 'Каталог' выделена синим цветом")
    public void isMainMenuIconSelected(){
        catalogMenuIcon.should(Condition.selected);
    }
}
