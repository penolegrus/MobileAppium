package pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static driver.EmulatorHelper.androidScrollToAnElementByText;
import static driver.EmulatorHelper.goBack;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import java.util.List;

import io.appium.java_client.MobileBy;
import io.qameta.allure.Step;


/**
 * Главная страница приложения
 */
public class MainPage extends SearchPage {

    private SelenideElement catalogButton = $(MobileBy.id("menu_catalog"));
    private ElementsCollection categories = $$(MobileBy.id("titleTv"));
    private SelenideElement mainMenuIcon = $(MobileBy.id("menu_main"));
    private SelenideElement adPopUp = $(MobileBy.id("commonBtnSA"));


    @Step("Открываем каталог в нижнем Меню")
    public CatalogPage openCatalog() {
        catalogButton.should(Condition.visible).click();
        return new CatalogPage();
    }

    @Step("Закрываем открывшееся всплывающее окно")
    public MainPage closePopUp() {
        if (adPopUp.exists()) {
            goBack();
        }
        return this;
    }

    @Step("Проверяем названия категорий в центре экрана {texts}")
    public MainPage checkCategoriesHasTexts(List<String> texts) {
        androidScrollToAnElementByText(texts.get(0));
        categories.should(CollectionCondition.size(10));
        categories.should(CollectionCondition.textsInAnyOrder(texts));
        return this;
    }

    @Step("Проверяем, что кнопка меню 'Главное Меню' выделена синим цветом")
    public void checkMainMenuButtonIsSelected() {
        mainMenuIcon.should(Condition.selected);
    }

    @Step("Проверяем, что кнопка меню 'Главное Меню' не выделена синим цветом")
    public void checkMainMenuButtonIsNotSelected() {
        mainMenuIcon.should(Condition.not(Condition.selected));
    }
}
