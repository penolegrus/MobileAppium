package driver;

import com.codeborne.selenide.WebDriverProvider;

import config.ConfigReader;
import helper.ApkInfoHelper;
import io.appium.java_client.android.AndroidDriver;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.annotation.Nonnull;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Класс для инициализации AndroidDriver
 */
public class EmulatorDriver implements WebDriverProvider {
    protected static AndroidDriver driver;
    //чтение пропертей
    private static final String DEVICE_NAME = ConfigReader.emulatorConfig.deviceName();
    private static final String PLATFORM_NAME = ConfigReader.emulatorConfig.platformName();
    private static String APP_PACKAGE = ConfigReader.emulatorConfig.appPackage();
    private static String APP_ACTIVITY = ConfigReader.emulatorConfig.appActivity();
    private static final String APP = ConfigReader.emulatorConfig.app();
    private static final String URL = ConfigReader.emulatorConfig.remoteURL();

    /**
     * Валидация URL ссылки из пропертей
     * @return
     */
    public static URL getUrl() {
        try {
            return new URL(URL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получаем абсолютный путь от рутового путя
     * @param filePath путь к файлу из корня прокта
     * @return
     */
    private String getAbsolutePath(String filePath) {
        File file = new File(filePath);
        assertTrue(file.exists(), filePath + " not found");//проверяем что файл существует

        return file.getAbsolutePath();
    }

    /**
     * Получаем AppPackage и AppActivity из чтения apk файла
     */
    private void initPackageAndActivity() {
        ApkInfoHelper helper = new ApkInfoHelper();
        //тернарное условие, если app_package не задано в пропертях, достаем из из apk
        APP_PACKAGE = APP_PACKAGE.isEmpty() ? helper.getAppPackageFromApk() : APP_PACKAGE;
        APP_ACTIVITY = APP_ACTIVITY.isEmpty() ? helper.getAppMainActivity() : APP_ACTIVITY;
    }


    /**
     * Создает appium сессиюю AndroidDriver
     * @param desiredCapabilities настройки для создания сесии
     * @return сессия AndroidDriver
     */
    @Nonnull
    @Override
    public WebDriver createDriver(DesiredCapabilities desiredCapabilities) {
        initPackageAndActivity();
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS,true);
        desiredCapabilities.setCapability("deviceName", DEVICE_NAME);
        desiredCapabilities.setCapability("platformName", PLATFORM_NAME);

        desiredCapabilities.setCapability("appPackage", APP_PACKAGE);
        desiredCapabilities.setCapability("appActivity", APP_ACTIVITY);

        desiredCapabilities.setCapability("app", getAbsolutePath(APP));
         driver = new AndroidDriver<>(getUrl(), desiredCapabilities);
         return driver;
    }
}
