package listeners;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;

import java.lang.reflect.Method;

/**
 * Кастомный листенер для переопределения логики завершения теста
 */
public class AllureListener implements AfterTestExecutionCallback {
    /**
     * Метод добавления скриншота в аллюра отчета через аннотацию
     * @param screenShot байты скриншотов
     * @return
     */
    @Attachment(value = "Page screenshot", type = "image/png")
    public static byte[] saveScreenshot(byte[] screenShot) {
        return screenShot;
    }

    /**
     * Переопределение логики завершения тестов у juni5
     * @param context контекст теста
     */
    @Override
    public void afterTestExecution(ExtensionContext context) {
        Method testMethod = context.getRequiredTestMethod(); //получаем тестовый метод
        String testName = testMethod.getName(); //получаем название тестового метода
        boolean testFailed = context.getExecutionException().isPresent();//проверяем упал ли тест
        if (testFailed) {//если тест упал
            if (!testName.contains("Screenshot")) {//если название метода не содержит Screenshot
                saveScreenshot(Selenide.screenshot(OutputType.BYTES)); //добавляем скриншот к упавшему тесту
            }
        }
    }
}

