package pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static driver.EmulatorHelper.goBack;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import org.junit.jupiter.api.Assertions;

import io.appium.java_client.MobileBy;
import io.qameta.allure.Step;

/**
 * Страница Корзина
 */
public class CartPage extends BasePage {

    private SelenideElement cartMenuIcon = $(MobileBy.id("menu_profile"));

    @Step("Открываем меню корзина")
    public CartPage openCartPage(){
        cartMenuIcon.should(Condition.visible).click();
        cartMenuIcon.should(Condition.selected);
        return this;
    }

    @Step("Проверяем количество товаров в корзине")
    public CartPage assertItemsCount(Integer count){
        Assertions.assertTrue(cartMenuIcon.getAttribute("content-desc").contains(String.valueOf(count)));
        return this;
    }

    @Step("Проверяем добавленный товар в корзину - {item}")
    public CartPage assertItemNameInCart(String item){
        SelenideElement itemInCart = $x(String.format("//*[@text='%s']",item));
        itemInCart.should(Condition.exist);
        return this;
    }

    @Step("Закрываем всплывающее окно")
    public CartPage closePop(){
        if($(MobileBy.id("commonBtnSA")).exists()){
            goBack();
        }
        return this;
    }
}
