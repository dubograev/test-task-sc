package ru.dubograev.tests;

import com.codeborne.selenide.Condition;
import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;

public class SearchTest extends TestBase{
    @Test
    @DisplayName("Sorting test")
    void sortingTest() {
        step("Skip welcome screens", () -> {
            $(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.widget.ImageButton")).click();
            $(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.ImageButton")).click();
            $(AppiumBy.id("com.android.permissioncontroller:id/permission_allow_one_time_button")).click();
        });

        step("Select the city", () -> {
            $(AppiumBy.id("ru.sportmaster.app:id/editText")).click();
            $(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.ViewFlipper/android.widget.FrameLayout/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]")).click();
        });

        step("Search for a product", ()-> {
            $(AppiumBy.id("ru.sportmaster.app:id/editText")).click();
            $(AppiumBy.id("ru.sportmaster.app:id/editText")).sendKeys("reebok");
            $(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]/android.view.View")).click();
        });

        step("Switch the sorting to 'По возрастанию цены'", () -> {
            $(AppiumBy.id("ru.sportmaster.app:id/textViewSorting")).click();
            $(AppiumBy.className("androidx.recyclerview.widget.RecyclerView")).$$(AppiumBy.className("android.view.ViewGroup")).get(3).click();
        });

        step("Check that sorting button name changed to 'По возрастанию цены'", ()-> {
            $(AppiumBy.id("ru.sportmaster.app:id/textViewSorting")).shouldHave(Condition.text("По возрастанию цены"));
        });

        step("Check that first item has the minimum price", ()-> {
            $(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout[1]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ViewFlipper/android.widget.ScrollView/android.widget.ViewFlipper/android.view.ViewGroup/android.widget.FrameLayout/androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.TextView[2]")).shouldHave(Condition.text("999 ₽"));
        });

    }
}
