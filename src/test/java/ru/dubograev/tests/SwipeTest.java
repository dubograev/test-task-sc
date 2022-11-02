package ru.dubograev.tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import static io.qameta.allure.Allure.step;
import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

public class SwipeTest {
    AppiumDriver driver;

    @BeforeEach
    public void setup(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        File app = getApp();

        UiAutomator2Options options = new UiAutomator2Options();
        options.merge(capabilities);
        options.setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
        options.setPlatformName("Android");
        options.setDeviceName("Pixel 2 XL API 30");
        options.setPlatformVersion("11.0");
        options.setApp(app.getAbsolutePath());
        options.setAppPackage("ru.sportmaster.app");
        options.setAppActivity("ru.sportmaster.app.presentation.start.StartActivity");

        try {
            driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub/"), options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(6000, TimeUnit.MILLISECONDS);
    }

    @Test
    @DisplayName("Swipe test")
    void horizontalScroll() {
        step("Check that text 'ВЕСЬ КАТАЛОГ В ТВОЁМ МОБИЛЬНОМ ПРИЛОЖЕНИИ' is displayed", () -> {
            WebElement element = driver.findElement(AppiumBy.id("ru.sportmaster.app:id/textViewTitle"));
            Assertions.assertThat(element.getText()).isEqualTo("ВЕСЬ КАТАЛОГ В\n" +
                    "ТВОЁМ МОБИЛЬНОМ\n" +
                    "ПРИЛОЖЕНИИ");
        });

        step("Swipe the screen from right to the left", () -> {
            WebElement element = driver.findElement(AppiumBy.id("ru.sportmaster.app:id/viewPager"));
            int centerY = element.getRect().y + (element.getSize().height/2);
            double startX = element.getRect().x + (element.getSize().width * 0.9);
            double endX = element.getRect().x + (element.getSize().width * 0.1);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);
            swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), (int) startX, centerY));
            swipe.addAction(finger.createPointerDown(0));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), (int) endX, centerY));
            swipe.addAction(finger.createPointerUp(0));
            driver.perform(Arrays.asList(swipe));
        });

        step("Check that text 'КОПИ БОНУСЫ И ОПЛАЧИВАЙ ИМИ ДО 30% ПОКУПКИ' is displayed", () -> {
            WebElement element = driver.findElement(AppiumBy.id("ru.sportmaster.app:id/textViewTitle"));
            Assertions.assertThat(element.getText()).isEqualTo("КОПИ БОНУСЫ И\n" +
                    "ОПЛАЧИВАЙ ИМИ ДО\n" +
                    "30% ПОКУПКИ");
        });
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
    }

    private File getApp() {
        String appUrl = "https://github.com/dubograev/test-task-sc/blob/master/build/resources/test/app/sportm.apk";
        String appPath = "src/test/resources/app/sportm.apk";

        File app = new File(appPath);
        if (!app.exists()) {
            try (InputStream in = new URL(appUrl).openStream()) {
                copyInputStreamToFile(in, app);
            } catch (IOException e) {
                throw new AssertionError("Failed to download application", e);
            }
        }
        return app;
    }
}
