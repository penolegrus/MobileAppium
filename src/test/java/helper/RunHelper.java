package helper;

import driver.EmulatorDriver;

public class RunHelper {

    private RunHelper() {
    }

    public static RunHelper runHelper() {
        return new RunHelper();
    }

    public Class<?> getDriverClass() {
        String deviceHost = "emulator";

        switch (deviceHost) {
            case "browserstack":
            //    return BrowserstackMobileDriver.class;
            case "selenoid":
            //   return SelenoidMobileDriver.class;
            case "emulator":
                return EmulatorDriver.class;
            case "real":
            //    return RealMobileDriver.class;
            default:
                throw new RuntimeException("В файле конфигурации нет параметра deviceHost: " +
                        "browserstack/selenoid/emulator/real");
        }
    }
}
