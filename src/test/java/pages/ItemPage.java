package pages;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;
import io.qameta.allure.Step;

/**
 * Страница описания товара
 */
public class ItemPage extends SearchPage {

    private SelenideElement addToCartBtn = $(MobileBy.id("mainBtn"));

    @Step("Добавляем товар в корзину")
    public ItemPage addToCart(){
        addToCartBtn.should(Condition.visible).click();
        return this;
    }

    @Step("Открываем карзину")
    public CartPage openCart(){
        return new CartPage().openCartPage();
    }
}
