package ru.dubograev.tests;

import com.codeborne.selenide.Condition;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Arrays;

import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;

public class SwipeTest extends TestBase{
    @Test
    @DisplayName("Swipe test")
    void swipe(AppiumDriver driver) {
        step("Check that text 'ВЕСЬ КАТАЛОГ В ТВОЁМ МОБИЛЬНОМ ПРИЛОЖЕНИИ' is displayed", () -> {
            $(AppiumBy.id("ru.sportmaster.app:id/textViewTitle")).shouldHave(Condition.text("ВЕСЬ КАТАЛОГ В ТВОЁМ МОБИЛЬНОМ ПРИЛОЖЕНИИ"));
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
            $(AppiumBy.id("ru.sportmaster.app:id/textViewTitle")).shouldHave(Condition.text("КОПИ БОНУСЫ И ОПЛАЧИВАЙ ИМИ ДО 30% ПОКУПКИ"));
        });
    }
}
