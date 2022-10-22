package listeners;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AllureListener implements AfterTestExecutionCallback {
    // Метод прикрепляет скриншот к отчёту allure.
    @Attachment(value = "Page screenshot", type = "image/png")
    public static byte[] saveScreenshot(byte[] screenShot) {
        return screenShot;
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        Method testMethod = context.getRequiredTestMethod();
        String testName = testMethod.getName();
        boolean testFailed = context.getExecutionException().isPresent();
        if (testFailed) {
            if (!testName.contains("Screenshot")) {
                saveScreenshot(Selenide.screenshot(OutputType.BYTES));
            }
        }
    }
}

