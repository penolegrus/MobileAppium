package tests;

import pages.MainPage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.io.File;

public class ScreenshotTests extends BaseTest{

    private TestInfo testInfo;

    @BeforeEach
    public void init(TestInfo testInfo) {
        this.testInfo = testInfo;
    }

    @Test
    public void testMainCatalogScreenshot(){
        File mainScreenScreenshot = new MainPage().openCatalog().fullPageScreenshot();
        assertScreenshot(mainScreenScreenshot, testInfo.getDisplayName());
    }

    @Test
    public void testMainScreenScreenshotFail(){
        File mainScreenScreenshot = new MainPage().fullPageScreenshot();
        assertScreenshot(mainScreenScreenshot, "testMainCatalogScreenshot()");
    }
}

