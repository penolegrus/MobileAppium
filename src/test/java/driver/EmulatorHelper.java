package driver;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

/**
 * Класс помощник для Page страниц
 */
public class EmulatorHelper extends EmulatorDriver{

    /**
     * Нажимает кнопку назад
     */
    public static void goBack(){
        driver.navigate().back();
    }

    /**
     * Листает к элементу по его тексту
     * @param text текст на элементе
     */
    public static void androidScrollToAnElementByText(String text){
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)" +
                        ".instance(0)).scrollIntoView(new UiSelector().textContains(\""+text+"\").instance(0))")
                .click();
    }

    /**
     * Закрывает клавиатуру если она есть
     */
    public static void closeKeyBoard(){
        if(driver.isKeyboardShown()){
            driver.hideKeyboard();
        }
    }

    /**
     * Вводит текст и нажимает Enter
     * @param element поле для ввода
     * @param text текст
     */
    public static void sendKeysAndFind(SelenideElement element, String text){
        element.sendKeys(text);
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
    }

}
