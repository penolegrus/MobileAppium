package tests;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;

import config.ConfigReader;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import listeners.AllureListener;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.StandardCopyOption;

import static helper.Constants.SCREENSHOT_TO_SAVE_FOLDER;
import static helper.DeviceHelper.executeBash;
import static helper.RunHelper.runHelper;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Базовый тестовый класс
 */
@ExtendWith(AllureListener.class)//расширяем тестовый класс
public class BaseTest {

    @BeforeAll
    public static void setup() {
        //добавляем логирование действий для аллюр отчета в виде степов
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        // папка для сохранения скриншотов selenide
        Configuration.reportsFolder = SCREENSHOT_TO_SAVE_FOLDER;
        //инициализируем андройд драйвер
        Configuration.browser = runHelper().getDriverClass().getName();
        Configuration.startMaximized = false;
        Configuration.browserSize = null;
        Configuration.timeout = 10000;
        disableAnimationOnEmulator();
    }

    /**
     * Отключение анимаций на эмуляторе чтобы не лагало
     */
    private static void disableAnimationOnEmulator() {
        executeBash("adb -s shell settings put global transition_animation_scale 0.0");
        executeBash("adb -s shell settings put global window_animation_scale 0.0");
        executeBash("adb -s shell settings put global animator_duration_scale 0.0");
    }

    /**
     * Проверка скриншота с эталоном для проверки верстки
     * @param actualScreenshot актуальный скриншот
     * @param expectedFileName название файла для сравнений
     */
    public void assertScreenshot(File actualScreenshot, String expectedFileName) {
        //в метод передается всегда название тестового метода, поэтому меняем скобки на файл с расширением для дальнейшего сохранения
        expectedFileName = expectedFileName.replace("()", ".png");
        //папка для хранения эталонных скриншотов
        String expectedScreensDir = "src/test/resources/expectedScreenshots/";
        //если скрншоты надо обновить
        if (ConfigReader.testConfig.isScreenshotsNeedToUpdate()) {
            try {
                //перемещаем текущий скрншот в папку с эталоном и заменяем файл
                Files.move(actualScreenshot.toPath(), new File(expectedScreensDir + expectedFileName).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //завершаем метод
            return;
        }

        //если скриншот надо сравнить
        // Загружаем ожидаемое изображения для сравнения.
        BufferedImage expectedImage = ImageComparisonUtil
                .readImageFromResources(expectedScreensDir + expectedFileName);

        // Загружаем актуальный скриншот.
        BufferedImage actualImage = ImageComparisonUtil
                .readImageFromResources(SCREENSHOT_TO_SAVE_FOLDER + actualScreenshot.getName());

        // Где будем хранить скриншот с различиями в случае падения теста.
        File resultDestination = new File("diff/diff_" + expectedFileName);

        // Сравниваем.
        ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage, resultDestination)
                .compareImages();

        //если скриншоты отличаются
        if (imageComparisonResult.getImageComparisonState().equals(ImageComparisonState.MISMATCH)) {
            try {
                //добавляем скриншот с отличиями к аллюр отчету в виде степа
                byte[] diffImageBytes = Files.readAllBytes(resultDestination.toPath());
                AllureListener.saveScreenshot(diffImageBytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        //сравниваем скриншоты
        assertEquals(ImageComparisonState.MATCH, imageComparisonResult.getImageComparisonState());
    }

    /**
     * Перед каждый тестом открываем приложение
     */
    @BeforeEach
    public void startDriver() {
        step("Открыть приложение", (Allure.ThrowableRunnableVoid) Selenide::open);
    }

    /**
     * После каждого теста закрываем AndroidDriver чтобы тест атомарным был
     */
    @AfterEach
    public void afterEach() {
        step("Закрыть приложение", Selenide::closeWebDriver);
    }


}
