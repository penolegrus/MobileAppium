package helper;

import config.ConfigReader;
import driver.EmulatorDriver;

/**
 * Класс помощник для создания универсального инстанса AndroidDriver
 */
public class RunHelper {

    private RunHelper() {
    }

    /**
     * Статичный консртуктор для вызова методов из класса без создания экземпляра
     * @return
     */
    public static RunHelper runHelper() {
        return new RunHelper();
    }

    /**
     * Реализуем AndroidDriver через кастомные классы для каждого варианта девайса
     * @return
     */
    public Class<?> getDriverClass() {
        String deviceHost = ConfigReader.testConfig.deviceHost();

        switch (deviceHost) {
            case "browserstack":
            //    return BrowserstackMobileDriver.class; можно создать этот класс и реализовать доп логику для BrowserStack
            case "selenoid":
            //   return SelenoidMobileDriver.class; можно создать этот класс и реализовать доп логику для Selenoid и многопоточности
            case "emulator":
                return EmulatorDriver.class; //класс для инициализации сессии для эмулятора
            case "real":
            //    return RealMobileDriver.class; можно создать этот класс и реализовать логику для реальных девайсов (доп поля нужны)
            default:
                throw new RuntimeException("В файле конфигурации нет параметра deviceHost: " +
                        "browserstack/selenoid/emulator/real");
        }
    }
}
