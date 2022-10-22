package config;

import org.aeonbits.owner.Config;

/**
 * Чтение ключей из test.properties
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties", //читаем env
        "file:src/test/resources/configs/test.properties", //читаем из файла
})
public interface TestConfig extends Config {
    @Key("updateScreenshots")
    @DefaultValue("false")
    boolean isScreenshotsNeedToUpdate();

    @Key("deviceHost")
    String deviceHost();
}
