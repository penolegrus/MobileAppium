package pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static helper.LocatorHelper.byResourceId;
import static helper.LocatorHelper.byText;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;


import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.touch.TouchActions;

import java.io.File;
import java.util.List;

import driver.EmulatorDriver;
import helper.LocatorHelper;
import io.appium.java_client.MobileBy;
import io.qameta.allure.Step;


public class MainPage extends SearchPage {

    private SelenideElement catalogButton = $(MobileBy.id("menu_catalog"));
    private ElementsCollection categories = $$(MobileBy.id("titleTv"));
    private SelenideElement mainMenuIcon = $(MobileBy.id("menu_main"));
    private SelenideElement adPopUp = $(MobileBy.id("commonBtnSA"));


    @Step("Открываем каталог в нижнем Меню")
    public CatalogPage openCatalog(){
        catalogButton.should(Condition.visible).click();
        return new CatalogPage();
    }

    @Step("Закрываем открывшееся всплывающее окно")
    public MainPage closePopUp(){
        adPopUp.should(Condition.visible);
        WebDriverRunner.getWebDriver().navigate().back();
        return this;
    }

    @Step("Проверяем названия категорий в центре экрана {texts}")
    public MainPage checkCategoriesHasTexts(List<String> texts){
        LocatorHelper.androidScrollToAnElementByText(texts.get(0));
        categories.should(CollectionCondition.size(10));
        categories.should(CollectionCondition.textsInAnyOrder(texts));
        return this;
    }

    @Step("Проверяем, что кнопка меню 'Главное Меню' выделена синим цветом")
    public void checkMainMenuButtonIsSelected(){
         mainMenuIcon.should(Condition.selected);
    }

    @Step("Проверяем, что кнопка меню 'Главное Меню' не выделена синим цветом")
    public void checkMainMenuButtonIsNotSelected(){
        mainMenuIcon.should(Condition.not(Condition.selected));
    }
}
