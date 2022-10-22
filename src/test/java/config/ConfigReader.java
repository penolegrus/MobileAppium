package config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigReader {

    public static final EmulatorConfig emulatorConfig = ConfigFactory.create(EmulatorConfig.class, System.getProperties());
    public static final TestConfig testConfig = ConfigFactory.create(TestConfig.class, System.getProperties());
}
