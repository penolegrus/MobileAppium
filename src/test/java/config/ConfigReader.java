package config;

import org.aeonbits.owner.ConfigFactory;

/**
 * Класс для читалки файлов .properties
 */
public class ConfigReader {

    /**
     * Читалка для emulator.properties
     */
    public static final EmulatorConfig emulatorConfig = ConfigFactory.create(EmulatorConfig.class, System.getProperties());
    /**
     * Читалка для test.properties
     */
    public static final TestConfig testConfig = ConfigFactory.create(TestConfig.class, System.getProperties());
}
